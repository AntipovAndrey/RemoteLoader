import React from 'react';
import {Route, Router} from 'react-router-dom';

import history from '../history';

const App = () => {
  return (
    <div className="ui container">
      <Router history={history}>
        <div>
        </div>
      </Router>
    </div>
  );
};

export default App;
