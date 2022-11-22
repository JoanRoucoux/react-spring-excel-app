import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import Utils from '../../../commons/utils/Utils';
import { Persontype } from '..';

const { donwloadFile } = Utils;

export const apiSlice = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080/api/excel',
  }),
  endpoints: (builder) => ({
    getTemplate: builder.query<void, void>({
      query: () => ({
        url: `/download`,
        responseHandler: async (response: Response) => {
          const blobURL: string = window.URL.createObjectURL(
            await response.blob()
          );
          donwloadFile(blobURL, 'excel_template.xlsx');
        },
        cache: 'no-cache',
      }),
    }),
    submitFile: builder.mutation<number, FormData>({
      query: (formData) => ({
        url: '/upload',
        method: 'POST',
        body: formData,
      }),
    }),
    getPersons: builder.query<Persontype[], void>({
      query: () => '/persons',
    }),
    deletePersons: builder.mutation({
      query: () => ({
        url: '/persons',
        method: 'DELETE',
      }),
    }),
  }),
});

export const {
  useLazyGetTemplateQuery,
  useSubmitFileMutation,
  useLazyGetPersonsQuery,
  useDeletePersonsMutation,
} = apiSlice;
