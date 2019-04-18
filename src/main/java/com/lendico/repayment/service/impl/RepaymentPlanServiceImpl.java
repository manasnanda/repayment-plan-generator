package com.lendico.repayment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lendico.repayment.exception.InvalidRequestException;
import com.lendico.repayment.model.Repayment;
import com.lendico.repayment.service.RepaymentCalculatorService;
import com.lendico.repayment.service.RepaymentPlanService;
import com.lendico.repayment.utils.RepaymentConstant;

/**
 * @author Manas Ranjan Nanda
 * Generate Repayments Plan
 * **/
@Service
public class RepaymentPlanServiceImpl implements RepaymentPlanService 
{

	private RepaymentCalculatorService repaymentCalculator;

	@Autowired
	public RepaymentPlanServiceImpl(final RepaymentCalculatorService repayCalculator) 
	{
		this.repaymentCalculator = repayCalculator;
	}

	/** Generate repayment plan
	 * 
	 *  @param amount - Loan Amount
	 *  @param nominialRate - Nominal Interest Rate
	 *  @param startDate - Start Date
	 *  @param duration - Remaining duration
	 *  @throws InvalidRequestException
	 *  @return Repayment
	 *  **/	
	@Override
	public List<Repayment> generatePlan(BigDecimal amount,int duration,double nominalRate,Date startDate)  throws InvalidRequestException
	{

		List<Repayment> repaymentList = new ArrayList<Repayment>();
		
		if(amount.compareTo(BigDecimal.ZERO)==0)
			return repaymentList;

		/** Setting calendar to input date **/
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(startDate);
		calDate.set(Calendar.SECOND,0);
		
		
		/** Initial Out Standing Principal **/
		BigDecimal initialOSPrincipal = amount.setScale(2, RepaymentConstant.ROUND_HALF_EVEN);

		for (int i = 0; i < duration; i++) 
		{
			
			Repayment repay = generateRepayment(calDate.getTime(), nominalRate, initialOSPrincipal, duration-i);
			
			repaymentList.add(repay);

			/**
			 * Roll Up calendar by month
			 **/
			calDate.add(Calendar.MONTH, 1);

			/** Setting new initial out standing amount **/
			initialOSPrincipal = repay.getRemainingOutStandingPrincipal();
		}

		return repaymentList;
	}

	/** Generate repayment 
	 * 
	 *  @param date - Installment Date
	 *  @param nominialRate - Nominal Interest Rate
	 *  @param initialOutStandging - Initial Outstanding Principal
	 *  @param duration - Remaining duration
	 *  @throws InvalidRequestException
	 *  @return Repayment
	 *  **/
	private Repayment generateRepayment(Date date, double nominalRate, BigDecimal initialOutStanding, int duration) throws InvalidRequestException 
	{
		
				
		BigDecimal annuityAmount = repaymentCalculator.getAnnuityAmount(duration, nominalRate, initialOutStanding);

		BigDecimal interest = repaymentCalculator.getInstallmentInterest(nominalRate, initialOutStanding);

		BigDecimal principal = repaymentCalculator.getPrinciple(annuityAmount, interest);

		BigDecimal remainingOSPrincipal = repaymentCalculator.getRemainingOutStanding(initialOutStanding, principal);
	
		return new Repayment(annuityAmount, date, initialOutStanding, interest,principal,
				remainingOSPrincipal);

	}

	
	
}
