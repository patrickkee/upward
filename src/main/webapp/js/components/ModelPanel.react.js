var React = require('react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');
var Icons = require('../constants/Icons');
var NewModelForm = require('./NewModelForm');
var ModelSelect = require('./ModelSelect');
var ADD_NEW_MODEL = "ADD_NEW_MODEL";

var ModelPanel = React.createClass({

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

  _createModel: function(/*object*/ event) {
    AppActions.createModel({ modelName: this.refs.newModelForm.state.modelName,
                             targetValue: this.state.selectedModel.targetValue,
                             targetDate: this.state.selectedModel.endDate });
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
      modelView = <NewModelForm ref="newModelForm" createModelCallback={this._createModel}/>
    } else {
      modelView = <ModelSelect  models={this.state.models} 
                                selectedModel={this.state.selectedModel} 
                                selectModelCallback={this._onSelectModel}/>
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
        <div> 
          <div className="target"> 
            <label className="targetValueLabel">Target Value</label>
            <input className="targetValueText"
                  type="text"
                  name="targetVal"
                  value={targetValue}
                  onChange={this._onTargetValueChange}/>
          </div> 
          <div className="target"> 
            <label className="targetDateLabel">Target Date</label>
            <input className="targetDateText"
                  type="text"
                  name="targetDate"
                  value={targetDate}
                  onChange={this._onTargetDateChange}/>
          </div> 
        </div> 
      </div>
    );
  },
});

module.exports = ModelPanel;
