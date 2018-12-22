package com.laotek.intelligentdraw.dataserver.common;

public class UsecaseDiag {
    private String usecaseDiagName;
    private String usecaseName;
    private String actorName;

    public UsecaseDiag() {
	super();
	// TODO Auto-generated constructor stub
    }

    public UsecaseDiag(String usecaseDiagName, String usecaseName, String actorName) {
	super();
	this.usecaseDiagName = usecaseDiagName;
	this.usecaseName = usecaseName;
	this.actorName = actorName;
    }

    public String getUsecaseDiagName() {
	return usecaseDiagName;
    }

    public String getUsecaseName() {
	return usecaseName;
    }

    public String getActorName() {
	return actorName;
    }
}
