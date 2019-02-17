package defs.objects.flyer;

import java.util.Random;

import defs.objects.explosions.Explosion;
import main.Main;
import main.StatsCollector;
import temperature.Functions;

public class EnemyFlyer extends Flyer {

	private int deltaX, acceleration;
	private int[] force;
	private double intelligence;
	final double AbsVel;

	public EnemyFlyer(Main main) {
		super(main, (int) (main.Width / 2.0 - ((new Random()).nextInt()) % (main.Width / 6)), 200);
		setMass(300);
		setIntelligence(1.0 + (new Random()).nextInt(50) / 100.0);
		setSpeedY(((5 + (new Random()).nextInt() % 5) * main.Height / 1080));
		AbsVel = getIntelligence();
		this.setSize(60 * main.Height / 1080);
	}

	@Override
	public void move() {
		setPosition(Functions.mathOperator.AdditionOfVectors(getPosition(), getVelocity()));
	}

	@Override
	public void move(Main main) {
		if ((new Random()).nextInt(1000) > 950) {
			this.shoot(main);
		}
//		if(Math.abs(this.getPosition()[0]-((HanSolo)(main.getHanSolo())).getPosition()[0])>350) {
//			this.getVelocity()[0]=this.getVelocity()[0]+
//				(int)(0.001*(Functions.AdditionOfVectors(	Functions.ReversalOfVector(this.getPosition()),
//														((HanSolo) (main.getHanSolo())).getPosition()))[0]);
//		}
//		this.setVelocity(Functions.ScalarMultiplication(intelligence*0.01/AbsVel,
//				Functions.AdditionOfVectors(Functions.ReversalOfVector(this.getPosition()),((HanSolo) (main.getHanSolo())).getPosition())));
		setX(getX() + (int) (Math.pow(getIntelligence(), 1) * getSpeedX()));
		setAcceleration(main.getHanSolo().getX() - getX());
		double[] vec1 = new double[2], vec2 = new double[2];
		vec1[0] = main.getHanSolo().getX();
		vec1[1] = main.Height / 8;
		vec2[0] = this.getX();
		vec2[1] = this.getY();
		double[] frc = Functions.mathOperator.AdditionOfVectors(vec1, Functions.mathOperator.ReversalOfVector(vec2));
		double tmpLength = Functions.mathOperator.MagnitudeOfVector(frc);
		if (tmpLength > 0.1) {
			frc = Functions.mathOperator.ScalarMultiplication(0.1, Functions.mathOperator.UnitVector(frc));
		}
		this.setVelocity(Functions.mathOperator.AdditionOfVectors(frc, this.getVelocity()));
//		if(Math.abs(this.getPosition()[0]-((HanSolo)(main.getHanSolo())).getPosition()[0])>50) 
//		{
//			if (getAcceleration()>0) {
//				setSpeedX((getIntelligence()*Math.min(getSpeedX()+1,6*main.Height/1080)));
//			}
//			else {
//				setSpeedX((getIntelligence()*Math.max(getSpeedX()-1,-6*main.Height/1080)));
//			}
//		}
//		getVelocity()[1]=Math.max((int)(3*intelligence),3);
		move();
		// Do we collide?
		if (this.getY() + this.getSize() > main.getHanSolo().getY()
				&& this.getX() < main.getHanSolo().getX() + main.getHanSolo().getSize()
				&& this.getX() + this.getSize() > main.getHanSolo().getX()
				&& this.getY() + this.getSize() < main.getHanSolo().getY() + main.getHanSolo().getSize()
				&& this.getY() + this.getSize() < main.Height) {
			((HanSolo) main.getHanSolo()).addScore(50);
			selfDestroy(main);
			StatsCollector.getInstance().setEnemies(1 + StatsCollector.getInstance().getEnemies());
			Explosion expl = new Explosion(main, this.getX(), this.getY());
			expl.setSize(this.getSize() * 2);
			main.add(expl);
			((HanSolo) main.getHanSolo()).setHealth(((HanSolo) main.getHanSolo()).getHealth() - 30);
		}
		if (this.getY() > main.Height) {
			((HanSolo) main.getHanSolo()).addScore(-25);
			selfDestroy(main);
		}
	}

	public void setIntelligence(double intelligence) {
		this.intelligence = intelligence;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	@Override
	public void shoot(Main main) {
		this.getGun().shoot(main);
		main.getPlainGunSound().trigger();
	}

	public double getIntelligence() {
		return intelligence;
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	public int[] getForce() {
		return force;
	}

	public void setForce(int[] force) {
		this.force = force;
	}

}
