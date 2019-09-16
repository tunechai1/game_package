package edu.utc.game;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;


import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class UI {
	
	private long window;
	private int width;
	private int height;
	
	public void init(int width, int height, String title)
	{
		
		
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		

		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		//set up OpenGL
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(1);
		
		// set projection to dimensions of window
        // set viewport to entire window
        GL11.glViewport(0,0,width,height);
         
        // set up orthographic projection to map world pixels to screen
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        this.width=width;
        this.height=height;

	}
	
	public long getWindow() { return window; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public boolean keyPressed(int key) { 
		return glfwGetKey(window, key) == GLFW_PRESS;
	}

}
