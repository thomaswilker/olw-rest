package olw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="ExternalResourceTypes")
@Document(indexName = "externalResourceType",type = "externalResourceType" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=ExternalResourceType.class)
public class ExternalResourceType extends EntityWithId<Long>{
	
	private String name;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="externalResourceTypeId")
	public Long getId() {
		return this.id;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
}
