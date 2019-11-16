package edu.utc.game;

import java.util.ArrayList;
import java.util.Arrays;

public class TileSheet
{
    public ArrayList<ArrayList> renderPoints;
    public Texture tileImg;
    public int numRows;
    public int numColumns;

    public TileSheet(Texture tileImg, int numRows, int numColumns)
    {
        renderPoints = new ArrayList<>();
        this.tileImg = tileImg;
        this.numRows = numRows;
        this.numColumns = numColumns;

        extractTiles();
    }

    private void extractTiles()
    {
        for(int row = 0; row < this.numRows; row++)
        {
            for (int col = 0; col < this.numColumns; col++)
            {

                XYPair<Object> startPoint = new XYPair<>(
                        (float) col / (float) numColumns,
                        (float)row / (float)numRows
                );

                XYPair<Object> endPoint = new XYPair<>(
                        (float)(col + 1) / (float)(numColumns),
                        (float)(row + 1) / (float)(numRows)
                );

                ArrayList tilePoints = new ArrayList();
                tilePoints.addAll(Arrays.asList(startPoint, endPoint));
                renderPoints.add(tilePoints);
            }
        }
    }

    public ArrayList<ArrayList> getRenderPoints()
    {
        return renderPoints;
    }

    public Texture getTileImg()
    {
        return tileImg;
    }

    public Tile getTile(int[] tileIndices)
    {
        XYPair<Float> start = (XYPair<Float>) renderPoints.get(tileIndices[0]).get(0);
        XYPair<Float> end = (XYPair<Float>) renderPoints.get(tileIndices[tileIndices.length - 1]).get(1);
        return new Tile(start, end, tileImg);
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }
}
