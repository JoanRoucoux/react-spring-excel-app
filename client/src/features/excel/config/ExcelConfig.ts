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

const EXCEL_ERROR_TYPE = {
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
 * @returns
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
        description: 'You have more than 500 allowed rows, please reduce it.',
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

const ExcelConfig = {
  ERROR_REASONS,
  EXCEL_ERROR_TYPE,
  getPersonsRows,
  getErrorAlertInfo,
};

export default ExcelConfig;
