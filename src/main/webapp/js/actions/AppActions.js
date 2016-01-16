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
  
};

module.exports = AppActions;
