package olw.controller;

import olw.controller.base.EntityRestController;
import olw.model.Collection;
import olw.repository.index.CollectionIndexRepository;
import olw.repository.jpa.CollectionRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class CollectionController extends EntityRestController<Collection, Long, CollectionRepository, CollectionIndexRepository> {

	
	
}
