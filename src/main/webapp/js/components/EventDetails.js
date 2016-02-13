var React = require('react');
var EventTypes = require('../constants/EventTypes');
var Icons = require('../constants/Icons');
var EventDetailItem = require('./EventDetailItem');

var ReactPropTypes = React.PropTypes;

var EventDetails = React.createClass({

  _onClickName: function(event) {
    this.setState({ editField: "Name"});
  },

  _onClickValue: function(event) {
    this.setState({ editField: "Value"});
  },

  _onClickPeriod: function(event) {
    this.setState({ editField: "Period"});
  },

  _onClickType: function(event) {
    this.setState({ editField: "Type"});
  },

  _onClickStart: function(event) {
    this.setState({ editField: "Start"});
  },

  _onClickEnd: function(event) {
    this.setState({ editField: "End"});
  },

  getInitialState: function() {
    return  { editField: "" }
  },

  render: function() {

    return (
      <div className="app-event-details">
        <EventDetailItem title="Name" value={this.props.modelEvent.name} 
                         editField={this.state.editField} onClickCallback={this._onClickName} />
        <EventDetailItem title="Value" value={this.props.modelEvent.value} 
                         editField={this.state.editField} onClickCallback={this._onClickValue} />
        <EventDetailItem title="Period" value={this.props.modelEvent.period} 
                         editField={this.state.editField} onClickCallback={this._onClickPeriod} />
        <EventDetailItem title="Type" value={this.props.modelEvent.type.pretty} 
                         editField={this.state.editField} onClickCallback={this._onClickType} />
        <EventDetailItem title="Start" value={this.props.modelEvent.startDate} 
                         editField={this.state.editField} onClickCallback={this._onClickStart} />
        <EventDetailItem title="End" value={this.props.modelEvent.endDate} 
                         editField={this.state.editField} onClickCallback={this._onClickEnd} />
      </div>
    );
  }

});

module.exports = EventDetails;
