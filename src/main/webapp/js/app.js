var React = require('react');
var ReactDOM = require('react-dom');
var Router = require('react-router').Router;
var Route = require('react-router').Route;
var hashHistory = require('react-router').hashHistory;
var App = require('./components/App.react');

ReactDOM.render(
    <Router history={hashHistory} >
    	<Route path="/" component={App} />
  	</Router>, 
    document.getElementById('app')
);