'use strict';

var React = require('react');
var AppStore = require('../stores/AppStore');

var MainSection = React.createClass({

  getInitialState: function() {
    return  {
              username: this.props.appState.email
            } 
  },

  componentDidMount: function() {
    AppStore.addChangeListener(this._onChange);
  },

  componentWillUnmount: function() {
    AppStore.removeChangeListener(this._onChange);
  },

  _onChange: function() {
    this.setState({
      username: this.props.appState.email
    });
  },

  render: function() {
    return (
      <div id="main">
        
      </div>
    );
  },

});

module.exports = MainSection;
