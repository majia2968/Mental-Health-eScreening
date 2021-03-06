package gov.va.escreening.repository;

import gov.va.escreening.dto.rule.RuleDto;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Collection;
import java.util.List;

public interface RuleRepository extends RepositoryInterface<Rule> {

    /**
     * Uses the given responses to calculate any associated Rules
     * @param responses 
     * @return List of any Rules that depend on at least one of the given responses.  If not rules 
     * depend on the given responses then an empty List is returned (i.e. never null).
     */
    public List<Rule> getRulesForResponses(Collection<SurveyMeasureResponse> responses);

    public List<Rule> getRuleForAssessment(int veteranAssessmentID);
    
    /**
     * @return all rules of the system but only ID and name are set
     */
    public List<RuleDto> findAllLight();
}
