package olw.repository.index;

import java.util.List;

import olw.model.Resource;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceIndexRepository extends ElasticsearchRepository<Resource, Long> {
	public List<Resource> findByName(String name);
}
