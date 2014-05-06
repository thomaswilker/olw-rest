package olw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import olw.model.base.Secured;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Rubrics")
@Document(indexName = "rubric",type = "rubric" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Rubric extends CollectionElement implements Secured {
	
	protected String name = null;
	protected List<Resource> resources = new ArrayList<Resource>();

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_RUBRICS", joinColumns = { @JoinColumn(name = "rubricId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@OrderColumn(name = "elementOrder")
	@JsonIgnore
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@Override
	@Transient
	@JsonIgnore
	public List<User> getUsers() {
		HashSet<User> users = new HashSet<User>();
		for (Resource r : resources) {
			users.addAll(r.getUsers());
		}
		return new ArrayList<User>(users);
	}
}
