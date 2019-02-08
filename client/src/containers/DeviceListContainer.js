import React from 'react';

const DeviceListContainer = props => {
  return (
    <div>
      <h2>Devices</h2>
      <div className="ui celled list">
        {props.devices.map(device => (
            <div className="item" key={device.deviceId}>
              <div className="right floated content">
                <button onClick={() => props.onSelect(device.deviceId)} className="ui button primary">
                  Open
                </button>
              </div>
              <i className="large middle aligned icon phone"/>
              <div className="content">
                {device.deviceId}
                <div className="description">{device.name}</div>
              </div>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DeviceListContainer;