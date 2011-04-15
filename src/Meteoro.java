import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class Meteoro extends Objeto {
	public static boolean EXPLODIU = false;
	int sizeX,sizeY,frame,startX,startY;
	double tipo,vel,velx,vely;
	double oldx,oldy;
	double ang,ang2;
	private double rotacao;
	public int XP;
	double life;
	Meteoro(double x,double y,double ang,int vel,int t) {
		this.X=x;
		this.Y=y;
		this.ang=ang;
		this.tipo=t;
		vida=100;
		this.vel=vel;
		velx=vel;
		vely=vel;
		vivo=true;
		if (GamePanel.rnd.nextBoolean())
			rotacao=Math.PI/8;
		else 
			rotacao=-Math.PI/8;
		
		inicializaTipo();
		
		XP=(int)(Constantes.METEORO_XP_BASE*(7-tipo));
	}



	@Override
	public void SimulaSe(int DiffTime) {
		// TODO Auto-generated method stub
		oldx = X;
		oldy = Y;
		
	
		
		X+=velx*Math.cos(ang)*DiffTime/1000.0f;
		Y+=vely*Math.sin(ang)*DiffTime/1000.0f;
		calculaIA(DiffTime);
	}



	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		AffineTransform trans = dbg.getTransform();
		dbg.translate(X+sizeX/2-XMundo, Y+sizeY/2-YMundo);
		dbg.rotate(ang2);
		//dbg.rotate(Math.PI/2);
		dbg.drawImage(CanvasGame.getImagemcharsets(),-sizeX/2,-sizeY/2,sizeX/2,sizeY/2,sizeX*frame+startX,startY,(sizeX*frame)+sizeX+startX,(startY)+sizeY,null);
		dbg.setTransform(trans);
		//dbg.drawOval((int)X,(int)Y, sizeX, sizeY); //ver envelope

	}
	private void calculaIA(int diffTime) {
		// TODO Auto-generated method stub
		ang2+=rotacao*diffTime/1000.0f;
					
			
			//colisao "paredes"
			if (X+sizeX>=GamePanel.PWIDTH+100||X<-GamePanel.PWIDTH/4-100)
			{	
				X=oldx;
				velx=-velx;
			}
			if (Y+sizeY>=GamePanel.PHEIGHT+100||Y<-GamePanel.PHEIGHT/4-100)
			{
				Y=oldy;
				vely=-vely;
			}
			//
			
			
			
		}
		
	
	private void inicializaTipo() {
		// TODO Auto-generated method stub
		if (tipo==Constantes.METEORO_GIGANTE) {
			 frame=0;
			 sizeX=60;
			 sizeY=50;
			 startX=0;
			 startY=250;
			 life=30;
			 
		 }
		if (tipo==Constantes.METEORO_GRANDE) {
			 frame=0;
			 sizeX=60;
			 sizeY=50;
			 startX=0;
			 startY=300;
			 life=25;

		}
		if (tipo==Constantes.METEORO_MEDIO) {
			 frame=0;
			 sizeX=40;
			 sizeY=40;
			 startX=60;
			 startY=258;	
			 life=20;

		}
		if (tipo==Constantes.METEORO_PEQUENO) {
			 frame=0;
			 sizeX=30;
			 sizeY=40;
			 startX=60;
			 startY=310;
			 life=10;

		 }

		if (tipo==Constantes.METEORO_MICRO) {
			 frame=0;
			 sizeX=25;
			 sizeY=25;
			 startX=95;
			 startY=315;	
			 life=5;

		 }
		if (tipo==Constantes.METEORO_LOL) {
			 frame=0;
			 sizeX=15;
			 sizeY=20;
			 startX=105;
			 startY=270;	
		 }
	}
	
	public void colidiu(Objeto a) {
		int px= (int)(a.X-X);
		int py= (int)(a.Y-X);
		double angulo;
		if (tipo==Constantes.METEORO_GIGANTE) {
			angulo = Math.atan2(py, px);
			vivo=false;
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo+Math.PI/8,100,Constantes.METEORO_GRANDE));
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo-Math.PI/8,150,Constantes.METEORO_MEDIO));
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo-Math.PI/8,150,Constantes.METEORO_LOL));
			
			if (GamePanel.rnd.nextInt(100)>50)
			CanvasGame.gerenciadorEfeitos.listaEfeitos.add(new Vida(X,Y,(int)life,ang));
			
		}	
		if (tipo==Constantes.METEORO_GRANDE) {
			angulo = Math.atan2(py, px);
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo+Math.PI/8,150,Constantes.METEORO_MEDIO));
			
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo-Math.PI/8,180,Constantes.METEORO_MICRO));
			vivo=false;
			if (GamePanel.rnd.nextInt(100)>70)

			CanvasGame.gerenciadorEfeitos.listaEfeitos.add(new Vida(X,Y,(int)life,ang));

		}
		if (tipo==Constantes.METEORO_MEDIO) {
			angulo = Math.atan2(py, px);
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo+Math.PI/8,150,Constantes.METEORO_PEQUENO));
			
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo-Math.PI/8,180,Constantes.METEORO_MICRO));
			vivo=false;
			if (GamePanel.rnd.nextInt(100)>60)

			CanvasGame.gerenciadorEfeitos.listaEfeitos.add(new Vida(X,Y,(int)life,ang));

		}
		if (tipo==Constantes.METEORO_PEQUENO) {
			angulo = Math.atan2(py, px);
			
			CanvasGame.listadeMeteoro.add(new Meteoro(X,Y,angulo-Math.PI/8,180,Constantes.METEORO_MICRO));
			vivo=false;
			
			if (GamePanel.rnd.nextInt(100)>50)
			CanvasGame.gerenciadorEfeitos.listaEfeitos.add(new Vida(X,Y,(int)life,ang));

		}
		if (tipo==Constantes.METEORO_MICRO) {
			
			if (GamePanel.rnd.nextInt(100)>70)
			CanvasGame.gerenciadorEfeitos.listaEfeitos.add(new Vida(X,Y,(int)life,ang));

			vivo=false;
			
		}
		if (tipo==Constantes.METEORO_LOL) {
			vivo=false;
			
		}
		
	}



}
