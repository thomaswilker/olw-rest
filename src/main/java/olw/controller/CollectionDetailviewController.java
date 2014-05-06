package olw.controller;

import java.util.function.Function;

import olw.controller.base.ViewRestController;
import olw.model.Collection;
import olw.model.view.CollectionDetailview;
import olw.repository.index.CollectionDetailviewIndexRespository;
import olw.repository.jpa.CollectionRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection-detailview")
public class CollectionDetailviewController extends ViewRestController<Collection, CollectionDetailview, Long, CollectionRepository, CollectionDetailviewIndexRespository> {

	@Override
	protected Function<Collection, CollectionDetailview> mapEntityToView() { return CollectionDetailview::toDetailview; };
	
	
	
	
}
