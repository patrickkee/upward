'use strict';

var React = require('react');
var UserProfile = require('../components/UserProfile')
var Logout = require('../components/Logout')

var UserPanel = React.createClass({

  render: function() {
    return (
      <div id="userpanel">
        <UserProfile appState={this.props.appState} />
        <Logout />
      </div>
    );
  },

});

module.exports = UserPanel;
