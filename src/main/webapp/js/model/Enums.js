'use strict';

var Enums = {};

Enums.Event = {};

Enums.Event.Type = {};

Enums.Event.Period = {

	MONTHLY: {
		type: "MONTHLY",
		pretty: "Monthly"
	},

	POINT_IN_TIME: {
		type: "POINT_IN_TIME",
		pretty: "Point In Time"
	},

	get: function(eventPeriod) {
		switch(eventPeriod) {
    		case "MONTHLY":
    			return this.MONTHLY;
    			break;
    		case "POINT_IN_TIME":
    			return this.POINT_IN_TIME;
    			break;    			
    	default:
    		//Nothing
    	}
    },

    getAll: function() {
    	var periods = [];
    	for (var e in this) {
	      if( this.hasOwnProperty(e) && 
	          !(this[e] && this[e].constructor && this[e].call && this[e].apply) ) {
	        	periods.push(this[e]);
	      } 
	    }  
	    return periods; 
    }

}


module.exports = Enums; 