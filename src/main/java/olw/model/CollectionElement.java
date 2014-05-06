package olw.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import olw.model.base.EntityWithId;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "CollectionElements")
@Inheritance(strategy = InheritanceType.JOINED)
@Document(indexName = "collectionElement",type = "collectionElement" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class CollectionElement extends EntityWithId<Long>{
	
	protected Set<Collection> collections = new HashSet<Collection>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "elementId")
	public Long getId() {
		return this.id;
	}

	public CollectionElement() {
		collections = new HashSet<Collection>();
	}

	public CollectionElement(Long id) {
		this();
		setId(id);
	}

	public CollectionElement(String id) {
		this();
		setId(Long.valueOf(id));
	}

	
	

	@ManyToMany
	@JoinTable(name = "COLLECTIONS_ELEMENTS", joinColumns = { @JoinColumn(name = "elementId") }, inverseJoinColumns = { @JoinColumn(name = "collectionId") })
	@JsonIgnore
	/**
	 * FIXME german comment
	 * Set statt List auf dieser Seite der Verknüpfung, da aus Sicht des Sammlungselements
	 * keine geordnete Liste von Sammlungen existieren muss. Die Ordnung muss nur aus Sicht der
	 * Sammlung vollständig (0-n, ohne Lücken) sein.
	 * Prinzipiell gilt: Aus @OrderColumn(name='elementOrder') und List wird von Hibernate eine Liste erstellt mit
	 * MAX(elementOrder) Elementen. Jede nicht existierende Ordnungszahl (elementOrder) auf dem Intervall
	 * von 0 - MAX(elementOrder) wird in der von Hibernate zurückgelieferten List mit Null-Wert aufgefüllt. 
	 * Die Ordnungsspalte elementOrder gibt die Reihenfolge der Sammlungselemente innerhalb der Sammlung an
	 * und nicht die Reihenfolge der Sammlungen aus Sicht eines Sammlungselements.
	 * 
	 * Achtung: Kein List hier benutzen, da sonst eine Liste  mit MAX(elementOrder) Elementen * collections
	 * zurückgegeben würde! 
	 */
	public Set<Collection> getCollections() {
		return collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}
	
}
