'use strict';

var React = require('react');

var Header = React.createClass({displayName: "Header",

  render: function() {
    return (
      React.createElement("header", {id: "header"}, 
        React.createElement("h1", null, "upward.")
      )
    );
  },

});

module.exports = Header;
