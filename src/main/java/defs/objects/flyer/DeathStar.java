package defs.objects.flyer;

import java.util.Random;

import defs.objects.explosions.DeathStarExplosion;
import defs.objects.guns.DoubleAimingGun;
import main.Main;

public class DeathStar extends StarDestroyer {

	public DeathStar(Main main) {
		super(main);

		main.setMessage(new String[3]);
		main.getMessage()[0] = "";
		main.getMessage()[1] = "Den Todesstern erreicht, du hast..";
		main.getMessage()[2] = "";
		main.getYodaObj().setMessage(main.getMessage());
		main.getYodaObj().setLevel(0);
		main.setPaused(true);

		main.setBoss(this);
		setMaxHealth(6000);
		setHealth(6000);
		setSize(300 * main.Height / 1080);
		setX((int) (0.5 * (main.Width)));
		setY((-getSize()));
		setGun(new DoubleAimingGun(this));
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getDeathStar(), getX() - (int) (0.5 * getSize()), getY() - (int) (0.5 * getSize()), getSize(),
				getSize());
	}

	@Override
	public void gotHit(Main main, int hit) {
		setHealth(getHealth() - hit);
		if (getHealth() < 1) {
			main.add(new DeathStarExplosion(main, getX() + (int) (0.5 * getSize()), getY() + getSize() + 10));
			main.add(new DarthVaderFlyer(main));
			main.remove(this);
			main.getChewBacca().trigger();
			main.getHan().trigger();
			main.setScore(((HanSolo) (main.getHanSolo())).getScore());
		}
	}

	@Override
	public void move(Main main) {
		if (getY() < 1) {
			setY(getY() + 2);
		}
		if ((new Random()).nextInt(70) < 4) {
			shoot(main);
		}
	}
}
