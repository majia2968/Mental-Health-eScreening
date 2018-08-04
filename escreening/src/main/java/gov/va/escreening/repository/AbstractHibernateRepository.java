package gov.va.escreening.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

@SuppressWarnings("unchecked")
public class AbstractHibernateRepository<T extends Serializable> implements RepositoryInterface<T> {

	final protected Logger logger = LoggerFactory.getLogger(getClass());

	private Class<T> clazz;

	@PersistenceContext
	protected EntityManager entityManager;

	protected final void setClazz(final Class<T> clazzToSet) {
		clazz = clazzToSet;
	}

	public final T findOne(final int id) {
		return entityManager.find(clazz, id);
	}

	public final List<T> findAll() {
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}
	
	//@Cacheable("avs")
	public final List<T> findAllAssessmentVariables() {
		return entityManager.createQuery("select distinct a FROM AssessmentVariable a left JOIN fetch a.measure left JOIN fetch a.assessmentVarChildrenList " +
				"left join fetch a.measureAnswer ").getResultList();
	}

	//@Cacheable("avs")
	public final List<T> findAvs() {
		//return entityManager.createQuery("from " + clazz.getName()).setHint("org.hibernate.cacheable", Boolean.TRUE).getResultList();
		return entityManager.createQuery("select distinct a FROM AssessmentVariable a left JOIN fetch a.assessmentVarChildrenList " +
				"left join fetch a.measureAnswer ").getResultList();
//		return entityManager.createQuery("select distinct a FROM AssessmentVariable a left JOIN fetch a.assessmentVarChildrenList " +
//				"left join fetch a.measureAnswer ").getResultList();

	}

	public final void create(final T entity) {
		// Persist is being used instead of 'save'. PK field will not be set immediately.
		entityManager.persist(entity);
	}

	public final T update(final T entity) {
		return entityManager.merge(entity);
	}

	public final void delete(final T entity) {
		entityManager.remove(entity);
	}

	public final void deleteById(final int entityId) {
		final T entity = findOne(entityId);
		delete(entity);
	}

	public final void flush() {
		entityManager.flush();
	}

	public final void commit() {
		entityManager.flush();
	}
}
