var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var AppActions = require('../actions/AppActions');
var ADD_NEW_MODEL = "ADD_NEW_MODEL";

var ModelSelect = React.createClass({

  //Simple toggle for icon hover effect
  _toggleHoverTrash: function() {
    var newVal = !this.state.trashHovering;
    this.setState({trashHovering: newVal});
  },

  //Simple toggle for icon hover effect
  _toggleSaveHovering: function() {
    var newVal = !this.state.saveHovering;
    this.setState({saveHovering: newVal});
  },
  
  getInitialState: function() {
    return  { trashHovering: false } 
  },


  render: function() {
    //Generate the collection of model options
    var modelDropdown = [];
    for (var p in this.props.models) {
       modelDropdown.push(<option key={p} value={this.props.models[p].name}>{this.props.models[p].name}</option>) 
    }   

     //Conditionally highlight the icon based on the state
    var trashHovering = this.state.trashHovering ? "squareIconHighlight" : "squareIconNoHighlight";
    var saveHovering = this.state.saveHovering ? "iconHighlight" : "iconNoHighlight";

    //Conditionally show the save icon
    var saveIcon = "" 
    if (this.props.showSave) {
      saveIcon =  <div>
                    <img className={saveHovering} src={Icons.SAVE} onClick={this.props.saveModelCallback} 
                         onMouseEnter={this._toggleSaveHovering} onMouseLeave={this._toggleSaveHovering}/>
                  </div>
    } 

    return (
      modelView = <div id="modelSelect">
                    <select className="select" value={this.props.selectedModel.name} 
                            onChange={this.props.selectModelCallback}>
                      {modelDropdown}
                      <option value={ADD_NEW_MODEL}>+ add new</option>)
                    </select>
                    <div>
                      <img  className={trashHovering} src={Icons.TRASH} onClick={this.props.deleteModelCallback} 
                            onMouseEnter={this._toggleHoverTrash} onMouseLeave={this._toggleHoverTrash}/>
                    </div>
                    {saveIcon}                   
                  </div>
    );
  },

});

module.exports = ModelSelect;
