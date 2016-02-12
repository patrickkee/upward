var AppDispatcher = require('../dispatcher/AppDispatcher');
var EventEmitter = require('events').EventEmitter;
var ActionTypes = require('../constants/ActionTypes');
var AppConstants = require('../constants/AppConstants');
var AppStates = require('../constants/AppStates');
var assign = require('object-assign');
var $ = require('jquery')
var CHANGE_EVENT = 'change';
var REMOTE_BASE_URL = 'https://patrickkee.com'

var defaultAppState = { viewState: AppStates.LOGIN_VIEW,
                        user: {email: "",
                               firstName: "",
                               password: ""},
                        models: [],
                        currentModel: {}
                       };

var appState = defaultAppState;

function fetchAccount(email, callback) {
  $.ajax({
    url: REMOTE_BASE_URL + "/api/accounts/" + email,
    dataType: 'json',
    type: 'GET',
    success: function(data) {
      appState.user = data;
      appState.viewState = AppStates.CONTENT_VIEW;
      AppStore.persistToLocalStorage();
      callback();     
    },
    error: function(xhr, status, err) {
      appState.user = "";
      appState.viewState = AppStates.LOGIN_FAIL_VIEW;
      AppStore.persistToLocalStorage();
      callback();
    }    
  });
};

function persistAccount(account, callback) {
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
      appState.viewState = AppStates.CONTENT_VIEW;
      AppStore.persistToLocalStorage();
      callback();     
    },
    error: function(xhr, status, err) {
      appState.user = "";
      appState.viewState = AppStates.LOGIN_FAIL_VIEW;
      AppStore.persistToLocalStorage();
      callback();
    }    
  });
};

function fetchModels(callback) {
  $.ajax({
    url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/",
    dataType: 'json',
    type: 'GET',
    success: function(data) {
      appState.models = data;
      if (Object.keys(appState.currentModel).length < 1) {
          setDefaultModel();
      }

      AppStore.persistToLocalStorage();
      callback();     
    },
    error: function(xhr, status, err) {
      appState.models = "";
      appState.currentModel = "";
      appState.viewState = AppStates.LOGIN_FAIL_VIEW;
      AppStore.persistToLocalStorage();
      callback();
    }    
  });
};

function persistModel(model, callback) {
  $.ajax({
    url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models?" +
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
      fetchModels(callback); //If we were successful then reload all the models from the backend
    },
    error: function(xhr, status, err) {
      setDefaultModel();
      callback();
    }    
  });
};

function deleteModel(model, callback) {
  $.ajax({
    url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/" + model.modelId,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    type: 'DELETE',
    success: function(data) {
      appState.currentModel = "";
      fetchModels(callback); //If we were successful then reload all the models from the backend
    },
    error: function(xhr, status, err) {
      //TODO: Something intelligent on error
    }    
  });
};

function setDefaultModel() {
  appState.currentModel = (appState.models.length > 0) ? appState.models[0] : {}
};

function fetchEvents(callback) {
  //Don't fetch events if no model is selected
  if (Object.keys(appState.currentModel).length > 0) {

    $.ajax({
      url: REMOTE_BASE_URL + "/api/accounts/" + appState.user.email + "/models/" + appState.currentModel.modelId + "/events",
      dataType: 'json',
      type: 'GET',
      success: function(data) {
        appState.currentModel.events = data;
        callback();     
      },
      error: function(xhr, status, err) {
        appState.currentModel.events = [];
        callback();
      }    
    });

  }
};

//TODO: HARD CODED FOR PROTOTYPE ONLY, not functional
function persistEvent(callback) {
  $.ajax({
      url: "https://patrickkee.com/api/accounts/patrickpdk@gmail.com/models/-1048710664/events",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: 'POST',
      data: JSON.stringify({name:"event1", period:"MONTHLY", type:"RECURRING_DEPOSIT", startDate:"01/25/2010", endDate:"01/25/2020", value:200.0}),
      success: function(data) {
        console.log(data)
      },
      error: function(xhr, status, err) {
        console.log(err.message)
      }    
  });
};

var AppStore = assign({}, EventEmitter.prototype, {

  persistToLocalStorage: function() {
    localStorage.setItem("appState", JSON.stringify(appState));
  },

  loadFromLocalStorage: function() {
    appState = appState;
    console.log(JSON.parse(localStorage.getItem("appState")));
    //appState = (localStorage.getItem("appState") === null ? appState : localStorage.getItem("appState");
  },

  getViewState: function() {
    return appState.viewState;
  },

  getUser: function() {
    return appState.user;
  },

  getUsername: function() {
    return appState.user.email;
  },

  getCurrentModel: function() {
    return appState.currentModel;
  },

  getModels: function(callback) {
    return appState.models;
  },

  emitChange: function() {
    this.emit(CHANGE_EVENT);
  },

  /**
   * @param {function} callback
   */
  addChangeListener: function(callback) {
    this.on(CHANGE_EVENT, callback);
  },

  /**
   * @param {function} callback
   */
  removeChangeListener: function(callback) {
    this.removeListener(CHANGE_EVENT, callback);
  }
});

// Register callback to handle all updates
AppDispatcher.register(function(action) {
  var text;
 
  switch(action.actionType) {
    case ActionTypes.LOGIN:
      //Enforce the order of async callbacks to ensure that user information
      //is loaded before model and event info
      var func0 = function() {AppStore.emitChange()};
      var func1 = function() {fetchEvents(func0)};
      var func2 = function() {fetchModels(func1)};
      fetchAccount(action.value,func2);
      break;

    case ActionTypes.LOGOUT:
      appState = defaultAppState;
      appState.viewState = AppStates.LOGIN_VIEW;
      AppStore.emitChange();
      break;

    case ActionTypes.SIGNUP:
      persistAccount(action.value, 
                     function() {AppStore.emitChange()}
                    );
      break;

    case ActionTypes.SELECT_MODEL:
      //Find the given model in the collection of models
      for (m in appState.models) {
        if (appState.models[m].name == action.value) {
          appState.currentModel = appState.models[m];
        }
      }
      AppStore.emitChange();
      break;

    case ActionTypes.SELECT_DEFAULT_MODEL:
      setDefaultModel();
      AppStore.emitChange();
      break;

    case ActionTypes.CREATE_MODEL:
      persistModel(action.value, 
                   function() {AppStore.emitChange()}
                  );
      break;

    case ActionTypes.UPDATE_MODEL:
      persistModel(action.value, 
                   function() {AppStore.emitChange()}
                  );
      break;

    case ActionTypes.DELETE_MODEL:
      var func0 = function() {AppStore.emitChange()};
      var func1 = function() {fetchModels(func0)};
      deleteModel(action.value,func1);
      break;

    default:
      // no op
  }
});

module.exports = AppStore;