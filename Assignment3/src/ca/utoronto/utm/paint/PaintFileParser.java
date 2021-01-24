package ca.utoronto.utm.paint;

import javafx.scene.paint.Color;
import org.ietf.jgss.Oid;

import javax.crypto.AEADBadTagException;
import java.awt.image.PixelInterleavedSampleModel;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private PaintModel paintModel; 
	
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$", Pattern.CASE_INSENSITIVE);
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$", Pattern.CASE_INSENSITIVE);
	private Pattern pCircleStart=Pattern.compile("^Circle$", Pattern.CASE_INSENSITIVE);
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$", Pattern.CASE_INSENSITIVE);
	private Pattern pRectangleStart = Pattern.compile("^Rectangle$", Pattern.CASE_INSENSITIVE);
	private Pattern pRectangleEnd = Pattern.compile("^EndRectangle$", Pattern.CASE_INSENSITIVE);
	private Pattern pPolylineStart = Pattern.compile("^Polyline$", Pattern.CASE_INSENSITIVE);
	private Pattern pPolylineEnd = Pattern.compile("^EndPolyline$", Pattern.CASE_INSENSITIVE);
	private Pattern pSquiggleStart = Pattern.compile("^Squiggle$", Pattern.CASE_INSENSITIVE);
	private Pattern pSquiggleEnd = Pattern.compile("^EndSquiggle$", Pattern.CASE_INSENSITIVE);
	private Pattern pcolor = Pattern.compile("^color:[0-9]{1,3},[0-9]{1,3},[0-9]{1,3}", Pattern.CASE_INSENSITIVE);
	private Pattern pfilled = Pattern.compile("^filled:(false|true)");
	private Pattern pcenter = Pattern.compile("^center:[(]{1}(-)?[0-9]{1,3},(-)?[0-9]{1,3}[)]{1}$", Pattern.CASE_INSENSITIVE);
	private Pattern pradius = Pattern.compile("^radius:[1-9]{1}[0-9]*");
	private Pattern prect1 = Pattern.compile("^p1:[(]{1}(-)?[0-9]{1,3},(-)?[0-9]{1,3}[)]{1}$", Pattern.CASE_INSENSITIVE);
	private Pattern prect2 = Pattern.compile("^p2:[(]{1}(-)?[0-9]{1,3},(-)?[0-9]{1,3}[)]{1}$", Pattern.CASE_INSENSITIVE);
	private Pattern ppoint = Pattern.compile("^point:[(]{1}(-)?[0-9]{1,3},(-)?[0-9]{1,3}[)]{1}$", Pattern.CASE_INSENSITIVE);
	private Pattern ppointStart = Pattern.compile("^points$", Pattern.CASE_INSENSITIVE);
	private Pattern ppointEnd = Pattern.compile("^endpoints$", Pattern.CASE_INSENSITIVE);
	private Pattern space = Pattern.compile("[//s]*", Pattern.CASE_INSENSITIVE);

	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}
	
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}
	
	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @param paintModel the paint model to add the commands to
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream, PaintModel paintModel) {
		this.paintModel = paintModel;
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following commands. As we parse the file, we modify 
		// the appropriate command.
		
		CircleCommand circleCommand = null; 
		RectangleCommand rectangleCommand = null;
		SquiggleCommand squiggleCommand = null;
		PolygonCommand polygonCommand = null;

		try {	
			int state=0; Matcher m; String l;
			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				l = l.replaceAll("\\s+","");
				this.lineNumber++;
				System.out.println(lineNumber+" "+l+" "+state);
				switch(state){
					case 0:
						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						}
						error("Expected Start of Paint Save File");
						return false;
					case 1: // Looking for the start of a new object or end of the save file
						m=pCircleStart.matcher(l);
						if(m.matches()){
							circleCommand = new CircleCommand(new Point(-1,-1), -1);
							state=21;
							break;
						}
						m = pRectangleStart.matcher(l);
						if(m.matches()){
							rectangleCommand = new RectangleCommand(new Point(-1,-1), new Point(-1,-1));
							state=31;
							break;
						}
						m = pPolylineStart.matcher(l);
						if(m.matches()){
							polygonCommand = new PolygonCommand();
							state=51;
							break;
						}
						m = pSquiggleStart.matcher(l);
						if(m.matches()){

							squiggleCommand = new SquiggleCommand();
							state=41;
							break;
						}
						m = pFileEnd.matcher(l);
						if (m.matches()) {
							state = 6;
							break;
						}
						m = space.matcher(l);
						if(m.matches()) {
							break;
						}
						return false;
						
	
					case 6:
						m = pFileEnd.matcher(l);
						
						if(m.matches()){
							paintModel.notifyObservers();
						
						}
						else {
							error("bad file end");
							return false;
						}
						break;
				
					case 21:
						m = pcolor.matcher(l);
						if (m.matches()){
							Color color;
							String [] tcolor = l.substring(l.indexOf(":")+1).split(",");
							
							for (int i = 0;i<3;i++) {
								if (Integer.parseInt(tcolor[i]) > 255) {
									error("color value out of bound");
									return false;
								}
							}
							color = Color.rgb(Integer.parseInt(tcolor[0]), Integer.parseInt(tcolor[1]), Integer.parseInt(tcolor[2]));
							circleCommand.setColor(color);
							state = 22;
						}
						else {
							error("Color incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 22:

						m = pfilled.matcher(l);
						if(m.matches()){
							boolean fill;
							String tfilled = l.substring(l.indexOf(":")+1);
							fill = Boolean.parseBoolean(tfilled);
							circleCommand.setFill(fill);
							state = 23;

						}
						else {
							error("fill incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 23:
						Point center;
						m = pcenter.matcher(l);
						if(m.matches()){
							String tpoint = l.substring(l.indexOf(":")+1);
							tpoint = tpoint.replace(")","").replace("(","");
							center = new Point(Integer.parseInt(tpoint.substring(0,tpoint.indexOf(","))), Integer.parseInt(tpoint.substring(tpoint.indexOf(",")+1)));
							circleCommand.setCentre(center);
							state = 24;
						}
						else{
							error("center incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 24:
						int radius;
						
						m = pradius.matcher(l);
						if(m.matches()){
							radius = Integer.parseInt(l.substring(l.indexOf(":")+1));
							if (radius< 0) {
								return false;
							}
							circleCommand.setRadius(radius);
							state = 25;

						}
						else{
							error("radius incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 25:
						m = pCircleEnd.matcher(l);
						if(m.matches()){
							paintModel.accept(new PaintVisitorImpl(), circleCommand);
//							paintModel.addCommand(circleCommand);
							circleCommand = null;
							state = 1;
						}
						else {
							error("encCircle incorrect format: line "+ this.lineNumber);
							return false;

						}
						break;

					case 31:

						m = pcolor.matcher(l);
						if (m.matches()){
							Color color;
							String [] tcolor = l.substring(l.indexOf(":")+1).split(",");
							for ( int i = 0;i<3;i++) {
								if (Integer.parseInt(tcolor[i])> 255) {
									error("color value out of bound");
									return false;
								}
							}
							
							color = Color.rgb(Integer.parseInt(tcolor[0]), Integer.parseInt(tcolor[1]), Integer.parseInt(tcolor[2]));
							rectangleCommand.setColor(color);
							
							state = 32;
						}
						else {
							error("Color incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 32:
						m = pfilled.matcher(l);
						if(m.matches()){
							boolean fill;
							String tfilled = l.substring(l.indexOf(":")+1);
							fill = Boolean.parseBoolean(tfilled);
							rectangleCommand.setFill(fill);
							state = 33;

						}
						else {
							error("fill incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 33:
						m = prect1.matcher(l);
						if(m.matches()){
							Point point = new Point(-1,-1);
							String tpoint = l.substring(l.indexOf(":")+1);
							tpoint = tpoint.replace(")","").replace("(","");
							point = new Point(Integer.parseInt(tpoint.substring(0,tpoint.indexOf(","))), Integer.parseInt(tpoint.substring(tpoint.indexOf(",")+1)));
							rectangleCommand.setP1(point);
							state = 34;

						}
						else {
							error("p1 incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 34:
						m = prect2.matcher(l);
						if(m.matches()){
							Point point = new Point(-1,-1);
							String tpoint = l.substring(l.indexOf(":")+1);
							tpoint = tpoint.replace(")","").replace("(","");
							point = new Point(Integer.parseInt(tpoint.substring(0,tpoint.indexOf(","))), Integer.parseInt(tpoint.substring(tpoint.indexOf(",")+1)));
							rectangleCommand.setP2(point);
							state = 35;

						}
						else {
							error("p2 incorrect format: line " + this.lineNumber);
							return false;
						}
						break;


					case 35:
						m = pRectangleEnd.matcher(l);
						if (m.matches()){
							paintModel.accept(new PaintVisitorImpl(), rectangleCommand);
//							paintModel.addCommand(rectangleCommand);
							rectangleCommand = null;
							state = 1;
						}
						else {
							error("endRectangle incorrect format: line " + this.lineNumber);
							return false;
						}
						break;


					case 41:
						m = pcolor.matcher(l);
						if (m.matches()){
							Color color;
							String [] tcolor = l.substring(l.indexOf(":")+1).split(",");
							for ( int i = 0;i<3;i++) {
								if (Integer.parseInt(tcolor[i])> 255) {
									error("color value out of bound");
									return false;
								}
							}
							color = Color.rgb(Integer.parseInt(tcolor[0]), Integer.parseInt(tcolor[1]), Integer.parseInt(tcolor[2]));
							squiggleCommand.setColor(color);
							state = 42;
						}
						else {
							error("Color incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 42:
						m = pfilled.matcher(l);
						if(m.matches()){
							boolean fill;
							String tfilled = l.substring(l.indexOf(":")+1);
							fill = Boolean.parseBoolean(tfilled);
							squiggleCommand.setFill(fill);
							state = 43;
						}
						else {
							error("fill incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 43:
						m = ppointStart.matcher(l);
						if(m.matches()){
							state = 44;
						}
						else {
							error("pointStart incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 44:
						m = ppointEnd.matcher(l);
						if (m.matches()){
							state = 45;
							break;
						}
						m = ppoint.matcher(l);
						if(m.matches()){
							Point point;
							String tpoint = l.substring(l.indexOf(":")+1);
							tpoint = tpoint.replace(")","").replace("(","");
							point = new Point(Integer.parseInt(tpoint.substring(0,tpoint.indexOf(","))), Integer.parseInt(tpoint.substring(tpoint.indexOf(",")+1)));
							squiggleCommand.add(point);
						}
						else{
							error("points incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 45:
						m = pSquiggleEnd.matcher(l);
						if(m.matches()){
//							paintModel.addCommand(squiggleCommand);
							paintModel.accept(new PaintVisitorImpl(), squiggleCommand);
							squiggleCommand = null;
							state =1;

						}
						else {

							error("endSquiggle incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 51:
						m = pcolor.matcher(l);
						if (m.matches()){
							Color color;
							String [] tcolor = l.substring(l.indexOf(":")+1).split(",");
							for ( int i = 0;i<3;i++) {
								if (Integer.parseInt(tcolor[i])> 255) {
									error("color value out of bound");
									return false;
								}
							}
							color = Color.rgb(Integer.parseInt(tcolor[0]), Integer.parseInt(tcolor[1]), Integer.parseInt(tcolor[2]));
							polygonCommand.setColor(color);
							state = 52;
						}
						else {
							error("Color incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 52:
						m = pfilled.matcher(l);
						if(m.matches()){
							boolean fill;
							String tfilled = l.substring(l.indexOf(":")+1);
							fill = Boolean.parseBoolean(tfilled);
							polygonCommand.setFill(fill);
							state = 53;
						}
						else {
							error("fill incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 53:
						m = ppointStart.matcher(l);
						if(m.matches()){
							state = 54;
						}
						else {
							error("pointStart incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
					case 54:
						m = ppointEnd.matcher(l);
						if (m.matches()){
							state = 55;
							break;
						}
						m = ppoint.matcher(l);
						if(m.matches()){
							Point point;
							String tpoint = l.substring(l.indexOf(":")+1);
							tpoint = tpoint.replace(")","").replace("(","");
							point = new Point(Integer.parseInt(tpoint.substring(0,tpoint.indexOf(","))), Integer.parseInt(tpoint.substring(tpoint.indexOf(",")+1)));
							polygonCommand.add(point);
						}
						else{
							error("points incorrect format: line " + this.lineNumber);
							return false;
						}
						break;

					case 55:
						m = pPolylineEnd.matcher(l);
						if(m.matches()){
							paintModel.accept(new PaintVisitorImpl(), polygonCommand);
//							paintModel.addCommand(polygonCommand);
							polygonCommand = null;
							state =1;
						}
						else {
							error("endPolygon incorrect format: line " + this.lineNumber);
							return false;
						}
						break;
				}
			}
		}  
		catch (Exception e){
			return false;
		}
		return true;
	}
}
