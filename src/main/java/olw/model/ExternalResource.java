package olw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="ExternalResources")
@Document(indexName = "externalResource",type = "externalResource" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=ExternalResource.class)
public class ExternalResource extends EntityWithId<Long> {

	
	private ExternalResourceType type = null;
	private String name;
	private List<Resource> resources = new ArrayList<Resource>();
	private List<Collection> collections = new ArrayList<Collection>();
	private String author = null;
	private String link;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="externalResourceId", unique = true)
	public Long getId() {
		return this.id;
	}
	

	public ExternalResource() {	}
	
	@Column(name="name")
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name="link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@ManyToOne
	@JoinColumn(name="type", nullable=false, referencedColumnName = "externalResourceTypeId", columnDefinition = "default 1")
	public ExternalResourceType getType() {
		return type;
	}

	public void setType(ExternalResourceType type) {
		this.type = type;
	}

	
	@ManyToMany
	@JoinTable(name = "RESOURCES_EXTERNALS", joinColumns = { @JoinColumn(name = "externalResourceId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@OrderColumn(name = "elementOrder")
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany
	@JoinTable(name = "COLLECTIONS_EXTERNALS", joinColumns = { @JoinColumn(name = "externalResourceId") }, inverseJoinColumns = { @JoinColumn(name = "collectionId") })
	@OrderColumn(name = "elementOrder")
	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	
}
