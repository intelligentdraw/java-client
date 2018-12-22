package com.laotek.intelligentdraw.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laotek.intelligentdraw.dataserver.common.RegistrationDto;
import com.laotek.intelligentdraw.dataserver.doc.Team;
import com.laotek.intelligentdraw.dataserver.doc.UserAccount;
import com.laotek.intelligentdraw.dataserver.repo.UserAccountRepository;

@RestController
public class RegistrationController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostMapping(value = "/user/registration")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDto registration) {

	UserAccount userAccount = new UserAccount(registration.getFullname(), registration.getEmailAddress(),
		registration.getPassword(), new Team(), new Date(), new Date());

	userAccountRepository.save(userAccount);

	return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
