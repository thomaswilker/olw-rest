package olw.repository.index;

import java.util.List;

import olw.model.Resource;
import olw.model.view.ResourceDetailview;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceDetailviewIndexRepository extends ElasticsearchRepository<ResourceDetailview, Long> {
	public List<Resource> findByName(String name);
}
