package com.controller;

import com.dto.AccountDto;
import com.entity.AccountEntity;
import com.entity.LoanEntity;
import com.entity.PaymentEntity;
import com.repository.LoanRepository;
import com.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository payRepo;

    @Autowired
    private LoanRepository loanRepo;


    @GetMapping("/{id}")
    public String payment(@PathVariable("id") Long id, Model model) {
        Map<String, Boolean> rs = handlerPayment(id, payRepo, loanRepo);

        if(!rs.get("isSuccess")) {
            return "error";
        }

        return "payments/success";
    }

    public Map<String, Boolean> handlerPayment(Long id, PaymentRepository payRepo, LoanRepository loanRepo){
        Map<String, Boolean> rs = new HashMap<>();

        Optional<PaymentEntity> OptPe = payRepo.findById(id);
        if(!OptPe.isPresent()) {
            rs.put("isSuccess", false);
        } else {
            PaymentEntity pe = OptPe.get();
            pe.setDatePayment(new Date());
            if(pe.getStatus() == 1){
                rs.put("isSuccess", false);
            } else {
                pe.setStatus(1);
                payRepo.save(pe);

                LoanEntity loan = pe.getLoan();
                loan.setNumPaidMonth(loan.getNumPaidMonth() + 1);
                loanRepo.save(loan);
                rs.put("isSuccess", true);
            }
        }

        return rs;
    }
}
