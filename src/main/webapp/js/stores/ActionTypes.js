'use strict';

var XHR = require('./XhrRequests');
var AppStates = require('../constants/AppStates');
var browserHistory = require('react-router').browserHistory;

var ActionTypes = {

	LOGIN: {
		key: "LOGIN",
		do: function(actionValue, callback, appState) {
			//Enforce the order of async callbacks to ensure that user information
			//is loaded before model and event info
			var fetchCurrentModelValues = function() {
				//Don't fetch events if no model is selected
				if (Object.keys(appState.currentModel).length > 0) {
				  XHR.fetchModelValues(appState.currentModel, callback, appState)
				} else {
				  callback();
				}
			}

			var fetchEvents = function() {
				//Don't fetch events if no model is selected
				if (Object.keys(appState.currentModel).length > 0) {
				  XHR.fetchEvents(appState.currentModel, fetchCurrentModelValues, appState)
				} else {
				  fetchCurrentModelValues();
				}
			};
			var setContentView = function() {
				appState.viewState = AppStates.CONTENT_VIEW;
				XHR.setDefaultModel(fetchEvents, appState);
			};
			var fetchModels = function() {
				//If the login was successful continue loading the account data, otherwise
				//set the login fail view state to prompt the user to signup
				if (appState.user.email != "") {
					XHR.fetchModels(setContentView, appState)
				} else { 
					appState.user = "";
					appState.viewState = AppStates.LOGIN_FAIL_VIEW;
					callback();
				}

			};

			//Sets the user's url to the default logged in view showing their models
			var setRouteUrl = function() {
				browserHistory.push('/models');
				fetchModels();
			};

			XHR.fetchAccount(actionValue, setRouteUrl, appState);
		}
	},

	LOGOUT: {
		key: "LOGOUT",
		do: function(actionValue, callback, appState) {
			//Do nothing for now, logic is simple enough to leave in store 
		}
	},

	SIGNUP: {
		key: "SIGNUP",
		do: function(actionValue, callback, appState) {
        appState.user = "";
        appState.viewState = AppStates.LOGIN_FAIL_VIEW;
        	var setViewState = function() 	{
        		if (appState.user != "") {
        			appState.viewState = AppStates.CONTENT_VIEW;
        		} else {
        			appState.viewState = AppStates.LOGIN_FAIL_VIEW;
        		};
        		callback();
        	}
			XHR.persistAccount(actionValue, setViewState, appState);
		}
	},

	SELECT_MODEL: {
		key: "SELECT_MODEL",
		do: function(actionValue, callback, appState) {
			//Find the given model in the collection of models
			var fetchCurrentModelValues = function() {
				//Don't fetch events if no model is selected
				if (Object.keys(appState.currentModel).length > 0) {
				  XHR.fetchModelValues(appState.currentModel, callback, appState)
				} else {
				  callback();
				}
			}

			var fetchEvents = function() { XHR.fetchEvents(appState.currentModel, fetchCurrentModelValues, appState) };
			var selectModel = function() {
				var m;
				for (m in appState.models) {
					if (appState.models[m].modelId == actionValue) {
					  appState.currentModel = appState.models[m];
					}
				}
				fetchEvents();
			}

			//TODO: Refactor this to fetch just the model we need rather than all of them, requires server side change
			XHR.fetchModels(selectModel, appState);	
		}
	},

	SELECT_DEFAULT_MODEL: {
		key: "SELECT_DEFAULT_MODEL",
		do: function(actionValue, callback, appState) {
			var fetchEvents = function() {
				if (Object.keys(appState.currentModel).length > 0) {
					XHR.fetchEvents(appState.currentModel, callback, appState);
				};
			}
			XHR.setDefaultModel(fetchEvents, appState)
		}
	},

	CREATE_MODEL: {
		key: "CREATE_MODEL",
		do: function(actionValue, callback, appState) {
			var fetchModels = function() { XHR.fetchModels(callback, appState); }; 
			XHR.createModel(actionValue, fetchModels, appState);
		}
	},

	UPDATE_MODEL: {
		key: "UPDATE_MODEL",
		do: function(actionValue, callback, appState) {
			//Store the events temporarily since we're replacing the parent model
			var tmpEvents = appState.currentModel.events; 
			var setEvents = function() {
							         appState.currentModel.events = tmpEvents;
							         callback();
							       };
			XHR.updateModel(actionValue, setEvents, appState);
		}
	},

	DELETE_MODEL: {
		key: "DELETE_MODEL",
		do: function(actionValue, callback, appState) {
			var setDefaultModel = function() { XHR.setDefaultModel(callback, appState); };
			var fetchModels = function() { XHR.fetchModels(setDefaultModel, appState); };
			XHR.deleteModel(actionValue, fetchModels, appState);
		}
	},

	UPDATE_EVENT: {
		key: "UPDATE_EVENT",
		do: function(actionValue, callback, appState) {
			var fetchCurrentModelValues = function() {
				//Don't fetch events if no model is selected
				if (Object.keys(appState.currentModel).length > 0) {
				  XHR.fetchModelValues(appState.currentModel, callback, appState)
				} else {
				  callback();
				}
			}

			//Fetch events after persisting to server to ensure appState is synchronized with server
			var fetchEvents = function() {
				XHR.fetchEvents(appState.currentModel, fetchCurrentModelValues, appState)
			};

			XHR.persistEvent(actionValue, fetchEvents, appState);
		}
	},

	CREATE_EVENT: {
		key: "CREATE_EVENT",
		do: function(actionValue, callback, appState) {
			var fetchCurrentModelValues = function() {
				//Don't fetch events if no model is selected
				if (Object.keys(appState.currentModel).length > 0) {
				  XHR.fetchModelValues(appState.currentModel, callback, appState)
				} else {
				  callback();
				}
			}

			//Fetch events after persisting to server to ensure appState is synchronized with server
			var fetchEvents = function() {
				XHR.fetchEvents(appState.currentModel, fetchCurrentModelValues, appState)
			};

			XHR.createEvent(actionValue, fetchEvents, appState);
		}
	},

	DELETE_EVENT: {
		key: "DELETE_EVENT",
		do: function(actionValue, callback, appState) {
			var fetchCurrentModelValues = function() {
				//Don't fetch events if no model is selected
				if (Object.keys(appState.currentModel).length > 0) {
				  XHR.fetchModelValues(appState.currentModel, callback, appState)
				} else {
				  callback();
				}
			}

			//Fetch events after persisting to server to ensure appState is synchronized with server
			var fetchEvents = function() {
				XHR.fetchEvents(appState.currentModel, fetchCurrentModelValues, appState)
			};

			XHR.deleteEvent(actionValue, fetchEvents, appState);
		}
	}
}

module.exports = ActionTypes;