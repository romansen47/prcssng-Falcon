package defs.objects.flyer;

import java.util.Random;

import defs.objects.benefit.Benefit;
import defs.objects.benefit.LaserGunBenefit;
import defs.objects.explosions.Explosion;
import defs.objects.guns.AimingGun;
import main.Main;

public class StarDestroyer extends EnemyFlyer {

	private static boolean firstDestroyer = true;

	public StarDestroyer(Main main) {
		super(main);
		setMaxHealth(4000);
		setMaxMuni(4000);
		main.setBoss(this);
		setHealth(4000);
		setX(main.Width / 2);
		setSize(150 * main.Height / 1080);
		setY(-200 * main.Height / 1080);
		setGun(new AimingGun(this));

	}

	@Override
	public void draw(Main main) {
		main.shape(main.getStardestroyer(), getX() - ((int) (0.5 * getSize())), getY(), getSize(), getSize());
		main.fill(255 * (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth(),
				255 * this.getHealth() / this.getMaxHealth(), 0);
		main.rect(getX() - ((int) (0.5 * getSize())), getY() - 10,
				(int) ((double) this.getHealth() / this.getMaxHealth() * getSize()), 13 * main.Height / 1080);
	}

	@Override
	public void move(Main main) {
		if (this.getY() < 1 * this.getSize()) {
			this.setY(this.getY() + 1);
		}
		if ((new Random()).nextInt(70) < 1) {
			shoot(main);
		}
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - 2 * hit);
		if (this.getHealth() < 1) {
			selfDestroy(main);
			@SuppressWarnings("unused")
			Benefit tmp = new LaserGunBenefit(main, getX(), getY(), 100, 2000);
		}
	}

	public static boolean isFirstDestroyer() {
		return firstDestroyer;
	}

	public static void setFirstDestroyer(boolean firstDestroyer) {
		StarDestroyer.firstDestroyer = firstDestroyer;
	}

	@Override
	public void selfDestroy(Main main) {
		super.selfDestroy(main);
		main.add(new Explosion(main, getX() + (int) (0.5 * getSize()), getY() + getSize() + 10));
		main.setLevel(main.getLevel() + 1);
		main.setBoss(null);
		main.getChewBacca().trigger();
		((HanSolo) (main.getHanSolo())).setMaxHealth(((HanSolo) (main.getHanSolo())).getMaxHealth() + 100);
		((HanSolo) (main.getHanSolo())).setMaxMuni(((HanSolo) (main.getHanSolo())).getMaxMuni() + 100);
	}

}
