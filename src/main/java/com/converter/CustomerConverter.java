package com.converter;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.dto.CustomerDto;
import com.entity.CustomerEntity;

@Component
public class CustomerConverter {
	public CustomerDto toDto(CustomerEntity ce) {
		CustomerDto c = new CustomerDto();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		c.setBirthDay(formatter.format(ce.getBirthday()));
		c.setEmail(ce.getEmail());
		c.setId(ce.getId());
		c.setName(ce.getName());
		c.setAddress(ce.getAddress());
		c.setPhone(ce.getPhone());
		return c;
	}
}
