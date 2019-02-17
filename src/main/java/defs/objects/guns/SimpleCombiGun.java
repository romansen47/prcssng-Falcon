package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.Bullet;
import defs.objects.munition.Laser;
import main.Main;

public class SimpleCombiGun extends PlainGun {

	public SimpleCombiGun(Flyer fly) {
		super(fly);
		setVerbrauch(2 * getVerbrauch() + (new LaserGun(fly)).getVerbrauch());
	}

	@Override
	public void shoot(Main main) {
		main.add(new Bullet(main, this.getFlyer().getX() - (int) (0.5 * this.getFlyer().getSize()),
				this.getFlyer().getY() - 20, (int) this.getFlyer().getSpeedX(), 20));
		main.add(new Bullet(main, this.getFlyer().getX() + (int) (0.5 * this.getFlyer().getSize()),
				this.getFlyer().getY() - 20, (int) this.getFlyer().getSpeedX(), 20));
		main.getPlainGunSound().trigger();
		if (main.getFrameCount() % 4 == 0) {
			main.addOnTop(new Laser(main, this.getFlyer().getX(), // +(int)(0.4*this.getFlyer().getSize()),
					this.getFlyer().getY()));
		} else if ((main.getFrameCount() + 2) % 4 == 0) {
			main.addOnTop(new Laser(main, this.getFlyer().getX() + (int) (0.6 * this.getFlyer().getSize()),
					this.getFlyer().getY()));
		}
		getFlyer().setMuni(getFlyer().getMuni() - getVerbrauch());
	}

}
