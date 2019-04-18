package com.lendico.repayment.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.lendico.repayment.exception.InvalidRequestException;
import com.lendico.repayment.service.RepaymentCalculatorService;
import com.lendico.repayment.service.impl.RepaymentCalculatorServiceImpl;
import com.lendico.repayment.utils.RepaymentConstant;

@RunWith(MockitoJUnitRunner.class)
public class RepaymentCalculatorImplTest 
{
	
	private RepaymentCalculatorService repaymentCalculatorService;
	
	@Before
	public void setUp()
	{
		repaymentCalculatorService = new RepaymentCalculatorServiceImpl();
	}
	
	@Test
	public void getAnnuityAmount_returnAnnuityAmount() throws InvalidRequestException
	{
		int duration = 24;
		double interestRate = 5.0;
		BigDecimal amount = new BigDecimal(5000);
		BigDecimal expectedAnnuityAmount = new BigDecimal(219.36).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		
		BigDecimal annuityAmount = repaymentCalculatorService.getAnnuityAmount(duration, interestRate, amount);
		
		assertThat(annuityAmount).isEqualTo(expectedAnnuityAmount);
	}
	
	
	
	
	@Test
	public void getInstallmentInterest_returnInstallmentInterest() throws InvalidRequestException
	{
		
		double nominalRate = 5.0;
		BigDecimal amount = new BigDecimal(5000);
		BigDecimal expectedAnnuityAmount = new BigDecimal(20.83).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		
		BigDecimal installmentInterest = repaymentCalculatorService.getInstallmentInterest(nominalRate, amount);
		
		assertThat(installmentInterest).isEqualTo(expectedAnnuityAmount);
	}
	
	@Test
	public void getPrinciple_returnPrincipal() throws InvalidRequestException
	{
		
		BigDecimal annuity = new BigDecimal(219.36).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		BigDecimal installmentInterest = new BigDecimal(20.83).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		BigDecimal expectedPrincipal = new BigDecimal(198.53).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		
		BigDecimal principal = repaymentCalculatorService.getPrinciple(annuity, installmentInterest);
		
		assertThat(principal).isEqualTo(expectedPrincipal);
	}
	
	@Test
	public void getRemainingOutStanding_returnRemainingOutstandingPrincipal() 
	{
		
		BigDecimal initialOutStanding = new BigDecimal(5000).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		BigDecimal principal = new BigDecimal(198.53).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		BigDecimal expectedPrincipal = new BigDecimal(4801.47).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		
		BigDecimal remainingOutstandingPrincipal = repaymentCalculatorService.getRemainingOutStanding(initialOutStanding, principal);
		
		assertThat(remainingOutstandingPrincipal).isEqualTo(expectedPrincipal);
	}
	
	@Test
	public void getRemainingOutStanding_returnZeroAsRemainOutStanding() 
	{
		
		BigDecimal initialOutStanding = new BigDecimal(100).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		BigDecimal principal = new BigDecimal(198.53).setScale(2,RepaymentConstant.ROUND_HALF_EVEN);
		BigDecimal expectedPrincipal = new BigDecimal(0);
		
		BigDecimal remainingOutstandingPrincipal = repaymentCalculatorService.getRemainingOutStanding(initialOutStanding, principal);
		
		assertThat(remainingOutstandingPrincipal).isEqualTo(expectedPrincipal);
	}
	
	
}
