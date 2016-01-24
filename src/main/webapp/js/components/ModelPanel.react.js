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
      <nav id="modelpanel">
        <div>
          <label className="title">Goal</label>
          <select className="model">
            <option value="Save for Car">Save for Car</option>
            <option value="new">+ add new</option>
          </select>
        </div>
        <table>
          <tr>
            <td><label className="targetValueLabel">Target Value</label></td>
            <td><input className="targetValueText"
              type="text"
              name="targetVal"
            /></td>
          </tr>
          <tr>
            <td><label className="targetDateLabel">Target Date</label></td>
            <td><input className="targetDateText"
            type="text"
            name="targetDate"
          /></td>
          </tr>
        </table>
      </nav>
    );
  },

});

module.exports = ModelPanel;
