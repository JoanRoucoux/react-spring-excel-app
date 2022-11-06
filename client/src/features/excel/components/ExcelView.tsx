import React from 'react';
import { Divider, Typography } from '@mui/material';
import DownloadView from './DownloadView';
import UploadView from './UploadView';
import PersonsView from './PersonsView';

const ExcelView = () => (
  <>
    <Typography component="h1" variant="h4" align="center">
      Excel App
    </Typography>
    <DownloadView />
    <Divider variant="middle" />
    <UploadView />
    <Divider variant="middle" />
    <PersonsView />
  </>
);

export default ExcelView;
