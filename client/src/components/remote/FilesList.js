import React, {Component} from 'react';
import {connect} from 'react-redux';

import {fetchFilePaths, requestFilePaths, updateFileTree} from '../../actions';
import FilesListContainer from '../../containers/FilesListContainer';
import {filesToTree} from '../../utils';

class FilesList extends Component {

  componentDidMount() {
    this.props.fetchFilePaths(this.props.deviceId)
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

  renderFilesList() {
    return <FilesListContainer fileTree={this.props.fileTree}
                               onTreeUpdated={this.updateFiles}/>;
  }

  renderListRequested() {
    return <div>Files list requested</div>;
  }

  onFilesRequested = () => {
    this.props.requestFilePaths(this.props.deviceId)
  };

  render() {
    if (!this.props.fileTree) {
      return this.renderLoading()
    }
    const emptyList = this.props.fileTree.length === 0;
    if (emptyList && this.props.listRequested) {
      return this.renderListRequested()
    }
    if (emptyList) {
      return this.renderNoContent()
    }
    return this.renderFilesList()
  }
}

const mapStateToProps = ({remote, fileTree}, {match: {params}}) => {
  const deviceId = params.deviceId;

  let filesTree;
  if (!(fileTree[deviceId]) || fileTree[deviceId].length === 0) {
    filesTree = filesToTree(remote.files[deviceId])
  } else {
    filesTree = fileTree[deviceId]
  }

  return {
    deviceId,
    fileTree: filesTree,
    listRequested: remote
      .sentCommands
      .filter(item => item.deviceId === deviceId)
      .filter(item => item.action === 'QUERY_LIST')
      .length !== 0
  }
};

export default connect(mapStateToProps,
  {fetchFilePaths, requestFilePaths, updateFileTree}
)(FilesList);
