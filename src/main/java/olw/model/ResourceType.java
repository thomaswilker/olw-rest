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
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ResourceTypes")
@Document(indexName = "resourceType",type = "resourceType" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ResourceType extends EntityWithId<Long> {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "resourcetypeId", unique=true)
	public Long getId() {
		return this.id;
	}
	
	@Field(index=FieldIndex.analyzed, store=false, type=FieldType.String)
	protected String code = null;

	public ResourceType() {
	}

	public ResourceType(Long id) {
		this.id = id;
	}

	public ResourceType(String id) {
		this.id = Long.valueOf(id);
	}

	
	@Column(name = "code", unique = true, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
