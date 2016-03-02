'use strict';

var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var AppActions = require('../actions/AppActions');
var ToggleIcon = require('./base/ToggleIcon');
var ADD_NEW_MODEL = "ADD_NEW_MODEL";

var ModelSelect = React.createClass({

  getInitialState: function() {
    return  { trashHovering: false } 
  },

  render: function() {
    //Generate the collection of model options
    var modelDropdown = [];
    for (var p in this.props.models) {
       modelDropdown.push(<option key={p} value={this.props.models[p].modelId}>{this.props.models[p].name}</option>) 
    }   

    //Conditionally show the save icon
    var saveIcon = "" 
    if (this.props.showSaveAndCancel) {
      saveIcon =  <div>
                    <ToggleIcon icon={Icons.CANCEL} iconSize="20" tooltip={"Cancel"} onClickCallback={this.props.cancelEditModelCallback}/>
                    <ToggleIcon icon={Icons.SAVE} iconSize="20" tooltip={"Save"} onClickCallback={this.props.saveModelCallback}/>
                  </div>
    } 

    return (
                  <div id="modelSelect">
                    <select className="select" value={this.props.selectedModel.modelId} 
                            onChange={this.props.selectModelCallback}>
                      {modelDropdown}
                      <option value={ADD_NEW_MODEL}>+ add new</option>
                    </select>
                    <div>
                      <ToggleIcon icon={Icons.TRASH} iconSize="20" tooltip={"Delete"} onClickCallback={this.props.deleteModelCallback}/>
                    </div>
                    {saveIcon}                   
                  </div>
    );
  },

});

module.exports = ModelSelect;
