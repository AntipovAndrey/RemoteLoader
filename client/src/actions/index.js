import {
  FETCH_DEVICES,
  FETCH_FILE_PATHS,
  REQUEST_FILE_PATHS,
  REQUEST_FILE,
  UPDATE_FILES_TREE,
  FETCH_UPLOADED_FILE_PATHS
} from './types';
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

export const requestFile = (deviceId, files) => async dispatch => {
  const queryFileActions = files.map(path => ({action: "FETCH_FILE", payload: path}));
  await remote.post(`/commands/save/${deviceId}`, queryFileActions);
  dispatch({type: REQUEST_FILE, payload: {...queryFileActions, deviceId}});
};

export const fetchUploadedFilePaths = (deviceId) => async dispatch => {
  const res = await remote.get(`/files/all/${deviceId}`);
  dispatch({type: FETCH_UPLOADED_FILE_PATHS, payload: res.data})
};

export const updateFileTree = (deviceId, newTree) => {
  return {type: UPDATE_FILES_TREE, payload: {newTree, deviceId}}
};
