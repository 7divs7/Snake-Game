package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Main extends JFrame implements KeyListener, ActionListener{
	
	public static final int WINDOW_SIZE = 775;
	
	private static JLabel jlabel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Snake snake;
	
	public Main()
	{	
		// create snake
		this.snake = new Snake(this);
		
		// timer for redrawing screen - action listener 
		Timer timer = new Timer(150, this); // every 150s window is redrawn - actionPerformed() is called
		timer.start();
		
		// timer for drawing apples on screen - java.util for creating a scheduled task
		java.util.Timer drawApples = new java.util.Timer();
		Apple st = new Apple(this.snake);
		drawApples.schedule(st, 0, 3000);
		
		// window creation and drawing - JFrame stuff
		add(this.snake);
		setTitle("Snake Game");
		setSize(WINDOW_SIZE, WINDOW_SIZE);
		this.addKeyListener(this); // to track keyboard input
		setLocationRelativeTo(null);
		jlabel = new JLabel("SCORE: 0");
	    jlabel.setFont(new Font("Verdana",1,20));
	    jlabel.setForeground (Color.blue);
	    jlabel.setHorizontalAlignment(JLabel.RIGHT);
		this.add(jlabel, BorderLayout.NORTH);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void setScore(int score)
	{
		jlabel.setText("SCORE: " + score);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		if(code == 39 && !this.snake.getDirection().equals("left"))
		{
			// if current direction is already left then we cannot go right since there will be a collision
			this.snake.setDirection("right"); // right arrow pressed
		}
		
		else if(code == 37 && !this.snake.getDirection().equals("right"))
		{
			this.snake.setDirection("left"); // left arrow pressed
		}
		
		else if(code == 38 && !this.snake.getDirection().equals("down"))
		{
			this.snake.setDirection("up"); // up arrow pressed
		}
		
		else if(code == 40 && !this.snake.getDirection().equals("up"))
		{
			this.snake.setDirection("down"); // down arrow pressed
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// redraw screen
		repaint();
	}
	
	// multi-thread system
	public static void main(String[] args)
	{
		EventQueue.invokeLater(Main::new);
	}
	
}
