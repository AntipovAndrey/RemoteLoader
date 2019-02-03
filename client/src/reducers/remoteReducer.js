import {FETCH_DEVICES} from '../actions/types';

const INITIAL_STATE = {
  devices: []
};

export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case FETCH_DEVICES:
      return {state, devices: action.payload};
    default:
      return state
  }
};
