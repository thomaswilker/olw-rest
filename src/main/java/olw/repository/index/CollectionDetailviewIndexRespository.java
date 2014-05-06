package olw.repository.index;

import olw.model.view.CollectionDetailview;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionDetailviewIndexRespository extends ElasticsearchRepository<CollectionDetailview, Long>{

}
