'use strict';

var React = require('react');
var Header = require('./Header.react');
var MainSection = require('./MainSection.react');
var UserPanel = require('./UserPanel');
var ModelPanel = require('./ModelPanel.react');
var EventPanel = require('./EventPanel.react');
var LoginForm = require('./LoginForm');
var AppStore = require('../stores/AppStore');
var AppStates = require('../constants/AppStates');

var App = React.createClass({

  _onChange: function() {
    this.setState({ appState: AppStore.getAppState() });
  },

  getInitialState: function() {
    return  { appState: AppStore.getAppState() } 
  },

  componentDidMount: function() {
    AppStore.addChangeListener(this._onChange);
  },

  componentWillUnmount: function() {
    AppStore.removeChangeListener(this._onChange);
  },

  render: function() {
    var viewStateUi = getViewStateUi(this.state.appState);
    
    return (
      <div>
        <Header />
        {viewStateUi}
        <MainSection appState={this.state.appState} />
      </div>
    );
  }

});

function getViewStateUi(appState) {
  var viewStateUi = "";

  switch(appState.viewState) {
    case AppStates.LOGIN_VIEW:
      viewStateUi = <LoginForm loginFailed={false}/>
      break;

    case AppStates.LOGIN_FAIL_VIEW:
      viewStateUi = <LoginForm loginFailed={true}/>
      break;

    case AppStates.CONTENT_VIEW:
      viewStateUi = <div>
                      <UserPanel  appState={appState} />
                      <ModelPanel appState={appState} />
                      <EventPanel appState={appState} />
                    </div>
      break;

    default:
      // no op
  }

  return viewStateUi;
};

module.exports = App;
