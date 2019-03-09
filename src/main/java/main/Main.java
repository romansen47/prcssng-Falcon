/**
 * @Author Ro Mansen
 */

package main;

import java.util.Random;

import ddf.minim.AudioSample;
import ddf.minim.Minim;
import defs.interfaces.IDrawable;
import defs.objects.flyer.DeathStar;
import defs.objects.flyer.EnemyFlyer;
import defs.objects.flyer.Flyer;
import defs.objects.flyer.ForthStarDestroyer;
import defs.objects.flyer.HanSolo;
import defs.objects.flyer.SecondStarDestroyer;
import defs.objects.flyer.StarDestroyer;
import defs.objects.flyer.ThirdStarDestroyer;
import defs.objects.munition.Asteroid;
import defs.objects.yoda.Yoda;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import processing.event.MouseEvent;
import processing.template.Gui;
import temperature.Ball;

public class Main extends Gui {

	/**
	 * prefix for fully qualified path name
	 */
	private static String prefix;

	/**
	 * Collector structure
	 */
	private final static StatsCollector statistic = StatsCollector.getInstance();

	public static StatsCollector getStatistic() {
		return Main.statistic;
	}

	public static void main(String[] args) {
		(new Main()).run("main.Main");
	}

	/**
	 * test data
	 */
	private PImage BackGround;

	/**
	 * StarFghter or DeathStar as private instances
	 */
	private IDrawable Boss;

	/**
	 * trigger for mouse click
	 */
	private int clicked;

	/**
	 * Container for the EnemyFlyer instances. These instances interact when they
	 * collide
	 */
	private EnemyFlyer[] Enemies;

	/**
	 * Counter for frames
	 */
	private int frameCount;

	/**
	 * HanSolo object as an attribute
	 */
	private IDrawable HanSolo;

	/**
	 * Various test data
	 */
	private PShape heart, explosion, muni, yoda, flyer, stardestroyer, deathStar, benefit, hansolo, asteroid, darth;

	/**
	 * Counter for level of game
	 */
	private int level = 0;

	/**
	 * Message for Yoda
	 */
	private String[] message;

	/**
	 * Minim sound player instance
	 */
	final public Minim mn = new Minim(this);

	/**
	 * Container for all objects that are drawn on a frame
	 */
	private IDrawable[] objects;

	/**
	 * trigger for stopping the game
	 */
	private boolean paused = false;

	/**
	 * Sound samples
	 */
	private AudioSample player, PlainGunSound, ExplosionSound, ChewBacca, han, Vader, yodaLaughter;

	/**
	 * Counter for score
	 */
	private int Score;

	/**
	 * Yoda messenger instance
	 */
	private Yoda Yoda;

	public void add(IDrawable obj) {
		final IDrawable[] tmp = this.getObjects().clone();
		this.setObjects(new IDrawable[tmp.length + 1]);
		this.getObjects()[0] = obj;
		for (int i = 0; i < tmp.length; i++) {
			this.getObjects()[i + 1] = tmp[i];
		}
	}

	public void addEnemy(EnemyFlyer obj) {
		EnemyFlyer[] tmp;
		if (this.Enemies != null && this.Enemies.length > 0) {
			tmp = this.Enemies.clone();
		} else {
			tmp = new EnemyFlyer[0];
		}
		this.Enemies = new EnemyFlyer[tmp.length + 1];
		this.Enemies[0] = obj;
		for (int i = 0; i < tmp.length; i++) {
			this.Enemies[i + 1] = tmp[i];
		}
		Main.getStatistic().setEnemies(Main.getStatistic().getEnemies() + 1);
	}

	public void addOnTop(IDrawable obj) {
		final IDrawable[] tmp = this.getObjects().clone();
		this.setObjects(new IDrawable[tmp.length + 1]);
		for (int i = 0; i < tmp.length; i++) {
			this.getObjects()[i] = tmp[i];
		}
		this.getObjects()[tmp.length] = obj;
	}

	public void createBoss() {
		if (this.getFrameCount() == 1500 && this.getBoss() == null) {
			this.add(new StarDestroyer(this));
		}
		if (this.getFrameCount() == 3000) {
			if (this.getBoss() == null) {
				this.add(new SecondStarDestroyer(this));
			} else {
				this.setFrameCount(1501);
			}
		}
		if (this.getFrameCount() == 4500) {
			if (this.getBoss() == null) {
				this.add(new ThirdStarDestroyer(this));
			} else {
				this.setFrameCount(3001);
			}
		}
		if (this.getFrameCount() == 6000) {
			if (this.getBoss() == null) {
				this.add(new ForthStarDestroyer(this));
			} else {
				this.setFrameCount(4501);
			}
		}
		if (this.getFrameCount() == 7500) {
			if (this.getBoss() == null) {
				this.add(new DeathStar(this));
			} else {
				this.setFrameCount(6001);
			}
		}
	}

