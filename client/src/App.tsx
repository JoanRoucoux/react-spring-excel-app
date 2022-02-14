import React, { FC } from 'react';
import { Button } from 'antd';
import logo from './logo.svg';
import './App.css';

const App: FC = () => (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <Button type="primary">
            Button
        </Button>
      </header>
    </div>
  );

export default App;
