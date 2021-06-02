package com;

import com.controller.CustomerController;
import com.converter.AccountConverter;
import com.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerControllerTest {
    private CustomerController controller = new CustomerController();

    @Autowired
    private AccountRepository accRepo;

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
}
