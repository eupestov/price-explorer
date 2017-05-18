import { getToken, checkLogin } from './auth';

export const requestInterceptor = (RestangularProvider) => {
  // use the custom query parameters function to format the API request correctly
  RestangularProvider.addFullRequestInterceptor((element, operation, what, url, headers, params) => {
    headers['Authorization'] = `Basic ${getToken()}`;
    return {params, headers};
  });
};

export const responseInterceptor = (RestangularProvider) => {
  RestangularProvider.addErrorInterceptor(function(response, deferred, responseHandler) {
    if(response.status === 401 || response.status === 403) {
        checkLogin();
        return false; // error handled
    }

    return true; // error not handled
  });
};
