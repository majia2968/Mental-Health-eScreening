package gov.va.escreening.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.va.escreening.controller.dashboard.ExportDataRestController;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.entity.AssessmentFormula;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Event;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.form.ExportDataFormBean;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.EventRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.export.DataDictionary;
import gov.va.escreening.service.export.DataDictionaryService;
import gov.va.escreening.service.export.ExportDataService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

//@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/spring/root-context.xml" })
public class AssessmentVariableSrviceTest {
			
	@Resource
	VeteranAssessmentService vaSvc;
	
	@Resource
	AssessmentVariableRepository avRepo;
	
	@Resource
    AssessmentVariableService avService;
	
	@Resource
	VeteranAssessmentRepository assessmentRepo;
	
    @Resource(type=AssessmentDelegate.class)
    AssessmentDelegate ad;

	@Resource(type = SurveyRepository.class)
	private SurveyRepository sr;	

	@Resource(type = AssessmentVariableRepository.class)
	private AssessmentVariableRepository avr;

	@Resource(type = VeteranAssessmentService.class)
	private VeteranAssessmentService veteranAssessmentService;

	@Autowired
	private ExportDataService exportDataService;

	@Autowired
	ExportDataRestController exportDataRestController;

	@Resource(name = "theDataDictionary")
	DataDictionary dd;
	@Resource(type = DataDictionaryService.class)
	DataDictionaryService dds;

	@Autowired
	private AssessmentEngineService assessmentEngineService;

	@Autowired
	EventRepository eventRepo;

	@Resource(type = ExpressionEvaluatorService.class)
	ExpressionEvaluatorService expressionEvaluator;

	private List<Measure> measures;
    @Test
	@Ignore
    public void testTimeSeries()
	{
		//Collection<AssessmentVariable> avList = avr.findAll();
		//System.out.println(avList.size());
		//Collection<AssessmentVariable> avListq = avr.findAllAssessmentVariables();
		//System.out.println(avListq.toString());

		//assertEquals(2568, measures.size());
		Table<String, String, Object> t = avService.getAssessmentAllVars(true, false);
		for (Map<String, Object> i : avTableToList(t)) {
			System.out.println(i.toString());
		}
		//System.out.println(avTableToList(t).toString());
		assertEquals(7816, avService.getAssessmentAllVars(true, false).size());
	}
    
    //@Ignore
	@Test
	public void testAskFormulasFor()
	{
		assertEquals(5,avService.askFormulasFor(16).size());
		//assertEquals(2,avService.askFormulasFor(16).size());
	}
	
	@Test
	@Transactional
	public void testAssessmentVariables()
	{

       // Collection<AssessmentVariable> avList = avr.findAvs();
        Collection<AssessmentVariable> avList = avr.findAll();
        List<AssessmentVarChildren> assessmentVarChildrenList;
        for (AssessmentVariable av : avList) {
        	//av.getDescription();
        	assessmentVarChildrenList = av.getAssessmentVarChildrenList();
        	for (AssessmentVarChildren af : assessmentVarChildrenList) {
        		//System.out.println(af.getAssessmentVarChildrenId());
        		//af.getAssessmentVarChildrenId();
        	}
        	//assessmentVarChildrenList = av.getAssessmentVarChildrenList();
        	
        			//assessmentFormulas.get(1);
        }

        //avRepo.findAvs();
	}
	

