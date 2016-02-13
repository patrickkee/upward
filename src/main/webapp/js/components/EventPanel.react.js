var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');
var Event = require('./Event.react');
var EventTypes = require('../constants/EventTypes');

var ModelPanel = React.createClass({

  _onEventClick: function(eventId) {
    this.setState({ showEventDetailId: (eventId == this.state.showEventDetailId) ? -1 : eventId });
  },

  getInitialState: function() {
    return  { showEventDetailId: -1 } 
  },

  render: function() {
    var eventList = [];
    var events = [{eventId: 0, name: "add new", type: EventTypes.get("NEW_EVENT")}] 
    events = events.concat(AppStore.getCurrentModel().events);
    
    var showDetailPanel;
    for (var e in events) {
      showDetailPanel = (this.state.showEventDetailId == events[e].eventId);
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
