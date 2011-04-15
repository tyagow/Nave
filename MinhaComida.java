import java.awt.Color;
import java.awt.Graphics2D;


public class MinhaComida extends Objeto {
	
	Color cor;
	public MinhaComida(int x, int y) {
		cor = Color.green;
		this.X=x;
		this.Y=y;
		life=100;
	}

	@Override
	public void SimulaSe(int DiffTime) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		
		dbg.setColor(cor);
		dbg.fillOval((int)(X-(life/4)), (int)(Y-(life/4)), (int)life/2, (int)life/2);
//		dbg.setColor(Color.BLACK);
//		dbg.drawOval((int)(X+life/4), (int)(Y+life/4), 5, 5);
//		
	}
	

}
