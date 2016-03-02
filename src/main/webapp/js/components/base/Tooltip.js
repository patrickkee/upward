'use strict';

var React = require('react');

var Tooltip = React.createClass({

  _toggleHover: function(/*object*/ event) {
    this.setState({hovering: (event.type == "mouseenter")});
  },

  getInitialState: function() {
    //AppStore.loadFromLocalStorage(); //having trouble loading initial state 
    return  {hovering: false} 
  },

  render: function() {

    var style = {
                  
                  height: "19px",
                  zIndex: "99",
                  marginTop: "6px",
                  display: "inline-block",
                  backgroundColor: "#ffffd9",
                  position: "relative",
                  borderStyle: "solid",
                  borderColor: "#9c9c9c",
                  borderWidth: "1px",
                  borderRadius: "4px",
                  boxShadow: "3px 3px 5px 3px rgba(0, 0, 0, 0.05)"
    };

    var textStyle = {
      fontSize: "13px",
      textAlign: "center",
      paddingTop: "0px",
      paddingBottom: "1px",
      paddingLeft: "2px",
      paddingRight: "3px",
      display: "inline",
      whiteSpace: "nowrap"
    };

    return (
        <div style={style}>
          <p style={textStyle}>{this.props.text}</p>
        </div>

    );
  }

});

module.exports = Tooltip;
