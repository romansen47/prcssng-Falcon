package defs.objects.explosions;

import java.util.Random;

import defs.interfaces.IDrawable;
import defs.objects.flyer.DarthVaderFlyer;
import main.Main;

public class DeathStarExplosion extends FinalExplosion {

	private Random r;

	public DeathStarExplosion(Main main, int x, int y) {
		super(main, x, y);
		setSize(150);
		setR(new Random());
	}

	@Override
	public void draw(Main main) {
		for (IDrawable obj : main.getObjects()) {
			if (obj != main.getHanSolo()) {
				main.remove(obj);
			}
		}
		main.remove(this);
		main.getPlayer().stop();
		main.getVader().trigger();
		main.add(new DarthVaderFlyer(main));
	}

	public Random getR() {
		return r;
	}

	public void setR(Random r) {
		this.r = r;
	}

}
