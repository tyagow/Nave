import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.text.StyledEditorKit.BoldAction;

public class CanvasGame extends GCanvas {
	
	public static int fase=1;
	public int oldfase=1;
	
	boolean LEFT, RIGHT,UP,DOWN;
	private static BufferedImage imagemcharsets;
	private BufferedImage imagemfundo;


	public static int mousex,mousey; 

	public static ArrayList<Objeto> listadeagentes = new ArrayList<Objeto>();
	public static ArrayList<Objeto> listadeMeteoro = new ArrayList<Objeto>();
	public static ArrayList<ControladorDeDirecao>  controladordedirecao= new ArrayList<ControladorDeDirecao>();
	public static Nave minhaNave;
	public static LinkedList<Particula> particulas = new LinkedList<Particula>();
	public static LinkedList<Projetil> projeteis = new LinkedList<Projetil>();

	public static GerenciadorEfeitos gerenciadorEfeitos;
	public static GCanvas instance;

	public CanvasGame() {
		
		controladordedirecao.add( new ControladorDeDirecao("Inimigos.csv"));
		controladordedirecao.add( new ControladorDeDirecao("boss.csv"));
		
		instance=this;
		setImagemcharsets(Constantes.LoadImage("nave.png"));
		imagemfundo=Constantes.LoadImage("space.png");
		Constantes.explosao=Constantes.LoadImage("explosao.png");

		Constantes.explosao2=Constantes.LoadImage("explosao2.png");

	
		
		gerenciadorEfeitos= new GerenciadorEfeitos();
		
		
		minhaNave=new Nave(GamePanel.PWIDTH/2, GamePanel.PHEIGHT/2, Color.black);
		Constantes.QuantidadeInimigos=3;
		Constantes.QuantidadeMeteoro=5;
		
		carregaInimigos(Constantes.QuantidadeInimigos*fase);
		carregaMeteoro(Constantes.QuantidadeMeteoro*fase);
		
		
		
		}
	@Override
	public void SimulaSe(long DiffTime) {
		// TODO Auto-generated method stub
		for(int i = 0;i < listadeagentes.size();i++){
			Inimigo a = (Inimigo)listadeagentes.get(i);
			listadeagentes.get(i).SimulaSe((int)DiffTime);
			  if (a.vida<=0) {
				    listadeagentes.remove(i);
				    gerenciadorEfeitos.ganhouXp(a.X,a.Y,a.XP);
					verificaFimNivel();
					gerenciadorEfeitos.explosao(a.X, a.Y, a.velx, a.vely);
					}
		}
		for(int i = 0;i < listadeMeteoro.size();i++){
			listadeMeteoro.get(i).SimulaSe((int)DiffTime);
			  if(listadeMeteoro.get(i).vivo==false){
				  listadeMeteoro.remove(i);
			  }
		}
//		for(int i = 0;i < particulas.size();i++){
//			particulas.get(i).SimulaSe((int)DiffTime);
//		}
		for(int i = 0;i < projeteis.size();i++){
			projeteis.get(i).SimulaSe((int)DiffTime);
			  if(projeteis.get(i).vivo==false){
				  projeteis.remove(i);
				}
		}
		Iterator<Particula> it = particulas.iterator();
		while(it.hasNext()){
			Particula part = it.next();
			part.SimulaSe((int)DiffTime);
			if(part.vivo==false){
				it.remove();
			}
		}
		minhaNave.SimulaSe((int) DiffTime);
		gerenciadorEfeitos.SimulaSe((int)DiffTime);
		Constantes.fase=fase;
		
		
	}
	public void verificaFimNivel() {
		
		if ( listadeagentes.size()<=0) {

			fase++;
			int v=fase*Constantes.QuantidadeInimigos;
			carregaInimigos(v);
			int x= fase*Constantes.QuantidadeMeteoro-listadeMeteoro.size();
			carregaMeteoro(x);
			
			
		}
	
	}

