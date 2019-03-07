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
		this.setY(this.getY() + 4);
		if (main.getHanSolo().getY() <= this.getY() + this.getSize()
				&& main.getHanSolo().getX() < this.getX() + this.getSize()
				&& main.getHanSolo().getX() + main.getHanSolo().getSize() > this.getX()
				&& main.getHanSolo().getY() + main.getHanSolo().getSize() > this.getY()) {
			main.remove(this);
			((HanSolo) (main.getHanSolo())).addGun(new SimpleCombiGun((HanSolo) (main.getHanSolo())));
		}
	}

}
