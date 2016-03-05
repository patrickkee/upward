'use strict';

var React = require('react');
var Event = require("./Event.react");
var Enums = require("../../model/Enums");

//A single event detail such as the name, type, value, etc
Event.Period = React.createClass({

  FIELD_NAME: "Period",
 
  _onValueChange: function(event) {
    this.props.callbacks.edit(this.FIELD_NAME, event.target.value);
  },

  //Delegate to parent form to toggle edit mode to ensure that only one field
  //can be edited at a time (reduces info user has to process visually)
  _onClick: function() {
    this.props.callbacks.click(this.FIELD_NAME);
  },

  render: function() {
    //Generate the collection of model options
    var eventPeriodOptions = Enums.Event.Period.getAll().map( p => ( <option key={p.type} value={p.type}>{p.pretty}</option> ));

    var output = "";
    if (this.props.editField.toLowerCase() == this.FIELD_NAME.toLowerCase()) {
      output =  <div className="eventDetailItem">
                  <label className="eventDetailItemLabel">{this.FIELD_NAME}:</label>
                  <select className="eventDetailItemInput" 
                          name={this.FIELD_NAME}
                          value={this.props.value}
                          disabled={false}
                          onChange={this._onValueChange} > 
                    {eventPeriodOptions}
                  </select>  
                </div>
    } else {
      output =  <div>
                  <label className="eventDetailItemLabel" onClick={this.props.onClickCallback}>{this.FIELD_NAME}:</label>
                  <input className="eventDetailItemInputDisabled"
                            type="text"
                            name={this.FIELD_NAME}
                            value={Enums.Event.Period.get(this.props.value).pretty}
                            readOnly={true}
                            onClick={this._onClick} />
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = Event.Period;
