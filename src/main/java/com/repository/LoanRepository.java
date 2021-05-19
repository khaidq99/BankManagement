package com.repository;

import com.entity.LoanEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<LoanEntity, Long>{
	@Query("Select le from LoanEntity le Where le.account.id=?1")
	List<LoanEntity> findByAccountId(Long accId);
}
