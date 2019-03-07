package defs.objects.flyer;

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
			main.add(
					new Explosion(main, this.getX() + (int) (0.5 * this.getSize()), this.getY() + this.getSize() + 10));
			main.setLevel(main.getLevel() + 1);
			main.setBoss(null);
			new DoubleLaserGunBenefit(main, this.getX(), this.getY(), 100, 2000);
			main.getChewBacca().trigger();
			this.selfDestroy(main);
		}
	}

}
