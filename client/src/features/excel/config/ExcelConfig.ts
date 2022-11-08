import { Utils } from '../../../commons';
import AlertInfoType from '../types/AlertInfoType';
import PersonType from '../types/PersonType';
import PersonRowType from '../types/PersonRowType';

const { isArray, Matcher } = Utils;

const ERROR_REASONS = {
  ERR_DEFAULT: 'err_default',
  ERR_FUNC_MAX_UPLOAD_SIZE: 'err_func_max_upload_size',
  ERR_FUNC_INVALID_FILE_EXTENSION: 'err_func_invalid_file_extension',
  ERR_FUNC_INVALID_ROW_SIZE: 'err_func_invalid_row_size',
  ERR_FUNC_INVALID_HEADER: 'err_func_invalid_header',
  ERR_FUNC_INVALID_FORMAT: 'err_func_invalid_format',
};

const EXCEL_ERROR_TYPES = {
  WRONG_CIVILITY_FORMAT: 'wrong_civility_format',
  WRONG_FIRSTNAME_FORMAT: 'wrong_firstname_format',
  WRONG_LASTNAME_FORMAT: 'wrong_lastname_format',
  WRONG_BIRTHDATE_FORMAT: 'wrong_birthdate_format',
  WRONG_STREET_ADDRESS_FORMAT: 'wrong_street_address_format',
  WRONG_CITY_FORMAT: 'wrong_city_format',
  WRONG_STATE_FORMAT: 'wrong_state_format',
  WRONG_ZIP_CODE_FORMAT: 'wrong_zip_code_format',
  WRONG_MOBILE_NUMBER_FORMAT: 'wrong_mobile_number_format',
  WRONG_EMAIL_ADDRESS_FORMAT: 'wrong_email_address_format',
  DUPLICATE_EMAIL_ADDRESS: 'duplicate_email_address',
};

const excelErrorTypeLabels = {
  [EXCEL_ERROR_TYPES.WRONG_CIVILITY_FORMAT]: 'civility',
  [EXCEL_ERROR_TYPES.WRONG_FIRSTNAME_FORMAT]: 'firstname',
  [EXCEL_ERROR_TYPES.WRONG_LASTNAME_FORMAT]: 'lastname',
  [EXCEL_ERROR_TYPES.WRONG_BIRTHDATE_FORMAT]: 'birthdate',
  [EXCEL_ERROR_TYPES.WRONG_STREET_ADDRESS_FORMAT]: 'street address',
  [EXCEL_ERROR_TYPES.WRONG_CITY_FORMAT]: 'city',
  [EXCEL_ERROR_TYPES.WRONG_STATE_FORMAT]: 'state',
  [EXCEL_ERROR_TYPES.WRONG_ZIP_CODE_FORMAT]: 'zip code',
  [EXCEL_ERROR_TYPES.WRONG_MOBILE_NUMBER_FORMAT]: 'mobile number',
  [EXCEL_ERROR_TYPES.WRONG_EMAIL_ADDRESS_FORMAT]: 'email address',
};

/**
 * Format and return persons rows for DataGrid
 *
 * @param {PersonType[] | undefined} persons list of persons
 * @return {PersonRowType[]} formatted persons rows
 */
const getPersonsRows = (persons: PersonType[] | undefined): PersonRowType[] => {
  let personRows: PersonRowType[] = [];
  if (persons && isArray(persons)) {
    persons.forEach((person: PersonType) => {
      const { id, fullName, birthdate, address, contact } = person;
      const personName: string = `${fullName.civility} ${fullName.firstname} ${fullName.lastname}`;
      personRows.push({
        id,
        fullName: personName,
        birthdate,
        ...address,
        ...contact,
      });
    });
  }
  return personRows;
};

/**
 * Get error alert info
 *
 * @param {string} error current error
 * @returns {AlertInfoType} error alert info
 */
const getErrorAlertInfo = (error: string): AlertInfoType =>
  Matcher()
    .on(
      () => error === ERROR_REASONS.ERR_FUNC_MAX_UPLOAD_SIZE,
      () => ({
        title: 'Max File Size!',
        description:
          'Your file has reach the limit size of 2Mb, please reduce it before uploading again.',
      })
    )
    .on(
      () => error === ERROR_REASONS.ERR_FUNC_INVALID_FILE_EXTENSION,
      () => ({
        title: 'Invalid File Extension!',
        description: 'Please upload a valid Excel file (.xls, .xlsx or .xlsm).',
      })
    )
    .on(
      () => error === ERROR_REASONS.ERR_FUNC_INVALID_ROW_SIZE,
      () => ({
        title: 'Invalid Row Size!',
        description: 'You have more than 100 allowed rows, please reduce it.',
      })
    )
    .on(
      () => error === ERROR_REASONS.ERR_FUNC_INVALID_HEADER,
      () => ({
        title: 'Invalid Header!',
        description:
          'Your file contains an invalid header, please fix it or download the template.',
      })
    )
    .on(
      () => error === ERROR_REASONS.ERR_FUNC_INVALID_FORMAT,
      () => ({
        title: 'Invalid Format!',
        description: 'Your file contains errors, please see below to fix them.',
      })
    )
    .otherwise(() => ({
      title: null,
      description: null,
    })) as AlertInfoType;

/**
 * Returns correct Excel error label
 *
 * @param {string} excelErrorType current Excel error type
 * @returns {string} label
 */
const getExcelErrorTypeLabel = (excelErrorType: string): string =>
  excelErrorType === EXCEL_ERROR_TYPES.DUPLICATE_EMAIL_ADDRESS
    ? 'Duplicate email address'
    : `Wrong ${excelErrorTypeLabels[excelErrorType]} format`;

const ExcelConfig = {
  ERROR_REASONS,
  EXCEL_ERROR_TYPES,
  excelErrorTypeLabels,
  getPersonsRows,
  getErrorAlertInfo,
  getExcelErrorTypeLabel,
};

export default ExcelConfig;
