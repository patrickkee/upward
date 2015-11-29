package com.patrickkee.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.google.common.base.Optional;
import com.patrickkee.model.account.Account;
import com.patrickkee.model.event.Event;
import com.patrickkee.model.model.type.Model;
import com.patrickkee.persistence.FinancialModelsDb;

@Path("accounts/{email}/models/{modelId}/events")
public class EventResource {

	@POST
	@Produces("application/json")
	public Response createEvent(@PathParam("email") String email, @PathParam("modelId") int modelId, Event event,
			@Context UriInfo uriInfo) {

		// Add the event to the model only if the account and model are found
		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		if (acct.isPresent() && acct.get().getModel(modelId).isPresent()) {
			Optional<Model> model = acct.get().getModel(modelId);

			model.get().addEvent(event);

			UriBuilder locationBuilder = uriInfo.getAbsolutePathBuilder();
			locationBuilder.path(Integer.toString(event.getEventId()));

			// Persist the account with the new event
			FinancialModelsDb.persistAccount(acct.get());
			return Response.created(locationBuilder.build()).entity(ResponseMessage.getNew("EVENT_CREATION_SUCCESSFUL",
					"The event was successfully created and persisted to the model")).build();

		} else {
			throw new WebApplicationException(404);
		}

	}
}