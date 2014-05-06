package olw.controller.base;

import java.io.Serializable;
import java.util.function.Function;
import java.util.stream.Collectors;

import olw.model.base.EntityWithId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class ViewRestController<T extends EntityWithId<ID>, V extends EntityWithId<ID>, ID extends Serializable, JPA extends JpaRepository<T,ID>, INDEX extends ElasticsearchCrudRepository<V, ID>>  extends BasicRestController<V,ID, INDEX>{

	@Autowired
	protected JPA jpaRepository; 
	
	@Override
	PagingAndSortingRepository<V, ID> selectRepository(Boolean indexed) {
		return indexRepository;
	}
	
	abstract protected Function<T,V> mapEntityToView();
	
	@Override
	Iterable<V> getIndexableElements() {
		return jpaRepository.findAll().stream().map(mapEntityToView()).collect(Collectors.toList());
	}

	
	
}
