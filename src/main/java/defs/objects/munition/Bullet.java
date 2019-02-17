package defs.objects.munition;

import defs.interfaces.IDrawable;
import defs.objects.explosions.Explosion;

//import java.util.Random;

import main.Main;
import temperature.Ball;

public class Bullet extends Ball implements IDrawable{

	private int speedY,speedX;
	private int size;
	private int Damage=20;
	
	public Bullet(Main main,int x,int y,int speedX,int speedY) {
		setX(x);
		setY(y);
		this.speedX=speedX;
		this.speedY=speedY;
		setSize(5*main.Height/1080);
	}

	@Override
	public void draw(Main main) {
		main.noStroke();
		main.fill(255,0,0);
		main.ellipse(getX(),getY(),size,2*size);
	}

	@Override
	public int getX() {
		return (int)(getPosition()[0]);
	}

	@Override
	public void setX(int x) {
		getPosition()[0]=x;
	}

	@Override
	public int getY() {
		return (int)(getPosition()[1]);
	}

	@Override
	public void setY(int y) {
		getPosition()[1]=y;
	}


	@Override
	public void move(Main main) {
		setY(getY()-speedY);
		setX(getX()+(int)(0.2*speedX));
		if (getY()<0 || getY()>main.Height) {
			main.remove(this);
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			if (speedY>0) {
				Main.getStatistic().setMissed(Main.getStatistic().getMissed()+1);
			}
		}
		for (IDrawable obj:main.getObjects()) {
			if ( obj!=(IDrawable)this && checkForImpact(obj)!=null ) {
				obj.gotHit(main,getSchaden());
					main.add(new Explosion(main, this.getX(),this.getY()));
				main.remove(this);
				try {
					this.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			};
		}
	} 

	public IDrawable checkForImpact(IDrawable obj) {
		if (!(obj instanceof Explosion) &&
			obj.getX()-((int)(0.5*obj.getSize()))<getX() &&
			obj.getX()+((int)(0.5*obj.getSize()))>getX() &&
			obj.getY()+obj.getSize()>=getY() &&
			obj.getY()<getY()+getSize()
				) {
			return obj;
		}
		return null;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setSize(int size) {
		this.size=size;
	}

	@Override
	public void gotHit(Main main, int i) {
	}

	public int getSchaden() {
		return this.Damage;
	}

	public void setSchaden(int schaden) {
		this.Damage=schaden;
	}
	
	public int getSpeedY() {
		return this.speedY;
	}

	public void setSpeedY(int Speed) {
		this.speedY=Speed;
	}

}
