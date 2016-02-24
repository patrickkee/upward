'use strict';

var React = require('react');
var EventTypes = require('../../constants/EventTypes');

//A single event detail such as the name, type, value, etc
var EventType = React.createClass({

  FIELD_NAME: "Type",
 
  //Pass value change along to parent, indicating what field's value changed. Parent will
  //own the event object and its state
  _onValueChange: function(event) {
    this.props.callbacks.edit(this.FIELD_NAME, EventTypes.get(event.target.value));
  },

  //Delegate to parent form to toggle edit mode to ensure that only one field
  //can be edited at a time (reduces info user has to process visually)
  _onClick: function() {
    this.props.callbacks.click(this.FIELD_NAME);
  },

  render: function() {
    //Generate the collection of model options
    var eventTypeOptions = [];
    for (var e in EventTypes) {
      if( EventTypes.hasOwnProperty(e) && 
          !(EventTypes[e] && EventTypes[e].constructor && EventTypes[e].call && EventTypes[e].apply) &&
          EventTypes[e] != EventTypes.NEW_EVENT ) {
        eventTypeOptions.push( <option key={e} value={EventTypes[e].type}>{EventTypes[e].pretty}</option> );
      } 
    }   

    var output = "";
    if (this.props.editField.toLowerCase() == this.FIELD_NAME.toLowerCase()) {
      output =  <div className="eventDetailItem">
                  <label className="eventDetailItemLabel">{this.FIELD_NAME}:</label>
                  <select className="eventDetailItemInput" 
                          name={this.FIELD_NAME}
                          value={this.props.value.type}
                          disabled={false}
                          onChange={this._onValueChange} > 
                    {eventTypeOptions}
                  </select>   
                </div>
    } else {
      output =  <div>
                  <label className="eventDetailItemLabel">{this.FIELD_NAME}:</label>
                  <input className="eventDetailItemInputDisabled" 
                         value={this.props.value.pretty} 
                         readOnly={true}
                         onClick={this._onClick} />
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = EventType;
