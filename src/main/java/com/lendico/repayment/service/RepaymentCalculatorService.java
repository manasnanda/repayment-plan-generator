package com.lendico.repayment.service;

import java.math.BigDecimal;

import com.lendico.repayment.exception.InvalidRequestException;

/**
 * @author Manas Ranjan Nanda
 * Repayment Calculate Service
 */
public interface RepaymentCalculatorService {
	
	BigDecimal getAnnuityAmount(int duration, double interestRate,BigDecimal amount) throws InvalidRequestException;

	BigDecimal getInstallmentInterest(double nominalRate, BigDecimal loanAmount);
	
	BigDecimal getPrinciple(BigDecimal annuityAmount, BigDecimal installmentInterest);
	
	BigDecimal getRemainingOutStanding(BigDecimal initialOutStanding, BigDecimal principal);

}
