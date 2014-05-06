package olw.model.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.elasticsearch.annotations.Document;

import olw.model.ExternalResource;
import olw.model.Resource;
import olw.model.view.helper.MappingHelper;

@Document(indexName = "resource_detailview",type = "resource_detailview" , shards = 1, replicas = 0, indexStoreType = "fs", refreshInterval = "-1")
public class ResourceDetailview extends ResourceOverview {
	protected String description = null;
	protected String note = null;
	protected List<ExternalResource> externalResources = new ArrayList<ExternalResource>();
	protected List<String> childs = new ArrayList<String>(); 
	protected String parent = null;
	
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public ResourceDetailview() {
	}
	
	public List<String> getChilds() {
		return childs;
	}

	public void setChilds(List<String> childs) {
		this.childs = childs;
	}

	public ResourceDetailview(Resource r) {
		
		id = r.getId();
		description = r.getDescription();
		name = r.getName();
		note = r.getNote();
		uuid = r.getUuid();
		code = r.getLicense().getCode();
		open = r.getOpen();
		deleted = r.getDeleted();
		downloadable = r.getDownloadable();
		if(r.getCreation() != null)
			creationDate = r.getCreation().getTime();
		
		if(r.getPublication() != null)
			publicationDate = r.getPublication().getTime();
		
		if(r.getCharacteristic() != null)
			characteristicType = r.getCharacteristic().getId();
		
		if(r.getLicense() != null)
			licenseType = r.getLicense().getId();
		
		if(r.getType() != null)
			resourceType = r.getType().getId();
		
		areas = MappingHelper.toViewCollection(AreaDTO::new, r.getAreas(), ArrayList::new);
		languages = MappingHelper.toViewCollection(LanguageDTO::new, r.getLanguages(), ArrayList::new);
		semesters = MappingHelper.toViewCollection(SemesterDTO::new, r.getSemesters(), ArrayList::new);
		collections = ResourceOverview.CollectionDTO.transferCollections(r);
		rubrics = MappingHelper.toViewCollection(RubricDTO::new, r.getRubrics(), HashSet::new);
		externalResources = r.getExternalResources();
		users = MappingHelper.toViewCollection(UserDTO::new, r.getUsers(), ArrayList::new);
		
		
		if(r.getChilds() != null) {
			for(Resource resource : r.getChilds())
				childs.add(resource.getUuid());
		}
		
		if(r.getParent() != null) {
			parent = r.getParent().getUuid();
		}
		
		
	}
	
	public List<ExternalResource> getExternalResources() {
		return externalResources;
	}

	public void setExternalResources(List<ExternalResource> externalResources) {
		this.externalResources = externalResources;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
