package olw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Admins")
@Document(indexName = "admin",type = "admin" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class Admin extends EntityWithId<Long> {
	
	
	@Field(index=FieldIndex.analyzed, store = true)
	private String accountName = null;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "adminId")
	public Long getId() {
		return this.id;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Column(name = "accountName", unique = true)
	@JsonIgnore
	public String getAccountName() {
		return accountName;
	}

	

}
