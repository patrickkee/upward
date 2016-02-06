var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var AppActions = require('../actions/AppActions');

var NewModelForm = React.createClass({

  //Reverts the UI back to the model selection mode & chooses the first model in the collection
  _cancelNewModel: function() {
    AppActions.selectDefaultModel();
  },

  render: function() {
    return (
      <div id="newModel">
        <input className="newModel" type="text" name="newModel"/>
        <img className="icon" src={Icons.CANCEL} onClick={this._cancelNewModel} />
        <img className="icon" src={Icons.SAVE} onClick={this.props.createModelCallback}/>
      </div>
    );
  },

});



module.exports = NewModelForm;
