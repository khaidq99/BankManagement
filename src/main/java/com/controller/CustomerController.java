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
		Map<String, Object> rs = handleSearchCustomer(searchKey, accRepo, ac);
		model.addAttribute("acc", rs.get("acc"));
		model.addAttribute("isSuccess", rs.get("acc"));

		return "customers/list";
	}

	public Map<String, Object> handleSearchCustomer(String key, AccountRepository repo, AccountConverter accCon){
		AccountEntity ae = repo.findByNumberAccount(key);
		Map<String, Object> result = new HashMap<>();

		if (ae != null){
			AccountDto acc = accCon.toDto(ae);
			result.put("acc", acc);
			result.put("isSuccess", true);
		} else {
			result.put("acc", null);
			result.put("isSuccess", false);
		}
		return result;
	}

	@GetMapping("/detail/{id}")
	public String showDetail(@PathVariable("id") Long id, Model model) {
		Map<String, Object> rs = getDetailCustomer(id, accRepo, ac, loanConverter, loanRepo);

		model.addAttribute("acc", rs.get("acc"));
		model.addAttribute("listLoan", rs.get("listLoan"));

		return "customers/detail";
	}

	public Map<String, Object> getDetailCustomer(@PathVariable("id") Long id, AccountRepository accRepo,
												 AccountConverter ac, LoanConverter loanConverter,
												 LoanRepository loanRepo){
		Map<String, Object> result = new HashMap<>();

		Optional<AccountEntity> OptAe = accRepo.findById(id);
		if(OptAe.isPresent()) {
			AccountDto acc = ac.toDto(OptAe.get());
			result.put("acc", acc);
		}

		List<LoanEntity> listE = loanRepo.findByAccountId(id);
		if(listE.isEmpty()) {
			result.put("listLoan", null);
		}
		else {
			List<LoanDto> listLoan = new ArrayList<>();
			for(LoanEntity le : listE) {
				listLoan.add(loanConverter.toDto(le));
			}
			result.put("listLoan", listLoan);
		}
		return result;
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
	public String createAccount(Model model, @ModelAttribute("account") AccountEntity account,
								@RequestParam("balance") String balance,
								@RequestParam("birthday") String birthday) throws ParseException {
        AccountEntity accountPost = (AccountEntity) model.getAttribute("account");

		Map<String, Object> resulst = handlerAddCustomer(accountPost, balance, birthday, cusRepo, accRepo);

        model.addAttribute("acc", resulst.get("acc"));
        return "customers/register_success";
    }

    public Map<String, Object> handlerAddCustomer(AccountEntity accountPost, String balance, String birthday,
											CustomerRepository cusRepo, AccountRepository accRepo)
			throws ParseException {
		Map<String, Object> result = new HashMap<>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date birthdayFormat = sdf.parse(birthday);
		accountPost.getCustomer().setBirthday(birthdayFormat);

		CustomerEntity customer = cusRepo.save(accountPost.getCustomer());
		accountPost.setCustomer(customer);
		accountPost.setBalance(Long.parseLong(balance));

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(accountPost.getPassword());
		accountPost.setPassword(encodedPassword);

		if(accRepo.save(accountPost) != null){
			result.put("acc", accountPost);
			result.put("isSuccess", true);
		} else {
			result.put("isSuccess", false);
		}

		return result;
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
		Map<String, Object> rs = handlerEditCustomer(id, accountPost, accRepo);

		return "redirect:/customer/detail/" + id;
	}

	public Map<String, Object> handlerEditCustomer(Long id, AccountEntity accountPost,
												   AccountRepository accRepo){
		Map<String, Object> result = new HashMap<>();

		Optional<AccountEntity> OptAe = accRepo.findById(id);
		AccountEntity accountEntity = OptAe.get();

		CustomerEntity customerEntity = accountEntity.getCustomer();
		customerEntity.setAddress(accountPost.getCustomer().getAddress());
		customerEntity.setEmail(accountPost.getCustomer().getEmail());
		customerEntity.setName(accountPost.getCustomer().getName());
		customerEntity.setPhone(accountPost.getCustomer().getPhone());

		result.put("isSuccess", true);

		return result;
	}


}
