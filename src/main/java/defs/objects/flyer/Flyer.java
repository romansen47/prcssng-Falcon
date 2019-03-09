package defs.objects.flyer;

import java.util.Random;

import defs.interfaces.IDrawable;
import defs.interfaces.IShooting;
import defs.objects.benefit.Benefit;
import defs.objects.explosions.Explosion;
import defs.objects.guns.PlainGun;
import defs.objects.guns.PlainGunEnemy;
import main.Main;
import processing.core.PImage;
import temperature.Ball;

public abstract class Flyer extends Ball implements IDrawable {

	// private IShooting[] Guns;
	private IShooting Gun;

	private int health, shield;
	private PImage image;
	private int maxHealth = 60, maxShield = 100, maxMuni = 2000;

	private int muni = 2000;

	public Flyer() {
	}

	public Flyer(Main main, int x, int y) {

		this.setX(x);
		this.setY(y);
		this.setSize(50 * main.Height / 1080);
		this.setGun(new PlainGunEnemy(this));
		this.setHealth(this.maxHealth);
		this.setShield(this.maxShield);
		int tmpx, tmpy;
		final int TMPX = this.getX(), TMPY = this.getY();
		if (this.getX() < main.Width / 2) {
			tmpx = -100 * main.Width / 1920;
		} else {
			tmpx = main.Width + 100 * main.Width / 1920;
		}
		tmpy = -100 * main.Height / 1080;
		for (int i = 0; i < 100; i++) {
			this.setX((int) (tmpx + i / 500.0 * (TMPX - tmpx)));
			this.setY((int) (tmpy + i / 500.0 * (TMPY - tmpy)));
			this.draw(main);
		}
	}

	@Override
	public void draw(Main main) {

		main.shape(main.getFlyer(), this.getX() - (int) (0.5 * this.getSize()),
				this.getY() - ((int) (0.5 * this.getSize())), this.getSize(), this.getSize());

		if (this.getShield() > 0) {
			main.stroke((int) (255. * (this.maxShield - this.shield) / this.maxShield),
					(int) (255. * this.shield / this.maxShield), 0);
			main.noFill();
			main.ellipse(this.getX(), this.getY(), 2 * this.getSize(), 2 * this.getSize());
		}

		// main.stroke((int)(255.*(maxHealth-health)/maxHealth),(int)(255.*health/maxHealth),0);
		main.fill((int) (255.0 * (this.maxHealth - this.health) / this.maxHealth),
				(int) (255.0 * this.health / this.maxHealth), 0);
		main.rect(this.getX() - ((int) (0.5 * this.getSize())), this.getY() - ((int) (0.5 * this.getSize())),
				(int) (1.0 * this.health / this.maxHealth * this.getSize()), 8);
		// main.stroke(0);
	}

	public IShooting getGun() {
		return this.Gun;
	}

	public int getHealth() {
		return this.health;
	}

	public PImage getImage() {
		return this.image;
	}

	public int getMaxHealth() {
		return this.maxHealth;
	}

	public int getMaxMuni() {
		return this.maxMuni;
	}

	public int getMaxShield() {
		return this.maxShield;
	}

	public int getMuni() {
		return this.muni;
	}

	public int getShield() {
		return this.shield;
	}

	@Override
	public int getSize() {
		return this.getRadius();
	}

	public double getSpeedX() {
		return this.getVelocity()[0];
	}

	public double getSpeedY() {
		return this.getVelocity()[1];
	}

	@Override
	public int getX() {
		return (int) (this.getPosition()[0]);
	}

	@Override
	public int getY() {
		return (int) (this.getPosition()[1]);
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - 3 * hit);
		if (this.getHealth() < 1) {
			((HanSolo) main.getHanSolo()).addScore(100);
			main.add(new Explosion(main, this.getX() + (int) (0.5 * this.getSize()),
					this.getY() + (int) (0.0 * this.getSize())));
			this.selfDestroy(main);
			Main.getStatistic().setKills(Main.getStatistic().getKills() + 1);
			if ((new Random()).nextInt(3) == 0) {
				new Benefit(main, this.getX(), this.getY(), 70, 50);
			}
		} else {
			((HanSolo) main.getHanSolo()).addScore(5);
		}
	}

	@Override
	public void selfDestroy(Main main) {
		main.remove(this);
		main.removeEnemy(main.getEnemyIndex(this));
		try {
			this.finalize();
		} catch (final Throwable e) {
			e.printStackTrace();
			// TODO
		}
	}

	public void setGun(IShooting g) {
		this.Gun = g;
	}

	public void setHealth(int health) {
		this.health = Math.max(Math.min(health, this.getMaxHealth()), 0);
	}

	public void setImage(PImage image) {
		this.image = image;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setMaxMuni(int maxMuni) {
		this.maxMuni = maxMuni;
	}

	public void setMaxShield(int maxShield) {
		this.maxShield = maxShield;
	}

	public void setMuni(int muni) {
		this.muni = Math.max(0, Math.min(muni, this.getMaxMuni()));
	}

	public void setShield(int shield) {
		this.shield = Math.max(Math.min(shield, this.getMaxShield()), 0);
	}

	@Override
	public void setSize(int size) {
		this.setRadius(size);
	}

	public void setSpeedX(double speedX) {
		this.getVelocity()[0] = speedX;
	}

	public void setSpeedY(double speedY) {
		this.getVelocity()[1] = speedY;
	}

	@Override
	public void setX(int x) {
		this.getPosition()[0] = x;
	}

	@Override
	public void setY(int y) {
		this.getPosition()[1] = y;
	}

	public void shoot(Main main) {
		if (this.getMuni() > ((PlainGun) this.getGun()).getVerbrauch()) {
			this.getGun().shoot(main);
		}
		this.setMuni(Math.max(0, this.getMuni() - (this.getGun()).getVerbrauch()));
	}

}
