package olw.model.base;



public abstract class EntityWithId<ID> {
	
	protected ID id;
	
	abstract public ID getId();
	
	public void setId(ID id) {
		this.id = id;
	}
}
