package olw.model;

/* Collection Domainobjekt-Klasse */
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import olw.model.base.EntityWithId;
import olw.model.base.Secured;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Collections")
@Document(indexName = "collection",type = "collection" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=Collection.class)
public class Collection extends EntityWithId<Long> implements Secured {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator", strategy = GenerationType.AUTO)
	@Column(name = "collectionId", unique = true)
	public Long getId() {
		return this.id;
	}
	
	//@Field(index = FieldIndex.analyzed, type=FieldType.String, indexAnalyzer="snowball", searchAnalyzer="snowball")
	protected String name = "";
	
	//@Field(index = FieldIndex.analyzed, type=FieldType.String, indexAnalyzer="stop", searchAnalyzer="stop")
	protected String note = "";

	protected List<URL> links = new ArrayList<URL>();
	
	protected CollectionType type = null;
	
	//@Field(index = FieldIndex.analyzed, type=FieldType.String, indexAnalyzer="stop", searchAnalyzer="stop")
	protected String description = "";
	
	//@Field(type=FieldType.Date)
	protected Date creation = new Date();
	
	//@Field(type=FieldType.Date)
	protected Date publication = new Date();
	
	protected List<User> users = new ArrayList<User>();
	protected Set<Tag> tags = new HashSet<Tag>();
	
	protected List<CollectionElement> collectionElements = new ArrayList<CollectionElement>();
	
	protected Set<String> uuids = new HashSet<String>();
	
	protected Set<Area> areas = new HashSet<Area>();
	
	protected Set<Language> languages = new HashSet<Language>();
	
	protected Set<Semester> semesters = new HashSet<Semester>();
	
	protected Date lastUpdate = new Date();
	
	protected List<ExternalResource> externalResources = new ArrayList<ExternalResource>();
	
	
	//@Field(index=FieldIndex.analyzed, type=FieldType.Boolean)
	protected Boolean deleted = new Boolean(false);

	public Collection() {
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	@Column(name="lastUpdate")
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "creation", nullable = false, columnDefinition = "date default sysdate")
	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@Column(name = "publication", nullable = false, columnDefinition = "date default sysdate")
	public Date getPublication() {
		return publication;
	}

	public void setPublication(Date publication) {
		this.publication = publication;
	}

	@ManyToOne
	@JoinColumn(name = "collectiontypeId", nullable = true)
	public CollectionType getType() {
		return type;
	}

	public void setType(CollectionType type) {
		this.type = type;
	}
	
//	@ElementCollection
//	@CollectionTable(name="Collection_links")
//	public List<URL> getLinks() {
//		return links;
//	}

	public void setLinks(List<URL> links) {
		this.links = links;
	}
	
	@ElementCollection
	public Set<String> getUuids() {
		return uuids;
	}

	public void setUuids(Set<String> uuids) {
		this.uuids = uuids;
	}
	
	@ManyToMany
	@JoinTable(name = "COLLECTIONS_TAGS", joinColumns = { @JoinColumn(name = "collectionId") }, inverseJoinColumns = { @JoinColumn(name = "tagId") })
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	
	@ManyToMany
	@JoinTable(name = "COLLECTIONS_ELEMENTS", joinColumns = { @JoinColumn(name = "collectionId") }, inverseJoinColumns = { @JoinColumn(name = "elementId") })
	@OrderColumn(name = "elementOrder")
	public List<CollectionElement> getCollectionElements() {
		return collectionElements;
	}

	public void setCollectionElements(List<CollectionElement> elements) {
		collectionElements = elements;
	}


	@Override
	@ManyToMany
	@JoinTable(name = "COLLECTIONS_USERS", joinColumns = { @JoinColumn(name = "collectionId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
		
	@ManyToMany
	@JoinTable(name = "COLLECTIONS_AREAS", 
			joinColumns = { @JoinColumn(name = "collectionId") }, 
			inverseJoinColumns = { @JoinColumn(name = "areaId") })
	public Set<Area> getAreas() {
		return areas;
	}


	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}


	@ManyToMany
	@JoinTable(name = "COLLECTIONS_LANGUAGES", 
			joinColumns = { @JoinColumn(name = "collectionId") }, 
			inverseJoinColumns = { @JoinColumn(name = "languageId") })
	public Set<Language> getLanguages() {
		return languages;
	}


	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}


	@ManyToMany
	@JoinTable(name = "COLLECTIONS_SEMESTERS", 
			joinColumns = { @JoinColumn(name = "collectionId") }, 
			inverseJoinColumns = { @JoinColumn(name = "semesterId") })
	public Set<Semester> getSemesters() {
		return semesters;
	}

	public void setSemesters(Set<Semester> semesters) {
		this.semesters = semesters;
	}
	
	@ManyToMany
	@JoinTable(name = "COLLECTIONS_EXTERNALS", joinColumns = { @JoinColumn(name = "externalResourceId") }, inverseJoinColumns = { @JoinColumn(name = "collectionId") })
	@OrderColumn(name = "elementOrder")
	public List<ExternalResource> getExternalResources() {
		return externalResources;
	}

	public void setExternalResources(List<ExternalResource> externalResources) {
		this.externalResources  = externalResources;
	}
	
	@Column(name="deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
}
