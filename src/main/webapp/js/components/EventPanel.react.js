var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');
var Event = require('./Event.react');
var EventTypes = require('../constants/EventTypes');

var ModelPanel = React.createClass({

  render: function() {
    var eventList = [];
    var events = [{eventId: 0, name: "add new", type: "NEW_EVENT"}]
    events = events.concat(AppStore.getCurrentModel().events);
    
    for (var e in events) {
       eventList.push(<Event key={e} modelEvent={events[e]} />) 
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
