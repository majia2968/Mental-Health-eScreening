(function() {
    'use strict';

    angular.module('Editors').controller('RulesDetailController', ['$scope', '$filter', 'rule', 'assessmentVariables', 'consults', 'healthFactors', 'dashboardAlerts', 'questions', 'MessageFactory', function($scope, $filter, rule, assessmentVariables, consults, healthFactors, dashboardAlerts, questions, MessageFactory) {

		var removeQueue = [], addQueue = [];

		console.log(rule);

        $scope.rule = rule;
		$scope.consults = consults;
		$scope.healthFactors = healthFactors;
		$scope.dashboardAlerts = dashboardAlerts;
		$scope.questions = questions;
		$scope.assessmentVariables = assessmentVariables;
		$scope.selectedEvents = {};
		$scope.alerts = MessageFactory.get();

		rule.getList('events').then(function(events) {
			$scope.selectedEvents.consults = $filter('filter')(events, {type: 1});
			$scope.selectedEvents.healthFactors = $filter('filter')(events, {type: 2});
			$scope.selectedEvents.dashboardAlerts = $filter('filter')(events, {type: 3});
			$scope.selectedEvents.questions = $filter('filter')(events, {type: 4});
		});

		$scope.removeEvent = function removeEvent(event) {
			removeQueue.push(event.id);
		};

		$scope.addEvent = function addEvent(event) {
			addQueue.push(event);
		};

		$scope.saveRule = function saveRule() {
			$scope.rule.save().then(function(response) {

				console.log(response);

				_.each(removeQueue, function(id) {
					rule.customDELETE('events/' + id);
				});

				_.each(addQueue, function(event) {
					rule.customPOST(event, 'events');
				});
			});

		};

    }]);
})();

