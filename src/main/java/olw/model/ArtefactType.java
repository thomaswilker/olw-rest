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
@Table(name = "ArtefactTypes")
@Document(indexName = "artefactType",type = "artefactType" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=ArtefactType.class)
public class ArtefactType extends EntityWithId<Long>  {
	
	protected String code = null;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "artefacttypeId")
	public Long getId() {
		return this.id;
	}
	
	@Column(name = "code", unique = true, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
