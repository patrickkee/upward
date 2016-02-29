'use strict';

var React = require('react');
var MaskedInput = require('react-maskedinput')

//A single event detail such as the name, type, value, etc
var EventType = React.createClass({

  FIELD_NAME: "", //This is set by the parent object passing the value in 
 
  //Pass value change along to parent, indicating what field's value changed. Parent will
  //own the event object and its state
  _onValueChange: function(event) {
    if (event.target.value.trim().length == 10) {
      this.props.callbacks.edit(this.FIELD_NAME, event.target.value);
    } 
  },

  //Delegate to parent form to toggle edit mode to ensure that only one field
  //can be edited at a time (reduces info user has to process visually)
  _onClick: function() {
    this.props.callbacks.click(this.FIELD_NAME);
  },

  componentWillMount: function() {
    this.FIELD_NAME = this.props.fieldName;
  },

  render: function() {
    var output = "";
    if (this.props.editField.toLowerCase() == this.FIELD_NAME.toLowerCase()) {
      output =  <div className="eventDetailItem">
                  <label className="eventDetailItemLabel">{this.FIELD_NAME}:</label>
                  <MaskedInput className="eventDetailItemInput" 
                               mask="11/11/1111" 
                               name={this.FIELD_NAME}
                               value={this.props.value}
                               placeholderChar=" " 
                               onChange={this._onValueChange}/>                  
                </div>
    } else {
      output =  <div>
                  <label className="eventDetailItemLabel" onClick={this.props.onClickCallback}>{this.FIELD_NAME}:</label>
                  <MaskedInput className="eventDetailItemInputDisabled" 
                               mask="11/11/1111" 
                               name={this.FIELD_NAME}
                               value={this.props.value}
                               placeholderChar=" " 
                               onChange={this._onValueChange}
                               onClick={this._onClick}/>  
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = EventType;
