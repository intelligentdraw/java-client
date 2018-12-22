package com.laotek.intelligentdraw.dataserver.common;

public enum DrawingType {
    USECASE_DIAG("Usecase Diagram"), SEQUENCE_DIAG("Sequence Diagram");

    private String type;

    private DrawingType(String type) {
	this.type = type;
    }

    public String getType() {
	return type;
    }
}
