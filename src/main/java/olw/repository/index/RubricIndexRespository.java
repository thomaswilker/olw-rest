package olw.repository.index;

import olw.model.Rubric;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubricIndexRespository extends ElasticsearchRepository<Rubric, Long>{

}
