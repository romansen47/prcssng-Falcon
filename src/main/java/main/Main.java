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
import processing.core.PImage;
import processing.core.PShape;
import processing.event.MouseEvent;
import processing.template.Gui;
import temperature.Ball;

public class Main extends Gui {

	public static void main(String[] args) {
		(new Main()).run("main.Main");
	}

	/**
	 * Collector structure
	 */
	private final static StatsCollector statistic = StatsCollector.getInstance();

	/**
	 * Counter for score
	 */
	private int Score;

	/**
	 * trigger for stopping the game
	 */
	private boolean paused = false;

	/**
	 * trigger for mouse click
	 */
	private int clicked;

	/**
	 * Counter for frames
	 */
	private int frameCount;

	/**
	 * Container for the EnemyFlyer instances. These instances interact when they
	 * collide
	 */
	private EnemyFlyer[] Enemies;

	/**
	 * Container for all objects that are drawn on a frame
	 */
	private IDrawable[] objects;

	/**
	 * HanSolo object as an attribute
	 */
	private IDrawable HanSolo;

	/**
	 * Minim sound player instance
	 */
	final public Minim mn = new Minim(this);

	/**
	 * Sound samples
	 */
	private AudioSample player, PlainGunSound, ExplosionSound, ChewBacca, han, Vader, yodaLaughter;

	/**
	 * prefix for fully qualified path name
	 */
	private static String prefix;

	/**
	 * StarFghter or DeathStar as private instances
	 */
	private IDrawable Boss;

	/**
	 * Counter for level of game
	 */
	private int level = 0;

	/**
	 * Various test data
	 */
	private PShape heart, explosion, muni, yoda, flyer, stardestroyer, deathStar, benefit, hansolo, asteroid, darth;

	/**
	 * test data
	 */
	private PImage BackGround;

	/**
	 * Yoda messenger instance
	 */
	private Yoda Yoda;

	/**
	 * Message for Yoda
	 */
	private String[] message;

	@Override
	public void settings() {
		fullScreen(P3D, 2);
		frameRate = 30;
	}

