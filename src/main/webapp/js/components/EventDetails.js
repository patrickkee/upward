var React = require('react');
var EventTypes = require('../constants/EventTypes');
var Icons = require('../constants/Icons');

var ReactPropTypes = React.PropTypes;

var EventDetails = React.createClass({

  render: function() {
   
    return (
      <div className="app-event-details">
        <label>Name: {this.props.modelEvent.name}</label>
        <label>Value: {this.props.modelEvent.value}</label>
        <label>Period: {this.props.modelEvent.period}</label>
        <label>Type: {this.props.modelEvent.type}</label>
        <label>Start: {this.props.modelEvent.startDate}</label>
        <label>End: {this.props.modelEvent.endDate}</label>
      </div>
    );
  },

});

module.exports = EventDetails;
