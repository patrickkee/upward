'use strict';

var React = require('react');

//A single event detail such as the name, type, value, etc
var EventValue = React.createClass({

  FIELD_NAME: "Value",
 
  //Pass value change along to parent, indicating what field's value changed. Parent will
  //own the event object and its state
  _onValueChange: function(event) {
    //TODO: THIS DOESNT ALLOW USERS TO DELETE THE LAST INTEGER IN THE FIELD. Editing this way is wierd, but currently
    //if users enter an empty string, they're allowed to try to persist to the server, and that will cause errors, so something
    //must be done
    if ( (!isNaN(parseFloat(event.target.value)) && isFinite(event.target.value)) &&
          event.target.value > 0 ) {
      this.props.callbacks.edit(this.FIELD_NAME, event.target.value);
    } 
    
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
                  <input className="eventDetailItemInput"
                            type="text"
                            name={this.FIELD_NAME}
                            value={this.props.value}
                            onChange={this._onValueChange}/>
                </div>
    } else {
      output =  <div>
                  <label className="eventDetailItemLabel" onClick={this.props.onClickCallback}>{this.FIELD_NAME}:</label>
                  <input className="eventDetailItemInputDisabled"
                            type="text"
                            name={this.FIELD_NAME}
                            value={this.props.value}
                            readOnly={true}
                            onClick={this._onClick} />
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = EventValue;
