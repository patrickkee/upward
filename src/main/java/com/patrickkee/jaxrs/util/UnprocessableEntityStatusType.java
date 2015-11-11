package com.patrickkee.jaxrs.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

public class UnprocessableEntityStatusType implements StatusType {

		private UnprocessableEntityStatusType() { }
	
	    @Override
	    public int getStatusCode() {
	        return 422;
	    }

	    @Override
	    public String getReasonPhrase() {
	        return "Unprocessable Entity";
	    }

	    @Override
	    public Response.Status.Family getFamily() {
	        return Response.Status.Family.CLIENT_ERROR;
	    }
	    
	    public static UnprocessableEntityStatusType getNew() {
	    	return new UnprocessableEntityStatusType();
	    }
	
}
