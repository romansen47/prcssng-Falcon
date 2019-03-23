package defs.objects.benefit;

import defs.objects.flyer.HanSolo;
import defs.objects.guns.SimpleCombiGun;
import main.Main;

public class SimpleCombiGunBenefit extends Benefit {

	public SimpleCombiGunBenefit(Main main, int x, int y, int size, int health) {
		super(main, x, y, size, health);
	}

	@Override
	public void move(Main main) {
		setY(getY() + 4);
		if (main.getHanSolo().getY() <= getY() + getSize() && main.getHanSolo().getX() < getX() + getSize()
				&& main.getHanSolo().getX() + main.getHanSolo().getSize() > getX()
				&& main.getHanSolo().getY() + main.getHanSolo().getSize() > getY()) {
			main.remove(this);
			((HanSolo) (main.getHanSolo())).addGun(new SimpleCombiGun((HanSolo) (main.getHanSolo())));
		}
	}

}
