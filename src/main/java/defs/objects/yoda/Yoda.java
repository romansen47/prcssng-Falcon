package defs.objects.yoda;

import defs.interfaces.IDrawable;
import main.Main;

public class Yoda implements IDrawable{

	private String[] message;
	private int X,Y,size,level=0;
	
	public Yoda(Main main, String[] Message) {
		this.message=Message;
		main.setPaused(true);
		this.setX(300*main.Height/1080);
		this.setY(main.Height);
		setSize(Math.max(100*main.Height/1080,(Message.length+1)*40*main.Height/1080));
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	@Override
	public void draw(Main main) {
		main.fill(0,255,0);
		main.rect(getX(),getY(),2*getSize(),getSize());
		main.textSize(20*main.Height/1080);
		for (int i=0;i<message.length;i++) {
			main.fill(0);
			main.text(message[i], getX()+80*main.Height/1080,getY()+50+i*20*main.Height/1080);
		}
		main.shape(main.getYoda(),getX()+2*getSize(),getY()+getSize(),getSize(),getSize());
	}

	@Override
	public void setX(int x) {
		X=x;
	}

	@Override
	public void setY(int y) {
		Y=y;
	}

	@Override
	public int getX() {
		return X;
	}

	@Override
	public int getY() {
		return Y;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setSize(int Size) {
		size=Size;
	}

	@Override
	public void move(Main main) {
		if (level==0) {
			if (getY()>main.Height-2*getSize()) {
				setY(getY()-35*main.Height/1080);
			}
			else if (main.mousePressed) {
				level=1;
			}
		}
		else {
			if(getY()<main.Height) {
				setY(getY()+35*main.Height/1080);
			}
			else {
				main.remove(this);
				level=0;
				main.setPaused(false);
				try {
					this.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		draw(main);
	}

	@Override
	public void gotHit(Main main, int i) {
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public double[] getPosition() {
		// TODO Auto-generated method stub
		return null;
	} 
	
}
