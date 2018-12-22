package com.laotek.intelligentdraw.controller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.laotek.intelligentdraw.dao.DrawingDAO;
import com.laotek.intelligentdraw.dataserver.common.DrawingDto;
import com.laotek.intelligentdraw.dataserver.common.DrawingType;
import com.laotek.intelligentdraw.dataserver.doc.Actor;
import com.laotek.intelligentdraw.dataserver.doc.Usecase;
import com.laotek.intelligentdraw.dataserver.doc.UsecaseDiag;
import com.laotek.intelligentdraw.dataserver.repo.DiagramRepository;
import com.laotek.intelligentdraw.graphics.DrawActor;
import com.laotek.intelligentdraw.graphics.DrawUsecase;
import com.laotek.intelligentdraw.graphics.Location;

@RestController
public class DiagramDrawController {

    @Autowired
    private DiagramRepository diagramRepository;

    @Autowired
    private DrawingDAO drawingDAO;

    private Font font = new Font("default", Font.BOLD, 11);
    private Canvas c = new Canvas();
    private FontMetrics defaultFontMetrics = c.getFontMetrics(font);

    @GetMapping(path = "/get-metadata/{id}/{uniqueCode}/{width}")
    public List<Map<String, String>> getDrawingMetadata(@PathVariable("id") String id,
	    @PathVariable("uniqueCode") String uniqueCode, @PathVariable("width") int diagWidth) {
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	DrawingDto drawingDto = drawingDAO.findDrawing(new Long(id), uniqueCode);

	BigDecimal widthBD = new BigDecimal(diagWidth).divide(new BigDecimal(1.5), 0, RoundingMode.DOWN);

	diagWidth = widthBD.intValue();

	List<Location> locations = drawActors(new DrawActor() {

	    @Override
	    public void drawTorso(int x1, int y1, int x2, int y2) {
	    }

	    @Override
	    public void drawString(Graphics g2, String currentLine, int x, int y) {
	    }

	    @Override
	    public void drawRightLeg(int x1, int y1, int x2, int y2) {
	    }

	    @Override
	    public void drawRightArm(int x1, int y1, int x2, int y2) {
	    }

	    @Override
	    public void drawLeftLeg(int x1, int y1, int x2, int y2) {
	    }

	    @Override
	    public void drawLeftArm(int x1, int y1, int x2, int y2) {
	    }

	    @Override
	    public void drawHead(int x, int y, int width, int height) {
	    }

	    @Override
	    public void dispose(Graphics g) {
	    }

	    @Override
	    public Graphics create2() {
		return null;
	    }
	}, drawingDto.getActors(), diagWidth);

	locations.forEach((Location location) -> {
	    Map<String, String> actorMetadata = new HashMap<String, String>();
	    actorMetadata.put("coords", String.format("%s,%s,%s,%s", location.getX1(), location.getY1(),
		    location.getX2(), location.getY2()));

	    actorMetadata.put("actorName", location.getEntityName());
	    actorMetadata.put("shape", "rect");
	    actorMetadata.put("type", "actor");
	    actorMetadata.put("fullname", "fullname");
	    list.add(actorMetadata);
	});

	locations = drawUsecases(new DrawUsecase() {
	    @Override
	    public void drawOval(int x1, int y1, int x2, int y2) {
	    }

	    @Override
	    public void drawString(Graphics g2, String currentLine, int x, int y) {
	    }

	    @Override
	    public Graphics create2() {
		return null;
	    }

	    @Override
	    public void dispose(Graphics g) {

	    }
	}, drawingDto.getUsecases(), diagWidth);

	locations.forEach((Location location) -> {
	    Map<String, String> usecaseMetadata = new HashMap<String, String>();
	    usecaseMetadata.put("coords", String.format("%s,%s,%s,%s", location.getX1(), location.getY1(),
		    location.getX2(), location.getY2()));

	    usecaseMetadata.put("usecaseName", location.getEntityName());
	    usecaseMetadata.put("shape", "rect");
	    usecaseMetadata.put("type", "usecase");
	    usecaseMetadata.put("fullname", "fullname");
	    list.add(usecaseMetadata);
	});

	return list;
    }

