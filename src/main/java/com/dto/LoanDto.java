package com.dto;

public class LoanDto {
	private Long id;
	private String startDate;
	private String description;
	private Long loan;
	private String paymentDate;
	private InteresDto interes;
	
	public LoanDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public Long getLoan() {
		return loan;
	}

	public void setLoan(Long loan) {
		this.loan = loan;
	}

	public InteresDto getInteres() {
		return interes;
	}
	public void setInteres(InteresDto interes) {
		this.interes = interes;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
}
