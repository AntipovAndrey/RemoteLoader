import React from 'react';
import {Route, Router} from 'react-router-dom';

import DeviceList from './remote/DeviceList';
import FilesList from './remote/FilesList';

import history from '../history';

const App = () => {
  return (
    <div className="ui container">
      <Router history={history}>
        <div>
          <Route path="/" exact component={DeviceList}/>
          <Route path="/files/list/:deviceId" exact component={FilesList}/>
        </div>
      </Router>
    </div>
  );
};

export default App;
