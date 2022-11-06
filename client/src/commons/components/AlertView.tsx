import React from 'react';
import { Alert, AlertColor, AlertTitle } from '@mui/material';

interface AlertViewType {
  title?: string;
  description?: string;
  severity?: AlertColor;
}

const AlertView = ({
  title,
  description,
  severity,
}: AlertViewType): JSX.Element => (
  <Alert severity={severity || 'error'} sx={{ pt: 0, mb: 2 }}>
    <AlertTitle>{title || 'Error'}</AlertTitle>
    {description || 'Oops, something went wrong!'}
  </Alert>
);

export default AlertView;
