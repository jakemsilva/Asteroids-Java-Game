/* Name: Jake Silva
 * uniquename: silvajm
 * Class name: Asteroid
 *
 * Notes for instructor (if any): none
 *
 * Received assistance from: No one
 * Expected score: 100 -- I completed all three extra credits
 */

import java.util.Random;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Asteroid {
	//set variables
	private int xPos, yPos;
	private Color clr;
	private final int WIDTH, HEIGHT;
	private Random r = new Random();
	private int deltaX = r.nextInt(15) + 1;
	private int deltaY = r.nextInt(15) + 1;
	private int[] xWindow = new int[4];
	private int[] yWindow = new int[4];
	private Polygon tracker;
	private boolean hit = false;
	/*
	 * Constructor that generates asteroid x and y position randomly, height and width and default color
	 */
	public Asteroid(Screen scr){
		xPos = r.nextInt(800);
		yPos = r.nextInt(800);
		WIDTH = r.nextInt(30) + 30;
		HEIGHT = r.nextInt(25) + 30;
		clr = Color.gray;
		calculatePoly();
		Polygon tracker = new Polygon(xWindow, yWindow, xWindow.length);
		//System.out.println("xpos: " + xPos + "\n ypos: " + yPos + "\n width: " + WIDTH + "\n height: " + HEIGHT);
	}

	/*
	 * Draw asteroid object.
	 */
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		//set color
		g.setColor(clr);
		//draw asteroid
		g.fillOval(xPos,yPos,WIDTH,HEIGHT);
		//g.setColor (Color.green);
		//g.fillPolygon(xWindow, yWindow, xWindow.length);
	}

	/*
	 * Move asteroid
	 */
	public void move(int w, int h){
		//create random object for 30% asteroid movement
		Random r = new Random();
		float chance = r.nextFloat();
		//move them 30% of the time
		if (chance <= 0.30f){
			xPos += deltaX;
			yPos += deltaY;
			calculatePoly();
			tracker = new Polygon(xWindow, yWindow, xWindow.length);
			if (xPos <= 0 || xPos >= w-WIDTH)
				deltaX = deltaX * -1;

			if (yPos <= 0 || yPos >= h-HEIGHT)
				deltaY = deltaY * -1;
		}
	}
	public void calculatePoly(){
		xWindow[0] = xPos;
		xWindow[1] = xPos + WIDTH;
		xWindow[2] = xPos + WIDTH;
		xWindow[3] = xPos;
		yWindow[0] = yPos;
		yWindow[1] = yPos;
		yWindow[2] = yPos + HEIGHT;
		yWindow[3] = yPos + HEIGHT;
	}
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public Polygon getTracker(){
		return tracker;
	}
	public boolean getHit(){
		return hit;
	}
	public void setHit(boolean val){
		hit = val;
	}

}
