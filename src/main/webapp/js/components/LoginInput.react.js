var React = require('react');
var AppActions = require('../actions/AppActions');
var AppConstants = require('../constants/AppConstants');
var AppStore = require('../stores/AppStore');
var ReactPropTypes = React.PropTypes;

var ENTER_KEY_CODE = 13;

var LoginInput = React.createClass({

  _login: function() {
    AppActions.login(this.props.username);
  },

  _onTextEntry: function(/*object*/ event) {
    this.props.username = event.target.value
  },

  _onKeyDown: function(event) {
    if (event.keyCode === ENTER_KEY_CODE) {
          this.props.username = event.target.value;
          this._login();
        }
  },

  render: function() {
    if (this.props.loginFailed) { 
      var failText = <p>Login failed, please retry</p>
    } else {
      var failText = ""
    }

    var focus = true

    return (
      

      <div id="login">
        <label>Username</label>
        <input
          className="LoginInput"
          type="text"
          name="username"
          onChange={this._onTextEntry}
          onKeyDown={this._onKeyDown}
          autoFocus={focus}
        />
        <button onClick={this._login}>Login</button>
        {failText}
      </div>  
    );
  }
});

module.exports = LoginInput;
