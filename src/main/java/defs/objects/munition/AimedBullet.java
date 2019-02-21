package defs.objects.munition;

import main.Main;

public class AimedBullet extends Bullet {

	private double ratio;

	public AimedBullet(Main main, int x, int y) {
		super(main, x, y, 0, -15 * main.Height / 1080);
		setSize(4 * main.Height / 1080);
		ratio = (main.getHanSolo().getX() - getX()) / (double) (main.getHanSolo().getY() - getY());
	}

	@Override
	public void move(Main main) {
		super.move(main);
		this.setX((int) (this.getX() - this.getSpeedY() * ratio));
	}
}