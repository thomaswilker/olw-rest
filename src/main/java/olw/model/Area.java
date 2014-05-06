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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Areas")
@Document(indexName = "area",type = "area" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=Area.class)
public class Area extends EntityWithId<Long> {
	
	
	@Id
	@Column(name = "areaId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}
	
	@Field(index = FieldIndex.analyzed, type=FieldType.String)
	protected String code = null;
	
	protected Set<Resource> resources = null;
	protected Set<Collection> collections = null;
	

	public Area() {
	}

	public Area(String code) {
		this.code = code;
	}

	
	@Column(name = "code", unique = true, nullable = false)
	@JsonProperty(value="name")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_AREAS", joinColumns = { @JoinColumn(name = "areaId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@JsonIgnore
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany
	@JoinTable(name = "COLLECTIONS_AREAS", joinColumns = { @JoinColumn(name = "areaId") }, inverseJoinColumns = { @JoinColumn(name = "collectionId") })
	@JsonIgnore
	public Set<Collection> getCollections() {
		return collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}

	
}