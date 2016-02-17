'use strict';

var React = require('react');
var Icons = require('../constants/Icons');
var ToggleIcon = require('./base/ToggleIcon');

//A single event detail such as the name, type, value, etc
var EventDetailItem = React.createClass({

  _onValueChange: function(event) {
    this.setState({value: event.target.value});
  },

  _onClick: function() {
    this.props.callbacks.click(this.props.title);
  },

  _onCancel: function() {
    this.setState({ value: this.props.value,
                    initialValue: this.props.value });
    this.props.callbacks.cancel();
  },

  _onSave: function() {
    this.props.callbacks.save(this.props.title, this.state.value);
  },

  getInitialState: function() {
    return  { value: this.props.value,
              initialValue: this.props.value } 
  },

  render: function() {
    var output = "";
    if (this.props.editField == this.props.title) {
      output =  <div className="eventDetailItem">
                  <label className="eventDetailItemLabel">{this.props.title}:</label>
                  <input className="eventDetailItemInput"
                            type="text"
                            name={this.props.title}
                            value={this.state.value}
                            onChange={this._onValueChange}/>
                  <div className="eventDetailItemIcon"><ToggleIcon icon={Icons.CANCEL} iconSize="16" onClickCallback={this._onCancel}/></div>
                  <div className="eventDetailItemIcon"><ToggleIcon icon={Icons.SAVE} iconSize="16" onClickCallback={this._onSave} /></div>
                </div>
    } else {
      output =  <div>
                  <label className="eventDetailItemLabel" onClick={this.props.onClickCallback}>{this.props.title}:</label>
                  <input className="eventDetailItemInputDisabled"
                            type="text"
                            name={this.props.title}
                            value={this.state.value}
                            readOnly={true}
                            onClick={this._onClick} />
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = EventDetailItem;
