package edu.utc.game;

public class Animation
{
    private Texture[] frames;
    private Texture currentFrame;
    private int elapsedTime = 0;
    private int frameLengthMs;
    private int pointer = 0;
    private float renderWidth;
    private float renderHeight;

    public Animation(Texture[] frames, int frameLengthMs)
    {
        this.frames = frames;
        this.frameLengthMs = frameLengthMs;
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
        if (elapsedTime > frameLengthMs)
        {
            elapsedTime = 0;
            this.pointer = (this.pointer == frames.length - 1) ? 0: this.pointer + 1;
            this.currentFrame = frames[pointer];
        }
        return this.currentFrame;
    }

    public Texture[] getFrames() {
        return frames;
    }

    public void setFrames(Texture[] frames) {
        this.frames = frames;
    }

    public Texture getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(Texture currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getFrameLengthMs() {
        return frameLengthMs;
    }

    public void setFrameLengthMs(int frameLengthMs) {
        this.frameLengthMs = frameLengthMs;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
}// end class
