var React = require('react');
var Header = require('./Header.react');
var MainSection = require('./MainSection.react');
var LoginInput = require('./LoginInput.react');


var App = React.createClass({

  getInitialState: function() {
    return null
  },

  componentDidMount: function() {
    //TBD
  },

  componentWillUnmount: function() {
    //TBD
  },

  render: function() {
    return (
      <div>
        <Header />
        <LoginInput />
        <MainSection />
      </div>
    );
  },

  _onChange: function() {
    //TBD
  }

});

module.exports = App;
