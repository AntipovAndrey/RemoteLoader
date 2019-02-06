import {UPDATE_FILES_TREE} from '../actions/types';

const INITIAL_STATE = [];

export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case UPDATE_FILES_TREE:
      return [...action.payload];
    default:
      return state
  }
};
