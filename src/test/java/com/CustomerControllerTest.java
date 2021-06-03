package com;

import com.controller.CustomerController;
import com.converter.AccountConverter;
import com.converter.LoanConverter;
import com.dto.LoanDto;
import com.entity.AccountEntity;
import com.entity.CustomerEntity;
import com.repository.AccountRepository;
import com.repository.CustomerRepository;
import com.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerControllerTest {
    private CustomerController controller = new CustomerController();

    @Autowired
    private AccountRepository accRepo;

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private CustomerRepository cusRepo;

    // found customer
    @Test
    public void testSearchCustomer1(){
        String key = "87798357";
        AccountConverter ac = new AccountConverter();
        Map<String, Object> result = controller.handleSearchCustomer(key, accRepo, ac);

        assertEquals(true, result.get("isSuccess"));
    }

    // not found customer
    @Test
    public void testSearchCustomer2(){
        String key = "abc";
        AccountConverter ac = new AccountConverter();
        Map<String, Object> result = controller.handleSearchCustomer(key, accRepo, ac);

        assertEquals(false, result.get("isSuccess"));
    }

    // exits customer, exist loan
    @Test
    public void testGetDetailCustomer3(){
        Long id = 2L;
        AccountConverter ac = new AccountConverter();
        LoanConverter lc = new LoanConverter();
        Map<String, Object> rs = controller.getDetailCustomer(id, accRepo, ac, lc, loanRepo);

        assertNotNull(rs.get("acc"));
        List<LoanDto> list = (List<LoanDto>) rs.get("listLoan");
        assertTrue(list.size() > 0);
    }

    // exits customer, not exist loan
    @Test
    public void testGetDetailCustomer4(){
        Long id = 3L;
        AccountConverter ac = new AccountConverter();
        LoanConverter lc = new LoanConverter();
        Map<String, Object> rs = controller.getDetailCustomer(id, accRepo, ac, lc, loanRepo);

        assertNotNull(rs.get("acc"));
        List<LoanDto> list = (List<LoanDto>) rs.get("listLoan");
        assertNull(list);
    }

    // not exits customer
    @Test
    public void testGetDetailCustomer5(){
        Long id = 10L;
        AccountConverter ac = new AccountConverter();
        LoanConverter lc = new LoanConverter();
        Map<String, Object> rs = controller.getDetailCustomer(id, accRepo, ac, lc, loanRepo);

        assertNull(rs.get("acc"));
    }

    // customer valid, account valid
    @Test
    public void testAddcustomer6() throws ParseException {
        AccountEntity account = new AccountEntity();
        String numAccount = "85696140";

        account.setNumberAccount(numAccount);
        account.setUsername("codejava");
        account.setPassword("123456");
        CustomerEntity cus = new CustomerEntity();
        account.setCustomer(cus);

        Map<String, Object> rs = controller.handlerAddCustomer(account, "1500000",
                "10/12/1999", cusRepo, accRepo);

        // check database
        AccountEntity ae = accRepo.findByNumberAccount(numAccount);
        assertEquals(ae.getUsername(), account.getUsername());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date birthdayFormat = sdf.parse("10/12/1999");
        assertEquals(ae.getCustomer().getBirthday(), birthdayFormat);
        assertEquals(ae.getBalance(), 1500000L);

        assertEquals(true, rs.get("isSuccess"));
    }


    // customer invalid
    @Test
    public void testAddcustomer7() throws ParseException {

        CustomerEntity cus = new CustomerEntity();
        // set exist id in database
        cus.setId(1L);

        AccountEntity account = new AccountEntity();
        String numAccount = "85696146";
        account.setNumberAccount(numAccount);
        account.setUsername("codejava");
        account.setPassword("123456");

        account.setCustomer(cus);

        Map<String, Object> rs = controller.handlerAddCustomer(account, "1500000",
                "10/12/1999", cusRepo, accRepo);

        // check database
        AccountEntity ae = accRepo.findByNumberAccount(numAccount);
        assertEquals(ae, null);
        assertEquals(true, rs.get("false"));
    }

    // account invalid
    @Test
    public void testAddcustomer8() throws ParseException {

        AccountEntity account = new AccountEntity();
        CustomerEntity cus = new CustomerEntity();
        account.setCustomer(cus);

        Map<String, Object> rs = controller.handlerAddCustomer(account, "1500000",
                "10/12/1999", cusRepo, accRepo);

        assertNull(rs);
        assertEquals(false, rs.get("isSuccess"));
    }

    // Exist id
    @Test
    public void testEditCustomer9(){
        Long id = 2L;
        AccountEntity accountPost = new AccountEntity();
        accountPost.setBalance(15000000L);
        CustomerEntity cus = new CustomerEntity();
        cus.setName("BBB");
        accountPost.setCustomer(cus);

        Map<String, Object> rs = controller.handlerEditCustomer(id, accountPost, accRepo);

        // check database
        Optional<AccountEntity> OptAe = accRepo.findById(id);
        AccountEntity editedAccount = OptAe.get();

        assertEquals(editedAccount.getBalance(), accountPost.getBalance());
        assertEquals(editedAccount.getCustomer().getName(), accountPost.getCustomer().getName());
        assertEquals(editedAccount.getBalance(), accountPost.getBalance());
        assertEquals(true, rs.get("isSuccess"));
    }

    // Not exist id
    @Test
    public void testEditCustomer10(){
        Long id = 10L;
        AccountEntity accountPost = new AccountEntity();
        accountPost.setBalance(15000000L);
        CustomerEntity cus = new CustomerEntity();
        cus.setName("BBB");
        accountPost.setCustomer(cus);

        Map<String, Object> rs = controller.handlerEditCustomer(id, accountPost, accRepo);

        assertEquals(false, rs.get("isSuccess"));
    }

    // Exits id
    @Test
    public void testGetEditForm11(){
        Long id = 3L;
        AccountConverter ac = new AccountConverter();
        Map<String, Object> rs = controller.getEditForm(id, accRepo, ac);

        assertNotNull(rs.get("acc"));
    }

    // Not exits id
    @Test
    public void testGetEditForm12(){
        Long id = 50L;
        AccountConverter ac = new AccountConverter();
        Map<String, Object> rs = controller.getEditForm(id, accRepo, ac);

        assertNull(rs.get("acc"));
    }

}
