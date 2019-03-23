package defs.objects.explosions;

import defs.interfaces.IDrawable;
import defs.objects.yoda.FinalYoda;
import main.Main;

public class DarthVaderExplosion extends DeathStarExplosion {

	public DarthVaderExplosion(Main main, int x, int y) {
		super(main, x, y);
		// main.delay(100);
		main.getYodaLaughter().trigger();

		main.setMessage(new String[7]);
		main.getMessage()[0] = "";
		main.getMessage()[1] = "Glatulation - die Tarainingsmission bestanden du hast";
		main.getMessage()[2] = "";
		main.getMessage()[3] = "";
		main.getMessage()[4] = "";
		main.getMessage()[5] = main.getScore() + " Punkte erreicht du diesmal hast";
		main.getMessage()[6] = "";

		main.setYodaObj(new FinalYoda(main, main.getMessage()));
	}

	@Override
	public void draw(Main main) {

		for (final IDrawable obj : main.getObjects()) {
			if (obj != main.getHanSolo()) {
				main.remove(obj);
			}
		}

		if ((main.getFrameCount() - getFrames()) < 30) {
			main.shape(main.getExplosion(), ((int) (getX() - getSize() / 5.0)), getY() - 5,
					(int) (0.1 * Math.sqrt(getFrames() / 30.0 * (main.getFrameCount() - getFrames()) / 30.0)
							* getSize()),
					(int) (0.1 * Math.sqrt(getFrames() / 30.0 * (main.getFrameCount() - getFrames()) / 30.0)
							* getSize()));
		}
		main.delay(2000);
		main.remove(this);
		main.add(main.getYodaObj());
		main.setPaused(true);

	}

}
