'use strict';

var $ = require('jquery')
var REMOTE_BASE_URL = 'https://patrickkee.com'
var EventTypes = require('../constants/EventTypes');
var AppStates = require('../constants/AppStates');

var XhrRequests = {

  fetchAccount: function(email, callback, appState) {
    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts/" + email,
      dataType: 'json',
      type: 'GET',
      success: function(data) {
        appState.user = data;
        callback();     
      },
      error: function(xhr, status, err) {
        console.log(status.message);
        callback();
      }    
    });
  },

  persistAccount: function(account, callback, appState) {
    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts?" +
            "&accountName=default"+
            "&firstName="+account.firstName+
            "&lastName=default"+
            "&email="+account.email,
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: 'POST',
      success: function(data) {
        appState.user = data;
        callback();     
      },
      error: function(xhr, status, err) {
        appState.user = "";
        console.log(status.message);
        callback();
      }    
    });
  },

  fetchModels: function(callback, appState) {
    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/",
      dataType: 'json',
      type: 'GET',
      success: function(data) {
        appState.models = data;
        callback(); 
      },
      error: function(xhr, status, err) {
        appState.models = "";
        callback();
      }    
    });
  },

  createModel: function(model, callback, appState) {
    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/?" +
            "&modelName=" + model.modelName +
            "&description=" +
            "&initialValue=0" +
            "&targetValue=" + model.targetValue +
            "&startDate=" + "01/01/2000" +
            "&endDate=" + model.targetDate,
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: 'POST',
      success: function(data) {
        appState.currentModel = data;
        callback(); //If we were successful then reload all the models from the backend
      },
      error: function(xhr, status, err) {
        callback();
      }    
    });
  },

  updateModel: function(model, callback, appState) {
    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/" + model.modelId + "?" +
            "description=none" +
            "&initialValue=0" +
            "&targetValue=" + encodeURIComponent(model.targetValue) +
            "&startDate=" + encodeURIComponent("01/01/2000") +
            "&endDate=" + encodeURIComponent(model.endDate),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: 'POST',
      success: function(data) {
        appState.currentModel = data;
        callback(); 
      },
      error: function(xhr, status, err) {
        callback();
      }    
    });
  },

  deleteModel: function(model, callback, appState) {
    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/" + model.modelId,
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: 'DELETE',
      success: function(data) {
        appState.currentModel = {};
        callback(); 
      },
      error: function(xhr, status, err) {
        //TODO: Something intelligent on error
        console.log(status.message);
        callback();
      }    
    });
  },

  setDefaultModel: function(callback, appState) {
    appState.currentModel = (appState.models.length > 0) ? appState.models[0] : {}
    if (callback != undefined) { 
      callback();
    }
  },

  fetchEvents: function(model, callback, appState) {
    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/" + model.modelId + "/events",
      dataType: 'json',
      type: 'GET',
      success: function(data) {
        //Inject the EventType object in place of the type's name for expanded info
        var e;
        for (e in data) {
          data[e].type = EventTypes.get(data[e].type); 
        }
        appState.currentModel.events = data;
        callback();     
      },
      error: function(xhr, status, err) {
        appState.currentModel.events = [];
        callback();
      }    
    });
  },

  persistEvent: function(modelEvent, callback, appState) {
    //Prepare model for post by removing front end enhancement to the event.type
    //TODO: This seems to be a technical debt from deciding to modify the object on the client side. 
    //It'd be best to fix this by providing a functional interface to the type attribute, rather than modify the
    //base object, that way it could be persisted as is. Refactor this...
    var serverEvent = JSON.parse(JSON.stringify(modelEvent));
    serverEvent.type = serverEvent.type.type;

    $.ajax({
        url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/" + appState.currentModel.modelId + "/events/" + modelEvent.eventId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        type: 'POST',
        data: JSON.stringify(serverEvent),
        success: function(data) {
          callback();
        },
        error: function(xhr, status, err) {
          console.log(err.message);
          callback();
        }    
    });
  }

};

module.exports = XhrRequests;