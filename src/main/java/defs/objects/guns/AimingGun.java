package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.AimedBullet;
import main.Main;

public class AimingGun extends PlainGun {

	public AimingGun(Flyer fly) {
		super(fly);
		setVerbrauch(50);
	}

	@Override
	public void shoot(Main main) {
		main.add(new AimedBullet(main, getFlyer().getX() - (int) (0.05 * getFlyer().getSize()),
				getFlyer().getY() + getFlyer().getSize() + 20));

		main.getPlainGunSound().trigger();
	}

}
