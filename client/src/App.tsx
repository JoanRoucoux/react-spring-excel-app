import React, { FC } from 'react';
import {
  Layout,
  Typography,
} from 'antd';
import { UploadForm } from './components';

const {
  Header,
  Content,
  Footer,
} = Layout;

const App: FC = () => (
  <Layout>
    <Header style={{
      position: 'fixed',
      zIndex: 1,
      width: '100%',
    }}
    >
      Excel App
    </Header>
    <Content
      className="site-layout"
      style={{
        padding: '0 50px',
        marginTop: 64,
      }}
    >
      <div
        className="site-layout-background"
        style={{
          padding: 24,
          minHeight: 380,
        }}
      >
        <Typography.Title>
          Excel Form
        </Typography.Title>
      </div>
    </Content>
    <Footer style={{ textAlign: 'center' }}>
      Antd Excel Form
    </Footer>
  </Layout>
);

export default App;
