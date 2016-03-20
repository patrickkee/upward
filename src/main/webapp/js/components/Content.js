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
 		var content = ''
 		if (this.props.appState.user.jwt !== "") {
 			content = <div>
					        <UserPanel  appState={this.props.appState} />
					        <VisPanel   appState={this.props.appState} />
					        <ModelPanel appState={this.props.appState} />
					        <EventPanel appState={this.props.appState} />
					      </div>
 		}

    return (
    	<div>{content}</div>
    )
  }

});

module.exports = Content;
