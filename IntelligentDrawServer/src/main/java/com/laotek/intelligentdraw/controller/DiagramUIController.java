package com.laotek.intelligentdraw.controller;

import java.util.Date;
import java.util.List;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.laotek.intelligentdraw.dao.DrawingDAO;
import com.laotek.intelligentdraw.dao.entities.Actor;
import com.laotek.intelligentdraw.dao.entities.Usecase;
import com.laotek.intelligentdraw.dataserver.common.ActorToUsecaseAssociate;
import com.laotek.intelligentdraw.dataserver.common.DiagResultDto;
import com.laotek.intelligentdraw.dataserver.common.DrawingDto;
import com.laotek.intelligentdraw.dataserver.common.DrawingType;
import com.laotek.intelligentdraw.dataserver.common.UsecaseDiag;
import com.laotek.intelligentdraw.dataserver.repo.DiagramRepository;

@RestController
public class DiagramUIController {

    @Autowired
    private DiagramRepository diagramRepository;

    @Autowired
    private DrawingDAO drawingDAO;

    @GetMapping(path = "/drawings")
    public List<DrawingDto> getDrawings() {

	return drawingDAO.findAll();
    }

    @PutMapping(value = "/new/usecase")
    public ResponseEntity<DiagResultDto> createNewDiagram(@RequestBody UsecaseDiag usecaseDiag) {

	String diagName = usecaseDiag.getUsecaseDiagName();
	DrawingDto drawingDto = new DrawingDto(null, null, diagName, null, DrawingType.USECASE_DIAG,
		usecaseDiag.getActorName(), usecaseDiag.getUsecaseName(), new Date(), new Date());

	List<Actor> actors = Lists.newArrayList();
	actors.add(new Actor(drawingDto.getInitialActor()));

	List<Usecase> usecases = Lists.newArrayList();
	usecases.add(new Usecase(drawingDto.getInitialUsecase()));

	Pair<Long, String> identifier = drawingDAO.save(usecaseDiag.getUsecaseDiagName(), actors, usecases);

	DiagResultDto result = new DiagResultDto();
	result.setId(identifier.getValue0());
	result.setUniqueCode(identifier.getValue1());
	return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/drawing/{diagramId}/{uniqueCode}")
    public DrawingDto getDiagram(@PathVariable("diagramId") String diagramId,
	    @PathVariable("uniqueCode") String uniqueCode) {
//
//	UsecaseDiag diag = (UsecaseDiag) diagramRepository.findById(diagramId).get();
//
//	String fullname = diag.getUserAccount() != null ? diag.getUserAccount().getFullname() : null;
//
//	List<String> actors = Lists.newArrayList();
//	diag.getActors().forEach((Actor actor) -> {
//	    actors.add(actor.getName());
//	});
//
//	List<String> usecases = Lists.newArrayList();
//	diag.getUsecases().forEach((Usecase usecase) -> {
//	    usecases.add(usecase.getName());
//	});
//
//	DrawingDto drawing = new DrawingDto(diag.getId(), diag.getName(), fullname, DrawingType.USECASE_DIAG, actors,
//		usecases, new Date(), new Date());
//
//	return drawing;

	return drawingDAO.findDrawing(Long.valueOf(diagramId), uniqueCode);
    }

    @GetMapping(path = "/getUsecases/{diagramId}/{uniqueCode}")
    public List<String> getUsecases(@PathVariable("diagramId") String diagramId,
	    @PathVariable("uniqueCode") String uniqueCode) {

	return drawingDAO.findDrawing(new Long(diagramId), uniqueCode).getUsecases();
    }

    @PostMapping(value = "/associateActorToUsecase")
    public void actorToUsecaseAssociate(@RequestBody ActorToUsecaseAssociate actorToUsecaseAssociate) {

	drawingDAO.actorToUsecaseAssociate(actorToUsecaseAssociate);
    }
}
