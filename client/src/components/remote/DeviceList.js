import React, {Component} from 'react';
import {connect} from 'react-redux';

import {fetchDevices} from '../../actions';

class DeviceList extends Component {

  componentDidMount() {
    this.props.fetchDevices();
    this.polling = setInterval(() => this.props.fetchDevices(), 1000);
  }

  componentWillMount() {
    clearInterval(this.polling)
  }

  renderList() {
    return this.props.devices.map(device => (
        <div className="item" key={device.deviceId}>
          <i className="large middle aligned icon phone"/>
          <div className="content">
            {device.deviceId}
            <div className="description">{device.name}</div>
          </div>
        </div>
      )
    );
  }

  render() {
    return (
      <div>
        <h2>Devices</h2>
        <div className="ui celled list">{this.renderList()}</div>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    devices: state.remote.devices
  }
};

export default connect(mapStateToProps, {fetchDevices})(DeviceList);
