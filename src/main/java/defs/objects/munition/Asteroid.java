package defs.objects.munition;

import java.util.Random;

import defs.interfaces.IDrawable;
import defs.objects.explosions.Explosion;
import main.Main;

public class Asteroid extends Bullet {

	Random r = new Random();

	public Asteroid(Main main) {
		super(main, 0, 0, 0, 0);
		this.setSize(((25 + this.r.nextInt(40)) * main.Height) / 1080);
		this.setX(((new Random()).nextInt((main.Width))));
		this.setY(1);
		this.setSpeedY(-5 - this.r.nextInt(10 * main.Height / 1080));
		this.setSchaden(3);
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getAsteroid(), (float) this.getX() - ((int) (0.5 * this.getSize())), this.getY(),
				((int) (0.5 * this.getSize())), ((int) (0.5 * this.getSize())));
	}

	@Override
	public void move(Main main) {
		this.setY(this.getY() - this.getSpeedY());
		if (this.getY() < 0 || this.getY() > main.Height) {
			this.selfDestroy(main);
		}
		for (final IDrawable obj : main.getObjects()) {
			if (obj != this && this.checkForImpact(obj) != null) {
				obj.gotHit(main, this.getSchaden());
				main.add(new Explosion(main, this.getX(), obj.getY() + obj.getSize()));
//				try {
//					this.finalize();
//				} catch (Throwable e) {
//				}
				this.selfDestroy(main);
			}
		}
	}

}
