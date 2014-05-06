package olw.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Tags")
@Document(indexName = "tag",type = "tag" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=Tag.class)
public class Tag extends EntityWithId<Long> {
	
	@Field(index=FieldIndex.analyzed, store=true, type=FieldType.String)
	protected String name = null;
	
	protected Set<Resource> resources = null;
	
	protected Set<Collection> collections = null;
	
	public Tag() {
	}

	public Tag(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "tagId")
	public Long getId() {
		return this.id;
	}

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_TAGS", joinColumns = { @JoinColumn(name = "tagId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@JsonIgnore
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany
	@JoinTable(name = "COLLECTIONS_TAGS", joinColumns = { @JoinColumn(name = "tagId") }, inverseJoinColumns = { @JoinColumn(name = "collectionId") })
	@JsonIgnore
	public Set<Collection> getCollections() {
		return collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}
}