	@Ignore
	@Test
	public void testVisibility()
	{
		List<Event> events = eventRepo.getEventByTypeFilteredByObjectIds(4, new ArrayList<>());
		assertEquals(2,events.size());
//		String jsonString = "{\"pageId\":350,\"navigation\":\"next\",\"userAnswers\":[{\"measureId\":\"3689\",\"isVisible\":true},{\"measureId\":\"3721\",\"answers\":[{\"answerId\":20413,\"answerResponse\":\"\"}],\"isVisible\":true},{\"measureId\":\"3693\",\"answers\":[{\"answerId\":20306,\"answerResponse\":\"0\"}],\"isVisible\":true},{\"measureId\":\"3698\",\"isVisible\":true},{\"measureId\":\"3694\",\"answers\":[{\"answerId\":20307,\"answerResponse\":\"\"}],\"isVisible\":true}]}";
//		Map<String, Object> requestData = new HashMap<String, Object>();
//		AssessmentRequest assessmentRequest = new AssessmentRequest();
//		ObjectMapper mapper = new ObjectMapper();
//		try
//		{
//			//Convert Map to JSON
//			assessmentRequest = mapper.readValue(jsonString, AssessmentRequest.class);
//			//Print JSON output
//			System.out.println(requestData);
//		}
//		catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		assessmentRequest.setAssessmentId(18);
//		Map<Integer, Boolean> inMemory = assessmentEngineService.getUpdatedVisibilityInMemory(assessmentRequest);

		//assertEquals(2,avService.askFormulasFor(16).size());
	}

	@Ignore
	@Test
	public void testExportData() throws java.text.ParseException
	{
		ExportDataFormBean exportDataFormBean = getSearchFormBean(null, "05/22/2000", "05/24/2018", "", "", "1", "", "", "", null);
		exportDataFormBean.setExportedByUserId(1);
		exportDataFormBean.setExportTypeId(3);
		if (!dds.tryPrepareDataDictionary(false)) {
			//modelAndView.addObject("createUserStatusMessage", Arrays.asList("Data Dictionary is being built. Please try again in a min. or so..."));
			//modelAndView.setViewName("exportData");
			//return modelAndView;
			//return "Data Dictionary is being built. Please try again in a min. or so...";
		}
		AssessmentDataExport dataExport = exportDataService.getAssessmentDataExport(exportDataFormBean, null);
		//List<VeteranAssessment> matchingAssessments = veteranAssessmentService.searchVeteranAssessmentForExport(exportDataFormBean);
	}

	//@Ignore
	@Test
	public void testformula()
	{
		Map<String, Object> formulaById = expressionEvaluator.fetchFormulaFromDbById(70027);
		assertEquals(2,formulaById.size());
	}

	private static final String IDENTIFIED_EXPORT = "identified";
	private static final String DEIDENTIFIED_EXPORT = "deidentified";

	public ExportDataFormBean getSearchFormBean(EscreenUser escreenUser,
												String fromAssessmentDate, String toAssessmentDate,
												String clinicianId, String createdByUserId, String programId,
												String veteranId, String comment, String exportDataType,
												List<String> errors) throws java.text.ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		ExportDataFormBean exportDataFormBean = new ExportDataFormBean();
		exportDataFormBean.setHasParameter(false);

		if (clinicianId != null && !clinicianId.isEmpty() && !"null".equals(clinicianId) && !clinicianId.equalsIgnoreCase("undefined")) {
			if (isInteger(clinicianId)) {
				exportDataFormBean.setClinicianId(Integer.parseInt(clinicianId));
				exportDataFormBean.setHasParameter(true);
			} else {
				errors.add("Clinician must be be a number.");
			}
		}

		if (createdByUserId != null && !createdByUserId.isEmpty() && !"null".equals(createdByUserId) && !createdByUserId.equalsIgnoreCase("undefined")) {
			if (isInteger(createdByUserId)) {
				exportDataFormBean.setCreatedByUserId(Integer.parseInt(createdByUserId));
				exportDataFormBean.setHasParameter(true);
			} else {
				errors.add("Created by user must be be a number.");
			}
		}

		if (veteranId != null && !veteranId.isEmpty() && !"null".equals(veteranId) && !veteranId.equalsIgnoreCase("undefined")) {
			if (isInteger(veteranId)) {
				exportDataFormBean.setVeteranId(Integer.parseInt(veteranId));
				exportDataFormBean.setHasParameter(true);
			} else {
				errors.add("Veteran must be be a number.");
			}
		}

