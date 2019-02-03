import {FETCH_DEVICES} from './types';
import remote from '../api/remote';

export const fetchDevices = () => async dispatch => {
  const res = await remote.get('/devices/all');
  dispatch({type: FETCH_DEVICES, payload: res.data});
};
