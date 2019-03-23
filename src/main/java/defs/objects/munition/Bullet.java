package defs.objects.munition;

import defs.interfaces.IDrawable;
import defs.objects.explosions.Explosion;

//import java.util.Random;

import main.Main;
import temperature.Ball;

public class Bullet extends Ball implements IDrawable {

	private int Damage = 20;
	private int size;
	private final int speedX;
	private int speedY;

	public Bullet(Main main, int x, int y, int speedX, int speedY) {
		setX(x);
		setY(y);
		this.speedX = speedX;
		this.speedY = speedY;
		setSize(5 * main.Height / 1080);
	}

	public IDrawable checkForImpact(IDrawable obj) {
		if (!(obj instanceof Explosion) && obj.getX() - ((int) (0.5 * obj.getSize())) < getX()
				&& obj.getX() + ((int) (0.5 * obj.getSize())) > getX() && obj.getY() + obj.getSize() >= getY()
				&& obj.getY() < getY() + getSize()) {
			return obj;
		}
		return null;
	}

	@Override
	public void draw(Main main) {
		main.noStroke();
		main.fill(255, 0, 0);
		main.ellipse(getX(), getY(), size, 2 * size);
	}

	public int getSchaden() {
		return Damage;
	}

	@Override
	public int getSize() {
		return size;
	}

	public int getSpeedY() {
		return speedY;
	}

	@Override
	public int getX() {
		return (int) (getPosition()[0]);
	}

	@Override
	public int getY() {
		return (int) (getPosition()[1]);
	}

	@Override
	public void gotHit(Main main, int i) {
	}

	@Override
	public void move(Main main) {
		setY(getY() - speedY);
		setX(getX() + (int) (0.2 * speedX));
		if (getY() < 0 || getY() > main.Height) {
			selfDestroy(main);
			if (speedY > 0) {
				Main.getStatistic().setMissed(Main.getStatistic().getMissed() + 1);
			}
		}
		for (final IDrawable obj : main.getObjects()) {
			if (obj != this && checkForImpact(obj) != null) {
				obj.gotHit(main, getSchaden());
				main.add(new Explosion(main, getX(), getY()));
				main.remove(this);
				try {
					finalize();
				} catch (final Throwable e) {
					e.printStackTrace();
				}
			}
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

	public void setSchaden(int schaden) {
		Damage = schaden;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	public void setSpeedY(int Speed) {
		speedY = Speed;
	}

	@Override
	public void setX(int x) {
		getPosition()[0] = x;
	}

	@Override
	public void setY(int y) {
		getPosition()[1] = y;
	}

}
