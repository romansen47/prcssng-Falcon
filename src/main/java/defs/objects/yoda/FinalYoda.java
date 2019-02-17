package defs.objects.yoda;

import main.Main;

public class FinalYoda extends Yoda{

	public FinalYoda(Main main, String[] Message) {
		super(main, Message);
		main.textSize((int)(15.*main.height/1080));
		setSize((int)Math.max(100.0*main.Height/1080,(Message.length+1)*25.0*main.Height/1080));
		//setSize(Math.max(100,Message.length*20));
	}

	@Override
	public void move(Main main) {
		if (getLevel()==0) {
			if (getY()>main.Height-2*getSize()) {
				setY(getY()-35);
			}
			else if (main.mousePressed) {
				setLevel(1);
			}
		}
		else {
			if(getY()<main.Height) {
				setY(getY()+35);
			}
			else {
				main.exit();
			}
		}
		draw(main);
	}
	
}
