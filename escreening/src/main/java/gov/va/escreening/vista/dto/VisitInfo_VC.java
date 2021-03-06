package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/6/14.
 */
public class VisitInfo_VC extends VisitBaseInfo implements HealthFactorVisitData{
    public VisitInfo_VC(VistaServiceCategoryEnum data) {
        super(data.name(), null);
    }

    public VisitInfo_VC(VistaServiceCategoryEnum data, String additionalData) {
        super(data.name(), additionalData);
    }

    @Override
    public VisitTypeEnum getType() {
        return VisitTypeEnum.ENCOUNTER_SERVICE_CATEGORY;
    }
}