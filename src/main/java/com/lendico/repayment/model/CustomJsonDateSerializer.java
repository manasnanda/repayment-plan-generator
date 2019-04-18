package com.lendico.repayment.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lendico.repayment.utils.RepaymentConstant;

/**
 * @author Manas Ranjan Nanda
 * Serialize date to date String
 * **/
public class CustomJsonDateSerializer extends JsonSerializer<Date>
{

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(RepaymentConstant.DATE_FORMAT);
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
	throws IOException, JsonProcessingException 
	{
		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);
	}
}