package main;

import processingTemplate.Gui;

public class SketchTemplate extends Gui{
	
	/**
	 * Use like a usual sketch in Processing IDE
	 */
	
	@Override
	public void setup() {
		/**
		 * super.setup();
		 */
	}
	
	
	@Override
	public void draw() {
		/**
		 * super.draw();
		 */
		
	}
	
	
	
	
	/**
	 * Replace main.SketchTemplate
	 * @param args Arguments delivered to PApplet
	 */
	
	public static void main(String[] args) {
		 (new Gui()).run("main.SketchTemplate");
	 }

}
