package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Snake extends JPanel{
	
	//private static final Color color = new Color(115, 162, 78);
	private static final int start = 250;
	private static final int speed = 25;
	
	private ArrayList<Cell> body;
	private String direction; 
	private Apple apple; // apple will be attached to a snake
	
	String directions[] = {"up", "down", "left", "right"};
	
	private Main window;
	
	
	public Snake(Main window)
	{
		this.window = window;
		this.body = new ArrayList<>();
		
		//initial length of snake is 3
		Cell lastCell = new Cell(start + Cell.WIDTH, start);
		for(int i = 0; i < 3; i++)
		{
			body.add(new Cell(lastCell.getPosx() - Cell.WIDTH, lastCell.getPosy()));
			lastCell = this.body.get(i); //body[0] is a cell
		}
		
		//initial direction is right
		this.direction = "right";
		//this.direction = directions[new Random().nextInt(4)];	
	}
	
	public void setDirection(String direction)
	{
		this.direction = direction;
	}
	
	public String getDirection()
	{
		return this.direction;
	}
	
	public int getScore()
	{
		return this.body.size();
	}
	
	public void growBody()
	{
		Cell tail = this.body.get(this.body.size() - 1);
		switch(this.direction) 
		{
			case "right": this.body.add(new Cell(tail.getPosx() - Cell.WIDTH, tail.getPosy())); break;
			case "left": this.body.add(new Cell(tail.getPosx() + Cell.WIDTH, tail.getPosy())); break;
			case "up": this.body.add(new Cell(tail.getPosx(), tail.getPosy() + Cell.HEIGHT)); break;
			case "down": this.body.add(new Cell(tail.getPosx(), tail.getPosy() - Cell.HEIGHT)); break;
		}
	}
	
	public void checkCollision()
	{
		Cell head = this.body.get(0); // snake head
		
		// check if snake ate itself
		for(int i = 1; i < this.body.size(); i++)
		{
			Cell otherPart = this.body.get(i);
			
			if(head.intersects(otherPart))
			{
				System.out.println("You Lose!");
				this.window.setVisible(false);
				JFrame parent = new JFrame("Game over!");
				JOptionPane.showMessageDialog(parent, "Your score: " + (this.body.size()-3));
				
				this.window.dispatchEvent(new WindowEvent(this.window, WindowEvent.WINDOW_CLOSING));
				System.exit(0);
			}
		}
		
		// check if snake ate apple
		if(this.apple != null)
		{
			Cell applePos = new Cell(this.apple.getPosx(), this.apple.getPosy());
			if(head.intersects(applePos))
			{
				// apple disappears
				this.apple = null;
				//snake size increases
				this.growBody();
			}
		}
	}
	
	
	public void moveSnake()
	{
		int x = 0;
		ArrayList<Cell> newList = new ArrayList<>();
		
		Cell lastHead = this.body.get(0);
		Cell head = new Cell(lastHead.getPosx(), lastHead.getPosy());
		
		
		switch(this.direction) 
		{
			case "right": 
				// right boundary check
				if(lastHead.getPosx() == Main.WINDOW_SIZE)
				{
					x = -(Main.WINDOW_SIZE + speed);
				}
				head.setPosx(x + speed); 
				break;
				
			case "left": 
				// left boundary check
				if(lastHead.getPosx() == 0)
				{
					x = Main.WINDOW_SIZE + speed;
				}
				head.setPosx(x - speed); 
				break;
				
			case "up": 
				// top boundary check
				if(lastHead.getPosy() == 0)
				{
					x = Main.WINDOW_SIZE + speed;
				}
				head.setPosy(x - speed); 
				break;
				
			case "down": 
				// bottom boundary check
				if(lastHead.getPosy() == Main.WINDOW_SIZE)
				{
					x = -(Main.WINDOW_SIZE + speed);
				}
				head.setPosy(x + speed); 
				break;
		}
		
		newList.add(head);
		
		// traverse old list and shift cells to accommodate movement
		for(int i = 1; i < this.body.size(); i++)
		{
			Cell previous = this.body.get(i-1); // 0 --> lastHead,.. and so on
			Cell newLocation = new Cell(previous.getPosx(), previous.getPosy());
			newList.add(newLocation);
		}
		
		this.body = newList;
		
		// collision check for every new move
		checkCollision();
		
		// update score
	
	}
	
	
	private void drawSnake(Graphics g)
	{
		moveSnake();
		
		// draw moved snake
		Graphics2D g2d = (Graphics2D) g;
		
		// draw apple
		if(this.apple != null)
		{
			g2d.setPaint(Color.red);
			g2d.drawRect(this.apple.getPosx(), this.apple.getPosy(), Cell.WIDTH, Cell.HEIGHT);
			g2d.fillRect(this.apple.getPosx(), this.apple.getPosy(), Cell.WIDTH, Cell.HEIGHT);
		}
		
		// draw snake
		g2d.setPaint(Color.green);
		for(Cell cell: this.body)
		{
			g2d.drawRect(cell.getPosx(), cell.getPosy(), Cell.WIDTH, Cell.HEIGHT);
			g2d.fillRect(cell.getPosx(), cell.getPosy(), Cell.WIDTH, Cell.HEIGHT);
		}
	}
	
	
	public void setApple(Apple apple)
	{
		this.apple = apple;
	}
	
	public Apple getApple()
	{
		return this.apple;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.black);
		Main.setScore(this.body.size() - 3);
		drawSnake(g);
	}
	
	

}
