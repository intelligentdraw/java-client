package com.laotek.intelligentdraw.dataserver.doc;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class UserAccount {

    @Id
    private String id;

    private String fullname;

    private String emailAddress;

    private String password;

    private Team team;

    private Date createDate;

    private Date updateDate;

    public UserAccount() {
    }

    public UserAccount(String fullname, String emailAddress, String password, Team team, Date createDate,
	    Date updateDate) {
	super();
	this.fullname = fullname;
	this.emailAddress = emailAddress;
	this.password = password;
	this.team = team;
	this.createDate = createDate;
	this.updateDate = updateDate;
    }

    public String getId() {
	return id;
    }

    public String getFullname() {
	return fullname;
    }

    public Team getTeam() {
	return team;
    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public String getPassword() {
	return password;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public Date getUpdateDate() {
	return updateDate;
    }

}
