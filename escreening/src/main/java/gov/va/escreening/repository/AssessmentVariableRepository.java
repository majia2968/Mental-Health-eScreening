package gov.va.escreening.repository;

import java.util.Collection;
import java.util.List;

import gov.va.escreening.entity.AssessmentVariable;

public interface AssessmentVariableRepository extends RepositoryInterface<AssessmentVariable> {
	List<AssessmentVariable> findAllFormulae();

	List<AssessmentVariable> findAllAvs();
	
	AssessmentVariable findOneByDisplayName(String name);

    Collection<AssessmentVariable> findByDisplayNames(List<String> displayNames);

    Collection<AssessmentVariable> getParentVariables(
            AssessmentVariable childVariable);
}
