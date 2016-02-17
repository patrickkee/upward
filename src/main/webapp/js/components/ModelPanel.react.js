'use strict';

var React = require('react');
var AppStore = require('../stores/AppStore');
var AppActions = require('../actions/AppActions');
var Icons = require('../constants/Icons');
var NewModelForm = require('./NewModelForm');
var ModelSelect = require('./ModelSelect');
var ADD_NEW_MODEL = "ADD_NEW_MODEL";

var ModelPanel = React.createClass({

  //Callback triggered by AppStore change listener
  _onChange: function() {
    var tmpCurrModel = this.props.appState.currentModel; 
    this.setState({ 
              models: this.props.appState.models,
              selectedModel: tmpCurrModel,
              initialSelectedModel: JSON.parse(JSON.stringify(tmpCurrModel)),
              addModelUi: (Object.keys(tmpCurrModel).length < 1) ? true : false,
              edited: false 
            });      
  },

  _onSelectModel: function(/*object*/ event) {
    if (event.target.value === ADD_NEW_MODEL) {
        this.setState({
                        addModelUi: true,
                        selectedModel: {},
                        edited: false
                      });
    } else {
      this.setState({edited: false});
      AppActions.selectModel(event.target.value);
    }
  },

  //Delete the model 
  _onDeleteModel: function() {
    AppActions.deleteModel(this.state.selectedModel);
  },

  _onCreateModel: function(/*object*/ event) {
    AppActions.createModel({ modelName: this.refs.newModelForm.state.modelName,
                             targetValue: this.state.selectedModel.targetValue,
                             targetDate: this.state.selectedModel.endDate });
    this.setState({edited: false});
  },

  _onUpdateModel: function(/*object*/ event) {
    AppActions.updateModel(this.state.selectedModel);
    this.setState({edited: false});
  },

  _onCancelEditModel: function() {
    var selectedModel = JSON.parse(JSON.stringify(this.state.initialSelectedModel))
    this.setState({
                    selectedModel: selectedModel,
                    edited: false
                  });
  },

  _onTargetValueChange: function(/*object*/ event) {
    var tmpModel = JSON.parse(JSON.stringify(this.state.selectedModel));
    tmpModel.targetValue = event.target.value

    this.setState({selectedModel: tmpModel,
                   edited: true });
  },

  _onTargetDateChange: function(/*object*/ event) {
    var tmpModel = JSON.parse(JSON.stringify(this.state.selectedModel));
    tmpModel.endDate = event.target.value

    this.setState({selectedModel: tmpModel,
                   edited: true});
  },

  getInitialState: function() {
    var tmpCurrModel = this.props.appState.currentModel; 
    return  { 
              models: this.props.appState.models,
              selectedModel: tmpCurrModel,
              initialSelectedModel: JSON.parse(JSON.stringify(tmpCurrModel)),
              addModelUi: (Object.keys(tmpCurrModel).length < 1) ? true : false,
              edited: false 
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
    if (this.state.addModelUi || Object.keys(this.state.selectedModel).length < 1) {
      modelView = <NewModelForm ref="newModelForm" createModelCallback={this._onCreateModel}/>
    } else {
      modelView = <ModelSelect  models={this.state.models} 
                                selectedModel={this.state.selectedModel} 
                                selectModelCallback={this._onSelectModel}
                                deleteModelCallback={this._onDeleteModel} 
                                saveModelCallback={this._onUpdateModel}
                                cancelEditModelCallback={this._onCancelEditModel}
                                showSaveAndCancel={this.state.edited} />
    }

    //Null handling for the case when we're adding a new model, so selectedModel is null
    var targetValue = ""
    var targetDate = ""
    if (Object.keys(this.state.selectedModel).length < 1 ) {
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
