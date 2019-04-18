package com.lendico.repayment.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lendico.repayment.exception.InvalidRequestException;
import com.lendico.repayment.model.Repayment;

/**
 * @author Manas Ranjan Nanda
 * 
 *  Repayment Plan Generator Service
 */
public interface RepaymentPlanService 
{

	List<Repayment> generatePlan(BigDecimal amount,int duration,double nominalRate,Date startDate) throws InvalidRequestException;

}
