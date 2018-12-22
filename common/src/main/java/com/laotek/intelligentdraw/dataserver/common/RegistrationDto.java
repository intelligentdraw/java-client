package com.laotek.intelligentdraw.dataserver.common;

public class RegistrationDto {

    private String fullname;
    private String emailAddress;
    private String password;

    public RegistrationDto() {
	super();
    }

    public RegistrationDto(String fullname, String emailAddress, String password) {
	super();
	this.fullname = fullname;
	this.emailAddress = emailAddress;
	this.password = password;
    }

    public String getFullname() {
	return fullname;
    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public String getPassword() {
	return password;
    }
}
