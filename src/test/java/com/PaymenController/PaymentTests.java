package com.PaymenController;

import com.controller.PaymentController;
import com.entity.LoanEntity;
import com.entity.PaymentEntity;
import com.repository.LoanRepository;
import com.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PaymentTests {
    @Autowired
    private PaymentRepository payRepo;

    @Autowired
    private LoanRepository loanRepo;

    // Test ExitsPayment, unpaid
    @Test
    public void testExitsPayment1(){
        Long id = 1L;
        Optional<PaymentEntity> optPay = payRepo.findById(id);
        PaymentEntity payment = optPay.get();
        payment.setStatus(1);
        payment.setDatePayment(new Date());
        payRepo.save(payment);

        LoanEntity loan = payment.getLoan();
        int paidNum = loan.getNumPaidMonth();
        loan.setNumPaidMonth(paidNum + 1);
        loanRepo.save(loan);

        // test the new update row
        optPay = payRepo.findById(id);
        PaymentEntity p = optPay.get();
        LoanEntity l = p.getLoan();

        assertEquals(p.getStatus(), 1);
        assertNotNull(p.getDatePayment());
        assertEquals(l.getNumPaidMonth(), paidNum + 1);
    }

    // Test ExitsPayment, paid
    @Test
    public void testExitsPayment2(){
        Long id = 5L;
        Optional<PaymentEntity> optPay = payRepo.findById(id);
        PaymentEntity payment = optPay.get();
        assertEquals(payment.getStatus(), 1);
    }

    // Test Does not exits payment
    @Test
    public void testDoesNotExitsPayment(){
        Long id = 10L;
        Optional<PaymentEntity> optPay = payRepo.findById(id);
        assertNull(optPay);
    }
}
