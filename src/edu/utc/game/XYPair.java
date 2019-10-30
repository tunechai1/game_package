package edu.utc.game;

public class XYPair<T>
{
	
	public T x;
	public T y;
	
	public XYPair(T x, T y)
	{
		this.x = x;
		this.y = y;
	}

	public String toString()
	{
		return "X: " + x + " Y: " + y;
	}
}
