import React from 'react';
import { ListItem, ListItemText } from '@mui/material';
import { ListChildComponentProps } from 'react-window';
import ExcelErrorType from '../types/ExcelErrorType';

const ExcelErrorItem = (props: ListChildComponentProps): JSX.Element => {
  const { data, index, style } = props;
  const excelError: ExcelErrorType = data[index];
  const { rowIndex, columnLetter, originalValue, excelErrorType } = excelError;
  return (
    <ListItem style={style} key={index} component="div" disablePadding>
      <ListItemText
        primary={`${rowIndex} | ${columnLetter} | ${originalValue} | ${excelErrorType}`}
      />
    </ListItem>
  );
};

export default ExcelErrorItem;
