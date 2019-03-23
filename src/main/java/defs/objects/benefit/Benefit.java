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
		setX(x);
		setY(y);
		setSize(size * main.Height / 1080);
		setMaxhealth(health);
		setHealth(health);
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
		main.shape(main.getBenefit(), getX() - ((int) (0.5 * getSize())), getY() - ((int) (0.5 * getSize()) - 20),
				getSize(), getSize());
		main.fill(255 * (getMaxhealth() - getHealth()) / getMaxhealth(), 255 * getHealth() / getMaxhealth(), 0);
		main.rect(getX() - ((int) (0.5 * getSize())), getY() - ((int) (0.5 * getSize())) - 10,
				(int) (1. * getHealth() / getMaxhealth() * getSize()), 8, 50);
	}

	public int getHealth() {
		return health;
	}

	public int getMaxhealth() {
		return maxhealth;
	}

	@Override
	public double[] getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getX() {
		return X;
	}

	@Override
	public int getY() {
		return Y;
	}

	@Override
	public void gotHit(Main main, int i) {
		setHealth(Math.max(0, getHealth() - i));
		if (health == 0) {
			selfDestroy(main);
		}
	}

	@Override
	public void move(Main main) {
		setY(getY() + 7);
		if (main.getHanSolo().getY() <= getY() + getSize() && main.getHanSolo().getX() < getX() + getSize()
				&& main.getHanSolo().getX() + main.getHanSolo().getSize() > getX()
				&& main.getHanSolo().getY() + main.getHanSolo().getSize() > getY()) {
			main.remove(this);
			((HanSolo) (main.getHanSolo())).setHealth(((HanSolo) (main.getHanSolo())).getHealth() + 100);
			((HanSolo) (main.getHanSolo())).setMuni(((HanSolo) (main.getHanSolo())).getMuni() + 700);
		}
	}

	@Override
	public void selfDestroy(Main main) {
		main.remove(this);
		try {
			finalize();
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public void setHealth(int h) {
		health = Math.max(0, Math.min(h, maxhealth));
	}

	public void setMaxhealth(int h) {
		maxhealth = h;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void setX(int x) {
		X = x;
	}

	@Override
	public void setY(int y) {
		Y = y;
	}

}
