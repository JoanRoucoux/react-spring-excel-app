import React from 'react';
import { Box, Grid, Typography } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import { FixedSizeList } from 'react-window';
import { AlertView, Utils } from '../../../commons';
import { useSubmitFileMutation } from '../api/excelSlice';
import ExcelConfig from '../config/ExcelConfig';
import ExcelErrorType from '../types/ExcelErrorType';
import ExcelErrorItem from './ExcelErrorItem';

const { isArray, isFetchBaseQueryError, isErrorType } = Utils;

const { ERROR_REASONS, getErrorAlertInfo } = ExcelConfig;

const UploadView = (): JSX.Element => {
  const [
    trigger,
    { data: personListSize, isLoading, isSuccess, isError, error },
  ] = useSubmitFileMutation();

  const handleChange = (file: File | null) => {
    if (file) {
      let formData = new FormData();
      formData.append('file', file);
      trigger(formData);
    }
  };

  let content;
  if (isSuccess) {
    const description: string = `You have successfully added ${personListSize} person(s)`;
    content = (
      <AlertView title="Success" description={description} severity="success" />
    );
  } else if (isError) {
    let errorReason: string = ERROR_REASONS.ERR_DEFAULT;
    let excelErrorList: ExcelErrorType[] | undefined;
    if (isFetchBaseQueryError(error) && isErrorType(error.data)) {
      errorReason = error.data.reason;
      excelErrorList = error.data.excelErrorList;
    }
    const { title, description } = getErrorAlertInfo(errorReason);
    content = (
      <>
        <AlertView title={title} description={description} />
        {excelErrorList && isArray(excelErrorList) && (
          <Box
            sx={{
              width: '100%',
              height: 150,
              mb: 2,
            }}
          >
            <FixedSizeList
              height={150}
              itemCount={excelErrorList.length}
              itemSize={35}
              itemData={excelErrorList}
              width="100%"
            >
              {ExcelErrorItem}
            </FixedSizeList>
          </Box>
        )}
      </>
    );
  }

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
            2/ Upload completed template
          </Typography>
          <Typography variant="subtitle1" gutterBottom>
            subtitle1. Lorem ipsum dolor sit amet, consectetur adipisicing elit.
            Quos blanditiis tenetur
          </Typography>
        </Grid>
        <Grid item xs={12} md={4} textAlign="center">
          <LoadingButton
            variant="contained"
            component="label"
            loading={isLoading}
          >
            Upload
            <input
              hidden
              accept=".xlsm, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
              type="file"
              onChange={(event: React.ChangeEvent<HTMLInputElement>) =>
                handleChange(event.target.files ? event.target.files[0] : null)
              }
            />
          </LoadingButton>
        </Grid>
      </Grid>
      {content}
    </>
  );
};

export default UploadView;
