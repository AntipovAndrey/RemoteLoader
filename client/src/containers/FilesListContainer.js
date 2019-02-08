import React from 'react';
import SortableTree from 'react-sortable-tree';
import FileExplorerTheme from 'react-sortable-tree-theme-file-explorer';

const FilesListContainer = ({fileTree, onTreeUpdated}) => {
  return (
    <div style={{height: '90vh'}}>
      <SortableTree
        treeData={fileTree}
        canDrag={false}
        onChange={onTreeUpdated}
        theme={FileExplorerTheme}/>
    </div>
  );
};

export default FilesListContainer;