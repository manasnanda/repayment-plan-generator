package com.lendico.repayment.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Repayment {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "%.2f")
	private BigDecimal borrowerPaymentAmount;		
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "%.2f")
	private BigDecimal initialOutStandingPrincipal;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "%.2f")
	private BigDecimal interest;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "%.2f")
	private BigDecimal principal;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "%.2f")
	private BigDecimal remainingOutStandingPrincipal; 
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	private Date date;
	
	
	public Repayment(BigDecimal annityAmount, Date date, BigDecimal initialOutStanding, BigDecimal interest,
			BigDecimal principal, BigDecimal remainingOutStandingPrincipal) {
		super();
		this.borrowerPaymentAmount = annityAmount;
		this.date = date;
		this.initialOutStandingPrincipal = initialOutStanding;
		this.interest = interest;
		this.principal = principal;
		this.remainingOutStandingPrincipal = remainingOutStandingPrincipal;
	}


	public BigDecimal getBorrowerPaymentAmount() {
		return borrowerPaymentAmount;
	}


	public Date getDate() {
		return date;
	}


	public BigDecimal getInitialOutStandingPrincipal() {
		return initialOutStandingPrincipal;
	}


	public BigDecimal getInterest() {
		return interest;
	}


	public BigDecimal getPrincipal() {
		return principal;
	}


	public BigDecimal getRemainingOutStandingPrincipal() {
		return remainingOutStandingPrincipal;
	}



	
	
}
