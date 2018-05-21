package gov.va.escreening.service;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class AssessmentVariableSrviceTest {
	
	@Resource
	AssessmentVariableService avs;
	
	@Test
	//@Ignore
	public void testGetAssessmentAllVars() {
		assertTrue(avs.getAssessmentAllVars(true, false).size() == 9);
	}
	
	@Test
	@Ignore
	public void testGetAssessmentVarsForSurvey() {
		avs.getAssessmentVarsForSurvey(59, true, false);
	}

}
