package defs.objects.flyer;

import defs.interfaces.IShooting;
import defs.objects.explosions.Explosion;
import defs.objects.explosions.FinalExplosion;
import defs.objects.guns.PlainGun;
import main.Main;
import processing.core.PConstants;
import temperature.Functions;

public final class HanSolo extends Flyer {

	private IShooting[] Guns;

	private int Score = 0;

	public HanSolo(Main main) {
		Guns = new IShooting[1];
		Guns[0] = new PlainGun(this);
		setGun(Guns[0]);
		setSize(90 * main.Height / 1080);
		setX(main.Width / 2);
		setY(main.Height - ((int) (1.5 * getSize())));
		setMaxHealth(400);
		setHealth(400);
		setMaxMuni(2000);
	}

	public void addGun(IShooting gun) {
		final IShooting[] tmpGuns = new IShooting[Guns.length + 1];
		for (int i = 0; i < Guns.length; i++) {
			tmpGuns[i] = Guns[i];
		}
		tmpGuns[Guns.length] = gun;
		Guns = tmpGuns;
		setGun(gun);
	}

	public void addScore(int n) {
		Score = Math.max(0, Score + n);
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getHansolo(), getX() - (int) (0.5 * getSize()), getY() - ((int) (0.5 * getSize())), getSize(),
				getSize());
	}

	public int getScore() {
		return Score;
	}

	@Override
	public void gotHit(Main main, int hit) {
		setHealth(getHealth() - hit);
		if (getHealth() < 1) {
			main.setScore(getScore());
			main.add(new FinalExplosion(main, getX() + (int) (0.5 * getSize()), getY() + (int) (0.0 * getSize())));
			main.remove(this);
		}
	}

	private int indexGun() {
		for (int i = 0; i < Guns.length; i++) {
			if (getGun() == Guns[i]) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void move() {
		setX((int) (getX() + getVelocity()[0]));
		setY((int) (getY() + getVelocity()[1]));
	}

	@Override
	public void move(Main main) {
		if (getHealth() < 0) {
			main.remove(this);
			main.add(new Explosion(main, getX(), getY()));
		}
		final double[] Mouse = new double[2];
		Mouse[0] = main.mouseX;
		Mouse[1] = main.mouseY;
		setVelocity(Functions.getInstance().getMathOperator().ScalarMultiplication(0.1,
				Functions.getInstance().getMathOperator().AdditionOfVectors(
						Functions.getInstance().getMathOperator().ReversalOfVector(getPosition()), Mouse)));

		if (main.getFrameCount() % 4 == 0 && (main.mousePressed && main.mouseButton == PConstants.LEFT)) {
			shoot(main);
			Main.getStatistic().setShots(Main.getStatistic().getShots() + 1);
		}
		if (getY() > main.Height) {
			selfDestroy(main);
			Main.getStatistic().setPerfectGame(false);
		}
		this.move();
	}

	public void nextGun() {
		final int n = indexGun();
		if (n == Guns.length - 1) {
			setGun(Guns[0]);
		} else {
			setGun(Guns[n + 1]);
		}
	}

	public void prevGun() {
		final int n = indexGun();
		if (n == 0) {
			setGun(Guns[Guns.length - 1]);
		} else {
			setGun(Guns[n - 1]);
		}
	}

	public void setScore(int n) {
		Score = Math.min(0, n);
	}
}
