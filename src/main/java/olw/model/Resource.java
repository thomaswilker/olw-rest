package olw.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import olw.model.base.Secured;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Resources")
@Document(indexName = "resource",type = "resource" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class Resource extends CollectionElement implements Secured {
	
	protected String uuid = null;
	
	@Field(index = FieldIndex.analyzed, type=FieldType.String, indexAnalyzer="snowball", searchAnalyzer="snowball")
	protected String name = null;
	
	@Field(index = FieldIndex.analyzed, type=FieldType.String)
	protected String description = null;
	
	@Field(index = FieldIndex.analyzed, type=FieldType.String)
	protected String note = null;
	
	protected List<URL> links = null;
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Nested)
	protected List<Language> languages = new ArrayList<Language>();
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Nested)
	protected License license = null;
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Nested)
	protected ResourceType type = null;
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Nested)
	protected ResourceCharacteristic characteristic = null;
	
	@JsonProperty("open")
	@Field(index = FieldIndex.analyzed, type=FieldType.Boolean)
	protected Boolean isOpen = new Boolean(false);
	
	@JsonProperty("deleted")
	@Field(index = FieldIndex.analyzed, type=FieldType.Boolean)
	protected Boolean isDeleted = new Boolean(false);
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Date)
	protected Date creation = new Date();
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Date)
	protected Date upload = new Date();
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Date)
	protected Date publication = new Date();
	
	protected Integer visits = 0;
	
	@Field(index=FieldIndex.analyzed, type=FieldType.Nested)
	protected Set<Area> areas = new HashSet<Area>();
	
	@Field(index=FieldIndex.analyzed, type=FieldType.Nested)
	protected Set<Artefact> artefacts = new HashSet<Artefact>();
	
	@Field(index=FieldIndex.analyzed, type=FieldType.Nested)
	protected List<User> users = new ArrayList<User>();
	
	@Field(index=FieldIndex.analyzed, type=FieldType.Nested)
	protected Set<Rubric> rubrics = new HashSet<Rubric>();
	
	@Field(index = FieldIndex.analyzed, type=FieldType.Boolean)
	protected Boolean downloadable = new Boolean(false);
	
	@Field(index=FieldIndex.analyzed, type=FieldType.Nested)
	protected Set<Semester> semesters = new HashSet<Semester>();
	
	@Field(index=FieldIndex.analyzed, type=FieldType.Nested)
	private List<ExternalResource> externalResources = new ArrayList<ExternalResource>();
	
	protected Resource parent = null;
	
	protected List<Resource> childs = new ArrayList<Resource>();
	
	
	@ManyToOne
	@JoinColumn(name = "parent", nullable = true)
	@JsonBackReference
	public Resource getParent() {
		return parent;
	}
	
	public void setParent(Resource parent) {
		this.parent = parent;
	}
	
	@OneToMany
	@JoinColumn(name="parent", nullable = true)
	@JsonManagedReference
	public List<Resource> getChilds() {
		return childs;
	}

	public void setChilds(List<Resource> childs) {
		this.childs = childs;
	}

		
	public Resource() {
	}

//	@ElementCollection
//	@CollectionTable(name="Resource_links")
//	public List<URL> getLinks() {
//		return links;
//	}

//	public void setLinks(List<URL> links) {
//		this.links = links;
//	}

	@Column(name = "uuid", unique = true, nullable = false)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "isdeleted")
	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "isopen")
	public Boolean getOpen() {
		return isOpen;
	}

	public void setOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	@Column(name = "upload", nullable = false, columnDefinition = "date default sysdate")
	public Date getUpload() {
		return upload;
	}

	public void setUpload(Date upload) {
		this.upload = upload;
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

	@Column(name = "visits")
	public Integer getVisits() {
		return visits;
	}

	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_USERS", joinColumns = { @JoinColumn(name = "resourceId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
	@OrderColumn(name = "elementOrder")
	@JsonIgnore
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_LANGUAGES", joinColumns = { @JoinColumn(name = "resourceId") }, inverseJoinColumns = { @JoinColumn(name = "languageId") })
	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> language) {
		languages = language;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_AREAS", joinColumns = { @JoinColumn(name = "resourceId") }, inverseJoinColumns = { @JoinColumn(name = "areaId") })
	public Set<Area> getAreas() {
		return areas;
	}

	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_SEMESTERS", joinColumns = { @JoinColumn(name = "resourceId") }, inverseJoinColumns = { @JoinColumn(name = "semesterId") })
	public Set<Semester> getSemesters() {
		return semesters;
	}

	public void setSemesters(Set<Semester> semesters) {
		this.semesters = semesters;
	}

	@OneToMany
	@JoinColumn(name = "resourceId", nullable = true)
	public Set<Artefact> getArtefacts() {
		return artefacts;
	}

	public void setArtefacts(Set<Artefact> artefacts) {
		this.artefacts = artefacts;
	}

	@ManyToOne
	@JoinColumn(name = "licenseId", nullable = false, referencedColumnName = "licenseId", columnDefinition = "default 1")
	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	@ManyToOne
	@JoinColumn(name = "resourcetypeId", nullable = false, referencedColumnName = "resourcetypeId", columnDefinition = "default 1")
	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "resourcecharacteristicId", nullable = true, referencedColumnName = "resourcecharacteristicId")
	public ResourceCharacteristic getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(ResourceCharacteristic characteristic) {
		this.characteristic = characteristic;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_RUBRICS", joinColumns = { @JoinColumn(name = "resourceId") }, inverseJoinColumns = { @JoinColumn(name = "rubricId") })
	@JsonIgnore
	public Set<Rubric> getRubrics() {
		return rubrics;
	}

	public void setRubrics(Set<Rubric> rubrics) {
		this.rubrics = rubrics;
	}

	@Column(name = "downloadable")
	public Boolean getDownloadable() {
		return downloadable;
	}

	public void setDownloadable(Boolean downloadable) {
		this.downloadable = downloadable;
	}
	
	@ManyToMany
	@JoinTable(name = "RESOURCES_EXTERNALS", joinColumns = { @JoinColumn(name = "resourceId") }, inverseJoinColumns = { @JoinColumn(name = "externalResourceId") })
	@OrderColumn(name = "elementOrder")
	@JsonIgnore
	public List<ExternalResource> getExternalResources() {
		return externalResources ;
	}

	public void setExternalResources(List<ExternalResource> externalResources) {
		this.externalResources = externalResources;
	}
	
	
}
