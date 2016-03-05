'use strict';

var AppState = (function() {

  var _state = {}

  var _copy = function() {
    return JSON.parse(JSON.stringify(_state));
  }

  return {
    AppState: function(state) {
      _state = JSON.parse(JSON.stringify(state));
      this.copy= _copy;
    },
    
  }
})();

module.exports = AppState;
