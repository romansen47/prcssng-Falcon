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
		this.frames = main.getFrameCount();
		this.NearestObject = this.getNearestObject(main);
	}

	@Override
	public IDrawable checkForImpact(IDrawable obj) {
		if (obj == this.NearestObject) {
			return obj;
		}
		return null;
	}

	@Override
	public void draw(Main main) {
		if (main.getFrameCount() - this.frames > 3) {
			this.selfDestroy(main);
		} else {
			main.stroke(255, 30, 30);
			if (this.NearestObject != null) {
				main.line(this.getX(), this.getY() - 10, this.getX(),
						this.NearestObject.getY() + this.NearestObject.getSize());
			} else {
				main.line(this.getX(), this.getY() - 10, this.getX(), 0);
			}
			main.stroke(0);
			main.remove(this);
		}
	}

	@Override
	public void move(Main main) {
		this.setY(this.getY() - this.getSpeedY());
		if (this.getY() < 0 || this.getY() > main.Height) {
			this.selfDestroy(main);
			if (this.getSpeedY() > 0) {
				Main.getStatistic().setMissed(Main.getStatistic().getMissed() + 1);
			}
		}
		for (final IDrawable obj : main.getObjects()) {
			if (obj != this && this.checkForImpact(obj) != null) {
				obj.gotHit(main, this.getSchaden());
				main.add(new Explosion(main, this.getX(), obj.getY() + obj.getSize()));
				this.selfDestroy(main);
			}
		}
	}

	private IDrawable getNearestObject(Main main) {
		double dist = main.Height;
		IDrawable ans = null;
		for (final IDrawable obj : main.getObjects()) {
			if (dist > 0 && !(obj instanceof Bullet) && !(obj instanceof Explosion) && obj != this
					&& obj.getY() < main.getHanSolo().getY() && (main.getHanSolo().getY() - obj.getY()) < dist
					&& this.getX() > obj.getX() - (0.5 * obj.getSize())
					&& this.getX() < obj.getX() + (0.5 * obj.getSize())) {
				ans = obj;
				dist = main.getHanSolo().getY() - obj.getY();
			}
		}
		return ans;
	}
}
