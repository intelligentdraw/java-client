package com.laotek.intelligentdraw.dataserver.common;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * id: string title: string; fullname: string; category: string; subCategory:
 * string; drawingType: string; created: string; updated: string;
 * 
 * @author larryoke
 *
 */
public class DrawingDto {
    private String id;
    private String uniqueCode;
    private String title;
    private String owner;
    private DrawingType drawingType;

    private String initialActor;
    private String initialUsecase;

    private List<String> actors;
    private List<String> usecases;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private Date created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private Date updated;

    /**
     * Request
     * 
     * @param id
     * @param title
     * @param owner
     * @param drawingType
     * @param initialActor
     * @param initialUsecase
     * @param created
     * @param updated
     */
    public DrawingDto(String id, String uniqueCode, String title, String owner, DrawingType drawingType,
	    String initialActor, String initialUsecase, Date created, Date updated) {
	super();
	this.id = id;
	this.setUniqueCode(uniqueCode);
	this.title = title;
	this.owner = owner;
	this.drawingType = drawingType;
	this.initialActor = initialActor;
	this.initialUsecase = initialUsecase;
	this.created = created;
	this.updated = updated;
    }

    /**
     * Response
     * 
     * @param id
     * @param title
     * @param owner
     * @param drawingType
     * @param initialActor
     * @param initialUsecase
     * @param created
     * @param updated
     */
    public DrawingDto(String id, String uniqueCode, String title, String owner, DrawingType drawingType,
	    List<String> actors, List<String> usecases, Date created, Date updated) {
	super();
	this.id = id;
	this.setUniqueCode(uniqueCode);
	this.title = title;
	this.owner = owner;
	this.drawingType = drawingType;
	this.actors = actors;
	this.usecases = usecases;
	this.created = created;
	this.updated = updated;
    }

    public DrawingDto() {
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getOwner() {
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    public DrawingType getDrawingType() {
	return drawingType;
    }

    public void setDrawingType(DrawingType drawingType) {
	this.drawingType = drawingType;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public Date getUpdated() {
	return updated;
    }

    public void setUpdated(Date updated) {
	this.updated = updated;
    }

    public String getInitialActor() {
	return initialActor;
    }

    public String getInitialUsecase() {
	return initialUsecase;
    }

    public List<String> getActors() {
	return actors;
    }

    public List<String> getUsecases() {
	return usecases;
    }

    public String getUniqueCode() {
	return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
	this.uniqueCode = uniqueCode;
    }

    public void setActors(List<String> actors) {
	this.actors = actors;
    }

    public void setUsecases(List<String> usecases) {
	this.usecases = usecases;
    }

}
