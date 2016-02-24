'use strict';

var React = require('react');
var MaskedInput = require('react-maskedinput')

//A single event detail such as the name, type, value, etc
var EventName = React.createClass({

  FIELD_NAME: "Name",

  _onValueChange: function(event) {
    if ( event.target.value.length <= 25 ) {
      this.props.callbacks.edit(this.FIELD_NAME, event.target.value);
    }
  },

  //Delegate to parent form to toggle edit mode to ensure that only one field
  //can be edited at a time (reduces info user has to process visually)
  _onClick: function() {
    this.props.callbacks.click("name");
  },

  render: function() {
    var output = "";
    if (this.props.editField.toLowerCase() == "name") {
      output =  <div className="eventDetailItem">
                  <label className="eventDetailItemLabel">Name:</label>
                  <input className="eventDetailItemInput"
                            type="text"
                            name={this.FIELD_NAME}
                            value={this.props.value}
                            onChange={this._onValueChange}/>
                </div>
    } else {
      output =  <div>
                  <label className="eventDetailItemLabel" onClick={this.props.onClickCallback}>Name:</label>
                  <div onClick={this._onClick}>
                    <input className="eventDetailItemInputDisabled"
                              type="text"
                              readOnly={true}
                              name={this.FIELD_NAME}
                              value={this.props.value} />
                  </div>
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = EventName;
