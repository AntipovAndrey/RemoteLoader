import React from 'react';

import DeviceListItem from './DeviceListItem';

const DeviceListContainer = ({devices, onSelect}) => {
  return (
    <div>
      <h2>Devices</h2>
      <div className="ui celled list">
        {devices.map(device => <DeviceListItem key={device.deviceId} device={device} onClick={onSelect}/>)}
      </div>
    </div>
  );
};

export default DeviceListContainer;