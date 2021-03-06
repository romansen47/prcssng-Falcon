package defs.objects.flyer;

import java.util.Random;

import defs.objects.explosions.DarthVaderExplosion;
import defs.objects.guns.DoubleAimingGun;
import main.Main;

public class DarthVaderFlyer extends EnemyFlyer {

	public DarthVaderFlyer(Main main) {
		super(main);
		// setSpeedY(0);
		setMaxHealth(8000);
		setHealth(8000);
		setMaxMuni(4000);
		setMuni(4000);
		setMass(500);
		main.setLevel(2);
		setIntelligence(1.1);
		setX(main.width / 2);
		setY(100);
		setSize(80 * main.Height / 1080);
		setGun(new DoubleAimingGun(this));
		int			tmpx, tmpy;
		final int	TMPX	= getX(), TMPY = getY();
		if (getX() < main.Width / 2) {
			tmpx = -200 * main.Width / 1920;
		} else {
			tmpx = main.Width / 2;
		}
		tmpy = 100 * main.Height / 1080;
		for (int i = 0; i < 200; i++) {
			setX((int) (tmpx + i / 200.0 * (TMPX - tmpx)));
			setY((int) (tmpy + i / 200.0 * (TMPY - tmpy)));
			draw(main);
		}
		// main.addEnemy(this);
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getDarth(), getX() - (int) (0.5 * getSize()), getY() - (int) (0.5 * getSize()), getSize(),
				getSize());
		main.fill(255 * (getMaxHealth() - getHealth()) / getMaxHealth(), 255 * getHealth() / getMaxHealth(), 0);
		main.noStroke();
		main.rect(getX() - (int) (0.5 * getSize()), getY() - (int) (0.5 * getSize()),
				(int) (1.0 * getHealth() / getMaxHealth() * getSize()), 12);
	}

	@Override
	public void gotHit(Main main, int hit) {
		setHealth(getHealth() - 3 * hit);
		if (getHealth() < 1) {
			((HanSolo) main.getHanSolo()).addScore(10000);
			main.add(new DarthVaderExplosion(main, getX() + (int) (0.5 * getSize()), getY() + (int) (0.0 * getSize())));
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
			shoot(main);
		}
		setX(getX() + (int) (Math.pow(getIntelligence(), 2) * getSpeedX()));
		setAcceleration(main.getHanSolo().getX() - getX());
		if (Math.abs(getPosition()[0] - ((HanSolo) (main.getHanSolo())).getPosition()[0]) > 150) {
			if (getAcceleration() > 0) {
				setSpeedX((getIntelligence() * Math.min(getSpeedX() + 1, 6 * main.Height / 1080)));
			} else {
				setSpeedX((getIntelligence() * Math.max(getSpeedX() - 1, -6 * main.Height / 1080)));
			}
		}
	}

}
