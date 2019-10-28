package edu.utc.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

	private int id;
	private int imgWidth;
	private int imgHeight;
	private XYPair<Float> renderStartP = new XYPair<>((float) 0, (float) 0);
	private XYPair<Float> renderEndP = new XYPair<>((float) 1, (float) 1);

	public Texture(String path)
	{
		try (MemoryStack stack= MemoryStack.stackPush())
		{
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);

			ByteBuffer img=stbi_load(path, w, h, comp, 0);
			if (img == null)
			{
				throw new RuntimeException("failed to load texture: " + stbi_failure_reason());

			}

			imgWidth = w.get();
			imgHeight = h.get();

			id = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,id);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, imgWidth, imgHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
		}
	}// end method

	public int getImgWidth() {
		return imgWidth;
	}// end method

	public int getImgHeight() {
		return imgHeight;
	}// end method

	public XYPair<Float> getRenderStartP() {
		return renderStartP;
	}// end method

	public void setRenderStartP(XYPair<Float> renderStartP) {
		this.renderStartP = renderStartP;
	}// end method

	public XYPair<Float> getRenderEndP() {
		return renderEndP;
	}// end method

	public void setRenderEndP(XYPair<Float> renderEndP) {
		this.renderEndP = renderEndP;
	}// end method

	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,id);
	}// end method

	public static void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
	}// end method

	public void draw(GameObject object)
	{
		// System.out.println("drawing");
		GL11.glColor3f(1,1,1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,  id);

		//System.out.println(textureID + " - " + width + " x " + height);
		Rectangle hitbox = object.getHitbox();

		float x = (float)hitbox.getX();
		float y = (float)hitbox.getY();
		float width = (float)hitbox.getWidth();
		float height = (float)hitbox.getHeight();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(renderStartP.x, renderStartP.y);
		// GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(renderEndP.x, renderStartP.y);
		// GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + width, y);
		GL11.glTexCoord2f(renderEndP.x, renderEndP.y);
		// GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + width, y + height);
		GL11.glTexCoord2f(renderStartP.x, renderEndP.y);
		// GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
	}// end method
}// end class
