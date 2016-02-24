'use strict';

var React = require('react');
var EventTypes = require('../../constants/EventTypes');
var Icons = require('../../constants/Icons');
var EventDetailItem = require('./EventDetailItem');
var EventName = require('./EventName');
var EventValue = require('./EventValue');
var EventPeriod = require('./EventPeriod');
var EventType = require('./EventType');
var EventDate = require('./EventDate');
var AppActions = require('../../actions/AppActions');
var ToggleIcon = require('../base/ToggleIcon');

var EventDetails = React.createClass({

  _onClick: function(fieldTitle) {
    this.setState({ editField: fieldTitle});
  },

  _onEditField: function(field, value) {
    var newModelEvent = JSON.parse(JSON.stringify(this.state.modelEvent));
    switch(field.toLowerCase()) {
      case "name":
           newModelEvent.name = value;
           break;
      case "period":
           newModelEvent.period = value;
           break;
      case "value":
           newModelEvent.value = value;
           break;    
      case "type":
           newModelEvent.type = value;
           break;
      case "start":
           newModelEvent.startDate = value;
           break;
      case "end":
           newModelEvent.endDate = value;
           break; 
      case "date":
           newModelEvent.startDate = value;
           newModelEvent.endDate = value;
           break;                 

      default: 
          //Nothing
    }    

    this.setState({ modelEvent: newModelEvent,
                    hasEdit: true }); 
  },

  _onSave: function() {
    AppActions.updateModelEvent(this.state.modelEvent);

    this.setState({ editField: "",
                    hasEdit: false });  
  },

  _onCancel: function() {
    this.setState({ editField: "",
                    hasEdit: false,
                    modelEvent: JSON.parse(JSON.stringify(this.state.initialModelEvent)) });
  },

  getInitialState: function() {
    return  { editField: "",
              modelEvent: this.props.modelEvent,
              initialModelEvent: JSON.parse(JSON.stringify(this.props.modelEvent)) }
  },

  render: function() {
    var callbacks = {click: this._onClick,
                     cancel: this._onCancel,
                     save: this._onSave, 
                     edit: this._onEditField }

    var formButtons = "";
    if (this.state.hasEdit) {
      formButtons = <div className="eventDetailsIcons"> 
                      <div className="eventDetailItemIcon"><ToggleIcon icon={Icons.CANCEL} iconSize="16" onClickCallback={callbacks.cancel}/></div>
                      <div className="eventDetailItemIcon"><ToggleIcon icon={Icons.SAVE} iconSize="16" onClickCallback={callbacks.save} /></div>
                    </div>
    }       

    var formFields = [];
    var reqAttrs = this.state.modelEvent.type.requiredAttrs;
    for (var e in reqAttrs) {
      switch(reqAttrs[e]) {
        case "EVENT_NAME":
          formFields.push(<EventName value={this.state.modelEvent.name} editField={this.state.editField} callbacks={callbacks} />);
          break;
        case "EVENT_TYPE":
          formFields.push(<EventType value={this.state.modelEvent.type} editField={this.state.editField} callbacks={callbacks} />);
          break;
        case "EVENT_VALUE":
          formFields.push(<EventValue value={this.state.modelEvent.value} editField={this.state.editField} callbacks={callbacks} />);
          break;          
        case "EVENT_PERIOD":
          formFields.push(<EventPeriod value={this.state.modelEvent.period} editField={this.state.editField} callbacks={callbacks} />);
          break;   
        case "START_DATE":
          formFields.push(<EventDate value={this.state.modelEvent.startDate} fieldName="Start" editField={this.state.editField} callbacks={callbacks} />);
          break;   
        case "END_DATE":
          formFields.push(<EventDate value={this.state.modelEvent.endDate} fieldName="End" editField={this.state.editField} callbacks={callbacks} />);
          break;   
        case "EFFECTIVE_DATE":
          formFields.push(<EventDate value={this.state.modelEvent.startDate} fieldName="Date" editField={this.state.editField} callbacks={callbacks} />);
          break;   
        default: //do nothing
      }
    }

    return (
    <div className="app-event-details">
        {formButtons}
        {formFields}
      </div>
    );
  }

});

module.exports = EventDetails;
