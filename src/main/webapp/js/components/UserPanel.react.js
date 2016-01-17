var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');

var ReactPropTypes = React.PropTypes;

var UserPanel = React.createClass({

  getInitialState: function() {
    return  {
              username: AppStore.getUsername()
            } 
  },

  _logout: function() {
    AppActions.logout();
  },

  render: function() {
    return (
      <div id="userpanel">
        <img src="./images/user.svg" onClick={this._logout} /><p onClick={this._logout}>{this.state.username}</p>
      </div>
    );
  },

});

module.exports = UserPanel;
