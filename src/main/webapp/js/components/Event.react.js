var React = require('react');
var EventTypes = require('../constants/EventTypes');
var Icons = require('../constants/Icons');
var EventDetails = require('./EventDetails');

var ReactPropTypes = React.PropTypes;

var Event = React.createClass({

  _onClick: function() {
    this.props.onEventClickCallback(this.props.modelEvent.eventId);
  },

  //Simple toggle for icon hover effect
  _onToggleHover: function() {
    var newVal = !this.state.hovering;
    this.setState({hovering: newVal});
  },

  getInitialState: function() {
    return  { hovering: false } 
  },

  render: function() {
    var icon = this.props.modelEvent.type.icon;
    var labelClass = (this.props.modelEvent.type != EventTypes.NEW_EVENT.type) ? "eventName" : "newEventName";
    var hovering = this.state.hovering ? "iconHighlight" : "iconNoHighlight";
    var eventDetails = (this.props.showDetailPanel) ? <EventDetails modelEvent={this.props.modelEvent} /> : ""

    return (
      <li className="event">
        <div className={hovering} onClick={this._onClick}>
          <img className="icon" src={icon} 
               onMouseEnter={this._onToggleHover} onMouseLeave={this._onToggleHover}/>
        </div>
        <label className={labelClass}>{this.props.modelEvent.name}</label>
        {eventDetails}
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
