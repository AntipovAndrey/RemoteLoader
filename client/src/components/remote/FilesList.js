import React, {Component} from 'react';
import {connect} from 'react-redux';

import {fetchFilePaths, requestFilePaths} from '../../actions';


class FilesList extends Component {

  componentDidMount() {
    this.props.fetchFilePaths(this.props.deviceId)
  }

  renderLoading() {
    return <h2>loading</h2>;
  }

  renderNoContent() {
    return (
      <div>
        <h2>No files for this device found</h2>
        <button onClick={this.onFilesRequested} className="ui button primary">Request files list</button>
      </div>
    );
  }

  renderFilesList(files) {
    return files.map(file => {
        return <div>{file.path}</div>
      }
    );
  }

  renderListRequested() {
    return <div>Files list requested</div>;
  }

  onFilesRequested = () => {
    this.props.requestFilePaths(this.props.deviceId)
  };

  render() {
    if (!this.props.files) {
      return this.renderLoading()
    }
    const emptyList = this.props.files.filesInfo.length === 0;
    if (emptyList && this.props.listRequested) {
      return this.renderListRequested()
    }
    if (emptyList) {
      return this.renderNoContent()
    }
    return this.renderFilesList(this.props.files.filesInfo)
  }
}

const mapStateToProps = (state, ownProps) => {
  const deviceId = ownProps.match.params.deviceId;
  return {
    deviceId,
    files: state.remote.files[deviceId],
    listRequested: state.remote
      .sentCommands
      .filter(item => item.deviceId === deviceId)
      .filter(item => item.action === 'QUERY_LIST')
      .length !== 0
  }
};

export default connect(mapStateToProps, {fetchFilePaths, requestFilePaths})(FilesList);
