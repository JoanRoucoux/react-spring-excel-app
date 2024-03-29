import React from 'react';
import { ListItem, ListItemText } from '@mui/material';
import { ListChildComponentProps } from 'react-window';
import ExcelConfig from '../config/ExcelConfig';
import ExcelErrorType from '../types/ExcelErrorType';

const { getExcelErrorTypeLabel } = ExcelConfig;

const ExcelErrorItem = (props: ListChildComponentProps): JSX.Element => {
  const { data, index, style } = props;
  const excelError: ExcelErrorType = data[index];
  const { rowIndex, columnLetter, originalValue, excelErrorType } = excelError;
  const excelErrorLabel: string = getExcelErrorTypeLabel(excelErrorType);
  return (
    <ListItem style={style} key={index} component="div" disablePadding>
      <ListItemText
        primary={`${rowIndex} | ${columnLetter} | ${originalValue} | ${excelErrorLabel}`}
      />
    </ListItem>
  );
};

export default ExcelErrorItem;
