import {combineReducers} from 'redux';

import remoteReducer from './remoteReducer';
import fileTreeReducer from './fileTreeReducer';

export default combineReducers({
  remote: remoteReducer,
  fileTree: fileTreeReducer
});
