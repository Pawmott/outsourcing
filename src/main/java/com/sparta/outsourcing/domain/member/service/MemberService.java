package com.sparta.outsourcing.domain.member.service;

import com.sparta.outsourcing.domain.favorite.model.entity.Favorite;
import com.sparta.outsourcing.domain.favorite.repository.FavoriteRepository;
import com.sparta.outsourcing.domain.member.controller.dto.SignupRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdatePasswordRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdateRequestDto;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepository;
import com.sparta.outsourcing.domain.member.service.dto.MemberInfoResponse;
import com.sparta.outsourcing.domain.member.service.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.service.dto.MemberSignupDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdateDto;
import com.sparta.outsourcing.domain.member.service.dto.UpdatePasswordDto;
import com.sparta.outsourcing.domain.review.model.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.global.jwt.entity.RefreshTokenEntity;
import com.sparta.outsourcing.global.jwt.repository.TokenRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final TokenRepository tokenRepository;
  private final ReviewRepository reviewRepository;
  private final FavoriteRepository favoriteRepository;

  public MemberResponseDto signup(SignupRequestDto dto) {
    MemberSignupDto info = MemberSignupDto.builder()
        .email(dto.getEmail())
        .nickname(dto.getNickname())
        .password(dto.getPassword())
        .number(dto.getNumber())
        .address(dto.getAddress())
        .build();

    if (memberRepository.checkEmail(info.getEmail())) {
      throw new IllegalArgumentException("해당 유저가 존재합니다.");
    }

    memberRepository.signIn(info);

    return MemberResponseDto.builder()
        .email(info.getEmail())
        .nickname(info.getNickname())
        .build();
  }

  public void logout(UserDetails userDetails) {
    Member member = memberRepository.findMemberOrElseThrow(userDetails.getUsername());
    RefreshTokenEntity refreshToken = tokenRepository.findByMemberId(member.getId());
    tokenRepository.deleteToken(refreshToken);
  }

  @Transactional(readOnly = true)
  public MemberInfoResponse memberInfo(Long memberId) {
    Member member = memberRepository.findMemberOrElseThrow(memberId);

    List<Favorite> favoriteList = favoriteRepository.findAllByMemberId(member.getId());
    List<Review> reviewList = reviewRepository.findByMemberEntityId(member.getId());
    
    return MemberInfoResponse.builder()
        .memberId(member.getId())
        .favoriteList(favoriteList)
        .reviewList(reviewList)
        .build();
  }

  public void updateMember(Long memberId, String email, UpdateRequestDto dto) {
    Member member = memberRepository.findMemberOrElseThrow(email);

    if (!member.checkId(memberId)) {
      throw new IllegalArgumentException("해당 유저의 권한이 없습니다.");
    }

    UpdateDto updateDto = new UpdateDto(dto);
    memberRepository.updateMember(updateDto, memberId);
  }

  public void deleteMember(Long memberId, String username) {
    Member member = memberRepository.findMemberOrElseThrow(username);

    if(member.getRole().equals(MemberRole.ADMIN) || member.checkId(memberId)) {
      memberRepository.deleteMember(memberId);
      return;
    }

    throw new IllegalArgumentException("유저의 권한이 없습니다.");
  }

  public void updatePasswordMember(Long memberId, String email, UpdatePasswordRequestDto dto) {
    Member member = memberRepository.findMemberOrElseThrow(email);

    if (!member.checkId(memberId)) {
      throw new IllegalArgumentException("해당 유저의 권한이 없습니다.");
    }

    UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(dto);
    updatePasswordDto.checkChangePasswordEquals(); // 비밀번호 일치여부

    memberRepository.updatePasswordMember(updatePasswordDto, member.getId());
  }
}
