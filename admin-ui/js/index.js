import 'ng-admin';
import { checkLogin, logout } from './auth';
import { requestInterceptor, responseInterceptor } from './api_flavor';
import config from './config';

angular.module('admin', ['ng-admin'])
  .config(['RestangularProvider', requestInterceptor])
  .config(['RestangularProvider', responseInterceptor])
  .config(['NgAdminConfigurationProvider', config])
  .controller('auth', ($scope) => $scope.logout = logout)
  .run(checkLogin);
