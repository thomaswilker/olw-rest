package olw.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Users")
@Document(indexName = "user",type = "user" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class User extends EntityWithId<Long> {
	
	protected String title = "";

	protected String firstName = "";
	
	protected String lastName = "";
	
	protected String accountName = null;
	
	protected String email = null;
	
	protected String organization = null;
	
	protected URL website = null;
	
	protected Set<Resource> resources = new HashSet<>();
	
	protected Set<Collection> collections = new HashSet<>();
	
	protected List<Assistant> assistants = new ArrayList<>();

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String id) {
		this.id = Long.valueOf(id);
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "userId", unique=true)
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "firstname", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastname", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "accountName", unique = true)
	@JsonIgnore
	@XmlTransient
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "organization")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Column(name = "website")
	public URL getWebsite() {
		return website;
	}

	public void setWebsite(URL website) {
		this.website = website;
	}

	@ManyToMany
	@JoinTable(name = "RESOURCES_USERS", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@JsonIgnore
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany
	@JoinTable(name = "COLLECTIONS_USERS", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "collectionId") })
	@JsonIgnore
	public Set<Collection> getCollections() {
		return collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}

	@ManyToMany
	@JoinTable(name = "USERS_ASSISTANTS", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "assistantId") })
	@JsonIgnore
	public List<Assistant> getAssistants() {
		return assistants;
	}

	public void setAssistants(List<Assistant> assistants) {
		this.assistants = assistants;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User u = (User) o;
			if (getId() == u.getId()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		long id = getId();
		long hash = id + getTitle().hashCode() + getFirstName().hashCode() + getLastName().hashCode();
		return (int) hash;
	}
}
