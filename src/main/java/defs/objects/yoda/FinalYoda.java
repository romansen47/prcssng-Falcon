package defs.objects.yoda;

import main.Main;

public class FinalYoda extends Yoda {

	public FinalYoda(Main main, String[] Message) {
		super(main, Message);
		main.textSize((int) (15. * main.height / 1080));
		this.setSize((int) Math.max(100.0 * main.Height / 1080, (Message.length + 1) * 25.0 * main.Height / 1080));
		// setSize(Math.max(100,Message.length*20));
	}

	@Override
	public void move(Main main) {
		if (this.getLevel() == 0) {
			if (this.getY() > main.Height - 2 * this.getSize()) {
				this.setY(this.getY() - 35);
			} else if (main.mousePressed) {
				this.setLevel(1);
			}
		} else {
			if (this.getY() < main.Height) {
				this.setY(this.getY() + 35);
			} else {
				main.exit();
			}
		}
		this.draw(main);
	}

}
