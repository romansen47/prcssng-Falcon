package defs.objects.flyer;

import defs.objects.benefit.Benefit;
import defs.objects.benefit.DoubleLaserGunBenefit;
import defs.objects.explosions.Explosion;
import main.Main;

public class ThirdStarDestroyer extends StarDestroyer {

	public ThirdStarDestroyer(Main main) {
		super(main);
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - 2 * hit);
		if (this.getHealth() < 1) {
			main.add(new Explosion(main, getX() + (int) (0.5 * getSize()), getY() + getSize() + 10));
			main.remove(this);
			main.setLevel(main.getLevel() + 1);
			main.setBoss(null);
			@SuppressWarnings("unused")
			Benefit tmp = new DoubleLaserGunBenefit(main, getX(), getY(), 100, 2000);
			main.getChewBacca().trigger();
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

}
