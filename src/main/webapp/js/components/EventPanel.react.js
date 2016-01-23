var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');

var ReactPropTypes = React.PropTypes;

var ModelPanel = React.createClass({

  _add: function() {
    //TBD
  },

  _delete: function() {
    //TBD
  },

  render: function() {
    return (
      <nav id="eventpanel">
        <div>
          <label className="eventLbl">Events</label>
        </div>
        <div>
          <ul>
            <li>
              
              <label className="newEvent">+  click to add event</label>
            </li>
            <li>
              <img src="./images/recur_dollar.svg" />
              <label>Monthly Savings</label>
            </li>          
            <li>
              <img src="./images/dollar.svg" />
              <label>Initial Deposit</label>
            </li>
          </ul>  
        </div>
      </nav>
    );
  },

});

module.exports = ModelPanel;
