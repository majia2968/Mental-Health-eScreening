/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {EScreeningDashboardApp|*|EScreeningDashboardApp|*|{}|{}}
 */
var EScreeningDashboardApp = EScreeningDashboardApp || { models: EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models") };

(function () {
	'use strict';

	function Rule(obj) {
		this.id = obj.id || null;
		this.name = obj.name || null;
		this.expression = obj.expression || null;
		this.condition = obj.condition || null;
	}

	Rule.prototype.getName = function getName() {
		return this.name;
	};

	EScreeningDashboardApp.models.Rule = Rule;

})();
