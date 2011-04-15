import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;


public class Inimigo extends Objeto{

	public int XP ;
	Color cor;
	double ang = 0;
	int vel = 100;
	int r = 10;
	private int tempoComida=0;
	private int tempoTiro=0;
	private double oldang;
	private double oldx,oldy;
	private int comidatime=0;
	private int time=0;
	int estado = 0;
	int iatimer = 0;
	int pontoDestino=0;
	double X0,Y0;
	public int frame=1;
	public int sizeX=40;
	public int sizeY=44;
	public int startX=0;

	public int startY=40;
	public int velx=50;
	public int vely=50;
	
	private boolean ANDA=false;
	private double oldx0;
	private double oldy0;
	public  double rotacao;
	private int tipo;
	private boolean BOSSATIRA=false;
	private int tempoTiroInimigo1;
	public Inimigo(int x,int y,Color cor) {
		// TODO Auto-generated constructor stub
		X = x;
		Y = y;
		XP=50;
//		X0=X;
//		Y0=Y;
		X0=X;
		Y0=Y;
		this.cor = cor;
		vida=100;
		rotacao= Constantes.fase*Math.PI/4;
		tipo =1;
		tempoTiroInimigo1= GamePanel.rnd.nextInt(2000)+1000;

	}
	public Inimigo(int x,int y) {
		// TODO Auto-generated constructor stub
		X = x;
		Y = y;
		XP=50;
		X0=X; 
		Y0=Y;

		//this.cor = cor;
		vida=300;
		rotacao= 2*Math.PI;
		tipo=2;
		
	}
	
	@Override
	public void SimulaSe(int DiffTime) {

		oldx = X;
		oldy = Y;
		oldx0=X0;
		oldy0=Y0;
		calculaIA(DiffTime);
		
		//ang+=Math.PI/90*DiffTime/1000.0f;
		if (ANDA) {
			X+=vel*Math.cos(ang)*DiffTime/1000.0f;
			Y+=vel*Math.sin(ang)*DiffTime/1000.0f;
			}
		
    	//X0+=velx/4*DiffTime/1000.0f;

		//Y0+=vely/4*DiffTime/1000.0f;

		//System.out.println(vely);
		
		if (X0 >=GamePanel.PWIDTH-GamePanel.PWIDTH/4 || X0<=GamePanel.PWIDTH/2 ) {

			X0=oldx0;
			velx=-velx;
		}
		if (Y0>=GamePanel.PHEIGHT-GamePanel.HEIGHT/4 || Y0 <=-GamePanel.PHEIGHT/2) {

			Y0=oldy0;
			vely=-vely;
			//System.out.println(vely);
		}
			
 
	}

	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		dbg.setColor(cor);

		AffineTransform trans = dbg.getTransform();
		dbg.translate(X+sizeX/2-XMundo, Y+sizeY/2-YMundo);
		dbg.rotate(ang);
		dbg.rotate(Math.PI/2);
		


		dbg.setColor(cor);



		dbg.drawImage(CanvasGame.getImagemcharsets(),-sizeX/2,-sizeY/2,sizeX/2,sizeY/2,sizeX*frame+startX,startY,(sizeX*frame)+sizeX+startX,(startY)+sizeY,null);

		dbg.setTransform(trans);
		//Direcionador d = CanvasGame.controladordedirecao.get(tipo-1).direcionadores.get(pontoDestino);
		
