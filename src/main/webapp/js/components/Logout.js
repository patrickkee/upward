'use strict';

var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var ToggleIcon = require('./base/ToggleIcon');
var AppActions = require('../actions/AppActions');

var Logout = React.createClass({

  _logout: function() {
    AppActions.logout();
  },

  getInitialState: function() {
    return  {
              showUserDetails: false
            } 
  },

  render: function() {
    return (
      <div id="logout">
        <div className="icon"> 
          <ToggleIcon icon={Icons.LOGOUT} iconSize="22" onClickCallback={this._logout}/>
        </div>
      </div>
    );
  },

});



module.exports = Logout;
