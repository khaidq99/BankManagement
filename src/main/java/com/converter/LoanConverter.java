package com.converter;

import com.dto.LoanDto;
import com.dto.PaymentDto;
import com.entity.LoanEntity;
import com.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoanConverter {
	@Autowired
	private InteresConverter ic;

	@Autowired
	private PaymentConverter pc;

	public LoanDto toDto(LoanEntity le) {
		LoanDto l = new LoanDto();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		l.setId(le.getId());
		l.setStartDate(formatter.format(le.getStartDate()));
		l.setLoan(le.getLoan());
		l.setDescription(le.getDescription());
		l.setMonthlyInterestAmount(le.getMonthlyInterestAmount());
		l.setMonthlyOriginAmount(le.getMonthlyOriginAmount());
		l.setNumPaidMonth(le.getNumPaidMonth());
		l.setInteres(ic.toDto(le.getInteres()));
		l.setMonthlyTotalAmount(le.getMonthlyTotalAmount());

		List<PaymentDto> listPay = new ArrayList<>();
		for(PaymentEntity p : le.getListPayment()){
			PaymentDto pay = pc.toDto(p);
			listPay.add(pay);
		}
		l.setListPayment(listPay);

		return l;
	}
}
