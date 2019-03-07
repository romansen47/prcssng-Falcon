package main;

public class StatsCollector {

	private static StatsCollector instance;

	public static StatsCollector getInstance() {
		if (StatsCollector.instance == null) {
			StatsCollector.instance = new StatsCollector();
		}
		return StatsCollector.instance;
	}

	private int shots, missed, enemies, kills;

	private boolean perfectGame;

	private StatsCollector() {
		this.setShots(0);
		this.setMissed(0);
		this.setEnemies(0);
		this.setKills(0);
		this.setPerfectGame(true);
	}

	public int getEnemies() {
		return this.enemies;
	}

	public int getKills() {
		return this.kills;
	}

	public int getMissed() {
		return this.missed;
	}

	public int getShots() {
		return this.shots;
	}

	public boolean isPerfectGame() {
		return this.perfectGame;
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
