package com.lendico.repayment.dto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lendico.repayment.utils.RepaymentConstant;

/**
 * @author Manas Ranjan Nanda
 * Deserialize date string to date
 * **/
public class CustomJsonDateDeserializer extends JsonDeserializer<Date>
{
    @Override
    public Date deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        SimpleDateFormat format = new SimpleDateFormat(RepaymentConstant.DATE_FORMAT);
        String date = jsonParser.getText();
        
        try 
        {
            return format.parse(date);
        } catch (ParseException e) 
        {
            throw new RuntimeException("Invalid Start Date");
        }

    }

}