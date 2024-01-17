package org.mql.java.models;

public class RelationEntity {
	
	public static enum RelationType {
        AGGREGATION,
        USE,
        EXTEND,
        IMPLEMENT,
        INNER_CLASS
      
    }

    private RelationType type;
    private String target;

	
	 public RelationEntity(RelationType type, String target) {
	        this.type = type;
	        this.target = target;
	    }

	    public RelationType getType() {
	        return type;
	    }

	    public void setType(RelationType type) {
	        this.type = type;
	    }

	    public String getTarget() {
	        return target;
	    }
	    
	    public void setTarget(String target) {
			this.target = target;
		}

}