	@Override
	public void setup() {

		// frameRate=30;
		strokeWeight(3);
		stroke(0, 0, 0);
		noCursor();
		if (OSValidator.isUnix() || OSValidator.isMac()) {
			prefix = "/tmp/data/";
		} else {
			prefix = "C:/Temp/data/";
		}

		prefix="";
		
		BackGround = loadImage(prefix + "space.jpg");

		Enemies = new EnemyFlyer[0];
		heart = loadShape(prefix + "heartbeat.svg");
		muni = loadShape(prefix + "bullets.svg");
		flyer = loadShape(prefix + "tiefighter.svg");
		setHansolo(loadShape(prefix + "milleniumFalcon.svg"));
		setExplosion(loadShape(prefix + "explosion.svg"));
		stardestroyer = loadShape(prefix + "StarDestroyer.svg");
		setDeathStar(loadShape(prefix + "deathStar.svg"));
		benefit = loadShape(prefix + "energy.svg");
		setAsteroid(loadShape(prefix + "ast1.svg"));
		setYoda(loadShape(prefix + "yoda.svg"));
		setDarth(loadShape(prefix + "vader.svg"));

		prefix="";
		try {
			setPlainGunSound(mn.loadSample(sketchPath(prefix + "laser.mp3")));
			setExplosionSound(mn.loadSample(sketchPath(prefix + "explosion.mp3")));
			setChewBacca(mn.loadSample(sketchPath(prefix + "chewbacca.wav")));
			setHan(mn.loadSample(sketchPath(prefix + "han.mp3")));
			setVader(mn.loadSample(sketchPath(prefix + "Vader.mp3")));
			setYodaLaughter(mn.loadSample(sketchPath(prefix + "YodaLaughter.mp3")));
			setPlayer(mn.loadSample(sketchPath(prefix + "Theme.mp3")));
		}catch(NullPointerException npe) {
			System.out.println("falscher Ordner!");
		}finally {
			prefix="./data/";
			setPlainGunSound(mn.loadSample(sketchPath(prefix + "laser.mp3")));
			setExplosionSound(mn.loadSample(sketchPath(prefix + "explosion.mp3")));
			setChewBacca(mn.loadSample(sketchPath(prefix + "chewbacca.wav")));
			setHan(mn.loadSample(sketchPath(prefix + "han.mp3")));
			setVader(mn.loadSample(sketchPath(prefix + "Vader.mp3")));
			setYodaLaughter(mn.loadSample(sketchPath(prefix + "YodaLaughter.mp3")));
			setPlayer(mn.loadSample(sketchPath(prefix + "Theme.mp3")));
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

	@Override
	public void draw() {
		image(BackGround, 0, 0, width, height);

		if (!paused) {

			setFrameCount(getFrameCount() + 1);

			HealHanSolo();
			createBoss();
			createRandomObjects();
			drawInformation();

			setClicked(clicked());
			for (IDrawable obj : getObjects()) {
				obj.move(this);
				obj.draw(this);
			}
		} else {
			getYodaObj().move(this);
		}
		Ball[] tmpPositions = temperature.Functions.Collision(Enemies, 1);
		for (int i = 0; i < Enemies.length; i++) {
			Enemies[i].setX(((int) (tmpPositions[i].getPosition()[0])));
			Enemies[i].setY(((int) (tmpPositions[i].getPosition()[1])));
			Enemies[i].setSpeedX(tmpPositions[i].getVelocity()[0]);
			Enemies[i].setSpeedY(tmpPositions[i].getVelocity()[1]);
		}

		// setEnemies((EnemyFlyer[])();
	}

	public void HealHanSolo() {
		if (getFrameCount() == 1) {
			getHan().trigger();
		}
		if (getFrameCount() % 80 == 0) {
			((HanSolo) this.getHanSolo()).setHealth(((HanSolo) this.getHanSolo()).getHealth() + 1);
			((HanSolo) this.getHanSolo()).setMuni(((HanSolo) this.getHanSolo()).getMuni() + 10);
		}
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
			EnemyFlyer tmp = new EnemyFlyer(this);
			if (getBoss() != null) {
				if ((new Random()).nextBoolean()) {
					tmp.setX(150 + (new Random()).nextInt(50));
				} else {
					tmp.setX(this.Width - 200 - (new Random()).nextInt(50));
				}
			}
			addOnTop(tmp);
			addEnemy(tmp);
		}
		if ((new Random()).nextInt(1000) > 995 - getLevel() * 5) {
			if (getBoss() == null) {
				Asteroid tmp = new Asteroid(this);
				addOnTop(tmp);
			}
		}
	}

	public void drawInformation() {
		fill(255 * (100 - ((HanSolo) getHanSolo()).getHealth()) / (float)((HanSolo) getHanSolo()).getMaxHealth(),
				255 * ((HanSolo) getHanSolo()).getHealth() / (float)((HanSolo) getHanSolo()).getMaxHealth(), 0);
		rect(30, 30, (this.Width / 3.0f) * ((HanSolo) getHanSolo()).getHealth() / (float)((HanSolo) getHanSolo()).getMaxHealth(),
				30);
		shape(heart, 40, 30, 30, 30);

		fill(0, 0, 255);
		rect(30, 80, (this.Width / 3.0f) * ((HanSolo) getHanSolo()).getMuni() / (float)((HanSolo) getHanSolo()).getMaxMuni(), 30);
		shape(muni, 40, 80, 30, 30);

		fill(0, 255, 255);
		rect(this.Width - (float)300, 30, 280, 60, 25);
		textSize(30);
		fill(0);
		text("Score: " + ((HanSolo) (getHanSolo())).getScore(), Width - (float)260, 80);
	}

	public void addOnTop(IDrawable obj) {
		IDrawable[] tmp = getObjects().clone();
		setObjects(new IDrawable[tmp.length + 1]);
		for (int i = 0; i < tmp.length; i++) {
			getObjects()[i] = tmp[i];
		}
		getObjects()[tmp.length] = obj;
	}

	public int getIndex(IDrawable element) {
		for (int i = 0; i < getObjects().length; i++) {
			if (getObjects()[i] == element) {
				return i;
			}
		}
		return (-1);
	}

	public void add(IDrawable obj) {
		IDrawable[] tmp = getObjects().clone();
		setObjects(new IDrawable[tmp.length + 1]);
		getObjects()[0] = obj;
		for (int i = 0; i < tmp.length; i++) {
			getObjects()[i + 1] = tmp[i];
		}
	}

	public void remove(int pos) {
		if (this.getObjects().length > 0 && pos >= 0) {
			IDrawable[] tmpElements = new IDrawable[this.getObjects().length - 1];
			for (int i = 0; i < pos; i++) {
				tmpElements[i] = this.getObjects()[i];
			}
			for (int i = pos; i < this.getObjects().length - 1; i++) {
				tmpElements[i] = this.getObjects()[i + 1];
			}
			setObjects(tmpElements);
		}
	}

	public void remove(IDrawable obj) {
		remove(getIndex(obj));
	}

	public IDrawable[] getObjects() {
		return objects;
	}

	public void setObjects(IDrawable[] objects) {
		this.objects = objects;
	}

	public PShape getExplosion() {
		return explosion;
	}

	public void setExplosion(PShape explosion) {
		this.explosion = explosion;
	}

	public PShape getHansolo() {
		return hansolo;
	}

	public void setHansolo(PShape hansolo) {
		this.hansolo = hansolo;
	}

	public PShape getFlyer() {
		return flyer;
	}

	public void setFlyer(PShape flyer) {
		this.flyer = flyer;
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		float e = event.getCount();
		if (e > 0) {
			((HanSolo) getHanSolo()).nextGun();
		} else {
			((HanSolo) getHanSolo()).prevGun();
		}
	}

	public PShape getStardestroyer() {
		return stardestroyer;
	}

	public void setStardestroyer(PShape stardestroyer) {
		this.stardestroyer = stardestroyer;
	}

	public PShape getBenefit() {
		return benefit;
	}

	public void setBenefit(PShape benefit) {
		this.benefit = benefit;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return this.Width;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public EnemyFlyer[] getEnemies() {
		return Enemies;
	}

	public void setEnemies(EnemyFlyer[] enemies) {
		Enemies = enemies;
	}

	public int getEnemyIndex(Flyer element) {
		for (int i = 0; i < Enemies.length; i++) {
			if (Enemies[i] == element) {
				return i;
			}
		}
		return (-1);
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
		getStatistic().setEnemies(getStatistic().getEnemies() + 1);
	}

	public void removeEnemy(int pos) {
		if (Enemies.length > 0 && pos >= 0) {
			EnemyFlyer[] tmpEnemies = new EnemyFlyer[Enemies.length - 1];
			for (int i = 0; i < pos; i++) {
				tmpEnemies[i] = Enemies[i];
			}
			for (int i = pos; i < this.Enemies.length - 1; i++) {
				tmpEnemies[i] = Enemies[i + 1];
			}
			Enemies = tmpEnemies;
		}
	}

	public static StatsCollector getStatistic() {
		return statistic;
	}

	public int getClicked() {
		return clicked;
	}

	public void setClicked(int clicked) {
		this.clicked = clicked;
	}

	public AudioSample getYodaLaughter() {
		return yodaLaughter;
	}

	public void setYodaLaughter(AudioSample yodaLaughter) {
		this.yodaLaughter = yodaLaughter;
	}

	public AudioSample getPlainGunSound() {
		return PlainGunSound;
	}

	public void setPlainGunSound(AudioSample plainGunSound) {
		PlainGunSound = plainGunSound;
	}

	public AudioSample getExplosionSound() {
		return ExplosionSound;
	}

	public void setExplosionSound(AudioSample explosionSound) {
		ExplosionSound = explosionSound;
	}

	public AudioSample getChewBacca() {
		return ChewBacca;
	}

	public void setChewBacca(AudioSample chewBacca) {
		ChewBacca = chewBacca;
	}

	public AudioSample getVader() {
		return Vader;
	}

	public void setVader(AudioSample vader) {
		Vader = vader;
	}

	public PShape getDeathStar() {
		return deathStar;
	}

	public void setDeathStar(PShape deathStar) {
		this.deathStar = deathStar;
	}

	public PShape getAsteroid() {
		return asteroid;
	}

	public void setAsteroid(PShape asteroid) {
		this.asteroid = asteroid;
	}

	public PShape getDarth() {
		return darth;
	}

	public void setDarth(PShape darth) {
		this.darth = darth;
	}

	public PShape getYoda() {
		return yoda;
	}

	public void setYoda(PShape yoda) {
		this.yoda = yoda;
	}

	public IDrawable getHanSolo() {
		return HanSolo;
	}

	public void setHanSolo(IDrawable hanSolo) {
		HanSolo = hanSolo;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

	public IDrawable getBoss() {
		return Boss;
	}

	public void setBoss(IDrawable boss) {
		Boss = boss;
	}

	public AudioSample getHan() {
		return han;
	}

	public void setHan(AudioSample han) {
		this.han = han;
	}

	public AudioSample getPlayer() {
		return player;
	}

	public void setPlayer(AudioSample player) {
		this.player = player;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	public Yoda getYodaObj() {
		return Yoda;
	}

	public void setYodaObj(Yoda yoda) {
		Yoda = yoda;
	}
}
