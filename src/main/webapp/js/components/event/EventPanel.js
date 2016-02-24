'use strict'; 

var React = require('react');
var AppStore = require('../../stores/AppStore');
var Event = require('./Event.react');
var EventTypes = require('../../constants/EventTypes');
var Models = require("../../model/models");

var ModelPanel = React.createClass({

  //Display the event details when a user clicks on the event
  _onEventClick: function(eventId) {
    this.setState({ showEventDetailId: (eventId == this.state.showEventDetailId) ? -1 : eventId });
  },

  //Don't show any event details by default
  getInitialState: function() {
    return  { showEventDetailId: -1 } 
  },

  render: function() {
    var eventList = [];
    var events = [{eventId: 0, name: "add new", type: EventTypes.get("NEW_EVENT"), period: Models.Events.Period.MONTHLY.type}]
    var serverEvents = this.props.appState.currentModel.events;
    if (serverEvents != undefined) {
      events = events.concat(this.props.appState.currentModel.events);
    } 
    
    var showDetailPanel = "";
    var e = 0;
    for (e in events) {
      showDetailPanel = (this.state.showEventDetailId == (events[e]).eventId);
      eventList.push(<Event key={e} modelEvent={events[e]} 
                            onEventClickCallback={this._onEventClick} 
                            showDetailPanel={showDetailPanel} />) 
    } 

    return (
      <nav id="eventpanel">
        <div>
          <label className="title">Events</label>
        </div>
        <div>
          <ul className="events">
            {eventList}
          </ul>  
        </div>
      </nav>
    );
  },

});

module.exports = ModelPanel;
