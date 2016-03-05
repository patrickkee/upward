'use strict';

var AppDispatcher = require('../dispatcher/AppDispatcher');
var EventEmitter = require('events').EventEmitter;
var ActionTypes = require('./ActionTypes');
var EventTypes = require('../constants/EventTypes');
var AppConstants = require('../constants/AppConstants');
var AppStates = require('../constants/AppStates');
var assign = require('object-assign');
var $ = require('jquery')

var CHANGE_EVENT = 'change';
var REMOTE_BASE_URL = 'https://patrickkee.com'

var appState = { viewState: AppStates.LOGIN_VIEW,
                  user: {email: "fooBar@gmail.com",
                         firstName: "",
                         password: ""},
                  models: [],
                  currentModel: {}
                };

var AppStore = assign({}, EventEmitter.prototype, {

  getAppState: function() {
    return JSON.parse(JSON.stringify(appState));
  },

  emitChange: function() {
    this.emit(CHANGE_EVENT);
  },

  addChangeListener: function(callback) {
    this.on(CHANGE_EVENT, callback);
  },

  removeChangeListener: function(callback) {
    this.removeListener(CHANGE_EVENT, callback);
  }
});

// Register callback to handle all updates
AppDispatcher.register(function(action) {
  var emitChange = function() {AppStore.emitChange()};

  switch(action.actionType) {
    case ActionTypes.LOGIN.key:
         ActionTypes.LOGIN.do(action.value, emitChange, appState);
      break;

    case ActionTypes.LOGOUT.key:
         appState = appStateHist[0];
         appState.viewState = AppStates.LOGIN_VIEW;
         emitChange();
      break;

    case ActionTypes.SIGNUP.key:
         ActionTypes.SIGNUP.do(action.value, emitChange, appState);
      break;

    case ActionTypes.SELECT_MODEL.key:
         ActionTypes.SELECT_MODEL.do(action.value, emitChange, appState);
      break;

    case ActionTypes.SELECT_DEFAULT_MODEL.key:
         ActionTypes.SELECT_DEFAULT_MODEL.do(action.value, emitChange, appState);
      break;

    case ActionTypes.CREATE_MODEL.key:
         ActionTypes.CREATE_MODEL.do(action.value, emitChange, appState);
      break;

    case ActionTypes.UPDATE_MODEL.key:
         ActionTypes.UPDATE_MODEL.do(action.value, emitChange, appState);
      break;

    case ActionTypes.DELETE_MODEL.key:
         ActionTypes.DELETE_MODEL.do(action.value, emitChange, appState);
      break;

    case ActionTypes.UPDATE_EVENT.key:
         ActionTypes.UPDATE_EVENT.do(action.value, emitChange, appState);
      break;

    case ActionTypes.CREATE_EVENT.key:
         ActionTypes.CREATE_EVENT.do(action.value, emitChange, appState);
      break;

    case ActionTypes.DELETE_EVENT.key:
         ActionTypes.DELETE_EVENT.do(action.value, emitChange, appState);
      break;      
    default:
      // no op
  }
});

module.exports = AppStore;