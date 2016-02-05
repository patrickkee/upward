var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');
var Icons = require('../constants/Icons');

var ADD_NEW_MODEL = "ADD_NEW_MODEL";

var ModelPanel = React.createClass({

  //Reverts the UI back to the model selection mode & chooses the first model in the collection
  _cancelNewModel: function() {
    AppActions.selectDefaultModel();
  },

  //Callback triggered by AppStore change listener
  _onChange: function() {
    var tmpCurrModel = AppStore.getCurrentModel();
    this.setState({
                    models: AppStore.getModels(),
                    selectedModel: tmpCurrModel,
                    addModelUi: (typeof tmpCurrModel === "undefined") ? true : false
                  });         
  },

  _onSelectModel: function(/*object*/ event) {
    if (event.target.value === ADD_NEW_MODEL) {
        this.setState({addModelUi: true,
                       selectedModel: {}
                      });
    } else {
      AppActions.selectModel(event.target.value);
    }
  },

  _onTargetValueChange: function(/*object*/ event) {
    var tmpModel = this.state.selectedModel
    tmpModel.targetValue = event.target.value

    this.setState({selectedModel: tmpModel});
  },

  _onTargetDateChange: function(/*object*/ event) {
    var tmpModel = this.state.selectedModel
    tmpModel.endDate = event.target.value

    this.setState({selectedModel: tmpModel});
  },

  getInitialState: function() {
    var tmpCurrModel = AppStore.getCurrentModel();
    return  {
              models: AppStore.getModels(),
              selectedModel: tmpCurrModel,
              addModelUi: (typeof tmpCurrModel === "undefined") ? true : false
            } 
  },

  componentDidMount: function() {
    AppStore.addChangeListener(this._onChange);
  },

  componentWillUnmount: function() {
    AppStore.removeChangeListener(this._onChange);
  },

  render: function() {
    var modelView = ""
    if (this.state.addModelUi || typeof this.state.selectedModel === "undefined") {
      //Build the create new model textbox
      modelView = <div className="newModel">
                    <input  className="newModel" type="text" name="newModel"/>
                    <img className="icon" src={Icons.CANCEL} onClick={this._cancelNewModel} />
                    <img className="icon" src={Icons.SAVE} />
                  </div>
    } else {
      //Build the models dropdown
      var modelDropdown = []
      for (var p in this.state.models) {
         modelDropdown.push(<option key={p} value={this.state.models[p].name}>{this.state.models[p].name}</option>) 
      }   

      modelView = <select className="model" value={this.state.selectedModel.name} onChange={this._onSelectModel}>
                    {modelDropdown}
                    <option value={ADD_NEW_MODEL}>+ add new</option>)
                  </select>
    }

    //Null handling for the case when we're adding a new model, so selectedModel is null
    var targetValue = ""
    var targetDate = ""
    if (typeof this.state.selectedModel === "undefined") {
      targetValue = ""
      targetDate = ""
    } else {
      targetValue = this.state.selectedModel.targetValue
      targetDate = this.state.selectedModel.endDate
    }

    return (
      <div id="modelpanel">
          <label className="title">Goal</label>
          {modelView}
        <table>
          <tr>
            <td><label className="targetValueLabel">Target Value</label></td>
            <td><input className="targetValueText"
                  type="text"
                  name="targetVal"
                  value={targetValue}
                  onChange={this._onTargetValueChange}
                />
            </td>
          </tr>
          <tr>
            <td><label className="targetDateLabel">Target Date</label></td>
            <td><input className="targetDateText"
                  type="text"
                  name="targetDate"
                  value={targetDate}
                  onChange={this._onTargetDateChange}
                />
            </td>
          </tr>
        </table>
      </div>
    );
  },
});

module.exports = ModelPanel;
