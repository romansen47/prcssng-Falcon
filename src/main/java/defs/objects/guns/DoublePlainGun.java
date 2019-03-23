package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.Bullet;
import main.Main;

public class DoublePlainGun extends PlainGun {

	public DoublePlainGun(Flyer fly) {
		super(fly);
		setVerbrauch(6);
	}

	@Override
	public void shoot(Main main) {
		main.add(new Bullet(main, getFlyer().getX() - (int) (0.5 * getFlyer().getSize()) + getFlyer().getSize() / 3,
				getFlyer().getY() - 20, (int) getFlyer().getSpeedX(), 20));
		main.add(new Bullet(main,
				getFlyer().getX() - (int) (0.5 * getFlyer().getSize()) + 2 * (getFlyer().getSize() / 3),
				getFlyer().getY() - 20, (int) getFlyer().getSpeedX(), 20));
		main.getPlainGunSound().trigger();
	}
}
