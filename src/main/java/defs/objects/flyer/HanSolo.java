package defs.objects.flyer;

import defs.interfaces.IShooting;
import defs.objects.explosions.Explosion;
import defs.objects.explosions.FinalExplosion;
import defs.objects.guns.PlainGun;
import main.Main;
import processing.core.PConstants;
import temperature.Functions;

public final class HanSolo extends Flyer {

	private int Score = 0;

	private IShooting[] Guns;

	public HanSolo(Main main) {
		this.Guns = new IShooting[1];
		this.Guns[0] = new PlainGun(this);
		this.setGun(this.Guns[0]);
		this.setSize(90 * main.Height / 1080);
		this.setX(main.Width / 2);
		this.setY(main.Height - ((int) (1.5 * this.getSize())));
		this.setMaxHealth(400);
		this.setHealth(400);
		this.setMaxMuni(2000);
	}

	public void addGun(IShooting gun) {
		final IShooting[] tmpGuns = new IShooting[this.Guns.length + 1];
		for (int i = 0; i < this.Guns.length; i++) {
			tmpGuns[i] = this.Guns[i];
		}
		tmpGuns[this.Guns.length] = gun;
		this.Guns = tmpGuns;
		this.setGun(gun);
	}

	public void addScore(int n) {
		this.Score = Math.max(0, this.Score + n);
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getHansolo(), this.getX() - (int) (0.5 * this.getSize()),
				this.getY() - ((int) (0.5 * this.getSize())), this.getSize(), this.getSize());
	}

	public int getScore() {
		return this.Score;
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - hit);
		if (this.getHealth() < 1) {
			main.setScore(this.getScore());
			main.add(new FinalExplosion(main, this.getX() + (int) (0.5 * this.getSize()),
					this.getY() + (int) (0.0 * this.getSize())));
			main.remove(this);
		}
	}

	@Override
	public void move() {
		this.setX((int) (this.getX() + this.getVelocity()[0]));
		this.setY((int) (this.getY() + this.getVelocity()[1]));
	}

	@Override
	public void move(Main main) {
		if (this.getHealth() < 0) {
			main.remove(this);
			main.add(new Explosion(main, this.getX(), this.getY()));
		}
		final double[] Mouse = new double[2];
		Mouse[0] = main.mouseX;
		Mouse[1] = main.mouseY;
		this.setVelocity(Functions.mathOperator.ScalarMultiplication(0.1, Functions.mathOperator
				.AdditionOfVectors(Functions.mathOperator.ReversalOfVector(this.getPosition()), Mouse)));

		if (main.getFrameCount() % 4 == 0 && (main.mousePressed && main.mouseButton == PConstants.LEFT)) {
			this.shoot(main);
			Main.getStatistic().setShots(Main.getStatistic().getShots() + 1);
		}
		if (this.getY() > main.Height) {
			this.selfDestroy(main);
			Main.getStatistic().setPerfectGame(false);
		}
		this.move();
	}

	public void nextGun() {
		final int n = this.indexGun();
		if (n == this.Guns.length - 1) {
			this.setGun(this.Guns[0]);
		} else {
			this.setGun(this.Guns[n + 1]);
		}
	}

	public void prevGun() {
		final int n = this.indexGun();
		if (n == 0) {
			this.setGun(this.Guns[this.Guns.length - 1]);
		} else {
			this.setGun(this.Guns[n - 1]);
		}
	}

	public void setScore(int n) {
		this.Score = Math.min(0, n);
	}

	private int indexGun() {
		for (int i = 0; i < this.Guns.length; i++) {
			if (this.getGun() == this.Guns[i]) {
				return i;
			}
		}
		return -1;
	}
}
