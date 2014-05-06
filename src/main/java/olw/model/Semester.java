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
import javax.xml.bind.annotation.XmlTransient;

import olw.model.base.EntityWithId;

import org.elasticsearch.index.Index;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Semester")
@Document(indexName = "semester",type = "semester" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class Semester extends EntityWithId<Long> {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "semesterId")
	public Long getId() {
		return this.id;
	}
	
	@Field(index=FieldIndex.analyzed, type=FieldType.Integer)
	protected Long year = null;
	
	protected Long part = null;
	
	protected String value = null;
	
	protected Set<Resource> resources = null;
	
	protected Set<Collection> collections = null;

	public Semester() {
	}

	@Column(name = "year")
	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	@Column(name = "part")
	public Long getPart() {
		return part;
	}

	public void setPart(Long part) {
		this.part = part;
	}

	@Column(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_SEMESTERS", joinColumns = { @JoinColumn(name = "semesterId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@JsonIgnore
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany
	@JoinTable(name = "COLLECTIONS_SEMESTERS", joinColumns = { @JoinColumn(name = "semesterId") }, inverseJoinColumns = { @JoinColumn(name = "collectionId") })
	@JsonIgnore
	public Set<Collection> getCollections() {
		return collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}

}
