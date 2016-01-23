var React = require('react');
var Header = require('./Header.react');
var MainSection = require('./MainSection.react');
var UserPanel = require('./UserPanel.react');
var ModelPanel = require('./ModelPanel.react');
var EventPanel = require('./EventPanel.react');
var LoginInput = require('./LoginInput.react');
var AppStore = require('../stores/AppStore');
var AppStates = require('../constants/AppStates');

var App = React.createClass({

  getInitialState: function() {
    //AppStore.loadFromLocalStorage(); //having trouble loading initial state 
    return  {
              viewState: AppStore.getViewState()
            } 
  },

  componentDidMount: function() {
    AppStore.addChangeListener(this._onChange);
  },

  componentWillUnmount: function() {
    AppStore.removeChangeListener(this._onChange);
  },

  render: function() {
    //TODO: Move State mapping to separate library
    var viewStateUi = getViewStateUi(this.state.viewState);
    
    return (
      <div>
        <Header />
        {viewStateUi}
        <MainSection />
      </div>
    );
  },

  _onChange: function() {
    this.setState({
                  viewState: AppStore.getViewState()
                  });
  }

});

function getViewStateUi(viewState) {
  var viewStateUi = "";

  switch(viewState) {
    case AppStates.LOGIN_VIEW:
      viewStateUi = <LoginInput loginFailed={false}/>
      break;

    case AppStates.LOGIN_FAIL_VIEW:
      viewStateUi = <LoginInput loginFailed={true}/>
      break;

    case AppStates.CONTENT_VIEW:
      viewStateUi = <div>
                      <UserPanel />
                      <ModelPanel />
                      <EventPanel />
                    </div>
      break;

    default:
      // no op
  }

  return viewStateUi;
};

module.exports = App;
