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
								      x: 10,
								      style: { "font": "20px 'Helvetica Neue', Helvetica, Arial, sans-serif, bold" }
								},
								legend: {
									enabled: false
								},
								lang: {
						        thousandsSep: ','
						    },
								xAxis: {
								  categories: xAxisVals,
								  title: {
					                text: 'Date',
					                style: { "font": "18px 'Helvetica Neue', Helvetica, Arial, sans-serif, bold" }
					            	 },
					        tickInterval: 24,
			            labels: {
			            		style: { "font": "14px 'Helvetica Neue', Helvetica, Arial, sans-serif, bold" },
			                formatter: function () {
			                	var theDate = new Date(this.value + ' 00:00:00');
			                  return (theDate.getMonth()+1) + '/' + theDate.getDate() + '/' + theDate.getFullYear();
			                }
			            }
								},
								yAxis: {
									title: {
					                text: 'Account Value',
					                style: { "font": "18px 'Helvetica Neue', Helvetica, Arial, sans-serif, bold" }
					            	 },
			            labels: {
			            		style: { "font": "14px 'Helvetica Neue', Helvetica, Arial, sans-serif, bold" },
			                formatter: function () {
												return '$' + this.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			                }
			            }
								},
								tooltip: {
								    formatter: function() {
								    	var theDate = new Date(this.x + ' 00:00:00');
								    	var prettyDate = (theDate.getMonth()+1) + '/' + theDate.getDate() + '/' + theDate.getFullYear();
											var prettyValue = '$' + this.y.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
								      return 'Account value on <b>' + prettyDate + '</b> is <b>' + prettyValue + '</b>';
								    }
								},
								series: [{
								  data: yAxisVals
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
