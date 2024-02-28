package com.sparta.outsourcing.domain.member.repository.history;

import com.sparta.outsourcing.domain.member.model.entity.History;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistoryJpaRepository extends JpaRepository<History, Long> {

  @Query(value = "select p from History p where p.memberId = ?1 order by p.createdDate desc limit 3")
  List<History> findTop3ByMemberIdOrderByCreatedDateDesc(Long memberId);
}
