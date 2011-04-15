import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;


public class Vida extends Objeto {

	double angulo,oldx,oldy;
	int tempoVida=0;
	int velx,vely,sizeX,sizeY;
	public float alpha = 1.0f;
	public Vida(double x,double y,int life,double ang) {
		// TODO Auto-generated constructor stub
		X=x;
		Y=y;
		this.life=life;
		this.angulo = ang;
		vivo=true;
		vely=velx=GamePanel.rnd.nextInt(200)+10;
		sizeY=10;
		sizeX=life;
	}

	@Override
	public void SimulaSe(int DiffTime) {
		// TODO Auto-generated method stub]
		tempoVida+=DiffTime;
		oldx=X;
		oldy=Y;
		X+=velx*Math.cos(angulo)*DiffTime/1000.0f;
		Y+=vely*Math.sin(angulo)*DiffTime/1000.0f;
		
		alpha-=0.1f*DiffTime/1000.0f;
		
		if(alpha<=0) {
			alpha=0;
			vivo=false;
		}
		
		if (tempoVida>=500&&Constantes.colidecircular(X, Y, sizeX/2, CanvasGame.minhaNave.X, CanvasGame.minhaNave.Y, CanvasGame.minhaNave.sizeX/2)){
			vivo=false;
			CanvasGame.minhaNave.life+=life;
			CanvasGame.gerenciadorEfeitos.ganhouLife(X,Y,life);
		
		}

		
		if (X<0||X+sizeX>=GamePanel.PWIDTH) {
			X=oldx;
			velx=-velx;
		}
		if (Y<0||Y+sizeY>=GamePanel.PHEIGHT) {
			Y=oldy;
			vely=-vely;
		}

	}
	
	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		
	    dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		dbg.setColor(Color.red);
		dbg.drawRect((int)X-1,(int) Y-1, sizeX+2, sizeY+2);
		dbg.setColor(Color.green);
		dbg.fillRect((int)X,(int) Y, sizeX+1, sizeY+1);
	    dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));


	}

}
