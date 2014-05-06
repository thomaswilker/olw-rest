package olw.repository.index;

import java.util.List;

import olw.model.Resource;
import olw.model.view.ResourceOverview;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceOverviewIndexRepository extends ElasticsearchRepository<ResourceOverview, Long> {
	public List<Resource> findByName(String name);
}
