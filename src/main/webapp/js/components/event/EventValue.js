'use strict';

var React = require('react');
var CurrencyMaskedInput = require('react-currency-masked-input');

//A single event detail such as the name, type, value, etc
var EventValue = React.createClass({

  FIELD_NAME: "Value",
 
  //Pass value change along to parent, indicating what field's value changed. Parent will
  //own the event object and its state
  _onMaskedChange: function(event, value) {
    //Send the cleaned value back to the event detail form
    this.props.callbacks.edit(this.FIELD_NAME, value);
  },

  //Delegate to parent form to toggle edit mode to ensure that only one field
  //can be edited at a time (reduces info user has to process visually)
  _onClick: function() {
    this.props.callbacks.click(this.FIELD_NAME);
  },

  render: function() {
    var output = "";
    if (this.props.editField.toLowerCase() == this.FIELD_NAME.toLowerCase()) {
      output =  <div className="eventDetailItem">
                  <label className="eventDetailItemLabel">{this.FIELD_NAME}:</label>
                  <CurrencyMaskedInput className="eventDetailItemInput"
                                       type="text"
                                       name={this.FIELD_NAME}
                                       ref={this.FIELD_NAME} 
                                       value={this.props.value} 
                                       showCents={true}
                                       currencySymbol={this.props.valueSymbol}
                                       onChange={this._onMaskedChange}
                                       required /> 
                </div>
    } else {
      output =  <div>
                  <label className="eventDetailItemLabel" onClick={this.props.onClickCallback}>{this.FIELD_NAME}:</label>
                  <CurrencyMaskedInput className="eventDetailItemInputDisabled"
                                       type="text"
                                       name={this.FIELD_NAME}
                                       ref={this.FIELD_NAME} 
                                       value={this.props.value}
                                       showCents={true}
                                       currencySymbol={this.props.valueSymbol}
                                       onChange={this._onMaskedChange}
                                       onClick={this._onClick}
                                       required /> 
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = EventValue;
