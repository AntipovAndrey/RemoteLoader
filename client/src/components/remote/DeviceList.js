import React, {Component} from 'react';
import {connect} from 'react-redux';

import history from '../../history';
import {fetchDevices} from '../../actions';
import DeviceListContainer from '../../containers/DeviceListContainer';

class DeviceList extends Component {

  componentDidMount() {
    this.props.fetchDevices();
    this.polling = setInterval(() => this.props.fetchDevices(), 1000);
  }

  componentWillMount() {
    clearInterval(this.polling)
  }

  onDeviceSelected = deviceId => {
    history.push(`/files/list/${deviceId}`);
  };

  render() {
    return (
      <DeviceListContainer
        devices={this.props.devices}
        onSelect={this.onDeviceSelected}
      />
    );
  }
}

const mapStateToProps = state => {
  return {
    devices: state.remote.devices
  }
};

export default connect(mapStateToProps, {fetchDevices})(DeviceList);
