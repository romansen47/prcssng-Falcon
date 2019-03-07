package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.Bullet;
import main.Main;

public class DoublePlainGun extends PlainGun {

	public DoublePlainGun(Flyer fly) {
		super(fly);
		this.setVerbrauch(6);
	}

	@Override
	public void shoot(Main main) {
		main.add(new Bullet(main,
				this.getFlyer().getX() - (int) (0.5 * this.getFlyer().getSize()) + this.getFlyer().getSize() / 3,
				this.getFlyer().getY() - 20, (int) this.getFlyer().getSpeedX(), 20));
		main.add(new Bullet(main,
				this.getFlyer().getX() - (int) (0.5 * this.getFlyer().getSize()) + 2 * (this.getFlyer().getSize() / 3),
				this.getFlyer().getY() - 20, (int) this.getFlyer().getSpeedX(), 20));
		main.getPlainGunSound().trigger();
	}
}
