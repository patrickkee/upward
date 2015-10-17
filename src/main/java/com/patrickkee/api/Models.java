package com.patrickkee.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.patrickkee.model.Account;
import com.patrickkee.model.Model;
import com.patrickkee.model.ResponseMessage;
import com.patrickkee.model.impl.SavingsForecastModel;
import com.patrickkee.persistence.AccountsDb;

@Path("accounts/{accountId}/models")
public class Models {

	@POST
	@Produces("application/json")
	public Account createModel(@PathParam("accountId") int accountId, 
							   @QueryParam("modelName") String modelName,
							   @QueryParam("description") String description,
							   @QueryParam("initialValue") BigDecimal initialValue,
							   @QueryParam("targetValue") BigDecimal targetValue,
							   @QueryParam("startDate") String startDate,
							   @QueryParam("endDate") String endDate) 
	{
		Account acct = AccountsDb.getAccountById(accountId);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		acct.addModel(savingsForecastModel);
		AccountsDb.persistAccount(acct);
		return AccountsDb.getAccountById(acct.getAccountId());
	}
	
	@DELETE
	@Path("/{modelId}")
	@Produces("application/json")
	public ResponseMessage removeModel(@PathParam("accountId") int accountId, 
									   @PathParam("modelId") int modelId) 
	{
		Account acct = AccountsDb.getAccountById(accountId);
		acct.removeModel(modelId);
		
		if (null == acct.getModel(modelId)) {
			return new ResponseMessage("Model successfully removed");
		} else {
			return new ResponseMessage("Model could not be removed");
		}
		
	}
	
}
