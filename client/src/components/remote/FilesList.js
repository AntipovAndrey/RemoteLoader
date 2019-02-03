import React, {Component} from 'react';
import {connect} from 'react-redux';


class FilesList extends Component {

  render() {
    return (
      <div>{this.props.deviceId}</div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  console.log(ownProps);
  return {
    deviceId: ownProps.match.params.deviceId
  }
};

export default connect(mapStateToProps, {})(FilesList);
