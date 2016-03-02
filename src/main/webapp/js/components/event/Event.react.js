'use strict';

var React = require('react');
var EventTypes = require('../../constants/EventTypes');
var Icons = require('../../constants/Icons');
var ToggleIcon = require('../base/ToggleIcon');
var EventDetails = require('./EventDetails');

var ReactPropTypes = React.PropTypes;

var Event = React.createClass({

  _onClick: function() {
    this.props.onEventClickCallback(this.props.modelEvent.eventId);
  },

  render: function() {
    var labelClass = (this.props.modelEvent.type.type != EventTypes.NEW_EVENT.type) ? "eventName" : "newEventName";
    var eventDetails = (this.props.showDetailPanel) ? <EventDetails modelEvent={this.props.modelEvent} isNewEvent={this.props.isNewEvent} /> : ""

    return (
      <li className="event">
        <ToggleIcon icon={this.props.modelEvent.type.icon} iconSize="20" tooltip={"Event Details"} onClickCallback={this._onClick}/>
        <label className={labelClass}>{this.props.modelEvent.name}</label>
        {eventDetails}
      </li>             
    );
  },

});

module.exports = Event;
