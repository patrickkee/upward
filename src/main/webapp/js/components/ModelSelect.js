var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var AppActions = require('../actions/AppActions');
var ADD_NEW_MODEL = "ADD_NEW_MODEL";

var ModelSelect = React.createClass({

  render: function() {
    var modelDropdown = [];
    for (var p in this.props.models) {
       modelDropdown.push(<option key={p} value={this.props.models[p].name}>{this.props.models[p].name}</option>) 
    }   

    return (
      //Build the models dropdown
      modelView = <select className="modelSelect" value={this.props.selectedModel.name} onChange={this.props.selectModelCallback}>
                    {modelDropdown}
                    <option value={ADD_NEW_MODEL}>+ add new</option>)
                  </select>
    );
  },

});

module.exports = ModelSelect;
