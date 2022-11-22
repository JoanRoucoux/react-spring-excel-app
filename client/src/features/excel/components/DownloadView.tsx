import React from 'react';
import { Grid, Typography } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import { AlertView } from '../../../commons';
import { useLazyGetTemplateQuery } from '../api/excelSlice';

const DownloadView = (): JSX.Element => {
  const [trigger, { isLoading, isError }] = useLazyGetTemplateQuery();

  return (
    <>
      <Grid
        container
        direction="row"
        justifyContent="center"
        alignItems="center"
        sx={{ pt: 3, pb: 2 }}
      >
        <Grid item xs={12} md={8}>
          <Typography component="h2" variant="h6" gutterBottom>
            1/ Download template
          </Typography>
          <Typography variant="subtitle1" gutterBottom>
            subtitle1. Lorem ipsum dolor sit amet, consectetur adipisicing elit.
            Quos blanditiis tenetur
          </Typography>
        </Grid>
        <Grid item xs={12} md={4} textAlign="center">
          <LoadingButton
            variant="contained"
            loading={isLoading}
            onClick={() => trigger()}
          >
            Download
          </LoadingButton>
        </Grid>
      </Grid>
      {isError && <AlertView />}
    </>
  );
};

export default DownloadView;
