package com.laotek.intelligentdraw.dataserver.doc;

import java.util.List;

public class UsecaseDiag extends Diagram {

    public UsecaseDiag(String name, DiagramType diagramType, UserAccount userAccount, List<Actor> actors,
	    List<Usecase> usecases) {
	super(name, diagramType, userAccount);
	this.actors = actors;
	this.usecases = usecases;
    }

    private List<Actor> actors;

    private List<Usecase> usecases;

    public List<Actor> getActors() {
	return actors;
    }

    public void setActors(List<Actor> actors) {
	this.actors = actors;
    }

    public List<Usecase> getUsecases() {
	return usecases;
    }

    public void setUsecases(List<Usecase> usecases) {
	this.usecases = usecases;
    }
}
