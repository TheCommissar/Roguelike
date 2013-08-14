package game;
import io.ResourceManager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import state.GameState;
import state.OverworldState;


public class Game extends Canvas
{
	// Globals
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static Point mousePos;
	public static Point oldMousePos;
	
	public static ResourceManager RESOURCES;
	
	public static Font FONT_TEXT;
	
	
	// Window components
	private JFrame container;
	private JPanel panel;
	
	private BufferStrategy strategy;
	
	private boolean waitingForKeyPress;
	
	
	// Game components
	private boolean gameRunning = true;
	
	private OverworldState overworld;
	private GameState currentState; 
	
	public Game() throws IOException
	{
		// Set up the window
		setUpWindow();
		addKeyListener(new KeyInputHandler());
		MouseInputHandler mih = new MouseInputHandler();
		addMouseListener(mih);
		addMouseMotionListener(mih);


		
		// Set up the resources
		RESOURCES = new ResourceManager();
		RESOURCES.loadSprites();
		
		// Instantiate the game states
		overworld = new OverworldState();
		currentState = overworld;
	}
	
	private void setUpWindow()
	{
		container = new JFrame("Roguelike");
		
		try { RESOURCES = new ResourceManager(); } 
		catch (IOException e1) { e1.printStackTrace(); }
		
		FONT_TEXT = new Font("SansSerif", Font.PLAIN, 12);
		
		panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);
		

		setBounds(5, 5, WIDTH, HEIGHT);
		
		panel.add(this);
		

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		

		container.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		});
		

		//addKeyListener(new KeyInputHandler());
		//addMouseListener(new MouseInputHandler());
		

		requestFocus();


		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}
	
	public void gameLoop() throws IOException
	{
		
		long lastLoopTime = System.currentTimeMillis();
		long delta;
		
		while (gameRunning)
		{	
			// Call game logic
			currentState.logic();
			//getMouse();
			
			Graphics2D gameRenderer = (Graphics2D) strategy.getDrawGraphics();
			
			// Let the game state render the frame
			currentState.render(gameRenderer);
			
			// Wipe rendering objects
			gameRenderer.dispose();
			
			// Display the frame
			strategy.show();
			
			// Get time in frame
			delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			// Sleep
			try 
			{ 
				Thread.sleep(15);
			}
			catch (Exception e) {}
		}
	}
	
	private class KeyInputHandler extends KeyAdapter 
	{
		
		private int pressCount = 1;
		
		public void keyPressed(KeyEvent e) 
		{
			if (waitingForKeyPress) 
			{
				return;
			}
			else
			{
				currentState.keyPressHandle(e);
			}
		} 
		
		public void keyReleased(KeyEvent e) 
		{
			if (waitingForKeyPress)
			{
				waitingForKeyPress = false;
			}
			else 
			{
				currentState.keyReleaseHandle(e);
			}
		}

		public void keyTyped(KeyEvent e) 
		{
			if (e.getKeyChar() == 27)
			{
				System.exit(0);
			}
		}
	}
	
	private class MouseInputHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			currentState.mousePressHandle(e);
		}
		
		public void mouseReleased(MouseEvent e)
		{
			currentState.mouseReleaseHandle(e);
		}
		
		public void mouseDragged(MouseEvent e)
		{
			oldMousePos = mousePos;
			mousePos = e.getPoint();
			currentState.mouseDragHandle(e);
		}
	}

	private void getMouse()
	{
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		mousePos.x = (int) b.getX() - getLocationOnScreen().x;
		mousePos.y = (int) b.getY() - getLocationOnScreen().y;
	}
	
	public static void main(String argv[]) throws IOException 
	{
		Game g = new Game();

		g.gameLoop();
	}
}