	public void createRandomObjects() {
		if ((new Random()).nextInt(1000) > 950 - this.getLevel()) {
			final EnemyFlyer tmp = new EnemyFlyer(this);
			if (this.getBoss() != null) {
				if ((new Random()).nextBoolean()) {
					tmp.setX(150 + (new Random()).nextInt(50));
				} else {
					tmp.setX(this.Width - 200 - (new Random()).nextInt(50));
				}
			}
			this.addOnTop(tmp);
			this.addEnemy(tmp);
		}
		if ((new Random()).nextInt(1000) > 995 - this.getLevel() * 5) {
			if (this.getBoss() == null) {
				final Asteroid tmp = new Asteroid(this);
				this.addOnTop(tmp);
			}
		}
	}

	@Override
	public void draw() {
		this.image(this.BackGround, 0, 0, this.width, this.height);

		if (!this.paused) {

			this.setFrameCount(this.getFrameCount() + 1);

			this.HealHanSolo();
			this.createBoss();
			this.createRandomObjects();
			this.drawInformation();

			this.setClicked(this.clicked());
			for (final IDrawable obj : this.getObjects()) {
				obj.move(this);
				obj.draw(this);
			}
		} else {
			this.getYodaObj().move(this);
		}
		final Ball[] tmpPositions = temperature.Functions.collision(this.Enemies, 1);
		for (int i = 0; i < this.Enemies.length; i++) {
			this.Enemies[i].setX(((int) (tmpPositions[i].getPosition()[0])));
			this.Enemies[i].setY(((int) (tmpPositions[i].getPosition()[1])));
			this.Enemies[i].setSpeedX(tmpPositions[i].getVelocity()[0]);
			this.Enemies[i].setSpeedY(tmpPositions[i].getVelocity()[1]);
		}

		// setEnemies((EnemyFlyer[])();
	}

	public void drawInformation() {
		this.fill(
				255 * (100 - ((HanSolo) this.getHanSolo()).getHealth())
						/ (float) ((HanSolo) this.getHanSolo()).getMaxHealth(),
				255 * ((HanSolo) this.getHanSolo()).getHealth() / (float) ((HanSolo) this.getHanSolo()).getMaxHealth(),
				0);
		this.rect(30, 30, (this.Width / 3.0f) * ((HanSolo) this.getHanSolo()).getHealth()
				/ ((HanSolo) this.getHanSolo()).getMaxHealth(), 30);
		this.shape(this.heart, 40, 30, 30, 30);

		this.fill(0, 0, 255);
		this.rect(30, 80, (this.Width / 3.0f) * ((HanSolo) this.getHanSolo()).getMuni()
				/ ((HanSolo) this.getHanSolo()).getMaxMuni(), 30);
		this.shape(this.muni, 40, 80, 30, 30);

		this.fill(0, 255, 255);
		this.rect(this.Width - (float) 300, 30, 280, 60, 25);
		this.textSize(30);
		this.fill(0);
		this.text("Score: " + ((HanSolo) (this.getHanSolo())).getScore(), this.Width - (float) 260, 80);
	}

	public PShape getAsteroid() {
		return this.asteroid;
	}

	public PShape getBenefit() {
		return this.benefit;
	}

	public IDrawable getBoss() {
		return this.Boss;
	}

	public AudioSample getChewBacca() {
		return this.ChewBacca;
	}

	public int getClicked() {
		return this.clicked;
	}

	public PShape getDarth() {
		return this.darth;
	}

	public PShape getDeathStar() {
		return this.deathStar;
	}

	public EnemyFlyer[] getEnemies() {
		return this.Enemies;
	}

	public int getEnemyIndex(Flyer element) {
		for (int i = 0; i < this.Enemies.length; i++) {
			if (this.Enemies[i] == element) {
				return i;
			}
		}
		return (-1);
	}

	public PShape getExplosion() {
		return this.explosion;
	}

	public AudioSample getExplosionSound() {
		return this.ExplosionSound;
	}

	public PShape getFlyer() {
		return this.flyer;
	}

	public int getFrameCount() {
		return this.frameCount;
	}

	public AudioSample getHan() {
		return this.han;
	}

	public PShape getHansolo() {
		return this.hansolo;
	}

