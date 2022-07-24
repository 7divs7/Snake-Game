package snake;

import java.util.Random;
import java.util.TimerTask;

public class Apple extends TimerTask{
	//use a timer task --> every 3 secs if apple is null then we draw a new apple - run()
	
	private int posx;
	private int posy;
	private Snake snake;
	
	public Apple(Snake snake)
	{
		this.snake = snake;
	}
	
	// set apple position
	public Apple()
	{
		this.posx = Cell.WIDTH * new Random().nextInt(20); 
		this.posy = Cell.HEIGHT * new Random().nextInt(20); // screen width is 500 x 500
	}
	
	// get apple position
	public int getPosx() {
		return posx;
	}
	
	public int getPosy() {
		return posy;
	}
	
	@Override
	public void run()
	{
		if(this.snake.getApple() == null)
		{
			this.snake.setApple(new Apple());
		}
	}
	

}
