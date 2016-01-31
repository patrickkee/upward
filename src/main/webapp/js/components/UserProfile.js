var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');

var UserProfile = React.createClass({

  _showUserDetails: function () {
    var newVal = !this.state.showUserDetails;
    this.setState({showUserDetails: newVal});
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
    var user = AppStore.getUser()
    var popout = ""
    
    //Conditionally highlight the user icon based on the state
    var userImgClass = this.state.hovering ? "iconHighlight" : "iconNoHighlight";

    //Conditionally render the popup based on the state
    if (this.state.showUserDetails) {
      popout =  <div className="popout">
                  <label>Name: {user.firstName}</label>
                  <label>Email: {user.email}</label>
                  <label>Password: *********</label>
                </div>
    } 

    return (
      <div id="userprofile">
        <label className="userName">{user.firstName}</label>
        <div className={userImgClass} onMouseEnter={this._toggleHover} onMouseLeave={this._toggleHover}>
          <img className="user" src={Icons.USER} onClick={this._showUserDetails}/>
        </div>
        {popout}
      </div>
    );
  },

});



module.exports = UserProfile;
