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
		this.setX(x);
		this.setY(y);
		this.speedX = speedX;
		this.speedY = speedY;
		this.setSize(5 * main.Height / 1080);
	}

	public IDrawable checkForImpact(IDrawable obj) {
		if (!(obj instanceof Explosion) && obj.getX() - ((int) (0.5 * obj.getSize())) < this.getX()
				&& obj.getX() + ((int) (0.5 * obj.getSize())) > this.getX() && obj.getY() + obj.getSize() >= this.getY()
				&& obj.getY() < this.getY() + this.getSize()) {
			return obj;
		}
		return null;
	}

	@Override
	public void draw(Main main) {
		main.noStroke();
		main.fill(255, 0, 0);
		main.ellipse(this.getX(), this.getY(), this.size, 2 * this.size);
	}

	public int getSchaden() {
		return this.Damage;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	public int getSpeedY() {
		return this.speedY;
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
	public void gotHit(Main main, int i) {
	}

	@Override
	public void move(Main main) {
		this.setY(this.getY() - this.speedY);
		this.setX(this.getX() + (int) (0.2 * this.speedX));
		if (this.getY() < 0 || this.getY() > main.Height) {
			this.selfDestroy(main);
			if (this.speedY > 0) {
				Main.getStatistic().setMissed(Main.getStatistic().getMissed() + 1);
			}
		}
		for (final IDrawable obj : main.getObjects()) {
			if (obj != this && this.checkForImpact(obj) != null) {
				obj.gotHit(main, this.getSchaden());
				main.add(new Explosion(main, this.getX(), this.getY()));
				main.remove(this);
				try {
					this.finalize();
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
			this.finalize();
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public void setSchaden(int schaden) {
		this.Damage = schaden;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	public void setSpeedY(int Speed) {
		this.speedY = Speed;
	}

	@Override
	public void setX(int x) {
		this.getPosition()[0] = x;
	}

	@Override
	public void setY(int y) {
		this.getPosition()[1] = y;
	}

}
