package olw.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "Licenses")
@Document(indexName = "license",type = "license" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@id", scope=License.class)
public class License extends EntityWithId<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "licenseId")
	public Long getId() {
		return this.id;
	}
	
	@Field(index = FieldIndex.analyzed, store = false, type=FieldType.String)
	protected String code = null;
	
	protected String note = null;
	
	public static final String CODE_CC_BY_NC_ND = "cc-by-nc-nd";
	public static final String CODE_ALL_RIGHTS_RESERVED = "all-rights-reserved";

	public License() {
	}

	public License(Long id) {
		this.id = id;
	}

	
	@Column(name = "code", unique = true, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
