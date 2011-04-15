import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class Projetil extends Objeto {
	
	int velx,vely;
	double dx,dy;
	Objeto pai;
	double ang;
	Color cor;
	int typ; // VARIAVEL QUE DEFINE O TIPO DE TIRO SETADA NO CONSTRUTOR
	private boolean EXPLODIU;
	private int sizeY;
	private int sizeX,startX,startY,frame;
	private boolean bateuMeteoro=false;
	private int timerColisaoMeteoro;
	
	
	public Projetil(Objeto pai,double X,double Y,int typ,double ang){
		// TODO Auto-generated constructor stub
		this.pai = pai;
		this.X = X;
		this.Y = Y;
	//	this.dx = dx;
		//this.dy = dy;
		this.typ = typ;
		frame=1;
		sizeX=10;
		sizeY=20;
		startX=58;
		startY=132;
		cor = null;
		
		this.ang = ang;
	
		sizeX=4;
		sizeY=4;

		//System.out.println(+this.dx+" "+this.dy+" "+X+" "+Y);
		
		velx=(int)(Math.cos(ang)*800);
		vely= (int) (Math.sin(ang)*800);
		
		vivo = true;
	}
	
	@Override
	public void SimulaSe(int DiffTime) {
		// TODO Auto-generated method stub

		X+=velx*DiffTime/1000.0f;
		Y+=vely*DiffTime/1000.0f;
		timerColisaoMeteoro+=DiffTime;
	//	System.out.println(+this.dx+" "+this.dy+" "+Y+" "+X);
		 boolean aux=false;
		 
			if (typ==1) { //tiro da nave
				
				for (int i =0;i<CanvasGame.listadeagentes.size();i++) {
					Inimigo ag = (Inimigo)CanvasGame.listadeagentes.get(i);
				if (Constantes.colidecircular(X, Y, sizeX, ag.X+ag.sizeX/2, ag.Y+ag.sizeY/2, ag.sizeY/2)) {
						aux=true;
						CanvasGame.listadeagentes.get(i).vida-=2*Constantes.DANO_TIRO;
						CanvasGame.gerenciadorEfeitos.ganhouXp( (int)X, (int)Y,Constantes.DANO_TIRO);
						//GamePanel.minhaNave.evoluiu();
						CanvasGame.minhaNave.gerenciaXP();
//						CanvasGame.gerenciadorEfeitos.explosao(ag.X+ag.sizeX/4,ag.Y+ag.sizeY/4,-velx,vely);

						break;
					}	
				}
				for (int i =0;i<CanvasGame.listadeMeteoro.size();i++) {
					Meteoro ag = (Meteoro)CanvasGame.listadeMeteoro.get(i);
				if (Constantes.colidecircular(X-sizeX/2, Y-sizeY/2, sizeX/2, ag.X+ag.sizeX/2, ag.Y+ag.sizeY/2, ag.sizeY/2)) {
						aux=true;
						ag.colidiu(this);
	
						CanvasGame.gerenciadorEfeitos.ganhouXp( (int)X, (int)Y,ag.XP);
						CanvasGame.minhaNave.gerenciaXP();
						//GamePanel.minhaNave.evoluiu();
						break;
					}	
				}
				
				
			}
			
			if (typ==2) { //tiro dos inimigos
				
//				for (int i =0;i<GamePanel.listadeagentes.size();i++) {
//					MeuAgente ag = (MeuAgente)GamePanel.listadeagentes.get(i);
				if (Constantes.colidecircular(X-sizeX/2, Y-sizeY/2, sizeX/2, CanvasGame.minhaNave.X+CanvasGame.minhaNave.sizeX/2, CanvasGame.minhaNave.Y+CanvasGame.minhaNave.sizeY/2, 10)) {
						aux=true;
						CanvasGame.minhaNave.life-=Constantes.DANO_TIRO;
					}
				for (int i =0;i<CanvasGame.listadeMeteoro.size();i++) {
					Meteoro ag = (Meteoro)CanvasGame.listadeMeteoro.get(i);
				if (!bateuMeteoro&&Constantes.colidecircular(X, Y, sizeX/2, ag.X, ag.Y, ag.sizeX/2)) {
					
						ag.colidiu(this);

						//GamePanel.minhaNave.evoluiu();
						bateuMeteoro=true;
						timerColisaoMeteoro=0;
		//
//						}
						break;
					}	
				}
//				}
				
			}
		
		if (X>GamePanel.PWIDTH+GamePanel.PWIDTH/8||X<-GamePanel.PWIDTH/8||Y<-GamePanel.PHEIGHT/8||Y>GamePanel.PHEIGHT+GamePanel.PWIDTH/8) 
			vivo=false;	
		
		
		if (aux) EXPLODIU=true;
		
		if(EXPLODIU){
			Particula part;
				for(int B = 0; B < 100;B++){
					int modv = -GamePanel.rnd.nextInt(200)+50;
					
					int pvx = 0;
					int pvy = 0;
				
						pvx = velx + modv;
						pvy = vely - modv;

					
					pvx = (int)(pvx*(0.4+0.25*GamePanel.rnd.nextFloat()));
					pvy = (int)(pvy*(0.4+0.25*GamePanel.rnd.nextFloat()));
					
					if(B%2==0){
						cor = Color.red;
					}else{
						cor = Color.cyan;
					}
//					if (B%4==0) {
						part = (Particula)new Faisca(X,Y,pvx/4,pvy/4,GamePanel.rnd.nextInt(300)+100,cor);
//					}else {
//						part = (Particula)new Faisca(X,Y,-pvx/4,-pvy/4,GamePanel.rnd.nextInt(400)+100,cor);
//					}
					
					CanvasGame.particulas.add(part);
					vivo = false;
				}
	
		
		}


	}

	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		dbg.setColor(cor);

		AffineTransform trans = dbg.getTransform();
		dbg.translate(X+sizeX/2, Y+sizeY/2);
		dbg.rotate(ang);
		dbg.rotate(Math.PI/2);
		


		dbg.setColor(Color.cyan);
		dbg.fillRect((int)-sizeX/2,(int) -sizeY, sizeX/2, sizeY*2);


		dbg.drawImage(CanvasGame.getImagemcharsets(),-sizeX/2,-sizeY/2,sizeX/2,sizeY/2,sizeX*frame+startX,startY,(sizeX*frame)+sizeX+startX,(startY)+sizeY,null);

		dbg.setTransform(trans);
		
		
		
	}
}
