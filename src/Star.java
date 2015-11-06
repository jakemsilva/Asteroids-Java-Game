/* Name: Jake Silva
 * uniquename: silvajm
 * Class name: Star
 *
 * Notes for instructor (if any): none
 *
 * Received assistance from: No one
 * Expected score: 100 -- I completed all three extra credits
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Star {
	//set variables
	private int xPos;
	private int yPos;
	Color starColor;
	Random r = new Random();
	
	public Star(int x, int y){
		//set color and initial position
		starColor = Color.yellow;
		xPos = r.nextInt(x);
		yPos = r.nextInt(y);
	}
	
	public void drawStar(Graphics g){
		//draw stars
		int[] xArr1 = {xPos, xPos+5, xPos+10};
		int[] yArr1 = {yPos + 10, yPos, yPos+10};
		int[] xArr2 = {xPos, xPos+10, xPos+5};
		int[] yArr2 = {yPos +5, yPos+5, yPos+10};
		
		g.setColor(starColor);
		g.fillPolygon(xArr1, yArr1, 3);
		g.fillPolygon(xArr2, yArr2, 3);	
	}
	
	/*
	 * Set star position. Used to update upon resize
	 */
	public void setStarPos(int rX, int rY){
		//random positioning
		xPos = r.nextInt(rX);
		yPos = r.nextInt(rY);
	}
}