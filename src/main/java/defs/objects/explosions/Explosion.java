package defs.objects.explosions;

import defs.interfaces.IDrawable;
import main.Main;

public class Explosion implements IDrawable {

	private int frames;
	// private PImage image;
	private int X, Y;

	public Explosion(Main main, int x, int y) {
		// image = main.getExplosion();
		this.setFrames(main.getFrameCount());
		this.setX(x);
		this.setY(y);
		main.getExplosionSound().trigger();
	}

	@Override
	public void draw(Main main) {
		final double expSize = 10;
		if ((main.getFrameCount() - this.getFrames()) < expSize) {
			main.shape(main.getExplosion(), ((int) (this.getX() - this.getSize() / 5.0)), this.getY() - 5,
					(int) (0.05
							* Math.sqrt(
									this.getFrames() / expSize * (main.getFrameCount() - this.getFrames()) / expSize)
							* this.getSize()),
					(int) (0.05
							* Math.sqrt(
									this.getFrames() / expSize * (main.getFrameCount() - this.getFrames()) / expSize)
							* this.getSize()));
		} else {
			this.selfDestroy(main);
		}
	}

	public int getFrames() {
		return this.frames;
	}

	@Override
	public int getSize() {
		return 150;
	}

	@Override
	public int getX() {
		return this.X;
	}

	@Override
	public int getY() {
		return this.Y;
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
			this.finalize();
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
		this.X = x;
	}

	@Override
	public void setY(int y) {
		this.Y = y;
	}

}
