import ExcelErrorType from './ExcelErrorType';

interface ErrorType {
  reason: string;
  message: string;
  timestamp: string;
  excelErrorList?: ExcelErrorType[];
}

export default ErrorType;
