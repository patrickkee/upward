var React = require('react');
var Header = require('./Header.react');
var MainSection = require('./MainSection.react');
var UserPanel = require('./UserPanel.react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppStates = require('../constants/AppStates');

var App = React.createClass({

  getInitialState: function() {
    return  {
              viewState: AppStore.getViewState()
            } 
  },

  componentDidMount: function() {
    AppStore.addChangeListener(this._onChange);
  },

  componentWillUnmount: function() {
    //TBD
  },

  render: function() {
    if (this.state.viewState==AppStates.CONTENT_VIEW) {
      userPanel = <UserPanel />
      loginPanel = ""
    } else {
      userPanel = "";
      loginPanel = <LoginInput />
    }
    

    return (
      <div>
        <Header />
        {userPanel}
        {loginPanel}
        <MainSection />
      </div>
    );
  },

  _onChange: function() {
    this.props.username = AppStore.getUsername();
    this.setState({
                      viewState: AppStore.getViewState()
                  });
  }

});

module.exports = App;
