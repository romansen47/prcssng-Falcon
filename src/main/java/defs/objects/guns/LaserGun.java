package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.Laser;
import main.Main;

public class LaserGun extends PlainGun {

	public LaserGun(Flyer fly) {
		super(fly);
		setVerbrauch(6);
	}

	@Override
	public void shoot(Main main) {
		main.addOnTop(new Laser(main, getFlyer().getX(), getFlyer().getY()));
	}
}
