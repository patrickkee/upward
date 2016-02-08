var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');
var Event = require('./Event.react');
var EventTypes = require('../constants/EventTypes');

var ModelPanel = React.createClass({

  render: function() {
    return (
      <nav id="eventpanel">
        <div>
          <label className="title">Events</label>
        </div>
        <div>
          <ul className="events">
            <Event eventType={EventTypes.NEW_EVENT} eventName="add new"/>
            <Event eventType={EventTypes.ACTUAL} eventName="Actual Account Value"/>
            <Event eventType={EventTypes.RECURRING_YIELD} eventName="Monthly Interest"/>
            <Event eventType={EventTypes.RECURRING_DEPOSIT} eventName="Monthly Savings"/>
            <Event eventType={EventTypes.ONE_TIME_DEPOSIT} eventName="Initial Deposit"/>
          </ul>  
        </div>
      </nav>
    );
  },

});

module.exports = ModelPanel;
