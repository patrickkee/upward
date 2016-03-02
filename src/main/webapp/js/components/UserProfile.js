'use strict';

var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var ToggleIcon = require('./base/ToggleIcon');

var UserProfile = React.createClass({

  _showUserDetails: function () {
    var newVal = !this.state.showUserDetails;
    this.setState({showUserDetails: newVal});
  },

  getInitialState: function() {
    return  { showUserDetails: false } 
  },

  render: function() {
    //Conditionally render the popup based on the state
    if (this.state.showUserDetails) {
      var popout = ""
      popout =  <div className="popout">
                  <label>Name: {this.props.appState.user.firstName}</label>
                  <label>Email: {this.props.appState.user.email}</label>
                  <label>Password: *********</label>
                </div>
    } 

    return (
      <div id="userprofile">
        <label className="userName">{this.props.appState.user.firstName}</label>
        <div className="icon">
          <ToggleIcon icon={Icons.USER} iconSize="22" tooltip={"User Profile"} onClickCallback={this._showUserDetails}/>
        </div>
        {popout}
      </div>
    );
  },

});

module.exports = UserProfile;
