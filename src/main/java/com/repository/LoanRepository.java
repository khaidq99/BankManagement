package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.entity.LoanEntity;

public interface LoanRepository extends CrudRepository<LoanEntity, Long>{
	@Query("Select le from LoanEntity le Where le.account.id=?1 And le.paymentDate=null")
	List<LoanEntity> findByAccountId(Long accId);
}
