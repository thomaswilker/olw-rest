package olw.model.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlType;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import olw.model.Collection;
import olw.model.CollectionElement;
import olw.model.Resource;
import olw.model.ResourceType;
import olw.model.Rubric;
import olw.model.base.EntityWithId;
import olw.model.view.helper.MappingHelper;

@Document(indexName = "collection_detailview",type = "collection_detailview" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class CollectionDetailview extends CollectionOverview {
	
	public String note = "";
	public String description = "";

	@Field(type=FieldType.Long)
	public Long publicationDate = null;
	
	public List<Long> collectionElements = new ArrayList<Long>();
	public Map<Long, ResourceDTO> resources = new HashMap<Long, ResourceDTO>();
	public Map<Long, RubricDTO> rubrics = new HashMap<Long, RubricDTO>();
	public List<TagDTO> tags = new ArrayList<TagDTO>();
	public List<URL> links = new ArrayList<URL>();

	public CollectionDetailview() { }
	
	public static CollectionDetailview toDetailview(Collection c) {
		
		CollectionDetailview detailview = toOverview(c, new CollectionDetailview());
		
		detailview = CollectionOverview.toOverview(c, detailview);
		
		detailview.description = c.getDescription();
		detailview.note = c.getNote();
		detailview.publicationDate = MappingHelper.ifNN(c.getPublication(), Date::getTime);
		
		
		Map<Long, ResourceDTO> resources = c.getCollectionElements().stream().filter(x -> x instanceof Resource).map(ResourceDTO::new).collect(Collectors.toMap(ResourceDTO::getId, Function.identity()));
		Map<Long, RubricDTO> rubrics = c.getCollectionElements().stream().filter(x -> x instanceof Rubric).map(RubricDTO::new).collect(Collectors.toMap(RubricDTO::getId, Function.identity()));
		List<Long> elements = c.getCollectionElements().stream().filter(x -> x != null).map(CollectionElement::getId).collect(Collectors.toList());
		
		detailview.resources = resources;
		detailview.rubrics = rubrics;
		detailview.collectionElements = elements;
		
		return detailview;
	}
	
	public static class TagDTO {
		
		public Long id = null;
		public String name = null;
		
		public TagDTO() { }
		
		public TagDTO(Long id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	public static class ResourceDTO extends EntityWithId<Long> {
		
		public Long id = null;
		public String name = null;
		public String type = null;
		public Long resourceType = null;
		public List<UserDTO> users = new ArrayList<>();
		public Long creationDate = null;
		public Long publicationDate = null;
		public Boolean open = null;

		public ResourceDTO() { }
		
		public ResourceDTO(CollectionElement e) {
			this((Resource) e);
		}
		
		public ResourceDTO(Resource r) {
			this.id = r.getId();
			this.name = r.getName();
			this.open = r.getOpen();
			this.resourceType = MappingHelper.ifNN(r.getType(), ResourceType::getId);
			this.creationDate = MappingHelper.ifNN(r.getCreation(), Date::getTime);
			this.publicationDate = MappingHelper.ifNN(r.getPublication(), Date::getTime);
			this.users = MappingHelper.toViewCollection(UserDTO::new, r.getUsers(), ArrayList::new);
			this.type = MappingHelper.ifNN(r.getType(), ResourceType::getCode);
		}

		@Override
		public Long getId() {
			return this.id;
		}
		
	}

	public static class RubricDTO extends EntityWithId<Long> {
		
		public Long id = null;
		public String name = "";
		public List<Long> resources = new ArrayList<Long>();

		public RubricDTO() { }

		public RubricDTO(CollectionElement e) {
			this((Rubric) e);
		}
		
		public RubricDTO(Rubric rubric) {
			this(rubric, rubric.getResources().stream().map(Resource::getId).collect(Collectors.toList()));
		}
		
		public RubricDTO(Rubric rubric, List<Long> resources) {
			this.id = rubric.getId();
			this.name = rubric.getName();
			this.resources = resources;
		}
		
		@Override
		public Long getId() {
			return this.id;
		}
	}
}
