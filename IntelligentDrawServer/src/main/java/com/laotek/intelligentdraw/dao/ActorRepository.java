package com.laotek.intelligentdraw.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laotek.intelligentdraw.dao.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
