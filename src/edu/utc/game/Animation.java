package edu.utc.game;

public class Animation
{
    private Texture frames;
    private int frameLengthMs;
    private int framesInSheet;
    private int currentFrame = 0;
    private int elapsedTime = 0;

    public Animation(Texture frames, int framesInSheet, int frameLengthMs)
    {
        this.framesInSheet = framesInSheet;
        this.frames = frames;
        this.frameLengthMs = frameLengthMs;
        frames.setRenderStartP(new XYPair<>(0f, 0f));
        frames.setRenderEndP(new XYPair<>((1f / framesInSheet), 1f));
    }// end constructor

    public int play(int delta)
    {
        System.out.println(elapsedTime);
        elapsedTime += delta;
        if (elapsedTime > frameLengthMs)
        {
            elapsedTime = 0;
            currentFrame = (currentFrame >= framesInSheet - 1)? 0: currentFrame + 1;
            frames.setRenderStartP(new XYPair<>((1f / framesInSheet) * currentFrame, 0f));
            frames.setRenderEndP(new XYPair<>((1f / framesInSheet) * (currentFrame + 1), 1f));
        }
        return this.currentFrame;
    }// end method

    public Texture getFrames() {
        return frames;
    }// end method

    public void setFrames(Texture frames) {
        this.frames = frames;
    }// end method

    public int getCurrentFrame() {
        return currentFrame;
    }// end method

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }// end method

    public int getElapsedTime() {
        return elapsedTime;
    }// end method

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }// end method

    public int getFrameLengthMs() {
        return frameLengthMs;
    }// end method

    public void setFrameLengthMs(int frameLengthMs) {
        this.frameLengthMs = frameLengthMs;
    }// end method
}// end class
