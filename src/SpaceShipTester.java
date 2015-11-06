
public class SpaceShipTester {
	
	public static void main(String args[]){
		SpaceShip s1 = new SpaceShip();
		SpaceShip s2 = new SpaceShip(550, 50); //upper right corner
		
		s2.setShooting(true);
		
		System.out.println("\nAbout to print s1:");
		System.out.println(s1);
		System.out.println("\nAbout to print s2:");

		System.out.println(s2);
		
		
	}

}