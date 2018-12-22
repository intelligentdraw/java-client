package com.laotek.intelligentdraw.dataserver.doc;

import org.springframework.data.annotation.Id;

public abstract class Diagram {

    @Id
    private String id;

    private String name;

    private DiagramType diagramType;

    private UserAccount userAccount;

    protected Diagram(String name, DiagramType diagramType, UserAccount userAccount) {
	super();
	this.name = name;
	this.diagramType = diagramType;
	this.userAccount = userAccount;
    }

    public String getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public DiagramType getDiagramType() {
	return diagramType;
    }

    public UserAccount getUserAccount() {
	return userAccount;
    }

}