		//dbg.drawRect((int)X0+d.x, (int)Y0+d.y, 10, 10);
//		
		dbg.drawRect((int)X-r, (int)Y-r-12, 50, 10);
		dbg.setColor(Color.green);
		dbg.fillRect((int)X-r+1, (int)Y-r-11, vida/2-1, 9);
		dbg.setColor(Color.gray);
		//dbg.drawRect((int)X0,(int) Y0, 10, 10);
	}
	
	public boolean colide(Meteoro agente){
		
		double dx = X-agente.X;
		double dy = Y-agente.Y;
		double sr = r+agente.sizeX;
		
		
		if((dx*dx+dy*dy)<(sr*sr)){
			return true;		}
		
		return false;
	}
	public boolean colide(double x2, double y2, double r2){
		
		double dx = X-x2;
		double dy = Y-y2;
		double sr = r+r2;
		
		if((dx*dx+dy*dy)<(sr*sr)){
			return true;
		}
		
		return false;
	}



	
	public void calculaIA(int DiffTime){
		

		tempoTiro += DiffTime;
		tempoComida+=DiffTime;
		comidatime+=DiffTime;
		time+=DiffTime;
		
		if (tempoTiro>tempoTiroInimigo1&&tipo==1) {
			
			tempoTiro=0;
			
			Constantes.fundo.run();

			CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2, Y+sizeY/2,2,ang));
		
		
		
		}		
		if (tempoTiro>Constantes.TEMPO_TIRO_INIMIGO2&&tipo==2) {
	
			BOSSATIRA=true;
		
		
		}
		
		
			if (startY<90&&time>50) {
				startY=90;
				time=0;
			}
			else if(time>50) {
				startY=44;
				time=0;
			
			}
		iatimer +=DiffTime;

		if(estado==0){
			if (tipo==1) {
					ANDA=false;
				    Direcionador d = CanvasGame.controladordedirecao.get(tipo-1).direcionadores.get(pontoDestino);
					
				    int dx = (int) ((X0+d.x)-X);
				    int dy= (int) ((Y0+d.y)-Y);
	
					if (ang < Math.atan2(dy, dx)) 
					{
						if (Math.atan2(dy, dx)-ang<=Math.PI/180 )
							estado=1;
						else						
						ang+=rotacao *DiffTime/1000.0f;
					
					}
					if (ang > Math.atan2(dy, dx)) {
	
						if (ang-Math.atan2(dy, dx)<=Math.PI/180 ) {
							estado=1;
						}
					
						else {
	
							ang-=rotacao*DiffTime/1000.0f;				
						}
					}
					if (ang == Math.atan2(dy, dx))
						estado=1;
					
			}
			if (tipo==2) {
				if(!BOSSATIRA) {
					ANDA=false;
				    Direcionador d = CanvasGame.controladordedirecao.get(tipo-1).direcionadores.get(pontoDestino);
					
				    int dx = (int) ((X0+d.x)-X);
				    int dy= (int) ((Y0+d.y)-Y);
	
					if (ang < Math.atan2(dy, dx)) 
					{
						if (Math.atan2(dy, dx)-ang<=Math.PI/180 )
							estado=1;
						else						
						ang+=rotacao *DiffTime/1000.0f;
					
					}
					if (ang > Math.atan2(dy, dx)) {
	
						if (ang-Math.atan2(dy, dx)<=Math.PI/180 ) {
							estado=1;
						}
					
						else {
	
							ang-=rotacao*DiffTime/1000.0f;				
						}
					}
					if (ang == Math.atan2(dy, dx))
						estado=1;
				}
				if(BOSSATIRA&&tempoTiro>Constantes.TEMPO_TIRO_INIMIGO2) {
					ANDA=false;

					
				    int dx = (int) ((CanvasGame.minhaNave.X)-X);
				    int dy= (int) ((CanvasGame.minhaNave.Y)-Y);
	
					if (ang < Math.atan2(dy, dx)) 
					{
						if (Math.atan2(dy, dx)-ang<=Math.PI/90 ) {
							estado=1;
							CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2+5, Y+sizeY/2-6,2,ang));
							CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2, Y+sizeY/2,2,ang));
							CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2-5, Y+sizeY/2+6,2,ang));
							BOSSATIRA=false;

							BOSSATIRA=false;
							tempoTiro=0;

						}
						else						
						ang+=rotacao *DiffTime/1000.0f;
					
					}
					if (ang > Math.atan2(dy, dx)) {
	
						if (ang-Math.atan2(dy, dx)<=Math.PI/90 ) {
							estado=1;
							CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2+5, Y+sizeY/2-6,2,ang));
							CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2, Y+sizeY/2,2,ang));
							CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2-5, Y+sizeY/2+6,2,ang));	
							BOSSATIRA=false;
							tempoTiro=0;

						}
					
						else {
	
							ang-=rotacao*DiffTime/1000.0f;				
						}
					}
					if (ang == Math.atan2(dy, dx)) {
						estado=1;
						CanvasGame.projeteis.add(new Projetil(this, X+sizeX/2, Y+sizeY/2,2,ang));
						BOSSATIRA=false;
						tempoTiro=0;

					}
					
				}
					
					
			}
			
		
		}
			
			

			
			
		
		if(estado==1){

				ANDA=true;
			    Direcionador d = CanvasGame.controladordedirecao.get(tipo-1).direcionadores.get(pontoDestino);
				
			    int dx = (int) ((X0+d.x)-X);
			    int dy= (int) ((Y0+d.y)-Y);
			    ang=Math.atan2(dy, dx);
			   
			if (colide(X0+d.x,Y0+d.y,1)) {
				
				if (pontoDestino<=7) 
				pontoDestino++;
				else pontoDestino=0;
				
				
				estado=0;
			    

		       
			       
		    }
				
		}
		if (pontoDestino<CanvasGame.controladordedirecao.get(tipo-1).direcionadores.size()) {
		    Direcionador d = CanvasGame.controladordedirecao.get(tipo-1).direcionadores.get(pontoDestino);
		    int dx = (int) ((X0+d.x)-X);
		    int dy= (int) ((Y0+d.y)-Y);


			if (colide(X0+d.x,Y0+d.y,0)) {
				pontoDestino++;

			       
		    }
		}else pontoDestino=0;
		

		
	
	}

	private boolean desviaAgentes() {
		// TODO Auto-generated method stub
		for(int i = 0; i < CanvasGame.listadeMeteoro.size(); i++){
			Meteoro agente = (Meteoro)CanvasGame.listadeMeteoro.get(i);
		
				if(colide(agente)){
					X = oldx;
					Y = oldy;
						double dx1 =  agente.X-X;
						double dy1 = agente.Y-Y;
						oldang = ang;
						iatimer=0;
						ang = Math.atan2(dy1, dx1)+Math.PI/2;
						estado=0;
					return true;
				}
			
		}
		return false;
	}
	
}
