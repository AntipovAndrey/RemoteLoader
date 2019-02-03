import React from 'react';
import {Route, Router} from 'react-router-dom';

import DeviceList from './remote/DeviceList';

import history from '../history';

const App = () => {
  return (
    <div className="ui container">
      <Router history={history}>
        <div>
          <Route path="/" exact component={DeviceList}/>
        </div>
      </Router>
    </div>
  );
};

export default App;
