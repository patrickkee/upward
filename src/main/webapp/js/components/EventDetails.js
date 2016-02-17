var React = require('react');
var EventTypes = require('../constants/EventTypes');
var Icons = require('../constants/Icons');
'use strict';

var EventDetailItem = require('./EventDetailItem');
var AppActions = require('../actions/AppActions');

var ReactPropTypes = React.PropTypes;

var EventDetails = React.createClass({

  _onClick: function(fieldTitle) {
    this.setState({ editField: fieldTitle});
  },

  _onCancel: function() {
    this.setState({ editField: ""});
  },

  _onSave: function(field, value) {
    var newModelEvent = this.state.modelEvent; 
    eval("newModelEvent." + field.toLowerCase() + "=" + value);

    AppActions.updateModelEvent(newModelEvent);

    this.setState({ editField: "",
                    modelEvent: newModelEvent });  
  },  

  getInitialState: function() {
    return  { editField: "",
              modelEvent: this.props.modelEvent }
  },

  render: function() {
    var callbacks = {click: this._onClick,
                     cancel: this._onCancel,
                     save: this._onSave }
    return (
      <div className="app-event-details">
        <EventDetailItem title="Name" value={this.state.modelEvent.name} 
                         editField={this.state.editField} callbacks={callbacks} />
        <EventDetailItem title="Value" value={this.state.modelEvent.value} 
                         editField={this.state.editField} callbacks={callbacks} />
        <EventDetailItem title="Period" value={this.state.modelEvent.period} 
                         editField={this.state.editField} callbacks={callbacks} />
        <EventDetailItem title="Type" value={this.state.modelEvent.type.pretty} 
                         editField={this.state.editField} callbacks={callbacks} />
        <EventDetailItem title="Start" value={this.state.modelEvent.startDate} 
                         editField={this.state.editField} callbacks={callbacks} />
        <EventDetailItem title="End" value={this.state.modelEvent.endDate} 
                         editField={this.state.editField} callbacks={callbacks} />
      </div>
    );
  }

});

module.exports = EventDetails;
