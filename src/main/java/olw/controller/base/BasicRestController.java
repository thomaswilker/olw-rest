package olw.controller.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import olw.model.base.EntityWithId;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BasicRestController<T extends EntityWithId<ID>, ID extends Serializable, INDEX extends ElasticsearchCrudRepository<T, ID>> {

	protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	protected ObjectMapper mapper = new ObjectMapper();
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected ElasticsearchTemplate template;
	
	@Autowired
	protected INDEX indexRepository;
	
	abstract PagingAndSortingRepository<T, ID> selectRepository(Boolean indexed);
	abstract Iterable<T> getIndexableElements();
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public T findById(@PathVariable("id") ID id, @RequestParam(value="indexed", required = false, defaultValue = "true") Boolean indexed) {
		return selectRepository(indexed).findOne(id);
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public Page<T>  findPaginated(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
	            					@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
	            					@RequestParam(value="indexed", required = false, defaultValue = "true") Boolean indexed) {

	        Assert.isTrue(page > 0, "Page index must be greater than 0");
	        return selectRepository(indexed).findAll(new PageRequest(page - 1, size));
	}
	
	@RequestMapping("/all")
	public Iterable<T> findAll(@RequestParam(value="indexed", required = false, defaultValue = "true") Boolean indexed) {
		return selectRepository(indexed).findAll();
	}
	
	@RequestMapping("/sync")
	public void index() {
		template.deleteIndex(entityClass);
		template.createIndex(entityClass);
		template.putMapping(entityClass);
		indexRepository.save(getIndexableElements());
	}
	
	
}
