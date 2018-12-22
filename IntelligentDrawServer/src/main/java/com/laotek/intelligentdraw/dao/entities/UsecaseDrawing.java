package com.laotek.intelligentdraw.dao.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("1")
public class UsecaseDrawing extends Drawing {

    @OneToMany(mappedBy = "usecaseDrawing", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Actor> actors;

    @OneToMany(mappedBy = "usecaseDrawing", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Usecase> usecases;

    public List<Actor> getActors() {
	return actors;
    }

    public List<Usecase> getUsecases() {
	return usecases;
    }

    public void setActors(List<Actor> actors) {
	this.actors = actors;
    }

    public void setUsecases(List<Usecase> usecases) {
	this.usecases = usecases;
    }

}
