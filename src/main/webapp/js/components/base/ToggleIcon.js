'use strict';

var React = require('react');

var ToggleIcon = React.createClass({

  _toggleHover: function(/*object*/ event) {
    this.setState({hovering: (event.type == "mouseenter")});
  },

  getInitialState: function() {
    //AppStore.loadFromLocalStorage(); //having trouble loading initial state 
    return  {hovering: false} 
  },

  render: function() {
    var borderRadius = "";
    if (this.props.icon.shape == "CIRCLE") {
      borderRadius = "25px";
    }  else 
    if (this.props.icon.shape == "SQUARE") {
      borderRadius = "1px";
    }

    var hoverStyle =  {
                    width: this.props.iconSize,
                    display: 'inline-block',
                    verticalAlign: 'middle',
                    height: this.props.iconSize,
                    borderRadius: borderRadius,
                    boxShadow: '0px 0px 10px 0px rgba(0, 0, 0, 0.4),0px 0px 10px 0px rgba(0, 0, 0, 0.4) inset'
                  };

    var noHoverStyle =  {
                          width: this.props.iconSize,
                          height: this.props.iconSize,
                          display: 'inline-block',
                          verticalAlign: 'middle',
                          borderRadius: borderRadius,
                          boxShadow: '0px 0px 10px 0px rgba(0, 0, 0, 0),0px 0px 10px 0px rgba(0, 0, 0, 0) inset'
                        };

    var imgStyle = { width: this.props.iconSize}

    //Conditionally highlight the user icon based on the state
    var style = this.state.hovering ? hoverStyle : noHoverStyle;

    return (
        <div style={style} onMouseEnter={this._toggleHover} onMouseLeave={this._toggleHover}>
          <img style={imgStyle} src={this.props.icon.image} onClick={this.props.onClickCallback}/>
        </div>

    );
  }

});

module.exports = ToggleIcon;
