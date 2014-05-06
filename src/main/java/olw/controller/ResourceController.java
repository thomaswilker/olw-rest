package olw.controller;

import java.util.List;

import olw.controller.base.EntityRestController;
import olw.model.Resource;
import olw.repository.index.ResourceIndexRepository;
import olw.repository.jpa.ResourceRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController extends EntityRestController<Resource, Long, ResourceRepository, ResourceIndexRepository> {
	
	
	@RequestMapping("/by/name")
	public List<Resource> findByTitle(@RequestParam(value="search", required=false, defaultValue="") String search) {
		return this.indexRepository.findByName(search);
	}
	
}
