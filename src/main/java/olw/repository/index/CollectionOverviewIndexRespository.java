package olw.repository.index;

import olw.model.Collection;
import olw.model.view.CollectionOverview;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionOverviewIndexRespository extends ElasticsearchRepository<CollectionOverview, Long>{

}
