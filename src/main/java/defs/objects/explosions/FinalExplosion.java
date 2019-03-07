package defs.objects.explosions;

import defs.objects.yoda.FinalYoda;
import main.Main;

public class FinalExplosion extends Explosion {

	public FinalExplosion(Main main, int x, int y) {
		super(main, x, y);
	}

	@Override
	public void draw(Main main) {
		if ((main.getFrameCount() - this.getFrames()) < 30) {
			main.shape(main.getExplosion(), ((int) (this.getX() - this.getSize() / 5.0)), this.getY() - 5,
					(int) (0.1 * Math.sqrt(this.getFrames() / 30.0 * (main.getFrameCount() - this.getFrames()) / 30.0)
							* this.getSize()),
					(int) (0.1 * Math.sqrt(this.getFrames() / 30.0 * (main.getFrameCount() - this.getFrames()) / 30.0)
							* this.getSize()));
		} else {
			main.remove(this);
			main.setMessage(new String[19]);
			main.getMessage()[0] = "";
			main.getMessage()[1] = "Den Todesstern aufgehalten du nicht hast";
			main.getMessage()[2] = "";
			main.getMessage()[3] = "Verloren diese Trainingsmission du hast";
			main.getMessage()[4] = "";
			main.getMessage()[5] = Main.getStatistic().getEnemies() + " Feinde bekaempfen du musstest";
			main.getMessage()[6] = "";
			main.getMessage()[7] = Main.getStatistic().getKills() + " Feinde ausgeschaltet du hast";
			main.getMessage()[8] = "";
			main.getMessage()[9] = Main.getStatistic().getShots() + "  Schuesse abgegeben du hast";
			main.getMessage()[10] = "";
			main.getMessage()[11] = Main.getStatistic().getMissed() + " Mal verfehlt dein Ziel du hast";
			main.getMessage()[12] = "";
			main.getMessage()[13] = "Deine Trefferquote: "
					+ 100.0 * (Main.getStatistic().getShots() - Main.getStatistic().getMissed())
							/ Main.getStatistic().getShots()
					+ "%";
			main.getMessage()[14] = "";
			main.getMessage()[15] = "------------------------------------------------";
			main.getMessage()[16] = "";
			main.getMessage()[17] = "Punktzahl: " + main.getScore() + " * "
					+ 1.0 * (Main.getStatistic().getShots() - Main.getStatistic().getMissed())
							/ Main.getStatistic().getShots()
					+ " = "
					+ ((int) (1.0 * main.getScore() * (Main.getStatistic().getShots() - Main.getStatistic().getMissed())
							/ Main.getStatistic().getShots()));
			main.getMessage()[18] = "";
			main.setYodaObj(new FinalYoda(main, main.getMessage()));
			main.add(main.getYodaObj());
			main.setPaused(true);
			this.selfDestroy(main);
		}
	}

}
