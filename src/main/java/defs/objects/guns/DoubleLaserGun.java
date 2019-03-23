package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.Laser;
import main.Main;

public class DoubleLaserGun extends LaserGun {

	public DoubleLaserGun(Flyer fly) {
		super(fly);
		setVerbrauch(14);
	}

	@Override
	public void shoot(Main main) {
		main.add(new Laser(main, getFlyer().getX() - (int) (0.5 * getFlyer().getSize()) + getFlyer().getSize() / 3,
				getFlyer().getY() - 20));
		main.add(
				new Laser(main, getFlyer().getX() - (int) (0.5 * getFlyer().getSize()) + 2 * (getFlyer().getSize() / 3),
						getFlyer().getY() - 20));
	}

}
