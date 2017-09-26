'use strict';

/**
 * Config for the router
 */

// ngRoute --- ng-view

/** angular.module('app')
.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/addOrder', {
        templateUrl: 'templates/add-order.html',
        controller: 'AddOrderController'
      }).
      when('/showOrders', {
        templateUrl: 'templates/show-orders.html',
        controller: 'ShowOrdersController'
      }).
      otherwise({
        redirectTo: '/addOrder'
      });
  }]); */

// ui-router --- ui-view

angular.module('app')
	.config(['$stateProvider','$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider) {

		// $locationProvider.html5Mode(true);

		var layout = "tpl/app.html";

		$urlRouterProvider.otherwise('/app/dashboard');

		$stateProvider
			.state('app', {
				abstract: true,
                url: '/app',
                templateUrl: layout
			})
			.state('app.dashboard', {
				url:'/dashboard',
				templateUrl:'tpl/dashboard.html'
			})
			.state('access', {
                  url: '/access',
                  template: '<div ui-view class="fade-in-right-big smooth"></div>',
                  data: {
                    requireLogin: false
                  }
              })
			.state('access.signin', {
				url: '/signin',
                templateUrl: 'tpl/signin.html',
			})
			.state('access.signup', {
				url: '/signup',
                templateUrl: 'tpl/signup.html',
			})

	}]);