package com.laotek.intelligentdraw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.laotek.intelligentdraw.dataserver.common.DrawingDto;
import com.laotek.intelligentdraw.dataserver.doc.Actor;
import com.laotek.intelligentdraw.dataserver.doc.DiagramType;
import com.laotek.intelligentdraw.dataserver.doc.Usecase;
import com.laotek.intelligentdraw.dataserver.doc.UsecaseDiag;
import com.laotek.intelligentdraw.dataserver.doc.UserAccount;
import com.laotek.intelligentdraw.dataserver.repo.DiagramRepository;

@RestController
public class DiagramDataController {

    @Autowired
    private DiagramRepository diagramRepository;

    @Deprecated
    @PostMapping(value = "/new/drawing")
    public ResponseEntity<String> createNewDiagram(@RequestBody DrawingDto drawingDto) {

	UserAccount userAccount = new UserAccount();

	List<Actor> actors = Lists.newArrayList();
	actors.add(new Actor(drawingDto.getInitialActor()));

	List<Usecase> usecases = Lists.newArrayList();
	usecases.add(new Usecase(drawingDto.getInitialUsecase()));

	UsecaseDiag diagram = new UsecaseDiag(drawingDto.getTitle(),
		DiagramType.find(drawingDto.getDrawingType().getType()), userAccount, actors, usecases);

	diagramRepository.save(diagram);

	return ResponseEntity.ok(diagram.getId());
    }

//    @Deprecated
//    @GetMapping(path = "/drawings/{accountId}")
//    public List<DrawingDto> getDrawings(@PathVariable("accountId") String accountId) {
//
//	List<DrawingDto> drawings = Lists.newArrayList();
//
//	diagramRepository.findAll().forEach((Diagram diag) -> {
//
//	    drawings.add(new DrawingDto(diag.getId(), diag.getdiag.getName(), "My name", DrawingType.USECASE_DIAG,
//		    Lists.newArrayList(), Lists.newArrayList(), new Date(), new Date()));
//	});
//
//	return drawings;
//    }
//
//    /**
//     * Moved
//     * 
//     * @param diagramId
//     * @return
//     */
//    @Deprecated
//    @GetMapping(path = "/drawing/{diagramId}")
//    public DrawingDto getDiagram(@PathVariable("diagramId") String diagramId) {
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
//    }

}
