package olw.repository.jpa;

import olw.model.Collection;
import olw.model.Rubric;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubricRepository extends JpaRepository<Rubric, Long> {

}
