import React from 'react';

const DeviceListItem = ({device, onClick}) => {
  return (
    <div className="item" key={device.deviceId}>
      <div className="right floated content">
        <button onClick={() => onClick(device.deviceId)} className="ui button primary">Open</button>
      </div>
      <i className="large middle aligned icon phone"/>
      <div className="content">
        {device.deviceId}
        <div className="description">{device.name}</div>
      </div>
    </div>
  );
};

export default DeviceListItem;