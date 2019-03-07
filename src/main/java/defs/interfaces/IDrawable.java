package defs.interfaces;

import main.Main;

public interface IDrawable {

	void draw(Main main);

	default double[] getPosition() {
		return null;
	}

	int getSize();

	int getX();

	int getY();

	void gotHit(Main main, int i);

	void move(Main main);

	void selfDestroy(Main main);

	void setSize(int size);

	void setX(int x);

	void setY(int y);
}
