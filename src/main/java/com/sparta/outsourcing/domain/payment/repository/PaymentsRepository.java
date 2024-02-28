package com.sparta.outsourcing.domain.payment.repository;

import com.sparta.outsourcing.domain.payment.entity.Payments;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentsRepository extends JpaRepository<Payments,Long> {

  List<Payments> findAllByOrderByPaymentId();
}
