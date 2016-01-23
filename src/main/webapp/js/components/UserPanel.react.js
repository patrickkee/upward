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
        <img src="./images/user.svg" />
        <img src="./images/logout.png" onClick={this._logout} />
      </div>
    );
  },

});

module.exports = UserPanel;
