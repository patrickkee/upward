var React = require('react');
var ReactDOM = require('react-dom');
var Router = require('react-router').Router;
var Route = require('react-router').Route;
var hashHistory = require('react-router').hashHistory;
var App = require('./components/App.react');
var Content = require('./components/Content');
var About = require('./components/About');

ReactDOM.render(
    <Router history={hashHistory} >
    	<Route path="/" component={App}>
    		<Route path="models" component={Content}>
    	</Route>
  	</Router>, 
    document.getElementById('app')
);