package gov.va.escreening.repository;

import gov.va.escreening.entity.Survey;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyRepositoryImpl extends AbstractHibernateRepository<Survey>
        implements SurveyRepository {

    private static final Logger logger = LoggerFactory.getLogger(SurveyRepositoryImpl.class);

    public SurveyRepositoryImpl() {
        super();
        setClazz(Survey.class);
    }

    @Override
    public List<Survey> getRequiredSurveys() {
        logger.trace("in getRequiredSurveys()");
        String sql = "SELECT s FROM Survey s JOIN s.surveySection ss WHERE ss.surveySectionId = :surveySectionId AND s.isPublished = true ORDER BY s.displayOrderForSection";

        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);
        query.setParameter("surveySectionId", 1);

        List<Survey> requiredSurveys = query.getResultList();

        return requiredSurveys;
    }
    
    @Override
    public List<Survey> findForVeteranAssessmentId(int veteranAssessmentId) {

        logger.trace("in findForVeteranAssessmentId()");
        //TODO: This might have to be ordered by s.displayOrderForSection
        String sql = "SELECT s FROM Survey s JOIN s.surveySection ss WHERE s.surveyId IN (SELECT s2.surveyId FROM VeteranAssessment va JOIN va.veteranAssessmentSurveyList vas JOIN vas.survey s2 WHERE va.veteranAssessmentId = :veteranAssessmentId) ORDER BY ss.displayOrder";

        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);

        List<Survey> resultList = query.getResultList();

        return resultList;
    }
    
    @Override
    public List<Survey> getSurveyList() {
        
        String sql = "SELECT s FROM Survey s JOIN s.surveySection ss ORDER BY s.name";

        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);

        return query.getResultList();
    }
    
    @Override
    public List<Survey> getMhaSurveyList()
    {
        String sql = "From Survey s where s.hasMha='1'";
        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);

        return query.getResultList();
    }

	@Override
	public List<Survey> findByTemplateId(Integer templateId) {
		String sql = "SELECT s From Survey s JOIN s.templates t  where t.templateId = :templateId";
        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);
        query.setParameter("templateId", templateId);
        return query.getResultList();
	}

    @Override
    public List<Survey> getSurveyListByIds(List<Integer> surveyIdList) {
        String sql = "From Survey s where s.id in (:ids)";
        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);
        query.setParameter("ids", surveyIdList);
        return query.getResultList();
    }
}
