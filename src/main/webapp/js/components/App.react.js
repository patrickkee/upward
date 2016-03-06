'use strict';

var React = require('react');
var Link = require('react-router').Link;
var Header = require('./Header');
var UserPanel = require('./UserPanel');
var ModelPanel = require('./ModelPanel.react');
var EventPanel = require('./event/EventPanel');
var VisPanel = require('./vis/VisPanel');
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
        {this.props.children}
      </div>
    );
  }

});

function getViewStateUi(appState) {
  var viewStateUi = "";

  switch(appState.viewState) {
    case AppStates.LOGIN_VIEW:
      viewStateUi = <LoginForm appState={appState} />
      break;

    case AppStates.LOGIN_FAIL_VIEW:
      viewStateUi = <LoginForm appState={appState} />
      break;

    case AppStates.CONTENT_VIEW:
      viewStateUi = <div>
                      <UserPanel  appState={appState} />
                      <VisPanel   appState={appState} />
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
