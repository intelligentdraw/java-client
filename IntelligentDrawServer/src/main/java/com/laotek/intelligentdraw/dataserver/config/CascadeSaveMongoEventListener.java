package com.laotek.intelligentdraw.dataserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import com.laotek.intelligentdraw.dataserver.doc.UserAccount;

public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
	Object source = event.getSource();
	if ((source instanceof UserAccount) && (((UserAccount) source).getTeam() != null)) {
	    mongoOperations.save(((UserAccount) source).getTeam());
	}
    }
}
