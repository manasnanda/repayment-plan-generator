package com.lendico.repayment.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.repayment.dto.RepaymentRequest;
import com.lendico.repayment.model.Repayment;
import com.lendico.repayment.service.RepaymentPlanService;
import com.lendico.repayment.utils.RepaymentConstant;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RepaymentPlanController.class)
public class RepaymentPlanControllerTest 
{

	@Autowired
	MockMvc mockMvc;

	@MockBean
	RepaymentPlanService repaymentService;

	@InjectMocks
	RepaymentPlanController repaymentPlanController;

	private final String END_POINT = "/v1/generate-plan";

	private ObjectMapper om = null;
    
    
    
    @Before
    public void setup(){
        
        this.om = new ObjectMapper();
        this.om.setDateFormat(new SimpleDateFormat(RepaymentConstant.DATE_FORMAT));
      //  repaymentPlanController = new RepaymentPlanController(repaymentService);
    }
    
    
	@Test
	public void generatePlan_returnRepaymentPlan() throws Exception 
	{
		int duration = 24;
		double nominalRate = 5.0;
		BigDecimal amount = new BigDecimal(5000);
		Date startDate = new SimpleDateFormat(RepaymentConstant.DATE_FORMAT).parse("2018-01-01T00:00:01Z");
		RepaymentRequest repaymentDTO = new RepaymentRequest(amount,nominalRate,duration,startDate);
			
		BigDecimal borrowerPaymentAmount = new BigDecimal(219.36).setScale(2,BigDecimal.ROUND_HALF_EVEN);
		BigDecimal remaningOS = new BigDecimal(4801.47).setScale(2,BigDecimal.ROUND_HALF_EVEN);
		BigDecimal interest = new BigDecimal(20.83).setScale(2,BigDecimal.ROUND_HALF_EVEN);
		BigDecimal principal = new BigDecimal(198.53).setScale(2,BigDecimal.ROUND_HALF_EVEN);
		List<Repayment> repayments = new ArrayList<Repayment>();
		repayments.add(new Repayment(borrowerPaymentAmount, startDate, amount, interest, principal, remaningOS));
		
		when(repaymentService.generatePlan(amount, duration, nominalRate, startDate)).thenReturn(repayments);

		 mockMvc.perform(MockMvcRequestBuilders.post(END_POINT)
		            .content(asJsonString(repaymentDTO))
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$[0].borrowerPaymentAmount ",  equalTo(219.36)));      
	}
	
	 private String asJsonString(final Object obj) {
	        try {
	        	 return om.writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

}
