import {combineReducers} from 'redux';

import remoteReducer from './remoteReducer';

export default combineReducers({
  remote: remoteReducer
});
