package defs.objects.yoda;

import defs.interfaces.IDrawable;
import main.Main;

public class Yoda implements IDrawable {

	private String[]	message;
	private int			X, Y, size, level = 0;

	public Yoda(Main main, String[] Message) {
		message = Message;
		main.setPaused(true);
		setX(300 * main.Height / 1080);
		setY(main.Height);
		setSize(Math.max(100 * main.Height / 1080, (Message.length + 1) * 40 * main.Height / 1080));
	}

	@Override
	public void draw(Main main) {
		main.fill(0, 255, 0);
		main.rect(getX(), getY(), 2 * getSize(), getSize());
		main.textSize(20 * main.Height / 1080);
		for (int i = 0; i < message.length; i++) {
			main.fill(0);
			main.text(message[i], getX() + 80 * main.Height / 1080, getY() + 50 + i * 20 * main.Height / 1080);
		}
		main.shape(main.getYoda(), getX() + 2 * getSize(), getY() + getSize(), getSize(), getSize());
	}

	public int getLevel() {
		return level;
	}

	public String[] getMessage() {
		return message;
	}

	@Override
	public double[] getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return size;
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
	public void gotHit(Main main, int i) {
	}

	@Override
	public void move(Main main) {
		if (level == 0) {
			if (getY() > main.Height - 2 * getSize()) {
				setY(getY() - 35 * main.Height / 1080);
			} else if (main.mousePressed) {
				level = 1;
			}
		} else {
			if (getY() < main.Height) {
				setY(getY() + 35 * main.Height / 1080);
			} else {
				level = 0;
				main.setPaused(false);
				selfDestroy(main);
			}
		}
		draw(main);
	}

	@Override
	public void selfDestroy(Main main) {
		main.remove(this);
		try {
			finalize();
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	@Override
	public void setSize(int Size) {
		size = Size;
	}

	@Override
	public void setX(int x) {
		X = x;
	}

	@Override
	public void setY(int y) {
		Y = y;
	}

}
