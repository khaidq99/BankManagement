package com.converter;

import com.dto.LoanDto;
import com.entity.LoanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class LoanConverter {
	@Autowired
	private InteresConverter ic;
	public LoanDto toDto(LoanEntity le) {
		LoanDto b = new LoanDto();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		b.setId(le.getId());
		b.setStartDate(formatter.format(le.getStartDate()));
		b.setLoan(le.getLoan());
		b.setDescription(le.getDescription());
		b.setMonthlyInterestAmount(le.getMonthlyInterestAmount());
		b.setMonthlyOriginAmount(le.getMonthlyOriginAmount());
		b.setNumPaidMonth(le.getNumPaidMonth());
		b.setInteres(ic.toDto(le.getInteres()));
		b.setMonthlyTotalAmount(le.getMonthlyTotalAmount());

		return b;
	}
}
