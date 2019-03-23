package defs.objects.guns;

import defs.interfaces.IShooting;
import defs.objects.flyer.Flyer;
import defs.objects.munition.Bullet;
import main.Main;

public class PlainGun implements IShooting {

	private Flyer	flyer;
	private int		Verbrauch;

	public PlainGun(Flyer fly) {
		setFlyer(fly);
		setVerbrauch(2);
	}

	public Flyer getFlyer() {
		return flyer;
	}

	@Override
	public int getVerbrauch() {
		return Verbrauch;
	}

	public void setFlyer(Flyer flyer) {
		this.flyer = flyer;
	}

	public void setVerbrauch(int verbrauch) {
		Verbrauch = verbrauch;
	}

	@Override
	public void shoot(Main main) {
		main.add(new Bullet(main, getFlyer().getX(), getFlyer().getY() - 20, (int) getFlyer().getSpeedX(), 20));
		main.getPlainGunSound().trigger();
		getFlyer().setMuni(getFlyer().getMuni() - getVerbrauch());
	}

}
