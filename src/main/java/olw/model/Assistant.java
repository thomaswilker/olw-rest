package olw.model;

import java.util.Date;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Assistants")
@Document(indexName = "assistant",type = "assistant" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=Assistant.class)
public class Assistant extends EntityWithId<Long> {
	
	private Date expirationDate = null;
	
	private Set<User> principals = null;
	
	private String firstName = "";
	
	private String lastName = "";
	
	private String accountName = null;
	
	private String email = null;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "assistantId")
	public Long getId() {
		return this.id;
	}
	
	public Assistant() {
	}

	public Assistant(Long id) {
		this.id = id;
	}

	public Assistant(String id) {
		this.id = Long.valueOf(id);
	}

	@Column(name = "expiration")
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@ManyToMany
	@JoinTable(name = "USERS_ASSISTANTS", joinColumns = { @JoinColumn(name = "assistantId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
	public Set<User> getPrincipals() {
		return principals;
	}

	public void setPrincipals(Set<User> principals) {
		this.principals = principals;
	}

	@Column(name = "firstname")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastname")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "accountName", unique = true)
	@XmlTransient @JsonIgnore
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



}
