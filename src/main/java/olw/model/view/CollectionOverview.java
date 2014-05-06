package olw.model.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlType;

import olw.model.Area;
import olw.model.CollectionType;
import olw.model.ExternalResource;
import olw.model.Language;
import olw.model.Semester;
import olw.model.User;
import olw.model.base.EntityWithId;
import olw.model.view.helper.MappingHelper;

import org.apache.log4j.Logger;
import org.elasticsearch.index.settings.IndexDynamicSettings;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@Document(indexName = "collection_overview",type = "collection_overview" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIgnoreProperties(ignoreUnknown=false)
public class CollectionOverview extends EntityWithId<Long> {
	
	public String name = null;
	public Boolean deleted = new Boolean(false);
	public Long creation = null;

	public CollectionType type = null;

	public Set<String> uuids = new HashSet<>();
	public List<ExternalResource> externalResources = new ArrayList<>();
	
	public Set<LanguageDTO> languages = new HashSet<>();
	public Set<AreaDTO> areas = new HashSet<>();
	public Set<SemesterDTO> semesters = new HashSet<>();
	public List<UserDTO> users = new ArrayList<>();
	
	public CollectionOverview() { }
	
	@Override
	public Long getId() {
		return this.id;
	}

	public static CollectionOverview toOverview(olw.model.Collection c) {
		return toOverview(c, new CollectionOverview());
	}
	
	public static <T extends CollectionOverview> T toOverview(olw.model.Collection c, T view) {
		
		view.id = c.getId();
		view.name = c.getName();
		view.creation = MappingHelper.ifNN(c.getCreation(), Date::getTime);
		view.deleted = c.getDeleted();
		
		view.type = c.getType();
		view.uuids = c.getUuids();
		view.externalResources = c.getExternalResources();
		
		HashSet<LanguageDTO> languages = MappingHelper.toViewCollection(LanguageDTO::new, c.getLanguages(), HashSet::new);
		HashSet<AreaDTO> areas = MappingHelper.toViewCollection(AreaDTO::new, c.getAreas(), HashSet::new);
		HashSet<SemesterDTO> semesters = MappingHelper.toViewCollection(SemesterDTO::new, c.getSemesters(), HashSet::new);
		ArrayList<UserDTO> users = MappingHelper.toViewCollection(UserDTO::new, c.getUsers(), ArrayList::new);
		
		view.languages = languages;
		view.areas = areas;
		view.semesters = semesters;
		view.users = users;
		
		return view;
	}
		
	public static class AreaDTO {

		public Long id;
		public String name;

		public AreaDTO() {
		}

		public AreaDTO(Area a) {
			this.id = a.getId();
			this.name = a.getCode();
		}
	}

	public static class SemesterDTO {

		public Long id;
		public Long part;
		public Long year;
		public String value;

		public SemesterDTO() {
		}

		public SemesterDTO(Semester s) {
			year = s.getYear();
			part = s.getPart();
			id = s.getId();
			value = s.getValue();
		}
	}

	public static class LanguageDTO {

		public Long id;
		public String name;

		public LanguageDTO() { }

		public LanguageDTO(Language l) {
			name = l.getCode();
			id = l.getId();
		}
	}

	public static class UserDTO {
		
		public Long id;
		public String firstName;
		public String lastName;
		public String title;

		public UserDTO() {}

		public UserDTO(User u) {
			id = u.getId();
			firstName = u.getFirstName();
			lastName = u.getLastName();
			title = u.getTitle();
		}
	}
}
