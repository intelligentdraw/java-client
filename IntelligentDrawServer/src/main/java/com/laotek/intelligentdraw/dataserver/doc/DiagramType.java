package com.laotek.intelligentdraw.dataserver.doc;

public enum DiagramType {
    USECASE("Usecase"), SEQUENCE("Sequence");

    private String type;

    private DiagramType(String type) {
	this.type = type;
    }

    public String getType() {
	return type;
    }

    public static DiagramType find(String type) {
	for (DiagramType dtype : DiagramType.values()) {
	    if (dtype.getType().split("\\s+")[0].toLowerCase().equals(type.split("\\s+")[0].toLowerCase())) {
		return dtype;
	    }
	}
	return null;
    }
}
