import React from 'react';
import { Box, Button, CircularProgress, Grid, Typography } from '@mui/material';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { LoadingButton } from '@mui/lab';
import { AlertView } from '../../../commons';
import {
  useLazyGetPersonsQuery,
  useDeletePersonsMutation,
} from '../../../commons/api/apiSlice';
import ExcelConfig from '../config/ExcelConfig';

const { getPersonsRows } = ExcelConfig;

const columns: GridColDef[] = [
  {
    field: 'fullName',
    headerName: 'Full Name',
  },
  {
    field: 'birthdate',
    headerName: 'Birthdate',
  },
  {
    field: 'streetAddress',
    headerName: 'Street Address',
  },
  {
    field: 'city',
    headerName: 'City',
  },
  {
    field: 'state',
    headerName: 'State',
  },
  {
    field: 'zipCode',
    headerName: 'ZIP Code',
  },
  {
    field: 'mobileNumber',
    headerName: 'Mobile Number',
  },
  {
    field: 'emailAddress',
    headerName: 'Email Address',
  },
];

const PersonList = (): JSX.Element => {
  const [
    triggerGetPersons,
    {
      data: persons,
      isLoading: isGetPersonsLoading,
      isSuccess: isGetPersonsSuccess,
      isError: isGetPersonsError,
    },
  ] = useLazyGetPersonsQuery();
  const [
    triggerDeletePersons,
    {
      isLoading: isDeletePersonsLoading,
      isSuccess: isDeletePersonsSuccess,
      isError: isDeletePersonsError,
      reset: resetDeletePersons,
    },
  ] = useDeletePersonsMutation();
  const showDataGrid: boolean = isGetPersonsSuccess && !isDeletePersonsSuccess;

  let content;
  if (isGetPersonsLoading) {
    content = (
      <Box sx={{ display: 'flex', justifyContent: 'center' }}>
        <CircularProgress />
      </Box>
    );
  } else if (showDataGrid) {
    content = (
      <>
        <div style={{ height: 300, width: '100%' }}>
          <div style={{ display: 'flex', height: '100%' }}>
            <div style={{ flexGrow: 1 }}>
              <DataGrid
                rows={getPersonsRows(persons)}
                columns={columns}
                pagination
                autoPageSize
                density="compact"
              />
            </div>
          </div>
        </div>
        <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
          <LoadingButton
            variant="contained"
            color="error"
            loading={isDeletePersonsLoading}
            onClick={triggerDeletePersons}
            sx={{ mt: 3, ml: 1 }}
          >
            Delete All
          </LoadingButton>
        </Box>
      </>
    );
  } else if (isGetPersonsError || isDeletePersonsError) {
    content = <AlertView />;
  }

  return (
    <>
      <Grid
        container
        direction="row"
        justifyContent="center"
        alignItems="center"
        sx={{ pt: 3, pb: 1 }}
      >
        <Grid item xs={12} md={8}>
          <Typography component="h2" variant="h6" gutterBottom>
            3/ Data display
          </Typography>
          <Typography variant="subtitle1" gutterBottom>
            subtitle1. Lorem ipsum dolor sit amet, consectetur adipisicing elit.
            Quos blanditiis tenetur
          </Typography>
        </Grid>
        <Grid item xs={12} md={4} textAlign="center">
          <Button
            variant="contained"
            onClick={() => {
              if (isDeletePersonsSuccess) {
                resetDeletePersons();
              }
              triggerGetPersons();
            }}
            disabled={isGetPersonsLoading || showDataGrid}
          >
            Get Persons
          </Button>
        </Grid>
      </Grid>
      {content}
    </>
  );
};

export default PersonList;
