import {FETCH_DEVICES, FETCH_FILE_PATHS, REQUEST_FILE_PATHS} from './types';
import remote from '../api/remote';

export const fetchDevices = () => async dispatch => {
  const res = await remote.get('/devices/all');
  dispatch({type: FETCH_DEVICES, payload: res.data});
};

export const fetchFilePaths = deviceId => async dispatch => {
  const res = await remote.get(`/paths/recent/${deviceId}`);
  dispatch({type: FETCH_FILE_PATHS, payload: res.data});
};

export const requestFilePaths = deviceId => async dispatch => {
  const queryListAction = {action: "QUERY_LIST"};
  await remote.post(`/commands/save/${deviceId}`, [queryListAction]);
  dispatch({type: REQUEST_FILE_PATHS, payload: {...queryListAction, deviceId}});
};
