package com;

import com.controller.LoanController;
import com.converter.AccountConverter;
import com.converter.PaymentConverter;
import com.entity.PaymentEntity;
import com.repository.AccountRepository;
import com.repository.InteresRepository;
import com.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LoanControllerTest {
    private LoanController controller = new LoanController();

    @Autowired
    private AccountRepository accRepo;

    @Autowired
    private PaymentRepository payRepo;

    @Autowired
    private InteresRepository iRepo;

    // exits id
    @Test
    public void testShowAddLoanForm1(){
        Long id = 2L;
        AccountConverter ac = new AccountConverter();
        Map<String, Object> result = controller.handlerShowAddForm(id, ac, accRepo);

        assertNotNull(result.get("acc"));
    }

    // not exits id
    @Test
    public void testShowAddLoanForm2(){
        Long id = 10L;
        AccountConverter ac = new AccountConverter();
        Map<String, Object> result = controller.handlerShowAddForm(id, ac, accRepo);

        assertNull(result.get("acc"));
    }

    // exist id
    @Test
    public void testHandlerShowDetailForm3(){
        Long id = 8L;
        PaymentConverter pc = new PaymentConverter();
        Map<String, Object> result = controller.handlerShowDetailForm(id, payRepo, pc);

        List<PaymentEntity> list = (List<PaymentEntity>) result.get("payments");
        assertEquals(list.size(), 3);
    }

    // not exist id
    @Test
    public void testHandlerShowDetailForm4(){
        Long id = 50L;
        PaymentConverter pc = new PaymentConverter();
        Map<String, Object> result = controller.handlerShowDetailForm(id, payRepo, pc);

        List<PaymentEntity> list = (List<PaymentEntity>) result.get("payments");
        assertEquals(list.size(), 0);
    }



}
