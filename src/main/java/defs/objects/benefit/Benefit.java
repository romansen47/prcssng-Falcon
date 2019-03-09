package defs.objects.benefit;

import defs.interfaces.IDrawable;
import defs.objects.flyer.HanSolo;
import main.Main;

public class Benefit implements IDrawable {

	private static boolean firstBenefit = true;

	public static boolean isFirstTime() {
		return Benefit.firstBenefit;
	}

	public static void setFirstTime(boolean firstTime) {
		Benefit.firstBenefit = firstTime;
	}

	private int health;

	private int maxhealth;

	private int X, Y, size;

	public Benefit(Main main, int x, int y, int size, int health) {
		this.setX(x);
		this.setY(y);
		this.setSize(size * main.Height / 1080);
		this.setMaxhealth(health);
		this.setHealth(health);
		main.addOnTop(this);
		if (Benefit.isFirstTime()) {
			Benefit.setFirstTime(false);
			main.setMessage(new String[5]);
			main.getMessage()[0] = "";
			main.getMessage()[1] = "Achtung!";
			main.getMessage()[2] = "Ersatzteile verloren dieser Tie Fighter hat..";
			main.getMessage()[3] = "Reperarieren den Millenium Falken du damit kannst!";
			main.getMessage()[4] = "";
			main.getYodaObj().setMessage(main.getMessage());
			main.getYodaObj().setLevel(0);
			main.setPaused(true);
		}
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getBenefit(), this.getX() - ((int) (0.5 * this.getSize())),
				this.getY() - ((int) (0.5 * this.getSize()) - 20), this.getSize(), this.getSize());
		main.fill(255 * (this.getMaxhealth() - this.getHealth()) / this.getMaxhealth(),
				255 * this.getHealth() / this.getMaxhealth(), 0);
		main.rect(this.getX() - ((int) (0.5 * this.getSize())), this.getY() - ((int) (0.5 * this.getSize())) - 10,
				(int) (1. * this.getHealth() / this.getMaxhealth() * this.getSize()), 8, 50);
	}

	public int getHealth() {
		return this.health;
	}

	public int getMaxhealth() {
		return this.maxhealth;
	}

	@Override
	public double[] getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public int getX() {
		return this.X;
	}

	@Override
	public int getY() {
		return this.Y;
	}

	@Override
	public void gotHit(Main main, int i) {
		this.setHealth(Math.max(0, this.getHealth() - i));
		if (this.health == 0) {
			this.selfDestroy(main);
		}
	}

	@Override
	public void move(Main main) {
		this.setY(this.getY() + 7);
		if (main.getHanSolo().getY() <= this.getY() + this.getSize()
				&& main.getHanSolo().getX() < this.getX() + this.getSize()
				&& main.getHanSolo().getX() + main.getHanSolo().getSize() > this.getX()
				&& main.getHanSolo().getY() + main.getHanSolo().getSize() > this.getY()) {
			main.remove(this);
			((HanSolo) (main.getHanSolo())).setHealth(((HanSolo) (main.getHanSolo())).getHealth() + 100);
			((HanSolo) (main.getHanSolo())).setMuni(((HanSolo) (main.getHanSolo())).getMuni() + 700);
		}
	}

	@Override
	public void selfDestroy(Main main) {
		main.remove(this);
		try {
			this.finalize();
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public void setHealth(int h) {
		this.health = Math.max(0, Math.min(h, this.maxhealth));
	}

	public void setMaxhealth(int h) {
		this.maxhealth = h;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void setX(int x) {
		this.X = x;
	}

	@Override
	public void setY(int y) {
		this.Y = y;
	}

}
