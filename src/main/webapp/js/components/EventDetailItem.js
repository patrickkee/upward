var React = require('react');
var Icons = require('../constants/Icons');

//A single event detail such as the name, type, value, etc
var EventDetailItem = React.createClass({

  _onValueChange: function(event) {
    this.setState({value: event.target.value});
  },

  getInitialState: function() {
    return  { value: this.props.value } 
  },

  render: function() {
    var output = "";
    if (this.props.editField == this.props.title) {
      output =  <div>
                  <label>{this.props.title}:</label>
                  <input className="eventDetailItemInput"
                            type="text"
                            name={this.props.title}
                            value={this.state.value}
                            onChange={this._onValueChange}/>
                </div>
    } else {
      output =  <div>
                  <label onClick={this.props.onClickCallback}>{this.props.title}: {this.state.value}</label>
                </div>
    }

    return (
      <div className="eventDetailItem">{output}</div>
    );
  },

});

module.exports = EventDetailItem;
