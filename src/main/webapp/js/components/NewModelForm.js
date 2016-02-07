var React = require('react');
var AppStore = require('../stores/AppStore');
var Icons = require('../constants/Icons');
var AppActions = require('../actions/AppActions');

var NewModelForm = React.createClass({

  //Reverts the UI back to the model selection mode & chooses the first model in the collection
  _cancelNewModel: function() {
    AppActions.selectDefaultModel();
  },

  //Simple toggle for icon hover effect
  _toggleHoverCancel: function() {
    var newVal = !this.state.cancelHovering;
    this.setState({cancelHovering: newVal});
  },

  //Simple toggle for icon hover effect
  _toggleHoverAccept: function() {
    var newVal = !this.state.acceptHovering;
    this.setState({acceptHovering: newVal});
  },

  _onChange: function(/*object*/ event) {
    this.setState({modelName: event.target.value});
  },

  getInitialState: function() {
    return  { cancelHovering: false,
              acceptHovering: false,
              modelName: "" } 
  },

  render: function() {
    //Conditionally highlight the user icon based on the state
    var cancelHovering = this.state.cancelHovering ? "iconHighlight" : "iconNoHighlight";
    var acceptHovering = this.state.acceptHovering ? "iconHighlight" : "iconNoHighlight";

    return (
      <div id="newModel">
        <input className="newModel" type="text" name="newModel" value={this.state.modelName} onChange={this._onChange}/>
        <div className={cancelHovering}>
          <img  className="icon" src={Icons.CANCEL} onClick={this._cancelNewModel} 
                onMouseEnter={this._toggleHoverCancel} onMouseLeave={this._toggleHoverCancel}/>
        </div>
        <div className={acceptHovering}>
        <img  className="icon" src={Icons.SAVE} onClick={this.props.createModelCallback} 
              onMouseEnter={this._toggleHoverAccept} onMouseLeave={this._toggleHoverAccept}/>
        </div>
      </div>
    );
  },

});



module.exports = NewModelForm;
