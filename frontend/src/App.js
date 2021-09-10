import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import Auth from './Auth';
import Home from './Home';

class App extends Component {
  render() {
      return (
          <div>
              <Route exact path="/" component={Home}/>
              <Route path="/auth" component={Auth}/>
          </div>
      );
  }
}
export default App;
