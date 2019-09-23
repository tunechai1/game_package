package edu.utc.game;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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
        
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    	// Can call "alc" functions at any time
    	long device = ALC10.alcOpenDevice((ByteBuffer)null);
    	ALCCapabilities deviceCaps = ALC.createCapabilities(device);

    	long context = ALC10.alcCreateContext(device, (IntBuffer)null);
    	ALC10.alcMakeContextCurrent(context);
    	AL.createCapabilities(deviceCaps);    	
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
