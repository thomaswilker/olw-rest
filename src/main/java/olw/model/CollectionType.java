package olw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.elasticsearch.index.Index;
import org.elasticsearch.index.store.Store;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Collectiontypes")
@Document(indexName = "collectionType",type = "collectionType" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class CollectionType extends EntityWithId<Long> {
	
	
	
	@Field(index = FieldIndex.analyzed, store = false, type=FieldType.String)
	protected String code = null;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "collectiontypeId", unique=true)
	public Long getId() {
		return this.id;
	}
	
	public CollectionType() {
	}

	public CollectionType(Long id) {
		this.id = id;
	}

	public CollectionType(String id) {
		this.id = Long.valueOf(id);
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