	public IDrawable getHanSolo() {
		return this.HanSolo;
	}

	public int getIndex(IDrawable element) {
		for (int i = 0; i < this.getObjects().length; i++) {
			if (this.getObjects()[i] == element) {
				return i;
			}
		}
		return (-1);
	}

	public int getLevel() {
		return this.level;
	}

	public String[] getMessage() {
		return this.message;
	}

	public IDrawable[] getObjects() {
		return this.objects;
	}

	public AudioSample getPlainGunSound() {
		return this.PlainGunSound;
	}

	public AudioSample getPlayer() {
		return this.player;
	}

	public int getScore() {
		return this.Score;
	}

	public PShape getStardestroyer() {
		return this.stardestroyer;
	}

	public AudioSample getVader() {
		return this.Vader;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return this.Width;
	}

	public PShape getYoda() {
		return this.yoda;
	}

	public AudioSample getYodaLaughter() {
		return this.yodaLaughter;
	}

	public Yoda getYodaObj() {
		return this.Yoda;
	}

	public void HealHanSolo() {
		if (this.getFrameCount() == 1) {
			this.getHan().trigger();
		}
		if (this.getFrameCount() % 80 == 0) {
			((HanSolo) this.getHanSolo()).setHealth(((HanSolo) this.getHanSolo()).getHealth() + 1);
			((HanSolo) this.getHanSolo()).setMuni(((HanSolo) this.getHanSolo()).getMuni() + 10);
		}
	}

	public boolean isPaused() {
		return this.paused;
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		final float e = event.getCount();
		if (e > 0) {
			((HanSolo) this.getHanSolo()).nextGun();
		} else {
			((HanSolo) this.getHanSolo()).prevGun();
		}
	}

	public void remove(IDrawable obj) {
		this.remove(this.getIndex(obj));
	}

	public void remove(int pos) {
		if (this.getObjects().length > 0 && pos >= 0) {
			final IDrawable[] tmpElements = new IDrawable[this.getObjects().length - 1];
			for (int i = 0; i < pos; i++) {
				tmpElements[i] = this.getObjects()[i];
			}
			for (int i = pos; i < this.getObjects().length - 1; i++) {
				tmpElements[i] = this.getObjects()[i + 1];
			}
			this.setObjects(tmpElements);
		}
	}

	public void removeEnemy(int pos) {
		if (this.Enemies.length > 0 && pos >= 0) {
			final EnemyFlyer[] tmpEnemies = new EnemyFlyer[this.Enemies.length - 1];
			for (int i = 0; i < pos; i++) {
				tmpEnemies[i] = this.Enemies[i];
			}
			for (int i = pos; i < this.Enemies.length - 1; i++) {
				tmpEnemies[i] = this.Enemies[i + 1];
			}
			this.Enemies = tmpEnemies;
		}
	}

	public void setAsteroid(PShape asteroid) {
		this.asteroid = asteroid;
	}

	public void setBenefit(PShape benefit) {
		this.benefit = benefit;
	}

	public void setBoss(IDrawable boss) {
		this.Boss = boss;
	}

	public void setChewBacca(AudioSample chewBacca) {
		this.ChewBacca = chewBacca;
	}

	public void setClicked(int clicked) {
		this.clicked = clicked;
	}

	public void setDarth(PShape darth) {
		this.darth = darth;
	}

	public void setDeathStar(PShape deathStar) {
		this.deathStar = deathStar;
	}

	public void setEnemies(EnemyFlyer[] enemies) {
		this.Enemies = enemies;
	}

	public void setExplosion(PShape explosion) {
		this.explosion = explosion;
	}

	public void setExplosionSound(AudioSample explosionSound) {
		this.ExplosionSound = explosionSound;
	}

