package defs.objects.guns;

import defs.objects.flyer.Flyer;
import defs.objects.munition.AimedBullet;
import main.Main;

public class DoubleAimingGun extends AimingGun{

	public DoubleAimingGun(Flyer fly) {
		super(fly);
	}
	
	@Override
	public void shoot(Main main) {
		main.add(new AimedBullet(main,this.getFlyer().getX()-(int)(0.05*this.getFlyer().getSize()),
				this.getFlyer().getY()+this.getFlyer().getSize()+20));
		main.add(new AimedBullet(main,this.getFlyer().getX()+(int)(0.05*this.getFlyer().getSize()),
				this.getFlyer().getY()+this.getFlyer().getSize()+20));
		main.getPlainGunSound().trigger();
	}

}
