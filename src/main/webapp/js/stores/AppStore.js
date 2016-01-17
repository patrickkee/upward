var AppDispatcher = require('../dispatcher/AppDispatcher');
var EventEmitter = require('events').EventEmitter;
var ActionTypes = require('../constants/ActionTypes');
var AppConstants = require('../constants/AppConstants');
var AppStates = require('../constants/AppStates');
var assign = require('object-assign');

var CHANGE_EVENT = 'change';

var username;
var authenticated = AppConstants.FALSE;
var currentViewState = AppStates.LOGIN_VIEW;

var AppStore = assign({}, EventEmitter.prototype, {

  getViewState: function() {
    return currentViewState;
  },

  getUsername: function() {
    return username;
  },

  isAuthenticated: function() {
    return authenticated;
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
      username = action.value;
      currentViewState = AppStates.CONTENT_VIEW;
      AppStore.emitChange();
      break;

    case ActionTypes.LOGOUT:
      username = "";
      currentViewState = AppStates.LOGIN_VIEW;
      AppStore.emitChange();
      break;

    default:
      // no op
  }
});

module.exports = AppStore;
