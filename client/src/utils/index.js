import remote from '../api/remote';

export const filesToTree = files => {
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

  return function toTreeData(tree, rootTitle) {
    return Object.keys(tree).map(title => {
      const dataNode = {title: title};
      dataNode.path = `${rootTitle}/${title}`;
      if (Object.keys(tree[title]).length > 0) {
        dataNode.children = toTreeData(tree[title], dataNode.path);
      }
      return dataNode;
    });
  }(tree, '');
};

export const downloadFile = async (uri, name, params) => {
  const response = await remote.get(uri, {...params, responseType: 'blob'});
  const url = window.URL.createObjectURL(new Blob([response.data]));
  const link = document.createElement('a');
  link.href = url;
  link.setAttribute('download', name);
  link.click();
  window.URL.revokeObjectURL(url);
};
