package snake;

public class Cell {
	
	private int posx;
	private int posy;
	
	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	
	public Cell(int posx, int posy) 
	{
		this.posx = posx;
		this.posy = posy;
	}
	
	//get --> to access current position
	//set --> to move across screen
	
	public int getPosx() {
		return posx;
	}

	public void setPosx(int step) {
		this.posx += step;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int step) {
		this.posy += step;
	}



	public boolean intersects(Cell c)
	{
		// check if the position are same as cell coordinates
		return this.posx == c.getPosx() && this.posy == c.getPosy();
	}
	
	

}
