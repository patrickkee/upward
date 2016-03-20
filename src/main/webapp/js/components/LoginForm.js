'use strict';

var React = require('react');
var browserHistory = require('react-router').browserHistory;
var AppActions = require('../actions/AppActions');
var AppConstants = require('../constants/AppConstants');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var ToggleIcon = require('./base/ToggleIcon');

var ENTER_KEY_CODE = 13;

var LoginForm = React.createClass({
  
  getInitialState: function() {
    return { email: "", password: "", firstName: "" };
  },

  _login: function() {
     if (this.props.appState.user.jwt === "" && this.state.email 
        && this.state.password && this.state.firstName) {
      AppActions.signup({ email: this.state.email,
                          password: this.state.password,
                          firstName: this.state.firstName });
    } else {
      AppActions.login(this.state.email);
    }
  },

  _onEmailTextEntry: function(/*object*/ event) {
     this.setState({email: event.target.value});
  },

  _onPasswordTextEntry: function(/*object*/ event) {
     this.setState({password: event.target.value});
  },

  _onFirstNameTextEntry: function(/*object*/ event) {
     this.setState({firstName: event.target.value});
  },

  _onKeyDown: function(event) {
    if (event.keyCode === ENTER_KEY_CODE ) {
          this._login();
        }
  },

  render: function() {
    if (this.props.appState.user.jwt !== "") { 
    
        var failText =  <div className="failText">
                          <label>
                            Could not login, please retry or add name to signup
                          </label>
                        </div>

        var signupPanel =   <div id="formItem">
                              <label>First Name</label>
                              <input  className="firstName" type="text" name="firstName"
                                      onChange={this._onFirstNameTextEntry}
                                      value={this.state.firstName} />  
                            </div> 
    } else {
      var failText = ""
      var signupPanel = ""
    }

    var focus = true
    var loginForm = ''
    if (this.props.appState.user.jwt === "") {
      loginForm =   <div id="login">
                      {failText}  
                      <div id="form">
                        <div id="formItem">
                          <label>Email</label>
                          <input  className="email" type="text" name="email" 
                                  value={this.state.email}
                                  onChange={this._onEmailTextEntry}
                                  autoFocus={focus} />
                        </div>
                        <div id="formItem">
                          <label>Password</label>
                          <input  className="password" type="password" name="password" 
                                  value={this.state.password}
                                  onChange={this._onPasswordTextEntry}
                                  onKeyDown={this._onKeyDown} />
                        </div>
                        {signupPanel}
                      </div>

                      <div className="loginButton">
                        <button onClick={this._login}>
                          Login or Signup&nbsp;&nbsp;
                          <ToggleIcon icon={Icons.LOGOUT} iconSize="16" onClickCallback={this.props._login}/>
                        </button>
                      </div>
                    </div>
      }

    return (
      <div>{loginForm}</div>
    )
  }
});

module.exports = LoginForm;
