'use strict';

var React = require('react');
var UserPanel = require('./UserPanel');
var ModelPanel = require('./ModelPanel.react');
var EventPanel = require('./event/EventPanel');
var VisPanel = require('./vis/VisPanel');
var LoginForm = require('./LoginForm');
var AppStore = require('../stores/AppStore');
var AppStates = require('../constants/AppStates');

var Content = React.createClass({

 render: function() {
    return (
      <div>
        <UserPanel  appState={appState} />
        <VisPanel   appState={appState} />
        <ModelPanel appState={appState} />
        <EventPanel appState={appState} />
      </div>
    );
  }

});

module.exports = Content;
