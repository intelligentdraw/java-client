package com.laotek.intelligentdraw.dataserver.doc;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Team {

    @Id
    private String id;

    private String name;

    @DBRef
    private List<UserAccount> userAccounts;

    public Team() {
	super();
    }

    public Team(String name, List<UserAccount> userAccounts) {
	super();
	this.name = name;
	this.userAccounts = userAccounts;
    }

    public String getName() {
	return name;
    }

    public List<UserAccount> getUserAccounts() {
	return userAccounts;
    }

}
