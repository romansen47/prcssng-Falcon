package defs.interfaces;

import main.Main;

public interface IDrawable {

	public void draw(Main main);
	
	public void setX(int x);
	
	public void setY(int y);
	
	public int getX();
	
	public int getY();
	
	public int getSize();
	
	public void setSize(int size);
	
	public void move(Main main);

	public void gotHit(Main main,int i);
	
	default public double[] getPosition() {return null;};
	
}
