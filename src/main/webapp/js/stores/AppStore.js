var AppDispatcher = require('../dispatcher/AppDispatcher');
var EventEmitter = require('events').EventEmitter;
var ActionTypes = require('../constants/ActionTypes');
var AppConstants = require('../constants/AppConstants');
var AppStates = require('../constants/AppStates');
var assign = require('object-assign');
var $ = require ('jquery')
var CHANGE_EVENT = 'change';

var appState = {viewState: AppStates.LOGIN_VIEW,
                user: {email: "",
                       firstName: "",
                       password: ""}
               };

function fetchAccount(email, callback) {
  $.ajax({
    url: "http://www.patrickkee.com/api/accounts/" + email,
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
    url: "http://patrickkee.com/api/accounts?" +
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

//http://patrickkee.com/api/accounts?accountName=foobar2&firstName=patrick&lastName=kee&email=patrickpdk@gmail.com

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
      fetchAccount(action.value, 
                   function() {AppStore.emitChange()}
                  );
      break;

    case ActionTypes.LOGOUT:
      appState.user = "";
      appState.viewState = AppStates.LOGIN_VIEW;
      AppStore.emitChange();
      break;

    case ActionTypes.SIGNUP:
      persistAccount(action.value, 
                     function() {AppStore.emitChange()}
                    );
      break;

    default:
      // no op
  }
});

module.exports = AppStore;
