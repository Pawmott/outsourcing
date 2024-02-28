package com.sparta.outsourcing.domain.member.repository.member;

import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.domain.member.model.entity.History;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.history.HistoryJpaRepository;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdatePasswordDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberJpaRepository memberJpaRepository;
  private final HistoryJpaRepository historyJpaRepository;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() {
    MemberEntity member = MemberEntity.builder()
        .email("admin@naver.com")
        .nickname("관리자")
        .password(passwordEncoder.encode("Test1234"))
        .address("테스트 주소")
        .number("010-0000-0000")
        .role(MemberRole.ADMIN)
        .build();

    if (!checkEmail(member.getEmail())) {
      memberJpaRepository.save(member);
    }
  }

  @Override
  public void signIn(MemberSignupDto dto) {
    memberJpaRepository.save(MemberEntity.of(dto.getEmail(),
        dto.getNickname(), passwordEncoder.encode(dto.getPassword()),
        dto.getAddress(), dto.getNumber()));
  }

  @Override
  public void updateMember(UpdateDto dto, Long memberId) {
    MemberEntity memberEntity = memberJpaRepository.findById(memberId).orElseThrow(
        () -> new EntityNotFoundException("해당 유저의 정보가 존재하지 않습니다.")
    );

    if (!passwordEncoder.matches(dto.getPassword(), memberEntity.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    memberEntity.updateMember(dto);
  }

  @Override
  public void updatePasswordMember(UpdatePasswordDto dto, Long memberId) {
    MemberEntity memberEntity = memberJpaRepository.findById(memberId).orElseThrow(
        () -> new EntityNotFoundException("해당 유저의 정보가 존재하지 않습니다.")
    );

    if (!passwordEncoder.matches(dto.getCurrentPassword(), memberEntity.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    List<History> passwordHistoryEntityList = historyJpaRepository.findTop3ByMemberIdOrderByCreatedDateDesc(
        memberId);

    passwordHistoryEntityList.forEach(
        passwordHistoryEntity -> {
          if(passwordEncoder.matches(dto.getChangePassword(), passwordHistoryEntity.getPassword())) {
            throw new IllegalArgumentException("최근에 해당 비밀번호로 변경한 적이 있습니다.");
          }
        }
    );

    // 비밀번호 변경
    memberEntity.updatePassword(passwordEncoder.encode(dto.getChangePassword()));
    // history에 변경된 비밀번호 저장
    History history = History.of(memberId, passwordEncoder.encode(dto.getChangePassword()));
    historyJpaRepository.save(history);
  }

  @Override
  public void deleteMember(Long memberId) {
    MemberEntity memberEntity = memberJpaRepository.findById(memberId).orElseThrow(
        () -> new EntityNotFoundException("해당 유저의 정보가 존재하지 않습니다.")
    );

    memberJpaRepository.delete(memberEntity);
  }

  @Override
  public boolean checkEmail(String email) {
    return memberJpaRepository.findByEmail(email).isPresent();
  }

  @Override
  public Member findMemberOrElseThrow(String email) {
    return memberJpaRepository.findByEmail(email).orElseThrow(
        () -> new EntityNotFoundException("해당 유저가 존재하지 않습니다.")
    ).toModel();
  }

  @Override
  public Member findMemberOrElseThrow(Long memberId) {
    return memberJpaRepository.findById(memberId).orElseThrow(
        () -> new EntityNotFoundException("해당 유저가 존재하지 않습니다.")
    ).toModel();
  }
}
