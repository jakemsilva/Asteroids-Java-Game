/* Name: Jake Silva
 * uniquename: silvajm
 * Class name: Spaceship
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
import java.awt.Polygon;

public class SpaceShip {
	//set variables
	private int xPos, yPos;
	private boolean shooting;
	private Color clr;
	private final int WIDTH, HEIGHT;
	private boolean crash = false;
	private int[] xWindow = new int[4];
	private int[] yWindow = new int[4];
	private Polygon tracker;
	public final int MOUSE_OFFSET = 25;
	
	/*
	 * Constructor that generates spaceship x and y position randomly, height and width, default color and default shooting behavior
	 */
	public SpaceShip(){
		Random randomGenerator = new Random();
		xPos = randomGenerator.nextInt(251); 
		yPos = randomGenerator.nextInt(251);
		shooting = false;
		WIDTH = 75;
		HEIGHT = 75;
		clr = Color.blue;
		calculatePoly();
		Polygon tracker = new Polygon(xWindow, yWindow, xWindow.length);
	}
	
	/*
	 * Constructor that sets ship behavior taking x and y position as parameters
	 */
	public SpaceShip(int xp, int yp){
		xPos = xp;
		yPos = yp;
		shooting = false;
		WIDTH = 75;
		HEIGHT = 75;
		clr = Color.red;
	}
	
	/*
	 * Set shooting variable via parameter
	 */
	public void setShooting(boolean shoot){
		shooting = shoot;
	}
	
	/*
	 * Draw spaceship object. num is the width of the screen
	 */
	public void draw(Graphics g, int num){
		Graphics2D g2 = (Graphics2D) g;
		//draw spaceship body
		g.setColor(clr);
		g.fillOval(xPos - MOUSE_OFFSET,yPos - MOUSE_OFFSET,WIDTH,HEIGHT);
		//draw windows
		g.setColor(Color.black);
		g.fillRect(xPos + 45 - MOUSE_OFFSET, yPos + 20 - MOUSE_OFFSET, 10, 10);
		g.fillRect(xPos + 45 - MOUSE_OFFSET, yPos + 40 - MOUSE_OFFSET, 10, 10);
		//draw arc on ship
		g.setColor(Color.yellow);
		g2.setStroke(new BasicStroke(3));
		g2.drawArc(xPos + 13 - MOUSE_OFFSET, yPos - 17 - MOUSE_OFFSET, 105, 105, 140, 90);
		//draw ship antennae
		g.setColor(Color.red);
		g.drawLine(xPos+5 - MOUSE_OFFSET, yPos+15 - MOUSE_OFFSET, xPos - 25 - MOUSE_OFFSET, yPos - 25 - MOUSE_OFFSET);
		g.drawLine(xPos+5 - MOUSE_OFFSET, yPos+45 - MOUSE_OFFSET, xPos - 50 - MOUSE_OFFSET, yPos - 30 - MOUSE_OFFSET);
		//if ship is shooting, draw firing line to edge of screen
		//g.setColor(Color.green);
		//g.fillPolygon(xWindow, yWindow, xWindow.length);
		g.setColor(Color.red);
		if(shooting){
			g.drawLine(xPos + WIDTH - MOUSE_OFFSET, yPos + (WIDTH / 2) - MOUSE_OFFSET, num, yPos + (WIDTH / 2) - MOUSE_OFFSET);
		}
	}
	
	/*
	 * Set color of spaceship
	 */
	public void setColor(Color starcolor){
		clr = starcolor;
	}
	
	/*
	 * Move spaceship
	 */
	public void move(int x, int y){
		xPos = x;
		yPos = y;
		calculatePoly();
		tracker = new Polygon(xWindow, yWindow, xWindow.length);
	}
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public int getHeight(){
		return HEIGHT;
	}
	public int getWidth(){
		return WIDTH;
	}
	public boolean getCrash(){
		return crash;
	}
	public void setCrash(boolean val){
		crash = val;
	}
	public Polygon getTracker(){
		return tracker;
	}
	public void calculatePoly(){
		xWindow[0] = xPos - MOUSE_OFFSET;
		xWindow[1] = xPos + WIDTH - MOUSE_OFFSET;
		xWindow[2] = xPos + WIDTH - MOUSE_OFFSET;
		xWindow[3] = xPos - MOUSE_OFFSET;
		yWindow[0] = yPos - MOUSE_OFFSET;
		yWindow[1] = yPos - MOUSE_OFFSET;
		yWindow[2] = yPos + HEIGHT - MOUSE_OFFSET;
		yWindow[3] = yPos + HEIGHT - MOUSE_OFFSET;
	}
}
