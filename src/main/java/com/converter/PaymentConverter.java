package com.converter;

import com.dto.PaymentDto;
import com.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

public class PaymentConverter {
    @Autowired
    private LoanConverter lc;

    public PaymentDto toDto(PaymentEntity pe){
        PaymentDto p = new PaymentDto();
        p.setId(pe.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
        p.setDatePayment(formatter.format(pe.getDatePayment()));
        p.setDeadlineDate(formatter.format(pe.getDeadlineDate()));
        p.setStatus(pe.getStatus());
        p.setLoanDto(lc.toDto(pe.getLoan()));

        return p;
    }
}
