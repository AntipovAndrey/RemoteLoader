import React from 'react';
import SortableTree from 'react-sortable-tree';
import FileExplorerTheme from 'react-sortable-tree-theme-file-explorer';
import _ from 'lodash';

const FilesListContainer = ({fileTree, onTreeUpdated, onClick}) => {
  return (
    <div style={{height: '90vh'}}>
      <SortableTree
        treeData={fileTree}
        canDrag={false}
        onChange={onTreeUpdated}
        theme={FileExplorerTheme}
        generateNodeProps={_.partialRight(generateNode, onClick)}/>
    </div>
  );
};

const generateNode = (rowInfo, onClick) => ({
  onClick: (event) => {
    if (rowInfo.node.children) return;
    if (event.target.className.includes('collapseButton')) return;
    if (event.target.className.includes('expandButton')) return;
    onClick(rowInfo.node.path);
  }
});

export default FilesListContainer;