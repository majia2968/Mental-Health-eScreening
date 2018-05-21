package gov.va.escreening.entity;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.exception.IllegalSystemStateException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "measure_answer")
@NamedQueries({
        @NamedQuery(name = "MeasureAnswer.findAll", query = "SELECT m FROM MeasureAnswer m")})
public class MeasureAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "measure_answer_id")
    private Integer measureAnswerId;
    @Column(name = "export_name")
    private String exportName;
    @Column(name = "other_export_name")
    private String otherExportName;
    @Column(name = "answer_text")
    private String answerText;
    @Column(name = "answer_type")
    private String answerType;
    @Column(name = "answer_value")
    private Integer answerValue;
    @Column(name = "calculation_value")
    private String calculationValue;
    @Column(name = "mha_value")
    private String mhaValue;
    @Column(name = "display_order")
    private Integer displayOrder;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measureAnswer", fetch = FetchType.LAZY)
    private List<MeasureAnswerValidation> measureAnswerValidationList;

    @JoinColumn(name = "measure_id", referencedColumnName = "measure_id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Measure measure;
    
    @JoinColumn(name = "calculation_type_id", referencedColumnName = "calculation_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CalculationType calculationType;
    
    //Do not make this lazy because it is used a lot during veteran assessment page transitions
    @OneToMany(mappedBy = "measureAnswer")
    private List<AssessmentVariable> assessmentVariableList;

    @Column(name = "vista_text")
    private String vistaText;
    
    @Transient
    private AssessmentVariable assessmentVariable = null;

    public String getVistaText() {
        return vistaText;
    }

    public void setVistaText(String vistaText) {
        this.vistaText = vistaText;
    }

    public MeasureAnswer() {
	this.dateCreated = new Date();
    }

    public MeasureAnswer(Integer measureAnswerId) {
        this.measureAnswerId = measureAnswerId;
	this.dateCreated = new Date();
    }

    public MeasureAnswer(Integer measureAnswerId, Date dateCreated) {
        this.measureAnswerId = measureAnswerId;
        this.dateCreated = dateCreated;
    }

    public Integer getMeasureAnswerId() {
        return measureAnswerId;
    }

    public void setMeasureAnswerId(Integer measureAnswerId) {
        this.measureAnswerId = measureAnswerId;
    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public String getOtherExportName() {
        return otherExportName;
    }

    public void setOtherExportName(String otherExportName) {
        this.otherExportName = otherExportName;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public Integer getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(Integer answerValue) {
        this.answerValue = answerValue;
    }

    public String getCalculationValue() {
        return calculationValue;
    }

    public void setCalculationValue(String calculationValue) {
        this.calculationValue = calculationValue;
    }

    public String getMhaValue() {
        return mhaValue;
    }

    public void setMhaValue(String mhaValue) {
        this.mhaValue = mhaValue;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<MeasureAnswerValidation> getMeasureAnswerValidationList() {
        return measureAnswerValidationList;
    }

    public void setMeasureAnswerValidationList(List<MeasureAnswerValidation> measureAnswerValidationList) {
        this.measureAnswerValidationList = measureAnswerValidationList;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public CalculationType getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(CalculationType calculationType) {
        this.calculationType = calculationType;
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
	public AssessmentVariable assessmentVariable() {
	    if(assessmentVariable == null){
    		List<AssessmentVariable> avList = getAssessmentVariableList();
    		if(avList == null || avList.isEmpty()){
    			ErrorBuilder.throwing(IllegalSystemStateException.class)
    				.toAdmin("Each measure answer should have an assessment variable assigned to it but the measure answer with the following ID doesn't: " + getMeasureAnswerId())
    				.toUser("A system issue has been detected. Please contact support")
    				.throwIt();
    		}
    		
    		assessmentVariable = avList.get(0);
	    }
	    return assessmentVariable;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (measureAnswerId != null ? measureAnswerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeasureAnswer)) {
            return false;
        }
        MeasureAnswer other = (MeasureAnswer) object;
        if ((this.measureAnswerId == null && other.measureAnswerId != null)
                || (this.measureAnswerId != null && !this.measureAnswerId.equals(other.measureAnswerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.MeasureAnswer[ measureAnswerId=" + measureAnswerId + " ]";
    }

    /**
     * method to try to pick some text to identify this answer
     * use the export name as #1 source of identifying, else try to match this identification with the Question variable name
     * else use this answer's answer text (this will be the case if this is an answer to a table question)
     * @return
     */
    public String getIdentifyingText() {
        String maIdentifyingText = getExportName();
        if (maIdentifyingText == null) {
            maIdentifyingText = getMeasure().getVariableName();
        }
        if (maIdentifyingText == null) {
            maIdentifyingText = getAnswerText();
        }
        return maIdentifyingText;
    }
}
