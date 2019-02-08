import {UPDATE_FILES_TREE} from '../actions/types';

const INITIAL_STATE = {};

export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case UPDATE_FILES_TREE:
      return {...state, [action.payload.deviceId]: action.payload.newTree};
    default:
      return state
  }
};
