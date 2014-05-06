package olw.controller.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import olw.model.base.EntityWithId;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityRestController<T extends EntityWithId<ID>, ID extends Serializable, JPA extends JpaRepository<T,ID>, INDEX extends ElasticsearchCrudRepository<T, ID>>  extends BasicRestController<T,ID, INDEX>{

	@Autowired
	protected JPA jpaRepository; 
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void delete(@PathVariable("id") ID id) {
		jpaRepository.delete(id);
		indexRepository.delete(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deleteAll() {
		jpaRepository.deleteAll();
		indexRepository.deleteAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	@Transactional
	public void update(@PathVariable("id") ID id, @RequestBody T resource) {
		
		resource.setId(id);
		T updatedResource = (T)jpaRepository.save(resource);
		indexRepository.save(updatedResource);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public T create(@RequestBody T resource) {
		T newResource = (T) jpaRepository.save(resource);
		indexRepository.save(newResource);
		return newResource;
	}
	
	protected PagingAndSortingRepository<T, ID> selectRepository(Boolean indexed) {
		if(indexed)	return indexRepository;
		else return jpaRepository;
	}

	@Override
	Iterable<T> getIndexableElements() {
		List<T> elements = jpaRepository.findAll().subList(0, 1);
		logger.info("size: " + elements.size());
		return elements;
	}

	
}
