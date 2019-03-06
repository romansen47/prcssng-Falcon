package defs.objects.munition;

import java.util.Random;

import defs.interfaces.IDrawable;
import defs.objects.explosions.Explosion;
import main.Main;

public class Asteroid extends Bullet {

	Random r = new Random();

	public Asteroid(Main main) {
		super(main, 0, 0, 0, 0);
		setSize(((25 + r.nextInt(40)) * main.Height) / 1080);
		setX(((new Random()).nextInt((main.Width))));
		setY(1);
		setSpeedY(-5 - r.nextInt(10 * main.Height / 1080));
		setSchaden(3);
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getAsteroid(), (float) getX() - ((int) (0.5 * getSize())), getY(), ((int) (0.5 * getSize())),
				((int) (0.5 * getSize())));
	}

	@Override
	public void move(Main main) {
		setY(getY() - getSpeedY());
		if (getY() < 0 || getY() > main.Height) {
			selfDestroy(main);
		}
		for (IDrawable obj : main.getObjects()) {
			if (obj != this && checkForImpact(obj) != null) {
				obj.gotHit(main, getSchaden());
				main.add(new Explosion(main, this.getX(), obj.getY() + obj.getSize()));
//				try {
//					this.finalize();
//				} catch (Throwable e) {
//				}
				selfDestroy(main);
			}
		}
	}

}
