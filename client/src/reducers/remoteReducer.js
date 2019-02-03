import {FETCH_DEVICES, REQUEST_FILE_PATHS, FETCH_FILE_PATHS} from '../actions/types';

const INITIAL_STATE = {
  devices: [],
  files: {},
  sentCommands: []
};

function filesReducer(state = INITIAL_STATE.files, action) {
  switch (action.type) {
    case FETCH_FILE_PATHS:
      return {...state, [action.payload.deviceId]: {data: action.payload}};
    default:
      return state
  }
}

function sentCommandsReducer(state = INITIAL_STATE.sentCommands, action) {
  switch (action.type) {
    case REQUEST_FILE_PATHS:
      return [...state, action.payload];
    default:
      return state;
  }
}

export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case FETCH_DEVICES:
      return {...state, devices: action.payload};
    case FETCH_FILE_PATHS:
      return {...state, files: filesReducer(state, action)};
    case REQUEST_FILE_PATHS:
      return {...state, sentCommands: sentCommandsReducer(state, action)};
    default:
      return state
  }
};
