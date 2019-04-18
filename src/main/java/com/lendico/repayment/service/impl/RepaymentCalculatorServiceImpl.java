package com.lendico.repayment.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.lendico.repayment.exception.InvalidRequestException;
import com.lendico.repayment.service.RepaymentCalculatorService;
import com.lendico.repayment.utils.RepaymentConstant;

/**
 * @author Manas Ranjan Nanda
 * Calculate Annuity Amount, Installment Interest, Principal and Remaining Outstanding Principal
 * **/
@Service
public class RepaymentCalculatorServiceImpl implements RepaymentCalculatorService 
{

	/**
	 * This method calculate the annuity amount for given amount, interest and
	 * duration
	 * 
	 * @param duration     - No of installments in months
	 * @param interestRate - Nominal interest rate
	 * @return BigDecimal - Annuity Amount
	 * @throws InvalidRequestException 
	 **/

	@Override
	public BigDecimal getAnnuityAmount(int duration, double interestRate, BigDecimal amount) throws InvalidRequestException 
	{

		try {
			/** Interest Rate by month as percentage **/
			double interestByMonth = interestRate / (12 * 100);

			/** calculate the annuity amount based on given formula **/
			double annuityAmount = (amount.doubleValue() * interestByMonth)
					/ (1 - Math.pow(1 + interestByMonth, -duration));
			
			/** Round of the calculated amount to 2 decimal **/
			return new BigDecimal(annuityAmount).setScale(2, RepaymentConstant.ROUND_HALF_EVEN);
		} catch (NumberFormatException e) {
			throw new InvalidRequestException("Invalid request parametes");
		}

	}

	/**
	 * This method calculate Installment Interest for given interest rate and amount
	 * 
	 * @param nominalRate - Interest Rate per Year
	 * @param amount      - Loan Amount
	 * @return BigDecimal - Installment Interest
	 **/
	@Override
	public BigDecimal getInstallmentInterest(double nominalRate, BigDecimal amount) 
	{

		int daysInMonth = 30;

		int daysInYear = 360;

		/** Interest Rate as percentage **/
		nominalRate = nominalRate / 100;

		/** calculate the InstallmentInterest by month based on given formula **/
		BigDecimal interest = new BigDecimal(nominalRate * daysInMonth * amount.doubleValue() / daysInYear);

		/**
		 * If calculated interest exceeded initialOutStandingPrincipal, set
		 * initialOutStandingPrinicipal as interest
		 * 
		 **/
		if (interest.compareTo(amount) > 0)
			interest = amount;

		/** Round of the calculated amount to 2 decimal **/
		interest = interest.setScale(2, RepaymentConstant.ROUND_HALF_EVEN);

		return interest.setScale(2, RepaymentConstant.ROUND_HALF_EVEN);

	}

	/**
	 * Calculate Principal for given loan amount and installment interest
	 * 
	 * @param annuityAmount       - Annuity Amount
	 * @param installmentInterest - Installment Interest
	 * @return BigDecimal - Principal Amount
	 * 
	 **/
	@Override
	public BigDecimal getPrinciple(BigDecimal annuityAmount, BigDecimal installmentInterest) 
	{

		/** Calculate Prinical based on given formula **/
		BigDecimal principal = annuityAmount.subtract(installmentInterest);

		principal = principal.setScale(2, RepaymentConstant.ROUND_HALF_EVEN);

		return principal;

	}

	/**
	 * Calculate Remaining Outstanding Principal
	 * 
	 * @param initialOutStanding - Initial Outstanding Principal
	 * @param principal          - Principal
	 * @return BigDecimal - Remaining Outstanding Principal
	 * 
	 **/
	@Override
	public BigDecimal getRemainingOutStanding(BigDecimal initialOutStanding, BigDecimal principal) 
	{

		/** Calculate Remaining OutStanding Principal **/
		BigDecimal remainingOSP = initialOutStanding.subtract(principal);

		/** Return Zero in case of negative value **/
		if (remainingOSP.compareTo(BigDecimal.ZERO) <= 0)
			remainingOSP = BigDecimal.ZERO;
		else
			remainingOSP = remainingOSP.setScale(2, RepaymentConstant.ROUND_HALF_EVEN);

		return remainingOSP;

	}

}
