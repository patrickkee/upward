var AppDispatcher = require('../dispatcher/AppDispatcher');
var ActionTypes = require('../stores/ActionTypes');

var AppActions = {

  login: function(username) {
  	var action = {
  					actionType: ActionTypes.LOGIN.key,
			      	value: username
			  	 }
	   AppDispatcher.dispatch(action);
  },
  
  logout: function() {
  	var action = {
  					actionType: ActionTypes.LOGOUT.key
			  	 }
	   AppDispatcher.dispatch(action);
  },

  signup: function(user) {
    var action = {
            actionType: ActionTypes.SIGNUP.key,
              value: user
           }
    AppDispatcher.dispatch(action);
  },

  selectModel: function(modelName) {
    var action = {
                  actionType: ActionTypes.SELECT_MODEL.key,
                  value: modelName
                }
    AppDispatcher.dispatch(action);
  },

  selectDefaultModel: function() {
    var action = {
                  actionType: ActionTypes.SELECT_DEFAULT_MODEL.key
                }
    AppDispatcher.dispatch(action);
  },

  createModel: function(model) {
    var action = {
                  actionType: ActionTypes.CREATE_MODEL.key,
                  value: model
                }
    AppDispatcher.dispatch(action);
  },

  updateModel: function(model) {
    var action = {
                  actionType: ActionTypes.UPDATE_MODEL.key,
                  value: model
                }
    AppDispatcher.dispatch(action);
  },

  deleteModel: function(model) {
    var action = {
                  actionType: ActionTypes.DELETE_MODEL.key,
                  value: model
                }
    AppDispatcher.dispatch(action);
  },

  updateModelEvent: function(modelEvent) {
    var action = {
                  actionType: ActionTypes.UPDATE_EVENT.key,
                  value: modelEvent
                }
    AppDispatcher.dispatch(action);
  },

  createModelEvent: function(modelEvent) {
    var action = {
                  actionType: ActionTypes.CREATE_EVENT.key,
                  value: modelEvent
                }
    AppDispatcher.dispatch(action);
  },

  deleteModelEvent: function(modelEvent) {
    var action = {
                  actionType: ActionTypes.DELETE_EVENT.key,
                  value: modelEvent
                }
    AppDispatcher.dispatch(action);
  },
};

module.exports = AppActions;
