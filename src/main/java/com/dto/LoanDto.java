package com.dto;

public class LoanDto {
	private Long id;
	private String startDate;
	private Long loan;
	private String description;
	private Long monthlyOriginAmount;
	private Long monthlyInterestAmount;
	private int numPaidMonth;
	private Long monthlyTotalAmount;
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

	public Long getMonthlyOriginAmount() {
		return monthlyOriginAmount;
	}

	public void setMonthlyOriginAmount(Long monthlyOriginAmount) {
		this.monthlyOriginAmount = monthlyOriginAmount;
	}

	public Long getMonthlyInterestAmount() {
		return monthlyInterestAmount;
	}

	public void setMonthlyInterestAmount(Long monthlyInterestAmount) {
		this.monthlyInterestAmount = monthlyInterestAmount;
	}

	public int getNumPaidMonth() {
		return numPaidMonth;
	}

	public void setNumPaidMonth(int numPaidMonth) {
		this.numPaidMonth = numPaidMonth;
	}

	public Long getMonthlyTotalAmount() {
		return monthlyTotalAmount;
	}

	public void setMonthlyTotalAmount(Long monthlyTotalAmount) {
		this.monthlyTotalAmount = monthlyTotalAmount;
	}
}
