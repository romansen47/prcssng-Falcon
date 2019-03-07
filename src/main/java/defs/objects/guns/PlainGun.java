package defs.objects.guns;

import defs.interfaces.IShooting;
import defs.objects.flyer.Flyer;
import defs.objects.munition.Bullet;
import main.Main;

public class PlainGun implements IShooting {

	private Flyer flyer;
	private int Verbrauch;

	public PlainGun(Flyer fly) {
		this.setFlyer(fly);
		this.setVerbrauch(2);
	}

	public Flyer getFlyer() {
		return this.flyer;
	}

	@Override
	public int getVerbrauch() {
		return this.Verbrauch;
	}

	public void setFlyer(Flyer flyer) {
		this.flyer = flyer;
	}

	public void setVerbrauch(int verbrauch) {
		this.Verbrauch = verbrauch;
	}

	@Override
	public void shoot(Main main) {
		main.add(new Bullet(main, this.getFlyer().getX(), this.getFlyer().getY() - 20,
				(int) this.getFlyer().getSpeedX(), 20));
		main.getPlainGunSound().trigger();
		this.getFlyer().setMuni(this.getFlyer().getMuni() - this.getVerbrauch());
	}

}
