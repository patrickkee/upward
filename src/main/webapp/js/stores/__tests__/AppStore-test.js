jest.dontMock('../../constants/AppConstants');
jest.dontMock('../AppStore');
jest.dontMock('object-assign');

describe('AppStore', function() {

  var AppConstants = require('../../constants/AppConstants');
  var AppDispatcher;
  var AppStore;
  var callback;

  // mock actions
  var actionTodoCreate = {
    actionType: AppConstants.TODO_CREATE,
    text: 'foo'
  };
  var actionTodoDestroy = {
    actionType: AppConstants.TODO_DESTROY,
    id: 'replace me in test'
  };

  beforeEach(function() {
    AppDispatcher = require('../../dispatcher/AppDispatcher');
    AppStore = require('../AppStore');
    callback = AppDispatcher.register.mock.calls[0][0];
  });

  it('registers a callback with the dispatcher', function() {
    expect(AppDispatcher.register.mock.calls.length).toBe(1);
  });

  it('should initialize with no to-do items', function() {
    var all = AppStore.getAll();
    expect(all).toEqual({});
  });

  it('creates a to-do item', function() {
    callback(actionTodoCreate);
    var all = AppStore.getAll();
    var keys = Object.keys(all);
    expect(keys.length).toBe(1);
    expect(all[keys[0]].text).toEqual('foo');
  });

  it('destroys a to-do item', function() {
    callback(actionTodoCreate);
    var all = AppStore.getAll();
    var keys = Object.keys(all);
    expect(keys.length).toBe(1);
    actionTodoDestroy.id = keys[0];
    callback(actionTodoDestroy);
    expect(all[keys[0]]).toBeUndefined();
  });

  it('can determine whether all to-do items are complete', function() {
    var i = 0;
    for (; i < 3; i++) {
      callback(actionTodoCreate);
    }
    expect(Object.keys(AppStore.getAll()).length).toBe(3);
    expect(AppStore.areAllComplete()).toBe(false);

    var all = AppStore.getAll();
    for (key in all) {
      callback({
        actionType: AppConstants.TODO_COMPLETE,
        id: key
      });
    }
    expect(AppStore.areAllComplete()).toBe(true);

    callback({
      actionType: AppConstants.TODO_UNDO_COMPLETE,
      id: key
    });
    expect(AppStore.areAllComplete()).toBe(false);
  });

});
