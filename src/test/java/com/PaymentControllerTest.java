package com;

import com.controller.PaymentController;
import com.entity.PaymentEntity;
import com.repository.LoanRepository;
import com.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PaymentControllerTest {
    private PaymentController controller = new PaymentController();

    @Autowired
    private PaymentRepository payRepo;

    @Autowired
    private LoanRepository loanRepo;

    // exits id, unpaid
    @Test
    public void testHandlerPayment1(){
        Long id = 3L;

        Map<String, Boolean> rs = controller.handlerPayment(id, payRepo, loanRepo);

        assertEquals(rs.get("isSuccess"), true);

        // check database
        Optional<PaymentEntity> OptPe = payRepo.findById(id);
        PaymentEntity pe = OptPe.get();
        assertEquals(pe.getStatus(), 1);

    }

    // exits id, paid
    @Test
    public void testHandlerPayment2(){
        Long id = 5L;

        Map<String, Boolean> rs = controller.handlerPayment(id, payRepo, loanRepo);

        assertEquals(rs.get("isSuccess"), false);
    }

    // not exits id
    @Test
    public void testHandlerPayment3(){
        Long id = 50L;

        Map<String, Boolean> rs = controller.handlerPayment(id, payRepo, loanRepo);

        assertEquals(rs.get("isSuccess"), false);
    }
}
