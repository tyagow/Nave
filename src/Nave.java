import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class Nave  extends Objeto {
	
	public double X,Y;
	Color cor;
	double ang=0;
	public int frame=0;
	public int sizeX=42;
	public int sizeY=42;
	public int startX=0;
	int r=10;
	int vel = 0;
	double oldx,oldy;
	
	public int startY=0;
	private int time;
	private int timeTiro=0;
	int timerColisaoMeteoro=0;
	public boolean LEFT=false;
	public boolean RIGHT=false;
	public boolean UP;
	public boolean DOWN;
	private boolean TIRO;
	public int XP;
	private boolean GANHOUXP;
	public double NaveVelocidadeRotacao=Math.PI;
	public  int NaveVelocidade=200;
	public int NaveVelocidadeMaxima=400;
	private boolean CONTROL_XP;
	public int nivel=1;
	private boolean bateuMeteoro;
	private double MaximoVidaNave=100;
	private boolean SOM_INICIOU=false;
	
	public Nave(int x,int y,Color cor) {
		X=x;
		Y=y;
		this.cor=cor;
		life = 100;
		vida=100;
		oldx=X;
		oldy=Y;
	
	}
	@Override
	public void SimulaSe(int DiffTime) {
		// TODO Auto-generated method stub
		oldx = X;
		oldy = Y;
		
		calculaIA(DiffTime);
		
		//ang+=Math.PI/90*DiffTime/1000.0f;
			
		X+=vel*Math.cos(ang)*DiffTime/1000.0f;
		Y+=vel*Math.sin(ang)*DiffTime/1000.0f;
		
//    	X0+=vel2/4*DiffTime/1000.0f;
//
//		Y0+=vel2/4*DiffTime/1000.0f;
//		
		
		if (X+r+1 >=GamePanel.PWIDTH || Y+r+1>=GamePanel.PHEIGHT  || Y-r-1 <=0 || X-r-1<=0) {
			
			X=oldx;
			Y=oldy;
			//vel2*=-1;
		}
 
	}
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		
		dbg.setColor(cor);
//		dbg.drawOval((int)(X-r),(int)(Y-r), 2*r, 2*r);		
//		
//		int bx = (int)(r*Math.cos(ang));
//		int by = (int)(r*Math.sin(ang));
//		
//		dbg.drawLine((int)X, (int)Y, (int)(X+bx), (int)(Y+by));
		AffineTransform trans = dbg.getTransform();
		dbg.translate(X+sizeX/2-XMundo, Y+sizeY/2-YMundo);
		dbg.rotate(ang);
		dbg.rotate(Math.PI/2);
		
//		int px = (int)X-XMundo;
//		int py = (int)Y-YMundo;


		dbg.setColor(cor);

		

		dbg.drawImage(CanvasGame.getImagemcharsets(),(int)-sizeX/2,(int)-sizeY/2,(int)sizeX/2,(int)sizeY/2,(int)sizeX*frame+startX,(int)startY,(int)(sizeX*frame)+sizeX+startX,(int)(startY)+sizeY,null);
		//dbg.drawImage(GamePanel.Constantes.explosao,(int)-sizeX/2,(int)-sizeY/2,(int)sizeX/2,(int)sizeY/2,(int)sizeX*1,(int)sizeY*1,(int)(sizeX*1)+sizeX,(int)sizeY*1,null);

		dbg.setTransform(trans);
		//dbg.drawImage(GamePanel.imagemcharsets,X,X,X );
		
		dbg.drawRect((int)X-r, (int)Y-r-12, 40, 10);
		dbg.setColor(Color.green);
		dbg.fillRect((int)X-r+1, (int)Y-r-11, (int)(life*40/MaximoVidaNave)-1, 9);
		
		dbg.drawRect((int)GamePanel.PWIDTH+Constantes.NAVE_ACELERADOR_X, (int)GamePanel.PHEIGHT+Constantes.NAVE_ACELERADOR_Y, Constantes.NAVE_ACELERADOR_SIZEX, Constantes.NAVE_ACELERADOR_SIZEY);
		dbg.fillRect((int)GamePanel.PWIDTH+Constantes.NAVE_ACELERADOR_X+1, (int)GamePanel.PHEIGHT+Constantes.NAVE_ACELERADOR_Y, 	(Constantes.NAVE_ACELERADOR_SIZEX*vel)/NaveVelocidade, Constantes.NAVE_ACELERADOR_SIZEY);
	//	dbg.drawOval((int)X,(int) Y, sizeX,sizeY);	
		//dbg.drawLine((int)X,(int)Y, (int)X+2,(int)Y+2);	
		
	}

	private void calculaIA(int diffTime) {
		// TODO Auto-generated method stub
		time+=diffTime;
		timeTiro+=diffTime;
		timerColisaoMeteoro+=diffTime;
		if (life>MaximoVidaNave)
			life = MaximoVidaNave;
		if (life<0) life=0;
		
		if (!SOM_INICIOU&&vel!=0) {
			Constantes.nave.start();
			SOM_INICIOU=true;
		}
		
		
		if ( timeTiro>=Constantes.TEMPO_TIRO){
			TIRO=true;
			timeTiro=0;           
			}
		if (LEFT)
			frame=0;
		else
			frame=1;
		
		if (RIGHT) 
			frame =2; 
		else if (!LEFT)
			frame=1;
		
//		if (vel >0)	 // atrito
//			vel-=10*diffTime/1000.0f;
//		else if (vel<0)
//			vel+=10*diffTime/1000.0f;
		if (timerColisaoMeteoro>=400) {
			bateuMeteoro=false;
		}
		
		
		if (vel!=0) {
			if (startY<90&&time>50) {
				startY=90;
				time=0;
			}
			else if(time>50) {
				startY=44;
				time=0;
			
			}
		}else startY=0;
		
		if (LEFT) {
			ang-=NaveVelocidadeRotacao*diffTime/1000.0f;
			//System.out.println(ang);
		}
		if (RIGHT) {
			ang+=NaveVelocidadeRotacao*diffTime/1000.0f;
			//System.out.println(ang);
		}
		if (UP) {
			if (vel<NaveVelocidade)
			vel+=NaveVelocidade*diffTime/1000.0f;
			//System.out.println(vel);

		}
		if (DOWN) {
			if (vel>-NaveVelocidade)
			vel-=NaveVelocidade*diffTime/1000.0f;
			//System.out.println(vel);
		}
		
		for (int i =0;i<CanvasGame.listadeMeteoro.size();i++) {
			Meteoro ag = (Meteoro)CanvasGame.listadeMeteoro.get(i);
		if (!bateuMeteoro&&Constantes.colidecircular(X, Y, sizeX/2, ag.X, ag.Y, ag.sizeX/2)) {
			
				ag.colidiu(this);

				life-=10;
				bateuMeteoro=true;
				timerColisaoMeteoro=0;
				
				
     			CanvasGame.gerenciadorEfeitos.explosao(ag.X+ag.sizeX/4,ag.Y+ag.sizeY/4,-100,100);

//				}
				break;
			}	
		}
//			
		
	}
	

	

	public void disparaTiro() {
		// TODO Auto-generated method stub
		if (TIRO) {
			Constantes.fundo.run();
			if (nivel<2) { 
				Projetil proj = new Projetil(this, X+sizeX/2, Y+sizeY/2,1,ang);
				CanvasGame.projeteis.add(proj);
			}
			if (nivel >=2) {
				CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2+Math.cos(ang)*20, Y+sizeY/2+Math.sin(ang)*20,1,ang+Math.PI/90));
				CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2, Y+sizeY/2,1,ang));
				CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2+Math.cos(ang)*20, Y+sizeY/2+Math.sin(ang)*20,1,ang-Math.PI/90));	
				
			}
			
		}
		TIRO=false;

	}
	
	public void gerenciaXP(){
	
		
		if (CanvasGame.gerenciadorEfeitos.xp>=nivel*Constantes.XP_EVOLUCAO) {
		
			NaveVelocidade+=5;
			NaveVelocidadeRotacao+=Math.PI/45;
			Constantes.TEMPO_TIRO-=6;
			MaximoVidaNave+=5;
			evoluiu();
			
		}
		

	}
	public void evoluiu () {
		CanvasGame.gerenciadorEfeitos.listaEfeitos.add(new Texto());
		
		nivel++;
	}
	

}
