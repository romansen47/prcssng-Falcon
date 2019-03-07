package defs.objects.yoda;

import defs.interfaces.IDrawable;
import main.Main;

public class Yoda implements IDrawable {

	private String[] message;
	private int X, Y, size, level = 0;

	public Yoda(Main main, String[] Message) {
		this.message = Message;
		main.setPaused(true);
		this.setX(300 * main.Height / 1080);
		this.setY(main.Height);
		this.setSize(Math.max(100 * main.Height / 1080, (Message.length + 1) * 40 * main.Height / 1080));
	}

	@Override
	public void draw(Main main) {
		main.fill(0, 255, 0);
		main.rect(this.getX(), this.getY(), 2 * this.getSize(), this.getSize());
		main.textSize(20 * main.Height / 1080);
		for (int i = 0; i < this.message.length; i++) {
			main.fill(0);
			main.text(this.message[i], this.getX() + 80 * main.Height / 1080,
					this.getY() + 50 + i * 20 * main.Height / 1080);
		}
		main.shape(main.getYoda(), this.getX() + 2 * this.getSize(), this.getY() + this.getSize(), this.getSize(),
				this.getSize());
	}

	public int getLevel() {
		return this.level;
	}

	public String[] getMessage() {
		return this.message;
	}

	@Override
	public double[] getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return this.size;
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
	public void gotHit(Main main, int i) {
	}

	@Override
	public void move(Main main) {
		if (this.level == 0) {
			if (this.getY() > main.Height - 2 * this.getSize()) {
				this.setY(this.getY() - 35 * main.Height / 1080);
			} else if (main.mousePressed) {
				this.level = 1;
			}
		} else {
			if (this.getY() < main.Height) {
				this.setY(this.getY() + 35 * main.Height / 1080);
			} else {
				this.level = 0;
				main.setPaused(false);
				this.selfDestroy(main);
			}
		}
		this.draw(main);
	}

	@Override
	public void selfDestroy(Main main) {
		main.remove(this);
		try {
			this.finalize();
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
		this.size = Size;
	}

	@Override
	public void setX(int x) {
		this.X = x;
	}

	@Override
	public void setY(int y) {
		this.Y = y;
	}

}
