package defs.objects.explosions;

import defs.interfaces.IDrawable;
import main.Main;

public class Explosion implements IDrawable{

	private int X,Y;
	private int frames;
	//private PImage image;
	
	public Explosion(Main main,int x, int y) {
		//image = main.getExplosion();
		this.setFrames(main.getFrameCount());
		setX(x);
		setY(y);
		main.getExplosionSound().trigger();
	}

	@Override
	public void draw(Main main) {
		double expSize=10;
		if ((main.getFrameCount()-getFrames())<expSize){			
			main.shape(main.getExplosion(), ((int)(getX()-getSize()/5.0)),getY()-5,
			(int)(0.05*Math.sqrt(getFrames()/expSize*(main.getFrameCount()-getFrames())/expSize)*getSize()),
			(int)(0.05*Math.sqrt(getFrames()/expSize*(main.getFrameCount()-getFrames())/expSize)*getSize()));
		}
		else {
			main.remove(this);
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setX(int x) {
		this.X=x;
	}

	@Override
	public void setY(int y) {
		this.Y=y;
	}

	@Override
	public int getX() {
		return this.X;
	}

	@Override
	public int getY() {
		return this.Y;
	}

	@Override
	public int getSize() {
		return 150;
	}

	@Override
	public void setSize(int size) {
	}

	@Override
	public void move(Main main) {
	}

	@Override
	public void gotHit(Main main, int i) {
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

}
