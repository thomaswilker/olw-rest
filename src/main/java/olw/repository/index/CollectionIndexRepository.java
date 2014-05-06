package olw.repository.index;

import olw.model.Collection;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface CollectionIndexRepository extends ElasticsearchRepository<Collection, Long>{

}
