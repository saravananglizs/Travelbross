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

		$urlRouterProvider.otherwise('/app/index');

		$stateProvider
			.state('app', {
				abstract: true,
        url: '/app',
        templateUrl: layout
			})
			.state('app.index', {
				url:'/index',
				templateUrl:'tpl/main.html'
			})
      .state('main', {
        url: '/main',
        template: '<div ui-view class="fade-in-right-big smooth"></div>',
        data: {
          requireLogin: false
        }
      })
      .state('main.dashboard', {
        // abstract: true,
        url:'/dashboard',
        templateUrl:'tpl/dashboard.html',
        // resolve: load(['assets/js/light-bootstrap-dashboard.js'])
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
        controller: 'AuthenticationController',
        resolve: load(['js/controller/authentication.js', 'js/services/util.js'])
			})

      function load(srcs, callback) {
            return {
                deps: ['$ocLazyLoad', '$q',
                  function( $ocLazyLoad, $q ){
                    var deferred = $q.defer();
                    var promise  = false;
                    srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                    if(!promise){
                      promise = deferred.promise;
                    }
                    angular.forEach(srcs, function(src) {
                      promise = promise.then( function(){
                        // if(JQ_CONFIG[src]){
                        //   return $ocLazyLoad.load(JQ_CONFIG[src]);
                        // }
                        // angular.forEach(MODULE_CONFIG, function(module) {
                        //   if( module.name == src){
                        //     name = module.name;
                        //   }else{
                        //     name = src;
                        //   }
                        // });
                        name = src;
                        return $ocLazyLoad.load(name);
                      } );
                    });
                    deferred.resolve();
                    return callback ? promise.then(function(){ return callback(); }) : promise;
                }]
            }
          }

	}]);
