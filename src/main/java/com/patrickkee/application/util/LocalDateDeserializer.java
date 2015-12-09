package com.patrickkee.application.util;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) {
		ObjectCodec oc = p.getCodec();
	    JsonNode node = null;
		try {
			node = oc.readTree(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    DateTime dt = formatter.parseDateTime(node.asText());
		return new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
	}

}