package defs.objects.benefit;

import defs.objects.flyer.HanSolo;
import defs.objects.flyer.StarDestroyer;
import defs.objects.guns.LaserGun;
import main.Main;

public class LaserGunBenefit extends Benefit {

	public LaserGunBenefit(Main main, int x, int y, int size, int health) {
		super(main, x, y, size, health);
		if (StarDestroyer.isFirstDestroyer()) {
			StarDestroyer.setFirstDestroyer(false);
			main.setMessage(new String[5]);
			main.getMessage()[0]	= "";
			main.getMessage()[1]	= "Achtung!";
			main.getMessage()[3]	= "Ersatzteile dieser Sternenzerstörer verloren hat";
			main.getMessage()[4]	= "R2D2 sie an den Millenium Falken montieren kann...";
			main.getMessage()[2]	= "";
			main.getYodaObj().setMessage(main.getMessage());
			main.getYodaObj().setLevel(0);
			main.setPaused(true);
		}
	}

	@Override
	public void move(Main main) {
		setY(getY() + 4);
		if (main.getHanSolo().getY() <= getY() + getSize() && main.getHanSolo().getX() < getX() + getSize()
				&& main.getHanSolo().getX() + main.getHanSolo().getSize() > getX()
				&& main.getHanSolo().getY() + main.getHanSolo().getSize() > getY()) {
			main.remove(this);
			((HanSolo) (main.getHanSolo())).addGun(new LaserGun((HanSolo) (main.getHanSolo())));
		}
	}
}
