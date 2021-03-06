package com.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="loan")
public class LoanEntity extends BaseEntity{
	@Column
	private Date startDate;
	
	@Column
	private Long loan;
	
	@Column
	private String description;

	@Column
	private Long monthlyOriginAmount;

	@Column
	private Long monthlyInterestAmount;

	@Column
	private int numPaidMonth;

	@Column
	private Long monthlyTotalAmount;

	@ManyToOne
	@JoinColumn(name="interes_id")
	private InteresEntity interes;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private AccountEntity account;

	@OneToMany(mappedBy = "loan")
	private List<PaymentEntity> listPayment;
	
	@PrePersist
	private void setStartDate() {
		if(this.startDate == null) this.startDate = new Date();
	}

	public LoanEntity() {
		super();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getLoan() {
		return loan;
	}

	public void setLoan(Long loan) {
		this.loan = loan;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InteresEntity getInteres() {
		return interes;
	}

	public void setInteres(InteresEntity interes) {
		this.interes = interes;
	}

	public List<PaymentEntity> getListPayment() {
		return listPayment;
	}

	public void setListPayment(List<PaymentEntity> listPayment) {
		this.listPayment = listPayment;
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
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
