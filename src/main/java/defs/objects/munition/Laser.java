package defs.objects.munition;

import defs.interfaces.IDrawable;
import defs.objects.explosions.Explosion;
import main.Main;

public class Laser extends Bullet {

	final int frames;
	IDrawable NearestObject;
	public int Schaden = 30;

	public Laser(Main main, int x, int y) {
		super(main, x, y, 0, 0);
		frames = main.getFrameCount();
		NearestObject = getNearestObject(main);
	}

	@Override
	public IDrawable checkForImpact(IDrawable obj) {
		if (obj == NearestObject) {
			return obj;
		}
		return null;
	}

	@Override
	public void draw(Main main) {
		if (main.getFrameCount() - frames > 3) {
			selfDestroy(main);
		} else {
			main.stroke(255, 30, 30);
			if (NearestObject != null) {
				main.line(getX(), getY() - 10, getX(), NearestObject.getY() + NearestObject.getSize());
			} else {
				main.line(getX(), getY() - 10, getX(), 0);
			}
			main.stroke(0);
			main.remove(this);
		}
	}

	private IDrawable getNearestObject(Main main) {
		double dist = main.Height;
		IDrawable ans = null;
		for (final IDrawable obj : main.getObjects()) {
			if (dist > 0 && !(obj instanceof Bullet) && !(obj instanceof Explosion) && obj != this
					&& obj.getY() < main.getHanSolo().getY() && (main.getHanSolo().getY() - obj.getY()) < dist
					&& getX() > obj.getX() - (0.5 * obj.getSize()) && getX() < obj.getX() + (0.5 * obj.getSize())) {
				ans = obj;
				dist = main.getHanSolo().getY() - obj.getY();
			}
		}
		return ans;
	}

	@Override
	public void move(Main main) {
		setY(getY() - getSpeedY());
		if (getY() < 0 || getY() > main.Height) {
			selfDestroy(main);
			if (getSpeedY() > 0) {
				Main.getStatistic().setMissed(Main.getStatistic().getMissed() + 1);
			}
		}
		for (final IDrawable obj : main.getObjects()) {
			if (obj != this && checkForImpact(obj) != null) {
				obj.gotHit(main, getSchaden());
				main.add(new Explosion(main, getX(), obj.getY() + obj.getSize()));
				selfDestroy(main);
			}
		}
	}
}