    /**
     * 
     * @param id
     * @param random
     * @return
     * @throws IOException
     */
    @RequestMapping(path = "/get-diagram/{id}/{uniqueCode}/{width}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getDiagram(@PathVariable("id") String id,
	    @PathVariable("uniqueCode") String uniqueCode, @PathVariable("width") int diagWidth) throws IOException {

	DrawingDto drawingDto = drawingDAO.findDrawing(new Long(id), uniqueCode);

	// DrawingDto drawingDto = findDrawing(id);

	BigDecimal widthBD = new BigDecimal(diagWidth).divide(new BigDecimal(1.5), 0, RoundingMode.DOWN);

	diagWidth = widthBD.intValue();

	int diagHeight = determinePanelHeight(diagWidth, drawingDto);

	// create diagram canvas
	Pair<BufferedImage, Graphics2D> imageAndGraphics = createDrawingCanvas(diagWidth, diagHeight);

	// add title to diagram
	writeDiagTitle(imageAndGraphics.getValue1(), drawingDto.getTitle());

	// draw actors

	Graphics graphics2D = imageAndGraphics.getValue1();
	drawActors(new DrawActor() {

	    @Override
	    public void drawTorso(int x1, int y1, int x2, int y2) {
		graphics2D.drawLine(x1, y1, x2, y2);
	    }

	    @Override
	    public void drawString(Graphics g2, String currentLine, int x, int y) {
		g2.drawString(currentLine, x, y);
	    }

	    @Override
	    public void drawRightLeg(int x1, int y1, int x2, int y2) {
		graphics2D.drawLine(x1, y1, x2, y2);
	    }

	    @Override
	    public void drawRightArm(int x1, int y1, int x2, int y2) {
		graphics2D.drawLine(x1, y1, x2, y2);
	    }

	    @Override
	    public void drawLeftLeg(int x1, int y1, int x2, int y2) {
		graphics2D.drawLine(x1, y1, x2, y2);
	    }

	    @Override
	    public void drawLeftArm(int x1, int y1, int x2, int y2) {
		graphics2D.drawLine(x1, y1, x2, y2);
	    }

	    @Override
	    public void drawHead(int x, int y, int width, int height) {
		graphics2D.drawOval(x, y, width, height);
	    }

	    @Override
	    public void dispose(Graphics g) {
		if (g != null) {
		    g.dispose();
		}
	    }

	    @Override
	    public Graphics create2() {
		Graphics g2 = graphics2D.create();
		g2.setFont(new Font("default", Font.BOLD, 11));
		return g2;
	    }
	}, drawingDto.getActors(), diagWidth);

	// draw usecases
	drawUsecases(new DrawUsecase() {
	    @Override
	    public void drawOval(int x1, int y1, int x2, int y2) {
		imageAndGraphics.getValue1().drawOval(x1, y1, x2, y2);
	    }

	    @Override
	    public void drawString(Graphics g2, String currentLine, int x, int y) {
		g2.drawString(currentLine, x, y);
	    }

	    @Override
	    public Graphics create2() {
		Graphics g2 = graphics2D.create();
		return g2;
	    }

	    @Override
	    public void dispose(Graphics g) {
		g.dispose();
	    }
	}, drawingDto.getUsecases(), diagWidth);

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ImageIO.write(imageAndGraphics.getValue0(), "png", baos);

	baos.flush();
	byte[] imageInByte = baos.toByteArray();

	HttpHeaders headers = new HttpHeaders();
	headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	headers.add("Pragma", "no-cache");
	headers.add("Expires", "0");

	ByteArrayResource resource = new ByteArrayResource(imageInByte);

	return ResponseEntity.ok().headers(headers).contentLength(imageInByte.length)
		.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    private int determinePanelHeight(int diagWidth, DrawingDto drawingDto) {
	int actors = drawingDto.getActors().size();
	int usecases = drawingDto.getUsecases().size();

	BigDecimal height = new BigDecimal(componentSideLength());
	if (actors > usecases) {
	    return height.multiply(new BigDecimal(actors)).intValue();

	} else {
	    return height.multiply(new BigDecimal(usecases)).intValue();
	}
    }

    private int componentSideLength() {
	return 125;
    }

    private DrawingDto findDrawing(String id) {

	UsecaseDiag diag = (UsecaseDiag) diagramRepository.findById(id).get();

	String fullname = diag.getUserAccount() != null ? diag.getUserAccount().getFullname() : null;

	List<String> actors = Lists.newArrayList();
	diag.getActors().forEach((Actor actor) -> {
	    actors.add(actor.getName());
	});

	List<String> usecases = Lists.newArrayList();
	diag.getUsecases().forEach((Usecase usecase) -> {
	    usecases.add(usecase.getName());
	});

	DrawingDto drawing = new DrawingDto(diag.getId(), null, diag.getName(), fullname, DrawingType.USECASE_DIAG,
		actors, usecases, new Date(), new Date());

	return drawing;
    }

    /**
     * 
     * @param diagWidth
     * @param diagHeight
     * @return
     */
    private Pair<BufferedImage, Graphics2D> createDrawingCanvas(int diagWidth, int diagHeight) {
	BufferedImage image = new BufferedImage(diagWidth, diagHeight, BufferedImage.TYPE_INT_BGR);
	Graphics2D graphic2D = image.createGraphics();

	graphic2D.setColor(Color.white);
	graphic2D.fillRect(0, 0, diagWidth, diagHeight);

	graphic2D.setColor(Color.lightGray);
	graphic2D.drawRect(0, 0, diagWidth - 1, diagHeight - 1);

	// reset
	graphic2D.setColor(Color.BLACK);

	Pair<BufferedImage, Graphics2D> imageAndGraphics = new Pair<BufferedImage, Graphics2D>(image, graphic2D);
	return imageAndGraphics;
    }

    /**
     * Write the title to the diagram
     * 
     * @param graphics2D
     * @param title
     */
    private void writeDiagTitle(Graphics2D graphics2D, String title) {

	Graphics2D g2d = (Graphics2D) graphics2D.create();

	g2d.setFont(new Font("default", Font.BOLD, 12));

	FontMetrics metrics = g2d.getFontMetrics();
	int messageWidth = metrics.stringWidth(title);

	// message
	g2d.drawString(title, 10, 15);

	// bottom line
	graphics2D.drawLine(0, 20, messageWidth + 10, 20);

	// right side line
	graphics2D.drawLine(messageWidth + 20, 0, messageWidth + 20, 10);

	// diagonal line to join bottom and side
	graphics2D.drawLine(messageWidth + 10, 20, messageWidth + 20, 10);

	g2d.dispose();
    }

    private List<Location> drawUsecases(DrawUsecase drawUsecase, List<String> usecases, int panelWidth) {

	List<Location> locations = new ArrayList<Location>();

	// 66% on x-side
	int xLocation = new BigDecimal(panelWidth).divide(new BigDecimal(3), 0, RoundingMode.UP).intValue() * 2;

	int componentWidth = componentSideLength();

	// 80% of width
	int componentHeight = new BigDecimal(componentWidth).multiply(new BigDecimal("0.70")).intValue();

	int margin = new BigDecimal(componentWidth).multiply(new BigDecimal("0.15")).intValue();

	IntStream.range(0, usecases.size()).forEach(currentYLocation -> {

	    // draw it here with margin above and below
	    int x1 = xLocation;
	    int y1 = currentYLocation + margin;
	    int width = componentWidth;
	    int height = componentHeight - margin;
	    drawUsecase.drawOval(x1, y1, width, height);

	    String usecaseName = usecases.get(currentYLocation);

	    // int stringWidth = defaultFontMetrics.stringWidth(usecaseName);

	    Graphics g2 = drawUsecase.create2();
	    String lines[] = getStringItems(usecaseName, 25, componentWidth);
	    IntStream.range(0, lines.length).forEach(lineIndex -> {

		String currentLine = lines[lineIndex];
		int stringWidth = defaultFontMetrics.stringWidth(currentLine);

		int textX = (componentWidth - stringWidth) / 2;
		drawUsecase.drawString(g2, currentLine, xLocation + textX, currentYLocation + (componentHeight / 2));

		locations.add(new Location(usecaseName, x1, y1, x1 + width, y1 + height));
	    });
	    drawUsecase.dispose(g2);
	});
	return locations;
    }

    private List<Location> drawActors(DrawActor drawActor, List<String> actors, int panelWidth) {

	List<Location> locations = new ArrayList<Location>();

	// 66% on x-side
	int xLocation = new BigDecimal(panelWidth).divide(new BigDecimal(4), 0, RoundingMode.UP).intValue();

	int diameter = 25;
	int radius = diameter / 2;

	// 80% of width
	int componentHeight = new BigDecimal(diameter).multiply(new BigDecimal("0.70")).intValue();

	int margin = new BigDecimal(diameter).multiply(new BigDecimal("0.15")).intValue();

	IntStream.range(0, actors.size()).forEach(actionIndex -> {

	    // draw the head
	    int x = xLocation;
	    int y = actionIndex + margin;
	    int width = diameter;
	    int height = componentHeight - margin;
	    drawActor.drawHead(x, y, width, height);

	    // torso
	    int x1 = x + radius;
	    int y1 = y + height;
	    int x2 = x1;
	    int y2 = y1 + new BigDecimal(diameter).multiply(new BigDecimal("0.65")).intValue();
	    drawActor.drawTorso(x1, y1, x2, y2);

	    // left arm
	    int x3 = x2;
	    int y3 = y1 + (y2 - y1) / 3;
	    int x4 = x2 - new BigDecimal(diameter).multiply(new BigDecimal("0.60")).intValue();
	    int y4 = y3 + new BigDecimal(diameter).multiply(new BigDecimal("0.25")).intValue();
	    drawActor.drawLeftArm(x3, y3, x4, y4);

	    // right arm
	    int x5 = x2;
	    int y5 = y3;
	    int x6 = x2 + new BigDecimal(diameter).multiply(new BigDecimal("0.60")).intValue();
	    int y6 = y4;
	    drawActor.drawRightArm(x5, y5, x6, y6);

	    // left leg
	    int x7 = x2;
	    int y7 = y2;
	    int x8 = x2 - new BigDecimal(diameter).multiply(new BigDecimal("0.60")).intValue();
	    int y8 = y7 + new BigDecimal(diameter).multiply(new BigDecimal("0.70")).intValue();
	    drawActor.drawLeftLeg(x7, y7, x8, y8);

	    // right leg
	    int x9 = x2;
	    int y9 = y2;
	    int x10 = x9 + new BigDecimal(diameter).multiply(new BigDecimal("0.70")).intValue();
	    int y10 = y8;
	    drawActor.drawRightLeg(x9, y9, x10, y10);

	    // desc
	    int componentWidth = diameter * 3;
	    Graphics g2 = drawActor.create2();
	    String actorName = actors.get(actionIndex);

	    String lines[] = getStringItems(actorName, diameter, componentWidth);
	    IntStream.range(0, lines.length).forEach(lineIndex -> {

		String currentLine = lines[lineIndex];
		int stringWidth = defaultFontMetrics.stringWidth(currentLine);
		int x11 = x1 - stringWidth / 2;
		int y11 = y10 + 15 + (lineIndex * 20);
		drawActor.drawString(g2, currentLine, x11, y11);
	    });
	    drawActor.dispose(g2);

	    locations.add(new Location(actorName, x, y, x10, y10));
	});

	return locations;
    }

    private String[] getStringItems(String text, int min, int max) {
	int stringWidth = defaultFontMetrics.stringWidth(text);
	if (stringWidth < max) {
	    return new String[] { text };
	}

	List<String> splits = new ArrayList<String>(Arrays.asList(text.split("\\s+")));
	ListIterator<String> splitIter = splits.listIterator();

	while (splitIter.hasNext()) {
	    String current = splitIter.next();

	    if (splitIter.previousIndex() == 0) {
		continue;
	    }

	    int width = defaultFontMetrics.stringWidth(current);

	    // too short to occupy a line
	    if (width < min && splitIter.hasPrevious()) {

		// add to previous if exist

		// call it twice to go back
		String previous = splitIter.previous();
		previous = splitIter.previous();

		width = defaultFontMetrics.stringWidth(previous);

		// add to previous if previous is within range size
		if (width <= max) {
		    splitIter.add(previous + " " + current);
		    // remove next
		    splitIter.next();
		    splitIter.remove();
		    splitIter.next();
		    splitIter.remove();
		}

	    }
	}
	return splits.toArray(new String[0]);
    }

}
