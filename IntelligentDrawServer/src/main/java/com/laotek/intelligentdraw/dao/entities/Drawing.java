package com.laotek.intelligentdraw.dao.entities;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.commons.lang.RandomStringUtils;

@Entity(name = "drawing")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "drawing_type", discriminatorType = DiscriminatorType.INTEGER)
public class Drawing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String uniqueCode = RandomStringUtils.random(10, true, true);

    private String name;

    private Date createDate;

    private Date updatedDate;

    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public Date getUpdatedDate() {
	return updatedDate;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
    }

    public String getUniqueCode() {
	return uniqueCode;
    }

}
