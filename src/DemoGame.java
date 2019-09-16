import edu.utc.game.Game;
import edu.utc.game.GameObject;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import java.awt.*;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;
import edu.utc.game.Scene;

enum playerRelation
{
	LEFT, RIGHT, ABOVE, BELOW
}

public class DemoGame extends Game implements Scene {
	
	private static java.util.Random rand=new java.util.Random();
	
	public static void main(String[] args)
	{
		// construct a DemoGame object and launch the game loop
		DemoGame game = new DemoGame();
		game.gameLoop();
	}

	// DemoGame instance data
	
	List<GameObject> targets;
	Player player;
	
	public DemoGame()
	{
		// inherited from the Game class, this sets up the window and allows us to access
		// Game.ui
		initUI(640, 480, "DemoGame");

		// screen clear is white (this could go in drawFrame if you wanted it to change
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		targets = new java.util.LinkedList<GameObject>();
		
		player = new Player();
		spawnTargets(10);
	}// end constructor
	
	public void spawnTargets(int count)
	{
		float r = rand.nextFloat()*0.5f+0.25f;
		float g = rand.nextFloat()*0.5f+0.25f;
		float b = rand.nextFloat()*0.5f+0.25f;
		
		for (int i=0; i<count; i++)
		{
			targets.add(new Target(player, r, g, b));
		}
	}// end method
	
	
	public Scene drawFrame(int delta) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		player.update(delta);

		// update all targets and player object
		for (GameObject o : targets)
		{
			o.update(delta);
		}
		
		// check for deactivated objects
		Iterator<GameObject> it = targets.iterator();
		while (it.hasNext()) {
			GameObject o = it.next();
			if (! o.isActive())
			{
				it.remove();
			}
		}
		
		// if all targets have been destroyed, spawn some more
		if (targets.isEmpty()) {
			spawnTargets(10);
		}
		
		// draw existing targets
		for (GameObject o : targets)
		{
			o.draw();
		}
		
		// draw the player last so it will appear on top of targets
		player.draw();
		
		return this;
	}// end method

	private class Player extends GameObject
	{
		private Point prevPosition;

		public Player()
		{
			this.hitbox.setSize(10, 10);
			this.hitbox.setLocation(Game.ui.getWidth()/2-5, Game.ui.getHeight()/2-5);
			this.setColor(1,0,0);
		}// end constructor

		public Point getPrevPosition() { return prevPosition; }
		// this allows you to steer the player object
		public void update(int delta)
		{
			prevPosition = hitbox.getLocation();
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
		}// end method
	}// end class
	
	private class Target extends GameObject
	{
		private Player player;
		private int size=50;
		private playerRelation playerRelPos;
		private int pushForce = 10;
		// construct a target in a random location within the bounds of the UI
		public Target(Player p, float r, float g, float b)
		{
			this.player = p;
			this.hitbox.setSize(size, size);
			this.setColor(r,g,b);
			this.hitbox.setLocation(
					(int)(rand.nextFloat()*Game.ui.getWidth()),
					(int)(rand.nextFloat()*Game.ui.getHeight()));
			//System.out.println(this.hitbox);
		}// end constructor

		// if the space key is pressed, check to see if we should deactivate this target
		public void update(int delta)
		{
			setPlayerRelation();
			if (player.intersects(this))
			{
				player.getHitbox().setLocation(player.getPrevPosition());
				Point playerPos = player.getHitbox().getLocation();
				switch (playerRelPos)
				{
					case ABOVE:
						hitbox.translate(0, pushForce);
						break;
					case BELOW:
						hitbox.translate(0, -pushForce);
						break;
					case LEFT:
						hitbox.translate(pushForce, 0);
						break;
					case RIGHT:
						hitbox.translate(-pushForce, 0);
						break;
				}
			}
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE) && player.intersects(this)) {
				 this.deactivate();
			}
		}// end method

		public void setPlayerRelation()
		{
			Point playerPos = player.getHitbox().getLocation();
			Point objPos = getHitbox().getLocation();
			if (playerPos.getY() < objPos.getY())
			{
				playerRelPos = playerRelation.ABOVE;
				return;
			}
			if (playerPos.getY() > objPos.getY() + getHitbox().getHeight())
			{
				playerRelPos = playerRelation.BELOW;
				return;
			}
			if (playerPos.getX() < objPos.getX())
			{
				playerRelPos = playerRelation.LEFT;
				return;
			}
			if (playerPos.getX() > objPos.getX() + hitbox.getWidth())
			{
				playerRelPos = playerRelation.RIGHT;
				return;
			}
		}//end method
	}// end class
}// end class
