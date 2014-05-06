package olw.controller;

import olw.controller.base.EntityRestController;
import olw.model.Rubric;
import olw.repository.index.RubricIndexRespository;
import olw.repository.jpa.RubricRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rubric")
public class RubricController extends EntityRestController<Rubric, Long, RubricRepository, RubricIndexRespository> {
	
	
	
}
