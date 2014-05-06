package olw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Artefacts")
@Document(indexName = "admin",type = "admin" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=Artefact.class)
public class Artefact extends EntityWithId<Long> {
	
	
	protected ArtefactType artefactType = null;
	
	protected Long size = null;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "artefactId")
	public Long getId() {
		return this.id;
	}
	
	@ManyToOne
	@JoinColumn(name = "artefacttypeId", nullable = true)
	public ArtefactType getArtefactType() {
		return artefactType;
	}

	public void setArtefactType(ArtefactType artefactType) {
		this.artefactType = artefactType;
	}

	@Column(name = "size", nullable = false)
	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
