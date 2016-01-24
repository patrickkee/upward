var React = require('react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');

var UserPanel = React.createClass({

  _logout: function() {
    AppActions.logout();
  },

  render: function() {
    var user = AppStore.getUser()

    return (
      <div id="userpanel">
        <p className="userName">{user.firstName}</p>
        <img className="user" src="./images/user.svg" />
        <img className="logout" src="./images/logout.png" onClick={this._logout} />
      </div>
    );
  },

});

module.exports = UserPanel;
