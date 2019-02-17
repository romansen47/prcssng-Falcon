package defs.objects.flyer;

import defs.interfaces.IShooting;
import defs.objects.explosions.Explosion;
import defs.objects.explosions.FinalExplosion;
import defs.objects.guns.PlainGun;
import main.Main;
import temperature.Functions;

public final class HanSolo extends Flyer{

	private int Score=0;
	
	private IShooting[] Guns;
	
	public HanSolo(Main main) {
		Guns=new IShooting[1];
		Guns[0]=new PlainGun(this);
		setGun(Guns[0]);
		setSize(90*main.Height/1080);
		setX(main.Width/2);
		setY(main.Height-((int)(1.5*this.getSize())));
		setMaxHealth(400);
		setHealth(400);
		setMaxMuni(2000);
	}

	@Override
	public void draw(Main main) {
		main.shape(main.getHansolo(),getX()-(int)(0.5*getSize()),getY()-((int)(0.5*getSize())),getSize(),(int)getSize());
	}

	@Override
	public void gotHit(Main main,int hit) {
		this.setHealth(this.getHealth()-hit);
		if (this.getHealth()<1) {
			main.setScore(getScore());
			main.add(new FinalExplosion(main,getX()+(int)(0.5*getSize()),
					getY()+(int)(0.0*getSize())));
			main.remove(this);
		}
	}
	
	public void setScore(int n){
		Score=Math.min(0,n);
	}
	
	public int getScore(){
		return Score;
	}
	
	public void addScore(int n){
		Score=Math.max(0,Score+n);
	}
	
	private int indexGun(){
		for (int i=0;i<Guns.length;i++){
			if (this.getGun()==Guns[i]){
				return i;
			}
		}
		return -1;
	}
	
	public void nextGun(){
		int n=indexGun();
		if (n==Guns.length-1){
			setGun(Guns[0]);
		}
		else{
			setGun(Guns[n+1]);
		}
	}
	
	public void prevGun(){
		int n=indexGun();
		if (n==0){
			setGun(Guns[Guns.length-1]);
		}
		else{
			setGun(Guns[n-1]);
		}
	}
	
	@Override 
	public void move(){
		setX((int)(getX()+this.getVelocity()[0]));
		setY((int)(getY()+this.getVelocity()[1]));
	}

	@Override
	public void move(Main main) {
		if (this.getHealth()<0) {
			main.remove(this);
			main.add(new Explosion(main,getX(),getY()));
		}
		double[] Mouse=new double[2];
		Mouse[0]=main.mouseX;
		Mouse[1]=main.mouseY;
		this.setVelocity(Functions.mathOperator.ScalarMultiplication(0.1,
				Functions.mathOperator.AdditionOfVectors(Functions.mathOperator.ReversalOfVector(this.getPosition()),Mouse)));
		
		if ( main.getFrameCount()%4==0 && (main.mousePressed && main.mouseButton==Main.LEFT)) {
			shoot(main);
			Main.getStatistic().setShots(Main.getStatistic().getShots()+1);
		}
		if (this.getY()>main.Height) {
			selfDestroy(main);
			Main.getStatistic().setPerfectGame(false);
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		move();
	}
	
	public void addGun(IShooting gun){
		IShooting[] tmpGuns=new IShooting[Guns.length+1];
		for (int i=0;i<Guns.length;i++){
			tmpGuns[i]=Guns[i];
		}
		tmpGuns[Guns.length]=gun;
		Guns=tmpGuns;
		setGun(gun);
	}
}
