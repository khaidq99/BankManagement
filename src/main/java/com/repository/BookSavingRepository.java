package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.entity.LoanEntity;

public interface BookSavingRepository extends CrudRepository<LoanEntity, Long>{
	@Query("Select bse from BookSavingEntity bse Where bse.account.id=?1 And bse.withdrawDate=null")
	List<LoanEntity> findByAccountId(Long accId);
}
