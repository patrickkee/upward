package com.patrickkee.resources;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import com.google.common.base.Optional;
import com.patrickkee.model.Model;
import com.patrickkee.model.impl.Account;
import com.patrickkee.model.impl.ResponseMessage;
import com.patrickkee.model.impl.SavingsForecastModel;
import com.patrickkee.persistence.AccountsDb;

@Path("accounts/{email}/models")
public class ModelResource {

	@POST
	@Produces("application/json")
	public Account createModel(@PathParam("email") String email, 
							   @QueryParam("modelName") String modelName,
							   @QueryParam("description") String description,
							   @QueryParam("initialValue") BigDecimal initialValue,
							   @QueryParam("targetValue") BigDecimal targetValue,
							   @QueryParam("startDate") String startDate,
							   @QueryParam("endDate") String endDate) 
	{
		Optional<Account> acct = AccountsDb.getAccountByEmail(email);
		Model savingsForecastModel = null;
		try {
			savingsForecastModel = SavingsForecastModel.newModel()
															 .name(modelName)
															 .description(description)
															 .initialValue(initialValue)
															 .targetValue(targetValue)
															 .startDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate))
															 .endDate(new SimpleDateFormat("dd/MM/yyyy").parse(endDate));
		} catch (ParseException e) {
			//Throw HTTP422, unprocessable entity error
			throw new WebApplicationException(422);
		}

		if (acct.isPresent()) {
			acct.get().addModel(savingsForecastModel);
			AccountsDb.persistAccount(acct.get());
			return AccountsDb.getAccountByEmail(email).get();
		} else {
			//Throw HTTP404, not found error
			throw new WebApplicationException(404);
		}
		
		
	}
	
	@DELETE
	@Path("/{modelId}")
	@Produces("application/json")
	public ResponseMessage removeModel(@PathParam("email") String email, 
									   @PathParam("modelId") int modelId) 
	{
		Optional<Account> acct = AccountsDb.getAccountByEmail(email);
		
		if (acct.isPresent()) {
			acct.get().removeModel(modelId);	
		} else {
			//Throw HTTP404, not found error
			throw new WebApplicationException(404); 
		}
		
		
		if (null == acct.get().getModel(modelId)) {
			return new ResponseMessage("Model successfully removed");
		} else {
			return new ResponseMessage("Model could not be removed");
		}
		
	}
	
}
