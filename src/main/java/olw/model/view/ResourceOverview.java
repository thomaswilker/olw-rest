package olw.model.view;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.elasticsearch.annotations.Document;

import olw.model.Area;
import olw.model.Collection;
import olw.model.Language;
import olw.model.Resource;
import olw.model.Rubric;
import olw.model.Semester;
import olw.model.User;
import olw.model.base.EntityWithId;

@Document(indexName = "resource_overview",type = "resource_overview" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class ResourceOverview extends EntityWithId<Long> {
	
	protected String uuid = null;
	protected String name = null;
	protected List<LanguageDTO> languages = new ArrayList<>();
	protected List<UserDTO> users = new ArrayList<>();
	protected List<AreaDTO> areas = new ArrayList<>();
	protected List<SemesterDTO> semesters = new ArrayList<>();
	protected Set<CollectionDTO> collections = new HashSet<>();
	protected Set<RubricDTO> rubrics = new HashSet<>();
	protected List<URL> links = new ArrayList<URL>();
	protected Long licenseType = null;
	protected Long characteristicType = null;
	protected String type = null;
	protected String code = null;
	protected URI uri = null;
	protected Long creationDate = null;
	protected Long publicationDate = null;
	protected Long uploadDate = null;
	protected Long resourceType = null;
	protected Boolean downloadable = new Boolean(false);
	protected Boolean open = new Boolean(false);
	protected Boolean deleted = new Boolean(false);
	
	
	

	public ResourceOverview() {
	
	}

	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public static class LanguageDTO {
		Long id = null;
		String name = null;

		public LanguageDTO() {
		}
		
		public LanguageDTO(Language l) {
			name = l.getCode();
			id = l.getId();
		}

	}

	
	public static class UserDTO {
		Long id = null;
		String firstName = null;
		String lastName = null;
		String title = null;

		public UserDTO() {	}
		
		public UserDTO(User u) {
			id = u.getId();
			firstName = u.getFirstName();
			lastName = u.getLastName();
			title = u.getTitle();
		}
		
	}

	
	public static class AreaDTO {
		
		Long id = null;
		String code = null;

		public AreaDTO() { }
		
		public AreaDTO(Area u) {
			id = u.getId();
			code = u.getCode();
		}
		
	}

	public static class SemesterDTO {
		
		Long id = null;
		Long part = null;
		Long year = null;

		
		public SemesterDTO() {
		}
		
		public SemesterDTO(Semester semester) {
			id = semester.getId();
			part = semester.getPart();
			year = semester.getYear();
		}
		
	}

	public static class CollectionDTO {
		
		Long id = null;
		String name = null;

		public CollectionDTO() {
		}

		public CollectionDTO(Collection collection) {
			this.id = collection.getId();
			this.name = collection.getName();
		}
		
		public CollectionDTO(Long id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public static Set<CollectionDTO> transferCollections(Resource resource) {
			
			Set<CollectionDTO> c = new HashSet<>();
			for(Collection collection : resource.getCollections()) {
				c.add(new CollectionDTO(collection));
			}
			
			for(Rubric rubric : resource.getRubrics()) {
				for(Collection collection : rubric.getCollections())
					c.add(new CollectionDTO(collection));
			}
			
			return c;
		}
		
		
	}

	public static class RubricDTO {
		
		Long id = null;
		String name = null;

		public RubricDTO() {
		}

		public RubricDTO(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public RubricDTO(Rubric rubric) {
			this.id = rubric.getId();
			this.name = rubric.getName();
		}
		
	}

	
}
