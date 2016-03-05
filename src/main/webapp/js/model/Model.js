'use strict';
//TODO: This is just experimental code, not in use yet. The goal here is to refactor the object creational pattern so
//value objects return from the server are decorated with state and functions required for rendering the UI
var Enums = require("./model/Enums");

var Period = (function() {

  return {
    Period: function(aPeriod) {
      this.period = Enums.Event.Period.get(aPeriod).type;
      this.pretty= Enums.Event.Period.get(aPeriod).pretty;
    },
  }

})();

Models.Event = (function() {

  var _state = {}

  var _copy = function() {
    return JSON.parse(JSON.stringify(_state));
  }

  return {
    AppState: function(anEvent) {
      this.event = JSON.parse(JSON.stringify(anEvent));
      this.event.period = new Period(anEvent.period)
      this.copy= _copy;
    },
    
  }
})();