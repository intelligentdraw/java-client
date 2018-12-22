package com.laotek.intelligentdraw.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laotek.intelligentdraw.dao.entities.Drawing;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {

}
