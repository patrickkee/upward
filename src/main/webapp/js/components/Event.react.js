var React = require('react');
var EventTypes = require('../constants/EventTypes');
var Icons = require('../constants/Icons');

var ReactPropTypes = React.PropTypes;

var Event = React.createClass({

  render: function() {
    var icon = getIcon(this.props.eventType);
    var labelClass = (this.props.eventType != EventTypes.NEW_EVENT) ? "eventName" : "newEventName";

    return (
      <li className="event">
        <img className="icon" src={icon} />
        <label className={labelClass}>{this.props.eventName}</label>
      </li>             
    );
  },

});

function getIcon(eventType) {
   var icon;

    switch(eventType) {
      case EventTypes.ACTUAL:
        icon = Icons.THUMBTACK
        break;

      case EventTypes.RECURRING_YIELD:
        icon = Icons.RECUR_PCT
        break;

      case EventTypes.RECURRING_DEPOSIT:
        icon = Icons.RECUR_DOLLAR
        break;

      case EventTypes.ONE_TIME_DEPOSIT:
        icon = Icons.MONEYBAG
        break;

      case EventTypes.NEW_EVENT:
        icon = Icons.NEW_EVENT
        break;

      default:
        //none
    }

    return icon;
}

module.exports = Event;
