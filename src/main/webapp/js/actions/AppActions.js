var AppDispatcher = require('../dispatcher/AppDispatcher');
var ActionTypes = require('../constants/ActionTypes');

var AppActions = {

  login: function(username) {
  	var action = {
  					actionType: ActionTypes.LOGIN,
			      	value: username
			  	 }
	   AppDispatcher.dispatch(action);
  },
  
  logout: function() {
  	var action = {
  					actionType: ActionTypes.LOGOUT
			  	 }
	   AppDispatcher.dispatch(action);
  },

  signup: function(user) {
    var action = {
            actionType: ActionTypes.SIGNUP,
              value: user
           }
    AppDispatcher.dispatch(action);
  },

  selectModel: function(modelName) {
    var action = {
                  actionType: ActionTypes.SELECT_MODEL,
                  value: modelName
                }
    AppDispatcher.dispatch(action);
  },

  selectDefaultModel: function() {
    var action = {
                  actionType: ActionTypes.SELECT_DEFAULT_MODEL
                }
    AppDispatcher.dispatch(action);
  },

  createModel: function(model) {
    var action = {
                  actionType: ActionTypes.CREATE_MODEL,
                  value: model
                }
    AppDispatcher.dispatch(action);
  },

};

module.exports = AppActions;
