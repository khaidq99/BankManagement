package com.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.converter.AccountConverter;
import com.converter.LoanConverter;
import com.dto.AccountDto;
import com.dto.LoanDto;
import com.entity.AccountEntity;
import com.entity.LoanEntity;
import com.entity.CustomerEntity;
import com.repository.AccountRepository;
import com.repository.LoanRepository;
import com.repository.CustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private AccountConverter ac;
	
	@Autowired
	private LoanRepository loanRepo;
	
	@Autowired
	private LoanConverter loanConverter;

	@Autowired
	private CustomerRepository cusRepo;
	
	@GetMapping
	public String showCustomer(Model model) {
		model.addAttribute("isSuccess", true);
		return "customers/list";
	}
	
	@PostMapping("/search")
	public String searchCustomer(@RequestParam("searchKey") String searchKey, Model model) {
		AccountEntity ae = accRepo.findByNumberAccount(searchKey);
		if(ae != null) {
			AccountDto acc = ac.toDto(ae);
			model.addAttribute("acc", acc);
			model.addAttribute("isSuccess", true);
		}
		else {
			model.addAttribute("isSuccess", false);
		}
		return "customers/list";
	}

	@GetMapping("/detail/{id}")
	public String showDetail(@PathVariable("id") Long id, Model model) {
		Optional<AccountEntity> OptAe = accRepo.findById(id);
		if(OptAe.isPresent()) {
			AccountDto acc = ac.toDto(OptAe.get());
			model.addAttribute("acc", acc);
		}
		List<LoanEntity> listE = loanRepo.findByAccountId(id);
		if(listE.isEmpty()) {
			model.addAttribute("listLoan", null);
		}
		else {
			List<LoanDto> listLoan = new ArrayList<>();
			for(LoanEntity le : listE) {
				listLoan.add(loanConverter.toDto(le));
			}
			model.addAttribute("listLoan", listLoan);
		}
		return "customers/detail";
	}

    @GetMapping("/add")
    public String showAddForm(Model model){
		AccountDto account = new AccountDto();

		int max = 99999999;
		int min = 10000000;
		Random random = new Random();
		int numAccount = random.nextInt(max - min) + min;

		account.setNumberAccount(String.valueOf(numAccount));
		model.addAttribute("account", account);
		return "customers/add";
    }

    @PostMapping("/add")
    public String createAccount(Model model, @ModelAttribute("account") AccountEntity account, @RequestParam("balance") String balance,
    		@RequestParam("birthday") String birthday) throws ParseException {
        AccountEntity accountPost = (AccountEntity) model.getAttribute("account");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date birthdayFormat = sdf.parse(birthday);
        accountPost.getCustomer().setBirthday(birthdayFormat);

        CustomerEntity customer = cusRepo.save(accountPost.getCustomer());
        accountPost.setCustomer(customer);
        accountPost.setBalance(Long.parseLong(balance));

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(accountPost.getPassword());
		accountPost.setPassword(encodedPassword);

        accRepo.save(accountPost);
        model.addAttribute("acc", accountPost);
        return "customers/register_success";
    }

	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable("id") Long id, Model model) {
		Optional<AccountEntity> OptAe = accRepo.findById(id);
		if(OptAe.isPresent()) {
			AccountDto acc = ac.toDto(OptAe.get());
			model.addAttribute("acc", acc);
		}
		return "customers/edit";
	}

	@PostMapping("/edit/{id}")
	public String editCustomer(Model model, @PathVariable("id") Long id,
							   @ModelAttribute("acc") AccountEntity acc){
		AccountEntity accountPost = (AccountEntity) model.getAttribute("acc");

		Optional<AccountEntity> OptAe = accRepo.findById(id);
		AccountEntity accountEntity = OptAe.get();

		CustomerEntity customerEntity = accountEntity.getCustomer();
		customerEntity.setAddress(accountPost.getCustomer().getAddress());
		customerEntity.setEmail(accountPost.getCustomer().getEmail());
		customerEntity.setName(accountPost.getCustomer().getName());
		customerEntity.setPhone(accountPost.getCustomer().getPhone());

		accRepo.save(accountEntity);
		return "redirect:/customer/detail/" + id;
	}
}
