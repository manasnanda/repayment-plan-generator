package com.lendico.repayment.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.repayment.dto.RepaymentRequest;
import com.lendico.repayment.exception.InvalidRequestException;
import com.lendico.repayment.model.Repayment;
import com.lendico.repayment.service.RepaymentPlanService;

/**
 * @author Manas Ranjan Nanda
 * Generate Repyament Plan for Loan will be routed by this controller
 * **/
@RestController
@RequestMapping("v1/generate-plan")
public class RepaymentPlanController 
{
	private static final Logger LOG = LoggerFactory.getLogger(RepaymentPlanController.class);
	
	@Autowired
	private RepaymentPlanService planService;
	
	
	/**
	 * Generate Repayment Plan
	 * @param RepaymentRequest
	 * @return List of RepaymentDTO
	 * **/
	@PostMapping
	public List<Repayment> generatePlan(@Valid @RequestBody RepaymentRequest repaymentReq) throws InvalidRequestException
	{
		LOG.info("Received Request to generate Repayment Plan");
		
		BigDecimal amount = repaymentReq.getLoanAmount();
		int duration = repaymentReq.getDuration();
		double nominalRate = repaymentReq.getNominalRate();
		Date startDate = repaymentReq.getStartDate();
		
		return planService.generatePlan(amount, duration, nominalRate, startDate);
	}
}
