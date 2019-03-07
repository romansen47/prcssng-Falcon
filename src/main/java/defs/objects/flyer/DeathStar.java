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
		this.setMaxHealth(6000);
		this.setHealth(6000);
		this.setSize(300 * main.Height / 1080);
		this.setX((int) (0.5 * (main.Width)));
		this.setY((-this.getSize()));
		this.setGun(new DoubleAimingGun(this));
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getDeathStar(), this.getX() - (int) (0.5 * this.getSize()),
				this.getY() - (int) (0.5 * this.getSize()), this.getSize(), this.getSize());
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - hit);
		if (this.getHealth() < 1) {
			main.add(new DeathStarExplosion(main, this.getX() + (int) (0.5 * this.getSize()),
					this.getY() + this.getSize() + 10));
			main.add(new DarthVaderFlyer(main));
			main.remove(this);
			main.getChewBacca().trigger();
			main.getHan().trigger();
			main.setScore(((HanSolo) (main.getHanSolo())).getScore());
		}
	}

	@Override
	public void move(Main main) {
		if (this.getY() < 1) {
			this.setY(this.getY() + 2);
		}
		if ((new Random()).nextInt(70) < 4) {
			this.shoot(main);
		}
	}
}
