package main;

public class StatsCollector {

	private static StatsCollector instance;

	public static StatsCollector getInstance() {
		if (StatsCollector.instance == null) {
			StatsCollector.instance = new StatsCollector();
		}
		return StatsCollector.instance;
	}

	private boolean perfectGame;

	private int shots, missed, enemies, kills;

	private StatsCollector() {
		setShots(0);
		setMissed(0);
		setEnemies(0);
		setKills(0);
		setPerfectGame(true);
	}

	public int getEnemies() {
		return enemies;
	}

	public int getKills() {
		return kills;
	}

	public int getMissed() {
		return missed;
	}

	public int getShots() {
		return shots;
	}

	public boolean isPerfectGame() {
		return perfectGame;
	}

	public void setEnemies(int enemies) {
		this.enemies = enemies;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setMissed(int missed) {
		this.missed = missed;
	}

	public void setPerfectGame(boolean perfectGame) {
		this.perfectGame = perfectGame;
	}

	public void setShots(int shots) {
		this.shots = shots;
	}

}