		if (fromAssessmentDate != null && !fromAssessmentDate.isEmpty() && !fromAssessmentDate.equalsIgnoreCase("undefined")) {
			try {
				exportDataFormBean.setFromAssessmentDate(sdf.parse(fromAssessmentDate));
				exportDataFormBean.setHasParameter(true);
			} catch (ParseException e) {
				errors.add("From Assessment Date must be a valid date: MM/dd/yyyy.");
			}
		}

		if (toAssessmentDate != null && !toAssessmentDate.isEmpty() && !toAssessmentDate.equalsIgnoreCase("undefined")) {
			try {
				exportDataFormBean.setToAssessmentDate(sdf.parse(toAssessmentDate));
				exportDataFormBean.setHasParameter(true);
			} catch (ParseException e) {
				errors.add("To Assessment Date must be a valid date: MM/dd/yyyy.");
			}
		}

		if (programId != null && !programId.isEmpty() && !"null".equals(programId) && !programId.equalsIgnoreCase("undefined")) {
			if (isInteger(programId)) {
				exportDataFormBean.setProgramId(Integer.parseInt(programId));
				exportDataFormBean.setHasParameter(true);
			} else {
				errors.add("Program must be be a number.");
			}
		}

		if (comment != null && !comment.isEmpty()) {
			exportDataFormBean.setCommentText(comment);
		}

		if (exportDataType != null && !exportDataType.isEmpty()) {
			if (exportDataType.equalsIgnoreCase(IDENTIFIED_EXPORT)) {
				exportDataFormBean.setExportTypeId(ExportTypeEnum.IDENTIFIED.getExportTypeId());
			} else if (exportDataType.equalsIgnoreCase(DEIDENTIFIED_EXPORT)) {
				exportDataFormBean.setExportTypeId(ExportTypeEnum.DEIDENTIFIED.getExportTypeId());
			} else {
				errors.add("Export type must either be 'identified' or 'deidentified'.");
			}
		}

		// Populate the clinic id list from the logged in user.
		// exportDataFormBean.setProgramIdList(escreenUser != null ? escreenUser.getProgramIdList() : null);

		// Assessment Start Date from last snapshop date is by default
		if (!exportDataFormBean.getHasParameter() && errors.isEmpty()) {
			exportDataFormBean.setFromAssessmentDate(exportDataService.getLastSnapshotDate());
			exportDataFormBean.setToAssessmentDate(new Date());
			exportDataFormBean.setHasParameter(true);
		}
		return exportDataFormBean;
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}



	private List<Map<String, Object>> avTableToList(Table<String, String, Object> t){

		List<Map<String, Object>> avs = Lists.newArrayList();
		System.out.println(t.rowMap().size());
		for (String rowKey : t.rowKeySet()) {
			Map<String, Object> m = Maps.newHashMap(t.row(rowKey)); // need HashMap as it allows nulls as key or values
			System.out.println(m.toString());
			// remove properties with 0 -- only send that data on the wire which makes sense
			for (Iterator<Map.Entry<String, Object>> eIter = m.entrySet().iterator(); eIter.hasNext(); ) {
				Map.Entry<String, Object> e = eIter.next();
				if (e.getValue().equals(0)) {
					eIter.remove();
				}
			}
			avs.add(m);
		}

		return avs;
	}


