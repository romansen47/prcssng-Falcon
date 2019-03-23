package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.Bullet;
import main.Main;

public class PlainGunEnemy extends PlainGun {

	public PlainGunEnemy(Flyer fly) {
		super(fly);
	}

	@Override
	public void shoot(Main main) {
		final Bullet blt = new Bullet(main, getFlyer().getX() + (int) (0.4 * getFlyer().getSize()),
				getFlyer().getY() + getFlyer().getSize() + 20, (int) getFlyer().getSpeedX(), 15);
		blt.setSpeedY(-blt.getSpeedY());
		main.add(blt);
		main.getPlainGunSound().trigger();
	}

}
