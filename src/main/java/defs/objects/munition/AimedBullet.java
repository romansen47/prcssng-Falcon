package defs.objects.munition;

import main.Main;

public class AimedBullet extends Bullet {

	private final double ratio;

	public AimedBullet(Main main, int x, int y) {
		super(main, x, y, 0, -15 * main.Height / 1080);
		this.setSize(4 * main.Height / 1080);
		this.ratio = (main.getHanSolo().getX() - this.getX()) / (double) (main.getHanSolo().getY() - this.getY());
	}

	@Override
	public void move(Main main) {
		super.move(main);
		this.setX((int) (this.getX() - this.getSpeedY() * this.ratio));
	}
}