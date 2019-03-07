package defs.objects.flyer;

import java.util.Random;

import defs.objects.benefit.LaserGunBenefit;
import defs.objects.explosions.Explosion;
import defs.objects.guns.AimingGun;
import main.Main;

public class StarDestroyer extends EnemyFlyer {

	private static boolean firstDestroyer = true;

	public static boolean isFirstDestroyer() {
		return StarDestroyer.firstDestroyer;
	}

	public static void setFirstDestroyer(boolean firstDestroyer) {
		StarDestroyer.firstDestroyer = firstDestroyer;
	}

	public StarDestroyer(Main main) {
		super(main);
		this.setMaxHealth(4000);
		this.setMaxMuni(4000);
		main.setBoss(this);
		this.setHealth(4000);
		this.setX(main.Width / 2);
		this.setSize(150 * main.Height / 1080);
		this.setY(-200 * main.Height / 1080);
		this.setGun(new AimingGun(this));

	}

	@Override
	public void draw(Main main) {
		main.shape(main.getStardestroyer(), this.getX() - ((int) (0.5 * this.getSize())), this.getY(), this.getSize(),
				this.getSize());
		main.fill(255 * (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth(),
				255 * this.getHealth() / this.getMaxHealth(), 0);
		main.rect(this.getX() - ((int) (0.5 * this.getSize())), this.getY() - 10,
				(int) ((double) this.getHealth() / this.getMaxHealth() * this.getSize()), 13 * main.Height / 1080);
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - 2 * hit);
		if (this.getHealth() < 1) {
			this.selfDestroy(main);
			new LaserGunBenefit(main, this.getX(), this.getY(), 100, 2000);
		}
	}

	@Override
	public void move(Main main) {
		if (this.getY() < 1 * this.getSize()) {
			this.setY(this.getY() + 1);
		}
		if ((new Random()).nextInt(70) < 1) {
			this.shoot(main);
		}
	}

	@Override
	public void selfDestroy(Main main) {
		super.selfDestroy(main);
		main.add(new Explosion(main, this.getX() + (int) (0.5 * this.getSize()), this.getY() + this.getSize() + 10));
		main.setLevel(main.getLevel() + 1);
		main.setBoss(null);
		main.getChewBacca().trigger();
		((HanSolo) (main.getHanSolo())).setMaxHealth(((HanSolo) (main.getHanSolo())).getMaxHealth() + 100);
		((HanSolo) (main.getHanSolo())).setMaxMuni(((HanSolo) (main.getHanSolo())).getMaxMuni() + 100);
	}

}
