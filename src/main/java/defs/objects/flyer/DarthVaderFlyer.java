package defs.objects.flyer;

import java.util.Random;

import defs.objects.explosions.DarthVaderExplosion;
import defs.objects.guns.DoubleAimingGun;
import main.Main;

public class DarthVaderFlyer extends EnemyFlyer {

	public DarthVaderFlyer(Main main) {
		super(main);
		// setSpeedY(0);
		this.setMaxHealth(8000);
		this.setHealth(8000);
		this.setMaxMuni(4000);
		this.setMuni(4000);
		this.setMass(500);
		main.setLevel(2);
		this.setIntelligence(1.1);
		this.setX(main.width / 2);
		this.setY(100);
		this.setSize(80 * main.Height / 1080);
		this.setGun(new DoubleAimingGun(this));
		int tmpx, tmpy;
		final int TMPX = this.getX(), TMPY = this.getY();
		if (this.getX() < main.Width / 2) {
			tmpx = -200 * main.Width / 1920;
		} else {
			tmpx = main.Width / 2;
		}
		tmpy = 100 * main.Height / 1080;
		for (int i = 0; i < 200; i++) {
			this.setX((int) (tmpx + i / 200.0 * (TMPX - tmpx)));
			this.setY((int) (tmpy + i / 200.0 * (TMPY - tmpy)));
			this.draw(main);
		}
		// main.addEnemy(this);
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getDarth(), this.getX() - (int) (0.5 * this.getSize()),
				this.getY() - (int) (0.5 * this.getSize()), this.getSize(), this.getSize());
		main.fill(255 * (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth(),
				255 * this.getHealth() / this.getMaxHealth(), 0);
		main.noStroke();
		main.rect(this.getX() - (int) (0.5 * this.getSize()), this.getY() - (int) (0.5 * this.getSize()),
				(int) (1.0 * this.getHealth() / this.getMaxHealth() * this.getSize()), 12);
	}

	@Override
	public void gotHit(Main main, int hit) {
		this.setHealth(this.getHealth() - 3 * hit);
		if (this.getHealth() < 1) {
			((HanSolo) main.getHanSolo()).addScore(10000);
			main.add(new DarthVaderExplosion(main, this.getX() + (int) (0.5 * this.getSize()),
					this.getY() + (int) (0.0 * this.getSize())));
			main.remove(this);
		} else {
			((HanSolo) main.getHanSolo()).addScore(100);
		}
	}
//
//	public int getAcceleration() {
//		return acceleration;
//	}
//
//	public void setAcceleration(int acceleration) {
//		this.acceleration = acceleration;
//	}

	@Override
	public void move(Main main) {
		if ((new Random()).nextInt() % 1000 < -850) {
			this.shoot(main);
		}
		this.setX(this.getX() + (int) (Math.pow(this.getIntelligence(), 2) * this.getSpeedX()));
		this.setAcceleration(main.getHanSolo().getX() - this.getX());
		if (Math.abs(this.getPosition()[0] - ((HanSolo) (main.getHanSolo())).getPosition()[0]) > 150) {
			if (this.getAcceleration() > 0) {
				this.setSpeedX((this.getIntelligence() * Math.min(this.getSpeedX() + 1, 6 * main.Height / 1080)));
			} else {
				this.setSpeedX((this.getIntelligence() * Math.max(this.getSpeedX() - 1, -6 * main.Height / 1080)));
			}
		}
	}

}
