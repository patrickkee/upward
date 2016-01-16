var React = require('react');
var App = require('./components/App.react');
var AppStore = require('./stores/AppStore');


React.render(
  <App viewState={AppStore.getViewState}/>,
  document.getElementById('app')
);
