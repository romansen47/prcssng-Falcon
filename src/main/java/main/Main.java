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
import temperature.Functions;
import temperature.IFunctions;

public class Main extends Gui {

	/**
	 * Math functions
	 */
	private final IFunctions functions = Functions.getInstance();

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
		final IDrawable[] tmp = getObjects().clone();
		setObjects(new IDrawable[tmp.length + 1]);
		getObjects()[0] = obj;
		for (int i = 0; i < tmp.length; i++) {
			getObjects()[i + 1] = tmp[i];
		}
	}

	public void addEnemy(EnemyFlyer obj) {
		EnemyFlyer[] tmp;
		if (Enemies != null && Enemies.length > 0) {
			tmp = Enemies.clone();
		} else {
			tmp = new EnemyFlyer[0];
		}
		Enemies = new EnemyFlyer[tmp.length + 1];
		Enemies[0] = obj;
		for (int i = 0; i < tmp.length; i++) {
			Enemies[i + 1] = tmp[i];
		}
		Main.getStatistic().setEnemies(Main.getStatistic().getEnemies() + 1);
	}

	public void addOnTop(IDrawable obj) {
		final IDrawable[] tmp = getObjects().clone();
		setObjects(new IDrawable[tmp.length + 1]);
		for (int i = 0; i < tmp.length; i++) {
			getObjects()[i] = tmp[i];
		}
		getObjects()[tmp.length] = obj;
	}

	public void createBoss() {
		if (getFrameCount() == 1500 && getBoss() == null) {
			add(new StarDestroyer(this));
		}
		if (getFrameCount() == 3000) {
			if (getBoss() == null) {
				add(new SecondStarDestroyer(this));
			} else {
				setFrameCount(1501);
			}
		}
		if (getFrameCount() == 4500) {
			if (getBoss() == null) {
				add(new ThirdStarDestroyer(this));
			} else {
				setFrameCount(3001);
			}
		}
		if (getFrameCount() == 6000) {
			if (getBoss() == null) {
				add(new ForthStarDestroyer(this));
			} else {
				setFrameCount(4501);
			}
		}
		if (getFrameCount() == 7500) {
			if (getBoss() == null) {
				add(new DeathStar(this));
			} else {
				setFrameCount(6001);
			}
		}
	}

	public void createRandomObjects() {
		if ((new Random()).nextInt(1000) > 950 - getLevel()) {
			final EnemyFlyer tmp = new EnemyFlyer(this);
			if (getBoss() != null) {
				if ((new Random()).nextBoolean()) {
					tmp.setX(150 + (new Random()).nextInt(50));
				} else {
					tmp.setX(Width - 200 - (new Random()).nextInt(50));
				}
			}
			addOnTop(tmp);
			addEnemy(tmp);
		}
		if ((new Random()).nextInt(1000) > 995 - getLevel() * 5) {
			if (getBoss() == null) {
				final Asteroid tmp = new Asteroid(this);
				addOnTop(tmp);
			}
		}
	}

	@Override
	public void draw() {
		this.image(BackGround, 0, 0, width, height);

		if (!paused) {

			setFrameCount(getFrameCount() + 1);

			HealHanSolo();
			createBoss();
			createRandomObjects();
			drawInformation();

			setClicked(clicked());
			for (final IDrawable obj : getObjects()) {
				obj.move(this);
				obj.draw(this);
			}
		} else {
			getYodaObj().move(this);
		}
		final Ball[] tmpPositions = getFunctions().collision(Enemies, 1);
		for (int i = 0; i < Enemies.length; i++) {
			Enemies[i].setX(((int) (tmpPositions[i].getPosition()[0])));
			Enemies[i].setY(((int) (tmpPositions[i].getPosition()[1])));
			Enemies[i].setSpeedX(tmpPositions[i].getVelocity()[0]);
			Enemies[i].setSpeedY(tmpPositions[i].getVelocity()[1]);
		}

		// setEnemies((EnemyFlyer[])();
	}

	public void drawInformation() {
		this.fill(255 * (100 - ((HanSolo) getHanSolo()).getHealth()) / (float) ((HanSolo) getHanSolo()).getMaxHealth(),
				255 * ((HanSolo) getHanSolo()).getHealth() / (float) ((HanSolo) getHanSolo()).getMaxHealth(), 0);
		this.rect(30, 30,
				(Width / 3.0f) * ((HanSolo) getHanSolo()).getHealth() / ((HanSolo) getHanSolo()).getMaxHealth(), 30);
		this.shape(heart, 40, 30, 30, 30);

		this.fill(0, 0, 255);
		this.rect(30, 80, (Width / 3.0f) * ((HanSolo) getHanSolo()).getMuni() / ((HanSolo) getHanSolo()).getMaxMuni(),
				30);
		this.shape(muni, 40, 80, 30, 30);

		this.fill(0, 255, 255);
		this.rect(Width - (float) 300, 30, 280, 60, 25);
		textSize(30);
		this.fill(0);
		this.text("Score: " + ((HanSolo) (getHanSolo())).getScore(), Width - (float) 260, 80);
	}

	public PShape getAsteroid() {
		return asteroid;
	}

	public PShape getBenefit() {
		return benefit;
	}

	public IDrawable getBoss() {
		return Boss;
	}

	public AudioSample getChewBacca() {
		return ChewBacca;
	}

	public int getClicked() {
		return clicked;
	}

	public PShape getDarth() {
		return darth;
	}

	public PShape getDeathStar() {
		return deathStar;
	}

	public EnemyFlyer[] getEnemies() {
		return Enemies;
	}

	public int getEnemyIndex(Flyer element) {
		for (int i = 0; i < Enemies.length; i++) {
			if (Enemies[i] == element) {
				return i;
			}
		}
		return (-1);
	}

	public PShape getExplosion() {
		return explosion;
	}

	public AudioSample getExplosionSound() {
		return ExplosionSound;
	}

	public PShape getFlyer() {
		return flyer;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public AudioSample getHan() {
		return han;
	}

	public PShape getHansolo() {
		return hansolo;
	}

	public IDrawable getHanSolo() {
		return HanSolo;
	}

	public int getIndex(IDrawable element) {
		for (int i = 0; i < getObjects().length; i++) {
			if (getObjects()[i] == element) {
				return i;
			}
		}
		return (-1);
	}

	public int getLevel() {
		return level;
	}

	public String[] getMessage() {
		return message;
	}

	public IDrawable[] getObjects() {
		return objects;
	}

	public AudioSample getPlainGunSound() {
		return PlainGunSound;
	}

	public AudioSample getPlayer() {
		return player;
	}

	public int getScore() {
		return Score;
	}

	public PShape getStardestroyer() {
		return stardestroyer;
	}

	public AudioSample getVader() {
		return Vader;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return Width;
	}

	public PShape getYoda() {
		return yoda;
	}

	public AudioSample getYodaLaughter() {
		return yodaLaughter;
	}

	public Yoda getYodaObj() {
		return Yoda;
	}

	public void HealHanSolo() {
		if (getFrameCount() == 1) {
			getHan().trigger();
		}
		if (getFrameCount() % 80 == 0) {
			((HanSolo) getHanSolo()).setHealth(((HanSolo) getHanSolo()).getHealth() + 1);
			((HanSolo) getHanSolo()).setMuni(((HanSolo) getHanSolo()).getMuni() + 10);
		}
	}

	public boolean isPaused() {
		return paused;
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		final float e = event.getCount();
		if (e > 0) {
			((HanSolo) getHanSolo()).nextGun();
		} else {
			((HanSolo) getHanSolo()).prevGun();
		}
	}

	public void remove(IDrawable obj) {
		this.remove(getIndex(obj));
	}

	public void remove(int pos) {
		if (getObjects().length > 0 && pos >= 0) {
			final IDrawable[] tmpElements = new IDrawable[getObjects().length - 1];
			for (int i = 0; i < pos; i++) {
				tmpElements[i] = getObjects()[i];
			}
			for (int i = pos; i < getObjects().length - 1; i++) {
				tmpElements[i] = getObjects()[i + 1];
			}
			setObjects(tmpElements);
		}
	}

	public void removeEnemy(int pos) {
		if (Enemies.length > 0 && pos >= 0) {
			final EnemyFlyer[] tmpEnemies = new EnemyFlyer[Enemies.length - 1];
			for (int i = 0; i < pos; i++) {
				tmpEnemies[i] = Enemies[i];
			}
			for (int i = pos; i < Enemies.length - 1; i++) {
				tmpEnemies[i] = Enemies[i + 1];
			}
			Enemies = tmpEnemies;
		}
	}

	public void setAsteroid(PShape asteroid) {
		this.asteroid = asteroid;
	}

	public void setBenefit(PShape benefit) {
		this.benefit = benefit;
	}

	public void setBoss(IDrawable boss) {
		Boss = boss;
	}

	public void setChewBacca(AudioSample chewBacca) {
		ChewBacca = chewBacca;
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
		Enemies = enemies;
	}

	public void setExplosion(PShape explosion) {
		this.explosion = explosion;
	}

	public void setExplosionSound(AudioSample explosionSound) {
		ExplosionSound = explosionSound;
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
		HanSolo = hanSolo;
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
		PlainGunSound = plainGunSound;
	}

	public void setPlayer(AudioSample player) {
		this.player = player;
	}

	public void setScore(int score) {
		Score = score;
	}

	public void setStardestroyer(PShape stardestroyer) {
		this.stardestroyer = stardestroyer;
	}

	@Override
	public void settings() {
		this.fullScreen(PConstants.P3D, 2);
		frameRate = 30;
	}

	@Override
	public void setup() {

		// frameRate=30;
		strokeWeight(3);
		this.stroke(0, 0, 0);
		noCursor();
		if (OSValidator.isUnix() || OSValidator.isMac()) {
			Main.prefix = "/tmp/data/";
		} else {
			Main.prefix = "C:/Temp/data/";
		}

		Main.prefix = "data/";

		BackGround = this.loadImage(Main.prefix + "space.jpg");

		Enemies = new EnemyFlyer[0];
		heart = this.loadShape(Main.prefix + "heartbeat.svg");
		muni = this.loadShape(Main.prefix + "bullets.svg");
		flyer = this.loadShape(Main.prefix + "tiefighter.svg");
		setHansolo(this.loadShape(Main.prefix + "milleniumFalcon.svg"));
		setExplosion(this.loadShape(Main.prefix + "explosion.svg"));
		stardestroyer = this.loadShape(Main.prefix + "StarDestroyer.svg");
		setDeathStar(this.loadShape(Main.prefix + "deathStar.svg"));
		benefit = this.loadShape(Main.prefix + "energy.svg");
		setAsteroid(this.loadShape(Main.prefix + "ast1.svg"));
		setYoda(this.loadShape(Main.prefix + "yoda.svg"));
		setDarth(this.loadShape(Main.prefix + "vader.svg"));

		Main.prefix = "";
		try {
			setPlainGunSound(mn.loadSample(this.sketchPath(Main.prefix + "laser.mp3")));
			setExplosionSound(mn.loadSample(this.sketchPath(Main.prefix + "explosion.mp3")));
			setChewBacca(mn.loadSample(this.sketchPath(Main.prefix + "chewbacca.wav")));
			setHan(mn.loadSample(this.sketchPath(Main.prefix + "han.mp3")));
			setVader(mn.loadSample(this.sketchPath(Main.prefix + "Vader.mp3")));
			setYodaLaughter(mn.loadSample(this.sketchPath(Main.prefix + "YodaLaughter.mp3")));
			setPlayer(mn.loadSample(this.sketchPath(Main.prefix + "Theme.mp3")));
		} catch (final NullPointerException npe) {
			System.out.println("falscher Ordner!");
		} finally {
			Main.prefix = "./data/";
			setPlainGunSound(mn.loadSample(this.sketchPath(Main.prefix + "laser.mp3")));
			setExplosionSound(mn.loadSample(this.sketchPath(Main.prefix + "explosion.mp3")));
			setChewBacca(mn.loadSample(this.sketchPath(Main.prefix + "chewbacca.wav")));
			setHan(mn.loadSample(this.sketchPath(Main.prefix + "han.mp3")));
			setVader(mn.loadSample(this.sketchPath(Main.prefix + "Vader.mp3")));
			setYodaLaughter(mn.loadSample(this.sketchPath(Main.prefix + "YodaLaughter.mp3")));
			setPlayer(mn.loadSample(this.sketchPath(Main.prefix + "Theme.mp3")));
		}
		setObjects(new IDrawable[0]);
		setHanSolo(new HanSolo(this));
		add(getHanSolo());
		getPlayer().trigger();

		setMessage(new String[6]);
		getMessage()[0] = "";
		getMessage()[1] = "Bald fertiggestellt der Todesstern";
		getMessage()[2] = "sein wird, Han";
		getMessage()[3] = "";
		getMessage()[4] = "Zuvor vernichten ihn du musst";
		getMessage()[5] = "Aufhalten das Imperium dich will";
		setYodaObj(new Yoda(this, getMessage()));
		add(getYodaObj());
		// add(new DarthVaderFlyer(this));
		// this.setBoss(this.getObjects()[0]);
		// addEnemy((EnemyFlyer)this.getBoss());
		// add(new ForthStarDestroyer(this));
	}

	public void setVader(AudioSample vader) {
		Vader = vader;
	}

	public void setYoda(PShape yoda) {
		this.yoda = yoda;
	}

	public void setYodaLaughter(AudioSample yodaLaughter) {
		this.yodaLaughter = yodaLaughter;
	}

	public void setYodaObj(Yoda yoda) {
		Yoda = yoda;
	}

	/**
	 * @return the functions
	 */
	public IFunctions getFunctions() {
		return functions;
	}
}
