import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import Auth from './Auth';
import Home from './Home';
import Login from './Login';

class App extends Component {
  render() {
      return (
          <div>
              <Route exact path="/" component={Home}/>
              <Route path="/auth" component={Auth}/>
              <Route exact path="/login" component={Login} /> 
          </div>
      );
  }
}
export default App;
