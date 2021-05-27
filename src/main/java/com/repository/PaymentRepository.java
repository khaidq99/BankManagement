package com.repository;

import com.entity.PaymentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
    @Query("Select pe from PaymentEntity pe Where pe.loan.id=?1")
    List<PaymentEntity> findByLoanId(Long loanId);
}
