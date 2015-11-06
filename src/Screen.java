/* Name: Jake Silva
 * uniquename: silvajm
 * Class name: Screen
 *
 * Notes for instructor (if any): none
 *
 * Received assistance from: No one
 * Expected score: 100 -- I completed all three extra credits
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.*;

public class Screen extends JPanel implements ComponentListener, ActionListener, MouseListener, MouseMotionListener, KeyListener {
	//Set system variables
	private SpaceShip ship;
	private final int NUM_STARS = 100;
	private Star[] stars = new Star[NUM_STARS];
	private ArrayList<Asteroid> ast = new ArrayList<Asteroid>();
	private final int NUM_ASTEROIDS = 15;
	private Timer timer;
	private Timer counter;
	private final int DELAY = 100;
	private final int WIDTH = 800;
	private final int HEIGHT = 800;
	private int lives;
	private int score;
	private int timerCount;
	private boolean gameOver = false; 


	public static void main(String[] args) {
		//Create jframe window, set content and make visible
		JFrame window = new JFrame("Space Ship");
		Screen content = new Screen();

		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.getContentPane().add (content);
		window.pack();
		window.setVisible(true);
	}

	public Screen(){
		//set background color
		setBackground(Color.black);
		//set preferred size and add listeners for user actions
		setPreferredSize (new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);
		addComponentListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
		lives = 3;
		score = 0;
		//Create ship object
		ship = new SpaceShip();
		//Create star objects
		for(int i = 0; i < 100; i++){
			stars[i] = new Star(800,800);
		}
		//Create asteroid objects
		for(int i = 0; i < NUM_ASTEROIDS; i++){
			ast.add(new Asteroid(this));
		}
		//create and start timer
		timer = new Timer(DELAY, this);
		timerCount = 15;
		timer.start();
		counter = new Timer(1000,new TimerListener());
		counter.start();
		Sound.play(System.getProperty("user.dir") + "/background.wav");
	}

	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			timerCount--;
			if(timerCount <= 0){
				gameOver();
			}
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int w = getWidth();
		int h = getHeight();
		//draw multiple stars
		for(int i = 0; i < stars.length; i++){
			stars[i].drawStar(g);
		}	
		for(int i = 0; i < ast.size(); i++){
			ast.get(i).draw(g);
			if(ast.get(i).getHit()){
				g2.setColor(Color.white);
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 48));
				g2.drawString("Hit",ast.get(i).getX(),ast.get(i).getY());
				Sound.play(System.getProperty("user.dir") + "/explosion.wav");
			}
		}
		ship.draw(g, w);
		g2.setColor(Color.white);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 28));
		g2.drawString("Time left: " + timerCount, 20, 100);
		g2.drawString("Score: " + score, 20, 60);
		g2.drawString("Lives left: " + lives, 20, 140);
		if(gameOver){
			g2.setColor(Color.white);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 68));
			g2.drawString("Game Over", ((w/2)-150), (h/2));
			counter.stop();
			timer.stop();
		}
	} // end paintComponent()

	@Override
	public void actionPerformed(ActionEvent event) {
		//loop through asteroid array and move them based on width and height of window
		for(int i = 0; i < ast.size(); i++){
			int w = getWidth();
			int h = getHeight();
			ast.get(i).move(w, h);
			//repaint screen
			repaint();
			if(ast.get(i).getTracker() != null && ship.getTracker() != null && ast.get(i).getTracker().getBounds().intersects(ship.getTracker().getBounds())){
				ast.remove(i);
				lives -= 1;
				if(lives > 1){
					ship.setColor(Color.yellow);
				}
				else{
					ship.setColor(Color.red);
				}
				if(lives == 0){
					gameOver = true;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		//start shooting on mouse press
		ship.setShooting(true);
		Sound.play(System.getProperty("user.dir") + "/laser.wav");
		int shotX = e.getX();
		int shotY = e.getY() - ship.MOUSE_OFFSET;
		processShot(shotX, shotY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//end shooting on mouse release
		destroyAsteroid();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//move ship on mouse drag
		ship.setShooting(false);
		ship.move(e.getX(), e.getY());		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//move ship on mouse moved
		ship.move(e.getX(), e.getY());
		for(int i = 0; i < ast.size(); i++){
			if(ast.get(i).getTracker() != null && ast.get(i).getTracker().getBounds().intersects(ship.getTracker().getBounds())){
				ast.remove(i);
				lives -= 1;
				if(lives > 1){
					ship.setColor(Color.yellow);
				}
				else{
					ship.setColor(Color.red);
				}
				if(lives == 0){
					gameOver = true;
				}
			}
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentResized(ComponentEvent e) {
		//repaint stars when window is resized
		int w = getWidth();
		int h = getWidth();
		for(int i = 0; i < stars.length; i++){
			stars[i].setStarPos(w,h);
		}
		repaint();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void updateScore(){
		score += 1;
	}
	public void gameOver(){
		gameOver = true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if(keyCode == 32){
			//get x and y of mouse
			int x = MouseInfo.getPointerInfo().getLocation().x - 16;
			int y = MouseInfo.getPointerInfo().getLocation().y - 97;
			processShot(x,y);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		ship.setShooting(false);
		for(int i = 0; i < ast.size(); i++){
			if(ast.get(i).getHit()){
				ast.remove(i);
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void processShot(int shotX, int shotY){
		ship.setShooting(true);
		Sound.play(System.getProperty("user.dir") + "/laser.wav");
		for(int i = 0; i < ast.size(); i++){
			if(ast.get(i).getX() > shotX){
				if (ast.get(i).getTracker() != null && ast.get(i).getTracker().contains(new Point(ast.get(i).getX(), shotY + (ship.getHeight() / 2)))){
					ast.get(i).setHit(true);
					updateScore();
				}
			}
		}
	}

	public void destroyAsteroid(){
		ship.setShooting(false);
		for(int i = 0; i < ast.size(); i++){
			if(ast.get(i).getHit()){
				ast.remove(i);
			}
		}
	}
}
