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

	private int maxHealth = 60, maxShield = 100, maxMuni = 2000;
	private int health, shield;
	private int muni = 2000;

	private PImage image;

	public Flyer() {
	}

	public Flyer(Main main, int x, int y) {

		setX(x);
		setY(y);
		setSize(50 * main.Height / 1080);
		setGun(new PlainGunEnemy(this));
		setHealth(maxHealth);
		setShield(maxShield);
		int tmpx, tmpy, TMPX = getX(), TMPY = getY();
		if (getX() < main.Width / 2) {
			tmpx = -100 * main.Width / 1920;
		} else {
			tmpx = main.Width + 100 * main.Width / 1920;
		}
		tmpy = -100 * main.Height / 1080;
		for (int i = 0; i < 100; i++) {
			setX((int) (tmpx + i / 500.0 * (TMPX - tmpx)));
			setY((int) (tmpy + i / 500.0 * (TMPY - tmpy)));
			draw(main);
		}
	}

	public void setGun(IShooting g) {
		this.Gun = g;
	}

	@Override
	public int getX() {
		return (int) (getPosition()[0]);
	}

	@Override
	public void setX(int x) {
		getPosition()[0] = x;
	}

	@Override
	public int getY() {
		return (int) (getPosition()[1]);
	}

	@Override
	public void setY(int y) {
		getPosition()[1] = y;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = Math.max(Math.min(health, getMaxHealth()), 0);
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = Math.max(Math.min(shield, getMaxShield()), 0);
	}

	@Override
	public void draw(Main main) {

		main.shape(main.getFlyer(), getX() - (int) (0.5 * getSize()), getY() - ((int) (0.5 * getSize())), getSize(),
				getSize());

		if (getShield() > 0) {
			main.stroke((int) (255. * (maxShield - shield) / maxShield), (int) (255. * shield / maxShield), 0);
			main.noFill();
			main.ellipse(getX(), getY(), 2 * getSize(), 2 * getSize());
		}

		// main.stroke((int)(255.*(maxHealth-health)/maxHealth),(int)(255.*health/maxHealth),0);
		main.fill((int) (255.0 * (maxHealth - health) / maxHealth), (int) (255.0 * health / maxHealth), 0);
		main.rect(getX() - ((int) (0.5 * getSize())), getY() - ((int) (0.5 * getSize())),
				(int) (1.0 * health / maxHealth * getSize()), 8);
		// main.stroke(0);
	}

	public void shoot(Main main) {
		if (this.getMuni() > ((PlainGun) getGun()).getVerbrauch()) {
			getGun().shoot(main);
		}
		this.setMuni(Math.max(0, this.getMuni() - (getGun()).getVerbrauch()));
	}

	public PImage getImage() {
		return image;
	}

	public void setImage(PImage image) {
		this.image = image;
	}

	@Override
	public int getSize() {
		return this.getRadius();
	}

	@Override
	public void setSize(int size) {
		this.setRadius(size);
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - 3 * hit);
		if (this.getHealth() < 1) {
			((HanSolo) main.getHanSolo()).addScore(100);
			main.add(new Explosion(main, getX() + (int) (0.5 * getSize()), getY() + (int) (0.0 * getSize())));
			selfDestroy(main);
			Main.getStatistic().setKills(Main.getStatistic().getKills() + 1);
			if ((new Random()).nextInt(3) == 0) {
				@SuppressWarnings("unused")
				Benefit tmp = new Benefit(main, getX(), getY(), 70, 50);
			}
		} else {
			((HanSolo) main.getHanSolo()).addScore(5);
		}
	}

	public int getMuni() {
		return muni;
	}

	public void setMuni(int muni) {
		this.muni = Math.max(0, Math.min(muni, getMaxMuni()));
	}

	public IShooting getGun() {
		return Gun;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMaxShield() {
		return maxShield;
	}

	public void setMaxShield(int maxShield) {
		this.maxShield = maxShield;
	}

	public int getMaxMuni() {
		return maxMuni;
	}

	public void setMaxMuni(int maxMuni) {
		this.maxMuni = maxMuni;
	}

	public void selfDestroy(Main main) {
		main.remove(this);
		main.removeEnemy(main.getEnemyIndex(this));
		try {
			this.finalize();
			// System.out.println(this.toString()+" has been removed!");
		} catch (Throwable e) {
			e.printStackTrace();
			// System.out.println(this.toString()+" could not be removed!");
		}
	}

	public double getSpeedX() {
		return getVelocity()[0];
	}

	public void setSpeedX(double speedX) {
		this.getVelocity()[0] = speedX;
	}

	public double getSpeedY() {
		return getVelocity()[1];
	}

	public void setSpeedY(double speedY) {
		this.getVelocity()[1] = speedY;
	}

}
