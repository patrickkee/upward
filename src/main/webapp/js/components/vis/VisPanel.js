'use strict';

var React = require('react');
global.Highcharts = require("highcharts");  
var ReactHighcharts = require('react-highcharts');

var VisPanel = React.createClass({
   
		_getModelValues: function() {
			var valObj = this.props.appState.currentModel.values;
			var vals = [];
			for (var v in valObj) {
	      if( valObj.hasOwnProperty(v) && 
	          !(valObj[v] && valObj[v].constructor && valObj[v].call && valObj[v].apply) ) {
	        vals.push( valObj[v] );
	      } 
	    }

	    return vals;
		},

		getVisConfig: function() {
			var xAxisVals = Object.keys(this.props.appState.currentModel.values);
			var yAxisVals = this._getModelValues();

			return 	{
								chart: {
			            backgroundColor: '#fdfdfd'
			          },
								title: {
								      text: 'Account Value Over Time',
								      align: 'center',
								      x: 10
								},
								xAxis: {
								  categories: xAxisVals,//['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
								},
								series: [{
								  data: yAxisVals//[29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
								}]
							}
		},

    render: function () {
    		var chartUi = "";
    		if (this.props.appState.currentModel.values != undefined) {
    			var visConfig = this.getVisConfig();
    			chartUi = <ReactHighcharts config={visConfig} />
    		}
    		

        return (
	        <div className="visPanel">
	        	{chartUi}
	        </div>
        );
    }
})

module.exports = VisPanel;
