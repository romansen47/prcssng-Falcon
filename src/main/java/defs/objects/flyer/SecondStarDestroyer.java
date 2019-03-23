package defs.objects.flyer;

import defs.objects.benefit.DoublePlainGunBenefit;
import defs.objects.explosions.Explosion;
import main.Main;

public class SecondStarDestroyer extends StarDestroyer {

	public SecondStarDestroyer(Main main) {
		super(main);
	}

	@Override
	public void gotHit(Main main, int hit) {
		setHealth(getHealth() - 2 * hit);
		if (getHealth() < 1) {
			main.add(new Explosion(main, getX() + (int) (0.5 * getSize()), getY() + getSize() + 10));
			main.setLevel(main.getLevel() + 1);
			main.setBoss(null);
			new DoublePlainGunBenefit(main, getX(), getY(), 100, 2000);
			main.getChewBacca().trigger();
			selfDestroy(main);
		}
	}

}
