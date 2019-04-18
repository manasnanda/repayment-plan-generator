package com.lendico.repayment.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Manas Ranjan Nanda
 * RepaymentDTO is used to communicate via HTTP API
 * **/
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RepaymentRequest 
{
	@NotNull(message = "Loan Amount can not be null!")
	@DecimalMin(value="0",message="Loan Amount can not be negative")
	private BigDecimal loanAmount;
	@NotNull(message = "Nomial Rate not be null!")
	@DecimalMin(value="0.1",message="Nominal Rate can not be 0 or negative")
	private double nominalRate;
	@NotNull(message = "Duration can not be null!")
	@Min(value=1,message="Duration can not be zero or negative")
	private int duration;
	
	@NotNull(message = "Start Date can not be null!")
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date startDate;
	
	
	
	
	public RepaymentRequest() {
		super();
	}

	public RepaymentRequest(BigDecimal amount, double nominalRate, int duration, Date startDate) {
		this.startDate = startDate;
		this.loanAmount = amount;
		this.duration = duration;
		this.nominalRate = nominalRate;
	}
	
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public double getNominalRate() {
		return nominalRate;
	}
	public int getDuration() {
		return duration;
	}
	public Date getStartDate() {
		return startDate;
	}
}
