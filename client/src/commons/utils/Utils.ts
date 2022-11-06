import { FetchBaseQueryError } from '@reduxjs/toolkit/query';
import { ErrorType } from '../../features/excel';

/**
 * Download a file from blob
 *
 * @param {string} blobURL blob URL
 * @param {string} fileName file name
 */
const donwloadFile = (blobURL: string, fileName: string): void => {
  const link: HTMLAnchorElement = document.createElement('a');
  link.href = blobURL;
  link.download = fileName;
  link.click();
};

/**
 * Returns whether or not the given parameter is an array and is not empty
 *
 * @param {unknown} array - array to check
 * @returns {boolean} true if array, false otherwise
 */
const isArray = (array: unknown): boolean =>
  Array.isArray(array) && array.length > 0;

/**
 * Type predicate to narrow an unknown error to `FetchBaseQueryError`
 */
const isFetchBaseQueryError = (
  error: unknown
): error is FetchBaseQueryError => {
  return typeof error === 'object' && error != null && 'status' in error;
};

/**
 * Type predicate to narrow an unknown data to `ErrorType`
 */
const isErrorType = (data: unknown): data is ErrorType => {
  return typeof data === 'object' && data != null && 'reason' in data;
};

/**
 *
 *
 * @param {*} value - Value to switch with
 * @returns
 */
const matched = (value: any) => ({
  on: () => matched(value),
  otherwise: () => value,
});

/**
 * This pattern will replace swith-case with a functional pattern (switch as expression)
 *
 * @param {*} value - Value to switch with
 * @returns {{otherwise: (function(*): *), on: (function(*, *): *)}}
 */
const Matcher = (value?: any) => ({
  on: (predicat: (arg0: any) => any, fn: (arg0: any) => any) =>
    predicat(value) ? matched(fn(value)) : Matcher(value),
  otherwise: (fn: (arg0: any) => any) => fn(value),
});

const Utils = {
  donwloadFile,
  isArray,
  isFetchBaseQueryError,
  isErrorType,
  Matcher,
};

export default Utils;
