package com.laotek.intelligentdraw.dao;

import java.util.List;

import org.javatuples.Pair;

import com.laotek.intelligentdraw.dao.entities.Actor;
import com.laotek.intelligentdraw.dao.entities.Usecase;
import com.laotek.intelligentdraw.dataserver.common.ActorToUsecaseAssociate;
import com.laotek.intelligentdraw.dataserver.common.DrawingDto;

public interface DrawingDAO {

    List<DrawingDto> findAll();

    Pair<Long, String> save(String usecaseName, List<Actor> actors, List<Usecase> usecases);

    DrawingDto findDrawing(long id, String uniqueCode);

    void actorToUsecaseAssociate(ActorToUsecaseAssociate actorToUsecaseAssociate);

}
