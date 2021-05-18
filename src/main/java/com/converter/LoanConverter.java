package com.converter;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dto.LoanDto;
import com.entity.LoanEntity;

@Component
public class LoanConverter {
	@Autowired
	private InteresConverter ic;
	public LoanDto toDto(LoanEntity bse) {
		LoanDto b = new LoanDto();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		b.setLoan(bse.getLoan());
		b.setDescription(bse.getDescription());
		b.setId(bse.getId());
		b.setInteres(ic.toDto(bse.getInteres()));
		b.setStartDate(formatter.format(bse.getStartDate()));
		if(bse.getPaymentDate() != null) {
			b.setPaymentDate(formatter.format(bse.getPaymentDate()));
		}
		return b;
	}
}