	@Override
	public void DesenhaSe(Graphics2D dbg) {
		// TODO Auto-generated method stub
		dbg.setColor(Color.black);
		dbg.fillRect (0, 0, GamePanel.PWIDTH, GamePanel.PHEIGHT);
		dbg.drawImage(imagemfundo, 0, 0, GamePanel.PWIDTH, GamePanel.PHEIGHT, 0, 0, imagemfundo.getWidth(), imagemfundo.getHeight(), null);
		

		
		dbg.setColor(Color.BLUE);	
		dbg.drawString("FPS: "+GamePanel.FPS, 10, 10);	
		
		for(int i = 0;i < listadeagentes.size();i++){
			listadeagentes.get(i).DesenhaSe(dbg, 0, 0);
		}
		for(int i = 0;i < listadeMeteoro.size();i++){
			listadeMeteoro.get(i).DesenhaSe(dbg, 0, 0);
		}	

		for(int i = 0;i < projeteis.size();i++){
			projeteis.get(i).DesenhaSe(dbg, 0, 0);
		}
		
		dbg.setColor(Color.yellow);
		dbg.setFont(new Font("SansSerif", Font.BOLD, (int) 20));
		dbg.drawString("Fase  " +fase,20,40);
		dbg.setColor(Color.red);
		
		dbg.drawString("Fase  " +fase, 20-2, 40-2);
		dbg.setColor(Color.red);
		dbg.setFont(new Font("SansSerif", Font.BOLD, (int) 14));
		
		//GamePanel.controladordedirecao.DesenhaSe(dbg, 0, 0);
		minhaNave.DesenhaSe(dbg, 0, 0);
		for(int i = 0;i < particulas.size();i++){
			particulas.get(i).DesenhaSe(dbg, 0, 0);
		}
		gerenciadorEfeitos.DesenhaSe(dbg, 0,0);

		
	}
	public void carregaInimigos(int ind) {
		
		if (fase<=Constantes.FASE_TROCA_INIMIGO) { 
			for(int i = 0; i < ind; i++){
				Color cor = Color.black;
				
				switch (GamePanel.rnd.nextInt(3)) {
				case 0:
					cor = Color.red;
					break;
				case 1:
					cor = Color.BLUE;
					break;
				case 2:
					cor = Color.green;
					break;
	
					
				default:
					break;
				}
				int a =( i%2);

				if (a==0)
					listadeagentes.add(new Inimigo(200+i*30,- GamePanel.PHEIGHT/4+10, cor));		
				else
					listadeagentes.add(new Inimigo(200+i*30, -GamePanel.PHEIGHT/4+150, cor));		
			}
		}else {
			int max = ind-(Constantes.FASE_TROCA_INIMIGO*Constantes.QuantidadeInimigos);
			
			for(int i = 0; i < max; i++){
				
				if (i%2==0)
					listadeagentes.add(new Inimigo(100+i*50,- GamePanel.PHEIGHT/4+10));		
				else
					listadeagentes.add(new Inimigo(100+i*50, -GamePanel.PHEIGHT/4+40));	
		
			}
		}
		

	}
	public void carregaMeteoro(int ind){ 
		for(int i = 0; i < ind; i++){
			 
			listadeMeteoro.add(new Meteoro(GamePanel.rnd.nextInt(GamePanel.PWIDTH),GamePanel.rnd.nextInt(GamePanel.PHEIGHT),Math.PI/((i%5)+1),50,((i%5)+1)));
			
			
			}
	}

	@Override
	void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT){
			minhaNave.LEFT = true;
			LEFT = true;
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			RIGHT = true;
			minhaNave.RIGHT = true;

		}
		if(keyCode == KeyEvent.VK_UP){
			UP = true;
			minhaNave.UP = true;

		}
		if(keyCode == KeyEvent.VK_DOWN){
			DOWN = true;
			minhaNave.DOWN = true;

		}
		if(keyCode == KeyEvent.VK_SPACE){
			
			minhaNave.disparaTiro();

		}
	}
	@Override
	void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT){ 
			LEFT = false;
			minhaNave.LEFT = false;

		}
		if(keyCode == KeyEvent.VK_RIGHT){
			RIGHT = false;
			minhaNave.RIGHT = false;

		}
		if(keyCode == KeyEvent.VK_UP){
			UP = false;
			minhaNave.UP = false;

		}
		if(keyCode == KeyEvent.VK_DOWN){
			DOWN = false;
			minhaNave.DOWN = false;

		}
	}
	
	@Override
	void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mousex = e.getX(); 
		mousey = e.getY();
	}
	@Override
	void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void setImagemcharsets(BufferedImage imagemcharsets) {
		this.imagemcharsets = imagemcharsets;
	}
	public static BufferedImage getImagemcharsets() {
		return imagemcharsets;
	}

}
