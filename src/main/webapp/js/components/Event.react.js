var React = require('react');
var EventTypes = require('../constants/EventTypes');

var ReactPropTypes = React.PropTypes;

var Event = React.createClass({

  render: function() {
    var icon = getIcon(this.props.eventType);

    return (
      <li className="event">
        <img className="icon" src={icon} />
        <label className="eventName">{this.props.eventName}</label>
      </li>             
    );
  },

});

function getIcon(eventType) {
   var icon;

    switch(eventType) {
      case EventTypes.ACTUAL:
        icon = "./images/thumbtack.png"
        break;

      case EventTypes.RECURRING_YIELD:
        icon = "./images/recur_pct.png"
        break;

      case EventTypes.RECURRING_DEPOSIT:
        icon = "./images/recur_dollar.png"
        break;

      case EventTypes.ONE_TIME_DEPOSIT:
        icon = "./images/moneybag_lg.png"
        break;

      default:
        //none
    }

    return icon;
}

module.exports = Event;
