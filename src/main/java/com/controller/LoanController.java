package com.controller;

import com.converter.AccountConverter;
import com.converter.InteresConverter;
import com.converter.LoanConverter;
import com.dto.AccountDto;
import com.dto.InteresDto;
import com.entity.AccountEntity;
import com.entity.InteresEntity;
import com.entity.LoanEntity;
import com.entity.PaymentEntity;
import com.repository.AccountRepository;
import com.repository.InteresRepository;
import com.repository.LoanRepository;
import com.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@Controller
@RequestMapping("/loan")
public class LoanController {
	@Autowired
	private LoanRepository loanRepo;

	@Autowired
	private PaymentRepository payRepo;
	
	@Autowired
	private LoanConverter lc;
	
	@Autowired
	private InteresRepository iRepo;
	
	@Autowired
	private InteresConverter ic;
	
	@Autowired
	private AccountConverter ac;
	
	@Autowired AccountRepository accRepo;
	
	@GetMapping("/add/{idAccount}")
	public String showAddForm(@PathVariable("idAccount") Long id, Model model) {
		Iterable<InteresEntity> listE = iRepo.findAll();
		List<InteresDto> listDto = new ArrayList<>();
		for(InteresEntity ie : listE) {
			listDto.add(ic.toDto(ie));
		}
		InteresEntity.Type[] types = InteresEntity.Type.values();
		for(InteresEntity.Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(listDto, type));
		}
		model.addAttribute("loanE", new LoanEntity());
		Optional<AccountEntity> OptAcc = accRepo.findById(id);
		if(OptAcc.isPresent()) {
			AccountDto acc = ac.toDto(OptAcc.get());
			model.addAttribute("acc", acc);
		}
		model.addAttribute("idAccount", id);
		return "loans/add";
	}

	@Transactional
	@PostMapping("/add/{idAccount}")
	public String processAddLoan(@RequestParam("interes") String idInteres,
								 @PathVariable("idAccount") String idAccount,
								 @RequestParam("description") String description,
								 @RequestParam("loan") String loan) {
		LoanEntity le = new LoanEntity();
		InteresEntity interestEntity = new InteresEntity();
		Optional<InteresEntity> OptI = iRepo.findById(Long.parseLong(idInteres));
		if (!OptI.isPresent()){
			return "error";
		}
		interestEntity = OptI.get();
		le.setInteres(interestEntity);

		Optional<AccountEntity> OptAcc = accRepo.findById(Long.parseLong(idAccount));
		if(!OptAcc.isPresent()) {
			return "error";
		}
		AccountEntity ae = OptAcc.get();
		le.setAccount(ae);

		try {
			// Số tiền vay ban đầu
			Long loanAmount = Long.parseLong(loan);
			// Số tháng vay
			int month = interestEntity.getNumber();
			// Lãi suất
			float interest = interestEntity.getRatio();

			// Gốc trả hàng tháng
			Long monthlyOriginAmount = Long.parseLong(Math.round(loanAmount/month)+"");
			// Lãi trả hàng tháng
			Long monthlyInterestAmount = Long.parseLong(Math.round(loanAmount * interest / 100)+"");
			// Tổng tiền phải trả hàng tháng
			Long total = monthlyOriginAmount + monthlyInterestAmount;

			le.setLoan(loanAmount);
			le.setDescription(description);
			le.setStartDate(new Date());
			le.setMonthlyInterestAmount(monthlyInterestAmount);
			le.setMonthlyOriginAmount(monthlyOriginAmount);
			le.setMonthlyTotalAmount(total);
			le.setNumPaidMonth(0);
			loanRepo.save(le);

			Date currentDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);

			for(int i = 1; i <= month; i++){
				PaymentEntity pe = new PaymentEntity();
				pe.setLoan(le);
				pe.setStatus(0); // 0 = chưa thanh toán, 1 = đã thanh toán
				// set deadline date
				c.add(Calendar.MONTH, i);
				Date addedDate = c.getTime();
				pe.setDeadlineDate(addedDate);
				c.setTime(currentDate);
				payRepo.save(pe);
			}

		} catch (Exception e){
			System.out.println(e);
		}

		return "redirect:/customer/detail/" + idAccount;
	}

	private List<InteresDto> filterByType(List<InteresDto> listDto, InteresEntity.Type type) {
		List<InteresDto> list  = new ArrayList<>();
		for (InteresDto i : listDto) {
			if(i.getType().equals(type.toString())) {
				list.add(i);
			}
		}
		return list;
	}



//    @GetMapping("/withdraw-info/{idBs}")
//    public String showWithdrawInfomation(@PathVariable("idBs") Long idBs, Model model){
//        Optional<LoanEntity> Opt = loanRepo.findById(idBs);
//        if(Opt.isPresent()) {
//        	LoanEntity bse = Opt.get();
//        	Date date = new Date();
//            bse.setWithdrawDate(date);
//            BookSavingDto bs = lc.toDto(bse);
//            model.addAttribute("bs", bs);
//            AccountDto acc = ac.toDto(bse.getAccount());
//            model.addAttribute("acc", acc);
//            double totalInteresD;
//            if(bse.getInteres().getType().toString().equals("NOLIMIT")) {
//            	Long diff = date.getTime() - bse.getStartDate().getTime();
//            	Long dayDiff = TimeUnit.MILLISECONDS.toDays(diff);
//            	totalInteresD = bse.getAmountSend() * bse.getInteres().getRatio() * dayDiff /360;
//            }
//            else {
//            	totalInteresD = bse.getAmountSend() * bse.getInteres().getRatio() * bse.getInteres().getNumber() / 12;
//            }
//            Long totalInteres = Math.round(totalInteresD);
//            model.addAttribute("totalInteres", totalInteres);
//        }
//        return "withdraw";
//    }
//
//    @GetMapping("/withdraw/{idBs}")
//    public String updateWithdraw(@PathVariable("idBs") Long idBs){
//        Optional<LoanEntity> Opt = loanRepo.findById(idBs);
//        String accId = null;
//        if(Opt.isPresent()) {
//        	LoanEntity bse = Opt.get();
//        	Date date = new Date();
//            bse.setWithdrawDate(date);
//            loanRepo.save(bse);
//            accId = bse.getAccount().getId().toString();
//
//        }
//        return "redirect:/customer/detail/" + accId;
//    }
}
