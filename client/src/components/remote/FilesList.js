import React, {Component} from 'react';
import {connect} from 'react-redux';

import {fetchFilePaths, fetchUploadedFilePaths, requestFile, requestFilePaths, updateFileTree} from '../../actions';
import FilesListContainer from '../../containers/FilesListContainer';
import {filesToTree} from '../../utils';

class FilesList extends Component {

  componentDidMount() {
    this.props.fetchUploadedFilePaths(this.props.deviceId);
    this.props.fetchFilePaths(this.props.deviceId);
  }

  renderLoading() {
    return <h2>loading</h2>;
  }

  updateFiles = newTree => this.props.updateFileTree(this.props.deviceId, newTree);

  renderNoContent() {
    return (
      <div>
        <h2>No files for this device found</h2>
        <button onClick={this.onFilesRequested} className="ui button primary">
          Request files list
        </button>
      </div>
    );
  }

  onFilesRequested = () => {
    this.props.requestFilePaths(this.props.deviceId);
  };

  renderFilesList() {
    return <FilesListContainer fileTree={this.props.fileTree}
                               onClick={this.onFileClicked}
                               onTreeUpdated={this.updateFiles}/>;
  }

  renderListRequested() {
    return <div>Files list requested</div>;
  }

  onFileClicked = path => {
    const availableFile = this.props.availableFiles.find(file => file.path === path);
    if (availableFile) {
      // todo: load here
    } else {
      this.props.requestFile(this.props.deviceId, [path]);
    }
  };

  render() {
    if (!this.props.fileTree) {
      return this.renderLoading()
    }
    const emptyList = this.props.fileTree.length === 0;
    if (emptyList && this.props.listRequested) {
      return this.renderListRequested();
    }
    if (emptyList) {
      return this.renderNoContent();
    }
    return this.renderFilesList();
  }
}

const mapStateToProps = ({remote, fileTree}, {match: {params}}) => {
  const deviceId = params.deviceId;

  let filesTree;
  if (!(fileTree[deviceId]) || fileTree[deviceId].length === 0) {
    filesTree = filesToTree(remote.files[deviceId] || []);
  } else {
    filesTree = fileTree[deviceId];
  }

  const availableForDevice = (remote.availableFiles[deviceId] || {}).filesInfo || [];

  return {
    deviceId,
    fileTree: filesTree,
    availableFiles: availableForDevice,
    listRequested: remote
      .sentCommands
      .filter(item => item.deviceId === deviceId)
      .filter(item => item.action === 'QUERY_LIST')
      .length !== 0
  };
};

export default connect(mapStateToProps,
  {fetchFilePaths, requestFilePaths, updateFileTree, requestFile, fetchUploadedFilePaths}
)(FilesList);
