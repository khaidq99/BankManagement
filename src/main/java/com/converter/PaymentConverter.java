package com.converter;

import com.dto.PaymentDto;
import com.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class PaymentConverter {
    @Autowired
    private LoanConverter lc;

    public PaymentDto toDto(PaymentEntity pe){
        PaymentDto p = new PaymentDto();
        p.setId(pe.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        if(pe.getDatePayment() != null){
            p.setDatePayment(formatter.format(pe.getDatePayment()));
        }
        p.setDeadlineDate(formatter.format(pe.getDeadlineDate()));
        p.setStatus(pe.getStatus());

        return p;
    }
}
