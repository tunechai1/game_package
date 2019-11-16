package edu.utc.game;

public class Tile extends GameObject
{
    private boolean collidable = false;
    private Texture texture;
    private XYPair<Float> start_render_p;
    private XYPair<Float> end_render_p;

    public Tile(XYPair<Float> start_render_p, XYPair<Float> end_render_p, Texture texture)
    {
        this.start_render_p = start_render_p;
        this.end_render_p = end_render_p;
        this.texture = texture;
    }

    @Override
    public void draw()
    {
        texture.setRenderStartP(start_render_p);
        texture.setRenderEndP(end_render_p);
        texture.draw(this);
    }

    public void setCollidable(){ collidable = !collidable; }

    public boolean isCollidable() { return collidable; }

    public String toString()
    {
        return "Start point: " + start_render_p + " end point: " + end_render_p + " collidable: " + collidable;
    }
}
