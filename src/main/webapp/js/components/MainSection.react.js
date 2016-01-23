var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');


var ReactPropTypes = React.PropTypes;

var MainSection = React.createClass({

  getInitialState: function() {
    return  {
              username: AppStore.getUsername()
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
      username: AppStore.getUsername()
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
