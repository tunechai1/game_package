package edu.utc.game;
import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

public  class GameObject {
	
	private boolean active=true;
	
	protected float r;
	protected float g;
	protected float b;

	protected Rectangle hitBox =new Rectangle();

	protected void setColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}// end method
	
	public boolean isActive() { return active; }// end method

	public void deactivate() { active=false; }// end method
	
	public boolean intersects(GameObject other)
	{
		return hitBox.intersects(other.hitBox);
	}// end method
	
	public Rectangle intersection(GameObject other)
	{
		return hitBox.intersection(other.hitBox);
	}// end method
	
    public void update(int delta) { }// end method
    
    public void draw() { 
    	
    	GL11.glColor3f(r, g, b);
    	
    	float x=(float) hitBox.getX();
    	float y=(float) hitBox.getY();
    	float width=(float) hitBox.getWidth();
    	float height=(float) hitBox.getHeight();
    	

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+width, y);
        GL11.glVertex2f(x+width, y+height);
        GL11.glVertex2f(x, y+height);
        GL11.glEnd();
    }// end method
}// end class
