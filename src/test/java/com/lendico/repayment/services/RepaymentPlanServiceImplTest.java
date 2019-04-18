package com.lendico.repayment.services;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.lendico.repayment.exception.InvalidRequestException;
import com.lendico.repayment.model.Repayment;
import com.lendico.repayment.service.RepaymentPlanService;
import com.lendico.repayment.service.impl.RepaymentCalculatorServiceImpl;
import com.lendico.repayment.service.impl.RepaymentPlanServiceImpl;
import com.lendico.repayment.utils.RepaymentConstant;

@RunWith(MockitoJUnitRunner.class)
public class RepaymentPlanServiceImplTest 
{

private RepaymentPlanService repaymentPlanService;
	
	@Before
	public void setUp()
	{		
		repaymentPlanService = new RepaymentPlanServiceImpl(new RepaymentCalculatorServiceImpl());
	}
	
	@Test
	public void generatePlan_returnRepaymentPlan() throws ParseException, InvalidRequestException
	{
		int duration = 24;
		double nominalRate = 5.0;
		BigDecimal amount = new BigDecimal(5000);
		Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2019");
		BigDecimal expectedAnnuityAmount = new BigDecimal(219.36).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		
		List<Repayment> repaymentPlan = repaymentPlanService.generatePlan(amount, duration, nominalRate, startDate);
		
		assertThat(repaymentPlan.size()).isEqualTo(duration);
		
		assertThat(repaymentPlan.get(0).getBorrowerPaymentAmount()).isEqualTo(expectedAnnuityAmount );
		
	}
	
	@Test(expected=InvalidRequestException.class)
	public void generatePlan_InvalidRequestException() throws ParseException, InvalidRequestException
	{
		int duration = 24;
		double nominalRate = 0;
		BigDecimal amount = new BigDecimal(5000);
		Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2019");
		
		repaymentPlanService.generatePlan(amount, duration, nominalRate, startDate);
		
				
	}
	
	@Test
	public void generatePlan_returnEmptyRepaymentPlan() throws ParseException, InvalidRequestException
	{
		int duration = 24;
		double nominalRate = 5.0;
		BigDecimal amount = new BigDecimal(0);
		Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2019");
		
		List<Repayment> repaymentPlan = repaymentPlanService.generatePlan(amount, duration, nominalRate, startDate);
		
		assertThat(repaymentPlan.size()).isEqualTo(0);
		
	}
	
}
