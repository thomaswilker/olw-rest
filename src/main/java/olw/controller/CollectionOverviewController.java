package olw.controller;

import java.util.function.Function;

import olw.controller.base.ViewRestController;
import olw.model.Collection;
import olw.model.view.CollectionDetailview;
import olw.model.view.CollectionOverview;
import olw.repository.index.CollectionDetailviewIndexRespository;
import olw.repository.index.CollectionOverviewIndexRespository;
import olw.repository.jpa.CollectionRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection-overview")
public class CollectionOverviewController extends ViewRestController<Collection, CollectionOverview, Long, CollectionRepository, CollectionOverviewIndexRespository>   {

	@Override
	protected Function<Collection, CollectionOverview> mapEntityToView() { return CollectionOverview::toOverview;}
	
}
