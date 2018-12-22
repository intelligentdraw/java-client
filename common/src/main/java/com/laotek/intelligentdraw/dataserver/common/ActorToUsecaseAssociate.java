package com.laotek.intelligentdraw.dataserver.common;

public class ActorToUsecaseAssociate {
    private int drawingId;
    private String uniqueCode;
    private String actorName;
    private String usecaseName;

    public String getActorName() {
	return actorName;
    }

    public String getUsecaseName() {
	return usecaseName;
    }

    public void setActorId(String actorId) {
	this.actorName = actorId;
    }

    public void setUsecaseName(String usecaseName) {
	this.usecaseName = usecaseName;
    }

    public int getDrawingId() {
	return drawingId;
    }

    public void setDrawingId(int drawingId) {
	this.drawingId = drawingId;
    }

    public String getUniqueCode() {
	return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
	this.uniqueCode = uniqueCode;
    }

}
