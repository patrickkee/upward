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

  _onKeyDown: function() {
    if (event.keyCode === ENTER_KEY_CODE) {
          this._login();
        }
  },

  render: function() {
    return (
      <div id="login">
        <label>Username</label>
        <input
          className="LoginInput"
          type="text"
          name="username"
          onChange={this._onTextEntry}
          onKeyDown={this._onKeyDown}
        />
        <button onClick={this._login}>Login</button>
      </div>  
    );
  }

});

module.exports = LoginInput;
