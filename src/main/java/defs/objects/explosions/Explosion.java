package defs.objects.explosions;

import defs.interfaces.IDrawable;
import main.Main;

public class Explosion implements IDrawable {

	private int frames;
	// private PImage image;
	private int X, Y;

	public Explosion(Main main, int x, int y) {
		// image = main.getExplosion();
		setFrames(main.getFrameCount());
		setX(x);
		setY(y);
		main.getExplosionSound().trigger();
	}

	@Override
	public void draw(Main main) {
		final double expSize = 10;
		if ((main.getFrameCount() - getFrames()) < expSize) {
			main.shape(main.getExplosion(), ((int) (getX() - getSize() / 5.0)), getY() - 5,
					(int) (0.05 * Math.sqrt(getFrames() / expSize * (main.getFrameCount() - getFrames()) / expSize)
							* getSize()),
					(int) (0.05 * Math.sqrt(getFrames() / expSize * (main.getFrameCount() - getFrames()) / expSize)
							* getSize()));
		} else {
			selfDestroy(main);
		}
	}

	public int getFrames() {
		return frames;
	}

	@Override
	public int getSize() {
		return 150;
	}

	@Override
	public int getX() {
		return X;
	}

	@Override
	public int getY() {
		return Y;
	}

	@Override
	public void gotHit(Main main, int i) {
	}

	@Override
	public void move(Main main) {
	}

	@Override
	public void selfDestroy(Main main) {
		main.remove(this);
		try {
			finalize();
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	@Override
	public void setSize(int size) {
	}

	@Override
	public void setX(int x) {
		X = x;
	}

	@Override
	public void setY(int y) {
		Y = y;
	}

}
