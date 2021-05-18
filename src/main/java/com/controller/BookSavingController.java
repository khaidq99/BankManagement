package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.converter.AccountConverter;
import com.converter.LoanConverter;
import com.converter.InteresConverter;
import com.repository.AccountRepository;
import com.repository.BookSavingRepository;
import com.repository.InteresRepository;

@Controller
@RequestMapping("/booksaving")
public class BookSavingController {
	@Autowired
	private BookSavingRepository bsRepo;
	
	@Autowired
	private LoanConverter bc;
	
	@Autowired
	private InteresRepository iRepo;
	
	@Autowired
	private InteresConverter ic;
	
	@Autowired
	private AccountConverter ac;
	
	@Autowired AccountRepository accRepo;
	
//	@GetMapping("/add/{idAccount}")
//	public String showAddForm(@PathVariable("idAccount") Long id, Model model) {
//		Iterable<InteresEntity> listE = iRepo.findAll();
//		List<InteresDto> listDto = new ArrayList<>();
//		for(InteresEntity ie : listE) {
//			listDto.add(ic.toDto(ie));
//		}
//		Type[] types = InteresEntity.Type.values();
//		for(Type type : types) {
//			model.addAttribute(type.toString().toLowerCase(), filterByType(listDto, type));
//		}
//		model.addAttribute("bs", new LoanEntity());
//		Optional<AccountEntity> OptAcc = accRepo.findById(id);
//		if(OptAcc.isPresent()) {
//			AccountDto acc = ac.toDto(OptAcc.get());
//			model.addAttribute("acc", acc);
//		}
//		return "to_saving";
//	}
	
//	@PostMapping("/add/{idAccount}")
//	public String processAddBookSaving(@RequestParam("interes") String idInteres, @PathVariable("idAccount") String idAccount,
//			@RequestParam("description") String description, @RequestParam("amountSend") String amountSend) {
//		LoanEntity bse = new LoanEntity();
//		Optional<AccountEntity> OptAcc = accRepo.findById(Long.parseLong(idAccount));
//		if(OptAcc.isPresent()) {
//			AccountEntity ae = OptAcc.get();
//			ae.setBalance(ae.getBalance() - Long.parseLong(amountSend));
//			bse.setAccount(ae);
//		}
//		bse.setAmountSend(Long.parseLong(amountSend));
//		bse.setDescription(description);
//		Optional<InteresEntity> OptI = iRepo.findById(Long.parseLong(idInteres));
//		if(OptI.isPresent()) {
//			bse.setInteres(OptI.get());
//		}
//		bsRepo.save(bse);
//		return "redirect:/customer/detail/" + idAccount;
//	}
//
//	private List<InteresDto> filterByType(List<InteresDto> listDto, Type type) {
//		List<InteresDto> list  = new ArrayList<>();
//		for (InteresDto i : listDto) {
//			if(i.getType().equals(type.toString())) {
//				list.add(i);
//			}
//		}
//		return list;
//	}
//
//
//
//    @GetMapping("/withdraw-info/{idBs}")
//    public String showWithdrawInfomation(@PathVariable("idBs") Long idBs, Model model){
//        Optional<LoanEntity> Opt = bsRepo.findById(idBs);
//        if(Opt.isPresent()) {
//        	LoanEntity bse = Opt.get();
//        	Date date = new Date();
//            bse.setWithdrawDate(date);
//            BookSavingDto bs = bc.toDto(bse);
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
//        Optional<LoanEntity> Opt = bsRepo.findById(idBs);
//        String accId = null;
//        if(Opt.isPresent()) {
//        	LoanEntity bse = Opt.get();
//        	Date date = new Date();
//            bse.setWithdrawDate(date);
//            bsRepo.save(bse);
//            accId = bse.getAccount().getId().toString();
//
//        }
//        return "redirect:/customer/detail/" + accId;
//    }
}
