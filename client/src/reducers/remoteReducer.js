import {
  FETCH_DEVICES,
  REQUEST_FILE_PATHS,
  REQUEST_FILE,
  FETCH_FILE_PATHS,
  FETCH_UPLOADED_FILE_PATHS
} from '../actions/types';

const INITIAL_STATE = {
  devices: [],
  files: {},
  availableFiles: {},
  sentCommands: []
};

function filesReducer(state = INITIAL_STATE.files, action) {
  switch (action.type) {
    case FETCH_UPLOADED_FILE_PATHS:
    case FETCH_FILE_PATHS:
      return {...state, [action.payload.deviceId]: action.payload};
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
      return {...state, files: filesReducer(state.files, action)};
    case FETCH_UPLOADED_FILE_PATHS:
      return {...state, availableFiles: filesReducer(state.availableFiles, action)};
    case REQUEST_FILE_PATHS:
    case REQUEST_FILE:
      return {...state, sentCommands: sentCommandsReducer(state.sentCommands, action)};
    default:
      return state
  }
};
