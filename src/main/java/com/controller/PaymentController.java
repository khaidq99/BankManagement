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
        Optional<PaymentEntity> OptPe = payRepo.findById(id);
        if(!OptPe.isPresent()) {
            return "error";
        }
        PaymentEntity pe = OptPe.get();
        pe.setDatePayment(new Date());
        pe.setStatus(1);
        payRepo.save(pe);

        LoanEntity loan = pe.getLoan();
        loan.setNumPaidMonth(loan.getNumPaidMonth() + 1);
        loanRepo.save(loan);

        return "payments/success";
    }
}
