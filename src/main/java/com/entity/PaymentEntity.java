package com.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payment")
public class PaymentEntity extends BaseEntity {
    @Column
    private Date datePayment;

    @Column
    private int status;

    @Column
    private Date deadlineDate;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private LoanEntity loan;

    public PaymentEntity() {
    }

    public PaymentEntity(Date datePayment, int status, Date deadlineDate, LoanEntity loan) {
        this.datePayment = datePayment;
        this.status = status;
        this.deadlineDate = deadlineDate;
        this.loan = loan;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LoanEntity getLoan() {
        return loan;
    }

    public void setLoan(LoanEntity loan) {
        this.loan = loan;
    }
}
