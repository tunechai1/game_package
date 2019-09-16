import edu.utc.game.Game;
import edu.utc.game.GameObject;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.List;

import edu.utc.game.Scene;

public class DemoGame extends Game implements Scene {
	
	public static void main(String[] args)
	{
		DemoGame game = new DemoGame();
		game.gameLoop();
	}

	List<GameObject> gameObjects;
	
	public DemoGame()
	{
		initUI(640, 480, "DemoGame");

		// screen clear is white (this could go in drawFrame if you wanted it to change
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		
		gameObjects = new java.util.LinkedList<GameObject>();
		
		gameObjects.add(new Bouncer());
		
		
	}
	
	public Scene drawFrame(int delta) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		
		for (GameObject o : gameObjects)
		{
			o.update(delta);
		}
		
		for (GameObject o : gameObjects)
		{
			o.draw();
		}
		
		return this;
	}
	
	private class Bouncer extends GameObject
	{
		public Bouncer()
		{
			this.hitbox.setSize(10, 10);
			this.hitbox.setLocation(Game.ui.getWidth()/2-5, Game.ui.getHeight()/2-5);
			this.setColor(1,0,0);
		}
		
		public void update(int delta)
		{
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_UP))
			{
				this.hitbox.translate(0,  (int)(-0.5*delta));
			}
			
		}
	}
	

}
