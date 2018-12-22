package com.laotek.intelligentdraw.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Usecase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private UsecaseDrawing usecaseDrawing;

    public Usecase(String name) {
	this.name = name;
    }

    public Usecase() {
    }

    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public UsecaseDrawing getUsecaseDrawing() {
	return usecaseDrawing;
    }

    public void setUsecaseDrawing(UsecaseDrawing usecaseDrawing) {
	this.usecaseDrawing = usecaseDrawing;
    }

}
