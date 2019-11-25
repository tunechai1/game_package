package edu.utc.game;

public class Animation
{
    private Texture frames;
    private int frameLengthMs;
    private int currentFrame = 0;
    private int elapsedTime = 0;
    private int numFrameSheetRows;
    private int numFrameSheetCols;
    private int[] frameIDs;
    private TileSheet animationSheet;

    public Animation(Texture frames, TileSheet animationSheet, int[] frameIDs,int frameLengthMs)
    {
        this.animationSheet = animationSheet;
        this.frameIDs = frameIDs;
        numFrameSheetRows = animationSheet.getNumRows();
        numFrameSheetCols = animationSheet.getNumColumns();
        this.frames = frames;
        this.frameLengthMs = frameLengthMs;
        frames.setRenderStartP(new XYPair<>(0f, 0f));
        frames.setRenderEndP(new XYPair<>(1f / numFrameSheetCols, 1f / numFrameSheetRows));
    }// end constructor

    public int play(int delta)
    {
        elapsedTime += delta;
        if (elapsedTime > frameLengthMs)
        {
            elapsedTime = 0;
            currentFrame = (currentFrame >= frameIDs.length - 1)? 0: currentFrame + 1;
            //frames.setRenderStartP((XYPair<Float>) animationSheet.getRenderPoints().get(frameIDs[currentFrame]).get(0));
            //frames.setRenderEndP((XYPair<Float>) animationSheet.getRenderPoints().get(frameIDs[currentFrame]).get(1));
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
        frames.setRenderStartP((XYPair<Float>) animationSheet.getRenderPoints().get(frameIDs[currentFrame]).get(0));
        frames.setRenderEndP((XYPair<Float>) animationSheet.getRenderPoints().get(frameIDs[currentFrame]).get(1));
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

    public void draw(GameObject object)
    {
        setCurrentFrame(currentFrame);
        frames.draw(object);
    }
}// end class