	public void setFlyer(PShape flyer) {
		this.flyer = flyer;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

	public void setHan(AudioSample han) {
		this.han = han;
	}

	public void setHansolo(PShape hansolo) {
		this.hansolo = hansolo;
	}

	public void setHanSolo(IDrawable hanSolo) {
		this.HanSolo = hanSolo;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	public void setObjects(IDrawable[] objects) {
		this.objects = objects;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void setPlainGunSound(AudioSample plainGunSound) {
		this.PlainGunSound = plainGunSound;
	}

	public void setPlayer(AudioSample player) {
		this.player = player;
	}

	public void setScore(int score) {
		this.Score = score;
	}

	public void setStardestroyer(PShape stardestroyer) {
		this.stardestroyer = stardestroyer;
	}

	@Override
	public void settings() {
		this.fullScreen(PConstants.P3D, 2);
		this.frameRate = 30;
	}

	@Override
	public void setup() {

		// frameRate=30;
		this.strokeWeight(3);
		this.stroke(0, 0, 0);
		this.noCursor();
		if (OSValidator.isUnix() || OSValidator.isMac()) {
			Main.prefix = "/tmp/data/";
		} else {
			Main.prefix = "C:/Temp/data/";
		}

		Main.prefix = "data/";

		this.BackGround = this.loadImage(Main.prefix + "space.jpg");

		this.Enemies = new EnemyFlyer[0];
		this.heart = this.loadShape(Main.prefix + "heartbeat.svg");
		this.muni = this.loadShape(Main.prefix + "bullets.svg");
		this.flyer = this.loadShape(Main.prefix + "tiefighter.svg");
		this.setHansolo(this.loadShape(Main.prefix + "milleniumFalcon.svg"));
		this.setExplosion(this.loadShape(Main.prefix + "explosion.svg"));
		this.stardestroyer = this.loadShape(Main.prefix + "StarDestroyer.svg");
		this.setDeathStar(this.loadShape(Main.prefix + "deathStar.svg"));
		this.benefit = this.loadShape(Main.prefix + "energy.svg");
		this.setAsteroid(this.loadShape(Main.prefix + "ast1.svg"));
		this.setYoda(this.loadShape(Main.prefix + "yoda.svg"));
		this.setDarth(this.loadShape(Main.prefix + "vader.svg"));

		Main.prefix = "";
		try {
			this.setPlainGunSound(this.mn.loadSample(this.sketchPath(Main.prefix + "laser.mp3")));
			this.setExplosionSound(this.mn.loadSample(this.sketchPath(Main.prefix + "explosion.mp3")));
			this.setChewBacca(this.mn.loadSample(this.sketchPath(Main.prefix + "chewbacca.wav")));
			this.setHan(this.mn.loadSample(this.sketchPath(Main.prefix + "han.mp3")));
			this.setVader(this.mn.loadSample(this.sketchPath(Main.prefix + "Vader.mp3")));
			this.setYodaLaughter(this.mn.loadSample(this.sketchPath(Main.prefix + "YodaLaughter.mp3")));
			this.setPlayer(this.mn.loadSample(this.sketchPath(Main.prefix + "Theme.mp3")));
		} catch (final NullPointerException npe) {
			System.out.println("falscher Ordner!");
		} finally {
			Main.prefix = "./data/";
			this.setPlainGunSound(this.mn.loadSample(this.sketchPath(Main.prefix + "laser.mp3")));
			this.setExplosionSound(this.mn.loadSample(this.sketchPath(Main.prefix + "explosion.mp3")));
			this.setChewBacca(this.mn.loadSample(this.sketchPath(Main.prefix + "chewbacca.wav")));
			this.setHan(this.mn.loadSample(this.sketchPath(Main.prefix + "han.mp3")));
			this.setVader(this.mn.loadSample(this.sketchPath(Main.prefix + "Vader.mp3")));
			this.setYodaLaughter(this.mn.loadSample(this.sketchPath(Main.prefix + "YodaLaughter.mp3")));
			this.setPlayer(this.mn.loadSample(this.sketchPath(Main.prefix + "Theme.mp3")));
		}
		this.setObjects(new IDrawable[0]);
		this.setHanSolo(new HanSolo(this));
		this.add(this.getHanSolo());
		this.getPlayer().trigger();

		this.setMessage(new String[6]);
		this.getMessage()[0] = "";
		this.getMessage()[1] = "Bald fertiggestellt der Todesstern";
		this.getMessage()[2] = "sein wird, Han";
		this.getMessage()[3] = "";
		this.getMessage()[4] = "Zuvor vernichten ihn du musst";
		this.getMessage()[5] = "Aufhalten das Imperium dich will";
		this.setYodaObj(new Yoda(this, this.getMessage()));
		this.add(this.getYodaObj());
		// add(new DarthVaderFlyer(this));
		// this.setBoss(this.getObjects()[0]);
		// addEnemy((EnemyFlyer)this.getBoss());
		// add(new ForthStarDestroyer(this));
	}

	public void setVader(AudioSample vader) {
		this.Vader = vader;
	}

	public void setYoda(PShape yoda) {
		this.yoda = yoda;
	}

	public void setYodaLaughter(AudioSample yodaLaughter) {
		this.yodaLaughter = yodaLaughter;
	}

	public void setYodaObj(Yoda yoda) {
		this.Yoda = yoda;
	}
}
