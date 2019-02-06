import React, {Component} from 'react';
import SortableTree from 'react-sortable-tree';
import FileExplorerTheme from 'react-sortable-tree-theme-file-explorer';
import {connect} from 'react-redux';

import {fetchFilePaths, requestFilePaths, updateFileTree} from '../../actions';


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

  renderFilesList() {
    return (
      <div style={{height: '90vh'}}>
        <SortableTree
          treeData={this.props.fileTree}
          canDrag={false}
          onChange={fileTree => this.props.updateFileTree(fileTree)}
          theme={FileExplorerTheme}
        />
      </div>
    );
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
  const filesTree = fileTree.length === 0 ? mapFilesToTree(remote.files[deviceId]) : fileTree;
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

const mapFilesToTree = files => {
  if (!files || !files.filesInfo) return;
  const tree = {};
  files.filesInfo
    .map(file => file.path.substr(1))
    .forEach(path => {
      let currentNode = tree;
      path.split('/').forEach(segment => {
        if (currentNode[segment] === undefined) {
          currentNode[segment] = {};
        }
        currentNode = currentNode[segment];
      });
    });

  return function toTreeData(tree) {
    return Object.keys(tree).map(title => {
      const dataNode = {title: title};
      if (Object.keys(tree[title]).length > 0) {
        dataNode.children = toTreeData(tree[title]);
      }

      return dataNode;
    });
  }(tree);
};
