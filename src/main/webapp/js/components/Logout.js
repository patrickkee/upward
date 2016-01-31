var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var AppActions = require('../actions/AppActions');

var Logout = React.createClass({

  _logout: function() {
    AppActions.logout();
  },

  _toggleHover: function() {
    var newVal = !this.state.hovering;
    this.setState({hovering: newVal});
  },

  getInitialState: function() {
    //AppStore.loadFromLocalStorage(); //having trouble loading initial state 
    return  {
              showUserDetails: false,
              hovering: false
            } 
  },

  render: function() {
    //Conditionally highlight the user icon based on the state
    var userImgClass = this.state.hovering ? "iconHighlight" : "iconNoHighlight";

    return (
      <div id="logout">
        <div className={userImgClass}> 
          <img  src={Icons.LOGOUT} onClick={this._logout} 
                onMouseEnter={this._toggleHover} onMouseLeave={this._toggleHover}/>
        </div>
      </div>
    );
  },

});



module.exports = Logout;
