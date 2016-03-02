'use strict';

var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var ToggleIcon = require('./base/ToggleIcon');
var AppActions = require('../actions/AppActions');

var NewModelForm = React.createClass({

  //Reverts the UI back to the model selection mode & chooses the first model in the collection
  _cancelNewModel: function() {
    AppActions.selectDefaultModel();
  },

  _onChange: function(/*object*/ event) {
    this.setState({modelName: event.target.value});
  },

  getInitialState: function() {
    return  { modelName: "" } 
  },

  render: function() {
    return (
      <div id="newModel">
        <input className="newModel" type="text" name="newModel" value={this.state.modelName} onChange={this._onChange}/>
        <ToggleIcon icon={Icons.CANCEL} iconSize="15" tooltip={"Cancel"} onClickCallback={this._cancelNewModel}/>
        <ToggleIcon icon={Icons.SAVE} iconSize="15" tooltip={"Save"} onClickCallback={this.props.createModelCallback}/>
      </div>
    );
  },

});



module.exports = NewModelForm;
