package com.laotek.intelligentdraw.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.laotek.intelligentdraw.dao.entities.Actor;
import com.laotek.intelligentdraw.dao.entities.Drawing;
import com.laotek.intelligentdraw.dao.entities.Usecase;
import com.laotek.intelligentdraw.dao.entities.UsecaseDrawing;
import com.laotek.intelligentdraw.dataserver.common.ActorToUsecaseAssociate;
import com.laotek.intelligentdraw.dataserver.common.DrawingDto;
import com.laotek.intelligentdraw.dataserver.common.DrawingType;
import com.laotek.intelligentdraw.dataserver.common.IntelligentDrawException;

@Repository
public class DrawingDAOImpl implements DrawingDAO {

    @Autowired
    private DrawingRepository drawingRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public List<DrawingDto> findAll() {
	Function<Drawing, DrawingDto> function = (Drawing drawing) -> {

	    return new DrawingDto(drawing.getId() + "", drawing.getUniqueCode(), drawing.getName(), "My name",
		    DrawingType.USECASE_DIAG, Lists.newArrayList(), Lists.newArrayList(), new Date(), new Date());
	};
	return drawingRepository.findAll().stream().map(function).collect(Collectors.toList());
    }

    @Override
    public Pair<Long, String> save(String usecaseName, List<Actor> actors, List<Usecase> usecases) {
	UsecaseDrawing drawing = new UsecaseDrawing();
	drawing.setCreateDate(new Date());
	drawing.setUpdatedDate(new Date());
	drawing.setName(usecaseName);
	drawing.setActors(actors);
	drawing.setUsecases(usecases);

	actors.get(0).setUsecaseDrawing(drawing);
	usecases.get(0).setUsecaseDrawing(drawing);
	drawingRepository.save(drawing);
	return new Pair(drawing.getId(), drawing.getUniqueCode());
    }

    @Override
    public DrawingDto findDrawing(long id, String uniqueCode) {
	Optional<Drawing> drawingOp = drawingRepository.findById(id);

	Drawing drawing = drawingOp
		.orElseThrow(() -> new IntelligentDrawException(String.format("Cannot find drawing with ID %s", id)));

	if (uniqueCode != null && uniqueCode.equals(drawing.getUniqueCode())) {
	    DrawingDto drawingDto = new DrawingDto();
	    drawingDto.setCreated(drawing.getCreateDate());
	    if (drawing instanceof UsecaseDrawing) {
		drawingDto.setDrawingType(DrawingType.USECASE_DIAG);
		UsecaseDrawing usecaseDrawing = (UsecaseDrawing) drawing;
		drawingDto.setActors(
			usecaseDrawing.getActors().stream().map(Actor::getName).collect(Collectors.toList()));
		drawingDto.setUsecases(
			usecaseDrawing.getUsecases().stream().map(Usecase::getName).collect(Collectors.toList()));
	    }
	    drawingDto.setUpdated(drawing.getUpdatedDate());
	    drawingDto.setTitle(drawing.getName());
	    return drawingDto;
	} else {
	    throw new IntelligentDrawException(String.format("Cannot match drawing with uniqueCode %s", uniqueCode));
	}
    }

    @Override
    public void actorToUsecaseAssociate(ActorToUsecaseAssociate actorToUsecaseAssociate) {

	String usecaseName = actorToUsecaseAssociate.getUsecaseName();

	String actorName = actorToUsecaseAssociate.getActorName();

	int drawingId = actorToUsecaseAssociate.getDrawingId();

	Optional<Drawing> drawingOpt = drawingRepository.findById((long) drawingId);

	Drawing drawing = drawingOpt.orElseThrow(
		() -> new IntelligentDrawException(String.format("Cannot find drawing with ID %s", drawingId)));

	UsecaseDrawing usecaseDrawing = (UsecaseDrawing) drawing;
	Optional<Usecase> foundUsecase = usecaseDrawing.getUsecases().stream()
		.filter(usecase -> usecase.getName().equals(usecaseName)).findFirst();

	Optional<Actor> foundActor = usecaseDrawing.getActors().stream()
		.filter(actor -> actor.getName().equals(actorName)).findFirst();

	if (foundUsecase.isPresent()) {

	} else {
	    Usecase usecase = new Usecase();
	    usecase.setName(usecaseName);
	    usecase.setUsecaseDrawing(usecaseDrawing);
	    usecaseDrawing.getUsecases().add(usecase);
	    drawingRepository.save(drawing);
	}

    }
}