//	@Test
//	public void testTimeSeries()
//	{
//		Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(TEST_VET_ID, 11, 3);
//		assertNotNull(timeSeries);
//		assertEquals(1, timeSeries.size());
//	}
//
//	@Test
//    public void testVeteran18_PainScoreTimeSeries(){
//        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 2300, 12);
//        assertNotNull(timeSeries);
//        assertEquals(1, timeSeries.size());
//    }
//
//    @Test
//    public void testVeteran18_PHQ9TimeSeries(){
//        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 1599, 12);
//        assertNotNull(timeSeries);
//        assertEquals(1, timeSeries.size());
//    }
//
//	@Test
//    public void testVeteran18_PTSDTimeSeries(){
//        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 1929, 12);
//        assertNotNull(timeSeries);
//        assertEquals(1, timeSeries.size());
//    }
//
//	@Test
//    public void testVeteran16_PHQ9TimeSeries(){
//        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(16, 1599, 12);
//        assertNotNull(timeSeries);
//        assertEquals(2, timeSeries.size());
//    }
//
//	@Test
//    public void testVeteran16_PHQ9TimeSeries_archivedAssessments(){
//		//Set archive date for these
//		for(VeteranAssessment assessment : assessmentRepo.findByVeteranId(16)){
//			if(assessment.getDateArchived() != null){
//				throw new IllegalStateException("If archive date is set then this code must change");
//			}
//			assessment.setDateArchived(new Date());
//			assessmentRepo.update(assessment);
//		}
//
//        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(16, 1599, 12);
//        assertNotNull(timeSeries);
//        //clean assessments should not be returned so only 2 here
//        assertEquals(2, timeSeries.size());
//
//        //unset archive date for these
//		for(VeteranAssessment assessment : assessmentRepo.findByVeteranId(16)){
//			assessment.setDateArchived(null);
//			assessmentRepo.update(assessment);
//		}
//    }
//
//    @Test
//    public void testRecordAllReportableScores(){
//        final VeteranAssessment testAssessment = vaSvc.findByVeteranAssessmentId(18);
//        ad.recordAllReportableScores(testAssessment);
//    }
//
//    @Test
//    public void testNewChildFormulaDependencyUpdatesAllParents() throws Exception{
//        AssessmentVariable rootFormula = createRootFormula();
//
//        //for child formula get parent
//        List<AssessmentVariable> descendants = collectDescendantList(rootFormula);
//        assertTrue(descendants.size() > 2);
//
//        //Find a child formula which we will add a new dependency to
//        AssessmentVariable childFormula = descendants.get(descendants.size()-1);
//
//        //add new dep to child and save
//        AssessmentVariable newDependency = addNewDependency(childFormula);
//
//        //Call test method (this updates all formulas up the chain)
//        avService.updateParentFormulas(childFormula);
//
//        //check to see that the new dep is associated with every descendant
//        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
//    }
//
//    @Test
//    public void testRemovingChildsDependencyRemovesDepFromParents(){
//        AssessmentVariable rootFormula = createRootFormula();
//
//        //for child formula get parent
//        List<AssessmentVariable> descendants = collectDescendantList(rootFormula);
//        assertTrue(descendants.size() > 2);
//
//        //Find a child formula which we will add a new dependency to
//        AssessmentVariable childFormula = descendants.get(descendants.size()-1);
//
//        //create and add new dep to child formula and save
//        AssessmentVariable newDependency = addNewDependency(childFormula);
//
//        //Call test method (this updates all formulas up the chain)
//        avService.updateParentFormulas(childFormula);
//
//        //check to see that the new dep is associated with every descendant
//        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
//
//        //Remove the dependency from the child formula
//        childFormula.setFormulaTemplate("");
//        removeChildAv(childFormula, newDependency);
//
//        //Call test method (this updates all formulas up the chain)
//        avService.updateParentFormulas(childFormula);
//
//        //test to make sure the dependency has been removed from all descendants
//        assertDescendantsMissingDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
//    }
//
//    /**
//     * Test to make sure if two different child formulas have the same dependency,
//     * removing that dependency from one of the child formulas will not cause it to be
//     * removed from the parent until it is removed from both child formulas.
//     */
//    @Test
//    public void testRemovingCommonDependencyKeepsDepInParents(){
//        AssessmentVariable rootFormula = createRootFormula();
//
//        //for child formula get parent
//        List<AssessmentVariable> descendants = collectDescendantList(rootFormula);
//        assertTrue(descendants.size() > 2);
//
//        //Find a child formula which we will add a new dependency to
//        AssessmentVariable childFormula1 = descendants.get(descendants.size()-1);
//
//        //Add another sibling descendant
//        AssessmentVariable childFormula2 = addChildFormula(rootFormula);
//
//        //create and add new dep to child formula 1 and save
//        AssessmentVariable newDependency = addNewDependency(childFormula1);
//
//        //add same dep to child formula 2
//        childFormula2.getAssessmentVarChildrenList().add(new AssessmentVarChildren(childFormula2, newDependency));
//        avRepo.update(childFormula2);
//
//        //Call test method (this updates all formulas up the chain)
//        avService.updateParentFormulas(childFormula1);
//        avService.updateParentFormulas(childFormula2);
//
//        //check to see that the new dep is associated with every descendant
//        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
//
//        //remove the dep from one of the descendants but not the other
//        removeChildAv(childFormula2, newDependency);
//        childFormula2.setFormulaTemplate("");
//
//        //Call test method to update parents (this should not remove the dep from the root formula)
//        avService.updateParentFormulas(childFormula2);
//
//        //check to see that the new dep is associated with every descendant
//        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
//
//        //Now remove the dep from the other sibling and the dep show go away
//        removeChildAv(childFormula1, newDependency);
//        childFormula1.setFormulaTemplate("");
//
//        //Call test method to update parents (this should remove the dep from the root formula because this is the last reference to it)
//        avService.updateParentFormulas(childFormula1);
//
//        //test to make sure the dependency has been removed from all descendants
//        assertDescendantsMissingDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
//    }
//
//    /**
//     * Creates a formula that has associated with it at least two child formulas with
//     * an empty list of dependencies
//     * @return formula that has been saved to the DB.
//     */
//    private AssessmentVariable createRootFormula(){
//
//        AssessmentVariable rootFormula = new AssessmentVariable();
//        rootFormula.setDisplayName("Root Formula");
//        rootFormula.setAssessmentVariableTypeId(TestAssessmentVariableBuilder.TYPE_FORMULA);
//        avRepo.create(rootFormula);
//
//        //add descendant formulas
//        AssessmentVariable childFormula = addChildFormula(rootFormula);
//        for(int i = 0; i < 4; i++){
//            childFormula = addChildFormula(childFormula);
//        }
//
//        return rootFormula;
//    }
//
//    /**
//     * Adds a child formula to {@code formula} and returns the child
//     */
//    private AssessmentVariable addChildFormula(AssessmentVariable formula){
//        if(formula.getAssessmentVarChildrenList() == null){
//            formula.setAssessmentVarChildrenList(Lists.<AssessmentVarChildren>newArrayList());
//        }
//
//        AssessmentVariable childFormula = new AssessmentVariable();
//        childFormula.setDisplayName("Child Formula");
//        childFormula.setAssessmentVariableTypeId(TestAssessmentVariableBuilder.TYPE_FORMULA);
//        childFormula.setAssessmentVarChildrenList(Lists.<AssessmentVarChildren>newArrayList());
//        avRepo.create(childFormula);
//
//        AssessmentVarChildren varChild = new AssessmentVarChildren(formula, childFormula);
//        formula.getAssessmentVarChildrenList().add(varChild);
//        avRepo.update(formula);
//        logger.trace("Added child formula {} to formula {}", childFormula.getAssessmentVariableId(), formula.getAssessmentVariableId());
//        return childFormula;
//    }
//
//    /**
//     * Creates and adds new dependency (AV) to formula and saves
//     * @param formula
//     * @return
//     */
//    private AssessmentVariable addNewDependency(AssessmentVariable formula){
//        AssessmentVariable newDependency = new AssessmentVariable();
//        newDependency.setDisplayName("testchildvar");
//        newDependency.setAssessmentVariableTypeId(TestAssessmentVariableBuilder.TYPE_ANSWER);
//        avRepo.create(newDependency);
//        //add dependency to fake formula
//        formula.setFormulaTemplate("[" + newDependency.getAssessmentVariableId() + "]");
//        formula.getAssessmentVarChildrenList().add(new AssessmentVarChildren(formula, newDependency));
//        avRepo.update(formula);
//        logger.trace("Added new dependency {} to formula {}", newDependency.getAssessmentVariableId(), formula.getAssessmentVariableId());
//        return newDependency;
//    }
//
//    /**
//     * Collects the longest list of descendants for the formula and asserts that each descendant contains the given dependency.
//     * @param formula
//     * @param dependency
//     */
//    private void assertDescendantsHaveDep(AssessmentVariable formula, AssessmentVariable dependency){
//        for(AssessmentVariable descendant : collectDescendantList(formula)){
//            Set<AssessmentVariable> descendentDeps = getDeps(descendant);
//            assertTrue("Formula with ID " + descendant.getAssessmentVariableId()
//                    + " does not have dependency with ID " + dependency.getAssessmentVariableId(),
//                    descendentDeps.contains(dependency));
//        }
//    }
//
//    /**
//     * Collects the longest list of descendants for the formula and asserts that
//     * each descendant does not contain the given dependency.
//     */
//    private void assertDescendantsMissingDep(AssessmentVariable formula, AssessmentVariable dependency){
//        for(AssessmentVariable descendant : collectDescendantList(formula)){
//            Set<AssessmentVariable> descendentDeps = getDeps(descendant);
//            assertFalse("Formula with ID " + descendant.getAssessmentVariableId()
//                    + " does have a dependency with variable ID " + dependency.getAssessmentVariableId(),
//                    descendentDeps.contains(dependency));
//        }
//    }
//
//    /**
//     * Removes the given child AV from the parent and (Hibernate) updates the parent
//     * @param parent
//     * @param child
//     */
//    @Transactional
//    private void removeChildAv(AssessmentVariable parent, AssessmentVariable child){
//        Iterator<AssessmentVarChildren> childIter = parent.getAssessmentVarChildrenList().iterator();
//        while(childIter.hasNext()){
//            AssessmentVarChildren children = childIter.next();
//            if(children.getVariableParent().equals(parent) && children.getVariableChild().equals(child)){
//                childIter.remove();
//                avRepo.update(parent);
//                avRepo.commit();
//                return;
//            }
//        }
//        throw new AssertionError("No child found. This probably means a bug in the test code.");
//    }
//
//    private Set<AssessmentVariable> getDeps(AssessmentVariable parent){
//        Set<AssessmentVariable> depSet = new TreeSet<>(new Comparator<AssessmentVariable>(){
//
//            @Override
//            public int compare(AssessmentVariable left, AssessmentVariable right) {
//                return left.getAssessmentVariableId().compareTo(right.getAssessmentVariableId());
//            }
//
//        });
//        if(parent.getAssessmentVarChildrenList() != null){
//            for(AssessmentVarChildren child : parent.getAssessmentVarChildrenList()){
//                if(child.getVariableParent().equals(parent)){
//                    AssessmentVariable childAv = child.getVariableChild();
//                    depSet.add(childAv);
//                }
//            }
//        }
//        return depSet;
//    }
//
//    /**
//     * @param parent
//     * @return the longest list of Descendant assessment variables for the given parent,
//     * in descending depth order, the parent will be the first element
//     */
//    private List<AssessmentVariable> collectDescendantList(AssessmentVariable parent){
//        List<AssessmentVariable> descendents = new ArrayList<>();
//        for(AssessmentVariable child : getDeps(parent)){
//            if(child.isFormula()){
//                List<AssessmentVariable> newDescendent = collectDescendantList(child);
//                if(newDescendent.size() > descendents.size()){
//                    descendents = newDescendent;
//                }
//            }
//        }
//        descendents.add(0, parent);
//        return descendents;
//    }
}
