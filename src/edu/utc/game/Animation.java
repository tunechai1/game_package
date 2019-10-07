package edu.utc.game;

public class Animation
{
    private Texture[] frames;
    private Texture currentFrame;
    private int elapsedTime = 0;
    private int framesPerSecond = 1;
    private int pointer = 0;

    public Animation(Texture[] frames, int framesPerSecond)
    {
        this.frames = frames;
        this.framesPerSecond = framesPerSecond;
        try
        {
            this.currentFrame = frames[pointer];
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }// end constructor

    public Texture play(int delta)
    {
        System.out.println(elapsedTime);
        elapsedTime += delta;
        System.out.println("pointer:" + pointer);
        if (elapsedTime / 1000 > framesPerSecond)
        {
            elapsedTime = 0;
            this.pointer = (this.pointer == frames.length - 1) ? 0: this.pointer + 1;
            this.currentFrame = frames[pointer];
        }
        return this.currentFrame;
    }

    // public Texture getCurrentFrame(){ return this.currentFrame; }
}// end class
