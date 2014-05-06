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
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Languages")
@Document(indexName = "language",type = "language" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class Language extends EntityWithId<Long> {
	
	protected String code = null;
	protected List<Resource> resources = new ArrayList<>();

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "languageId", unique=true)
	public Long getId() {
		return this.id;
	}
	
	public Language() {
	}

	public Language(String code) {
		id = Long.valueOf(code);
	}

	public Language(Long id) {
		code = id.toString();
	}

	public Language(String[] ids) {
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
	@JoinTable(name = "RESOURCES_LANGUAGES", joinColumns = { @JoinColumn(name = "languageId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@JsonIgnore
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

}
