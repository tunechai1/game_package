import edu.utc.game.Game;
import edu.utc.game.GameObject;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.Iterator;
import java.util.List;

import edu.utc.game.Scene;

public class DemoGame extends Game implements Scene {
	
	private static java.util.Random rand=new java.util.Random();

	
	public static void main(String[] args)
	{
		DemoGame game = new DemoGame();
		game.gameLoop();
	}

	List<GameObject> gameObjects;
	Player player;
	
	public DemoGame()
	{
		initUI(640, 480, "DemoGame");

		// screen clear is white (this could go in drawFrame if you wanted it to change
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		
		gameObjects = new java.util.LinkedList<GameObject>();
		
		player = new Player();
		spawnTargets(10, 0, 0, 0);
		
		
	}
	
	public void spawnTargets(int count, float r, float g, float b)
	{
		for (int i=0; i<count; i++)
		{
			gameObjects.add(new Target(player, r, g, b));
		}
	}
	
	public Scene drawFrame(int delta) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		
		for (GameObject o : gameObjects)
		{
			o.update(delta);
		}
		player.update(delta);
		
		Iterator<GameObject> it = gameObjects.iterator();
		while (it.hasNext()) {
			GameObject o = it.next();
			if (! o.isActive())
			{
				it.remove();
			}
		}
		
		if (gameObjects.isEmpty()) {
			spawnTargets(10, 1, 0, 0);
		}
		
		for (GameObject o : gameObjects)
		{
			o.draw();
		}
		
		player.draw();
		
		return this;
	}
	
	private class Player extends GameObject
	{
		public Player()
		{
			this.hitbox.setSize(10, 10);
			this.hitbox.setLocation(Game.ui.getWidth()/2-5, Game.ui.getHeight()/2-5);
			this.setColor(1,0,0);
		}
		
		public void update(int delta)
		{
			float speed=0.25f;
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_UP))
			{
				this.hitbox.translate(0,  (int)(-speed*delta));
			}
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN))
			{
				this.hitbox.translate(0,  (int)(speed*delta));
			}
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT))
			{
				this.hitbox.translate((int)(-speed*delta), 0);
			}
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT))
			{
				this.hitbox.translate((int)(speed*delta),0);
			}
		}
	}
	
	private class Target extends GameObject
	{
		private Player player;
		private int size=50;
		
		
		public Target(Player p, float r, float g, float b)
		{
			this.player = p;
			this.hitbox.setSize(size, size);
			this.setColor(r,g,b);
			this.hitbox.setLocation(
					(int)(rand.nextFloat()*Game.ui.getWidth()),
					(int)(rand.nextFloat()*Game.ui.getHeight()))
					;
			System.out.println(this.hitbox);
		}

		public void update(int delta)
		{
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE) && player.intersects(this)) {
				 this.deactivate();
			}
		}
	
	}

}
