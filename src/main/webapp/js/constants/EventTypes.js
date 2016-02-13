var keyMirror = require('keymirror');

var Icons = require('../constants/Icons');
var EventTypes = {

	NEW_EVENT: {
		type: "NEW_EVENT",
		pretty: "New Event",
		icon: Icons.NEW_EVENT
	},

	ACTUAL: {
		type: "ACTUAL",
		pretty: "Actual",
		icon: Icons.THUMBTACK
	},

	RECURRING_YIELD: {
		type: "RECURRING_YIELD",
		pretty: "Recurring Yield",
		icon: Icons.RECUR_PCT
	},

	RECURRING_DEPOSIT: {
		type: "RECURRING_DEPOSIT",
		pretty: "Recurring Deposit",
		icon: Icons.RECUR_DOLLAR
	},	

	ONE_TIME_DEPOSIT: {
		type: "ONE_TIME_DEPOSIT",
		pretty: "One-Time Deposit",
		icon: Icons.MONEYBAG
	},

	get: function(eventType) {
		switch(eventType) {
    		case "NEW_EVENT":
    			return this.NEW_EVENT;
    		case "ACTUAL":
    			return this.ACTUAL;
    		case "RECURRING_YIELD":
    			return this.RECURRING_YIELD;
    		case "RECURRING_DEPOSIT":
    			return this.RECURRING_DEPOSIT;
    		case "ONE_TIME_DEPOSIT":
    			return this.ONE_TIME_DEPOSIT;    			    			
    		default:
    			//No-op
    	}
	},

}

module.exports = EventTypes; 