package gov.va.escreening.entity;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.ValidationDataTypeEnum;
import gov.va.escreening.exception.IllegalSystemStateException;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "measure")
@NamedQueries({
        @NamedQuery(name = "Measure.findAll", query = "SELECT m FROM Measure m") })
public class Measure implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "measure_id")
    private Integer measureId;
    @Basic(optional = false)
    @Column(name = "measure_text")
    private String measureText;
    @Basic(optional = false)
    @Column(name = "is_required")
    private boolean isRequired;
    @Basic(optional = false)
    @Column(name = "is_mha")
    private boolean isMha;
    @Basic(optional = false)
    @Column(name = "is_patient_protected_info")
    private boolean isPatientProtectedInfo;
    // @Max(value=?) @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce
    // field validation
    @Column(name = "score_weight")
    private Float scoreWeight;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "measure_type_id", referencedColumnName = "measure_type_id")
    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    private MeasureType measureType;
    
    @Column(name = "vista_text")
    private String vistaText;
    
    @Column(name = "variable_name")
    private String variableName;
    
    public String getVistaText() {
        return vistaText;
    }

    public void setVistaText(String vistaText) {
        this.vistaText = vistaText;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    /**
     * This should only be set when this measure is the child of another measure. This can be pulled out into another
     * table but I wanted to keep it simple for now
     */
    @Column(name = "display_order")
    @Basic(optional = true)
    private Integer displayOrder;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measure", orphanRemoval=true)
    private List<MeasureValidation> measureValidationList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measure")
    @OrderBy("displayOrder")
    private List<MeasureAnswer> measureAnswerList;
    
    @OneToMany(mappedBy = "measure")
    private List<AssessmentVariable> assessmentVariableList;
    
    @ManyToOne(cascade = { CascadeType.ALL }, optional = true,fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_measure_id")
    private Measure parent;
    @OrderBy("displayOrder")
    @OneToMany(mappedBy = "parent")
    private Set<Measure> children = new HashSet<Measure>();

    public Measure() {
	this.dateCreated = new Date();
    }

    public Measure(Integer measureId) {
        this.measureId = measureId;
	this.dateCreated = new Date();
    }

    public Measure(Integer measureId, String measureText, boolean isRequired, boolean isPatientProtectedInfo,
            Date dateCreated) {
        this.measureId = measureId;
        this.measureText = measureText;
        this.isRequired = isRequired;
        this.isPatientProtectedInfo = isPatientProtectedInfo;
        this.dateCreated = dateCreated;
    }

    public Integer getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }

    public String getMeasureText() {
        return measureText;
    }

    public void setMeasureText(String measureText) {
        this.measureText = measureText;
    }

    public boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public boolean getIsMha() {
        return isMha;
    }

    public void setIsMha(boolean isMha) {
        this.isMha = isMha;
    }

    public boolean getIsPatientProtectedInfo() {
        return isPatientProtectedInfo;
    }

    public void setIsPatientProtectedInfo(boolean isPatientProtectedInfo) {
        this.isPatientProtectedInfo = isPatientProtectedInfo;
    }

    public Float getScoreWeight() {
        return scoreWeight;
    }

    public void setScoreWeight(Float scoreWeight) {
        this.scoreWeight = scoreWeight;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public MeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<MeasureValidation> getMeasureValidationList() {
        return measureValidationList;
    }

    public void setMeasureValidationList(List<MeasureValidation> measureValidationList) {
        this.measureValidationList = measureValidationList;
    }

    public List<MeasureAnswer> getMeasureAnswerList() {
        return measureAnswerList;
    }

    public void setMeasureAnswerList(List<MeasureAnswer> measureAnswerList) {
        this.measureAnswerList = measureAnswerList;
    }

    public List<AssessmentVariable> getAssessmentVariableList() {
        return assessmentVariableList;
    }

    public void setAssessmentVariableList(List<AssessmentVariable> assessmentVariableList) {
        this.assessmentVariableList = assessmentVariableList;
    }
    
    /**
     * This is a method to move our code base from a one to many between measure and assessment variables to a one to one (as it should be)
     * @return
     * @throws IllegalSystemStateException is there is no AV for this measure.
     */
	public AssessmentVariable getAssessmentVariable() {
		List<AssessmentVariable> avList = getAssessmentVariableList();
		if(avList == null || avList.isEmpty()){
			ErrorBuilder.throwing(IllegalSystemStateException.class)
				.toAdmin("Each measure should have an assessment variable assigned to it but measure with the following ID doesn't: " + getMeasureId())
				.toUser("A system issue has been detected. Please contact support")
				.throwIt();
		}
		
		return avList.get(0);
	}

    public Measure getParent() {
        return parent;
    }

    public void setParent(Measure parent) {
        this.parent = parent;
    }

    /**
     * @return a non-null set of this measure's children
     */
    public Set<Measure> getChildren() {
        if (children == null){
            children = new HashSet<>();
        }
        return children;
    }

    public void setChildren(Set<Measure> children) {
        this.children = children;
    }

    public void addChild(Measure child) {
        children.add(child);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (measureId != null ? measureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Measure)) {
            return false;
        }
        Measure other = (Measure) object;
        if ((this.measureId == null && other.measureId != null)
                || (this.measureId != null && !this.measureId.equals(other.measureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.Measure[ measureId=" + measureId + ", text=\"" + measureText + "\" ]";
    }

    public boolean isNumeric() {
    	if(measureValidationList != null){
    		//Iterate through validations looking for the datatype validation, then see if it is NUMBER
    		for(MeasureValidation validation : measureValidationList){
    			if(validation.getValidation().getCode().toLowerCase() == "datatype"
    					&& ValidationDataTypeEnum.NUMBER.toString().equalsIgnoreCase(validation.getTextValue())){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public boolean isParent(){
    	return measureType != null && 
    			(measureType.getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX 
				|| measureType.getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX
				|| measureType.getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_TABLE);
    }
}
