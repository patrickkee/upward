package com.patrickkee.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.google.common.base.Optional;
import com.patrickkee.model.account.Account;
import com.patrickkee.model.event.Event;
import com.patrickkee.model.model.Model;
import com.patrickkee.persistence.FinancialModelsDb;

@Path("accounts/{email}/models/{modelId}/events")
public class EventResource {

	/**
	 * Creates a new event in a given model 
	 * @param email
	 * @param modelId
	 * @param event
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEvent(@PathParam("email") String email, @PathParam("modelId") int modelId, Event event,
			@Context UriInfo uriInfo) {

		// Add the event to the model only if the account and model are found
		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		if (acct.isPresent() && acct.get().getModel(modelId).isPresent() && event != null) {
			Optional<Model> model = acct.get().getModel(modelId);

			model.get().addOrUpdateEvent(event);

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
	
	@Path("/{eventId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEvent(@PathParam("email") String email, @PathParam("modelId") int modelId, 
								@PathParam("eventId") int eventId, Event updatedEvent, @Context UriInfo uriInfo) {

		// Update the event in model only if the account, model, and event are found
		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		if (acct.isPresent() && acct.get().getModel(modelId).isPresent() && 
			acct.get().getModel(modelId).get().getEvent(eventId) != null && updatedEvent != null) {
			Optional<Model> model = acct.get().getModel(modelId);

			model.get().addOrUpdateEvent(updatedEvent);

			UriBuilder locationBuilder = uriInfo.getAbsolutePathBuilder();
			locationBuilder.path(Integer.toString(updatedEvent.getEventId()));

			// Persist the account with the new event
			FinancialModelsDb.persistAccount(acct.get());
			return Response.created(locationBuilder.build()).entity(ResponseMessage.getNew("EVENT_UPDATE_SUCCESSFUL",
					"The event was successfully updated and persisted to the model")).build();
		} else {
			throw new WebApplicationException(404);
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvent(@PathParam("email") String email, @PathParam("modelId") int modelId) {

		// Add the event to the model only if the account and model are found
		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		if (acct.isPresent() && acct.get().getModel(modelId).isPresent()) {
			//List<Model> modelList = new ArrayList<Model>(acct.get().getModels().values());
			List<Event> eventList = new ArrayList<Event>(acct.get().getModel(modelId).get().getEvents().values());

			return Response.ok().entity(eventList).build();

		} else {
			throw new WebApplicationException(404);
		}
	}
}
