import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;


public class GerenciadorEfeitos extends Objeto {

	public static LinkedList<Objeto> listaEfeitos = new LinkedList<Objeto>();
	int xp,sizeXP;
	public GerenciadorEfeitos() {
		sizeXP=38;
		xp=0;
	}
	@Override
	public void SimulaSe(int DiffTime) {
		// TODO Auto-generated method stub
		for(int i = 0;i < listaEfeitos.size();i++){
			  listaEfeitos.get(i).SimulaSe((int)DiffTime);
			  if(listaEfeitos.get(i).vivo==false){
				  listaEfeitos.remove(i);
			  }
		}

	}

	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		for(int i = 0;i < listaEfeitos.size();i++){
			  listaEfeitos.get(i).DesenhaSe(dbg,XMundo,YMundo);
	
		}
		System.out.println(listaEfeitos.size());
		dbg.setColor(Color.yellow);
		dbg.setFont(new Font("SansSerif", Font.BOLD, (int) sizeXP));
		dbg.drawString(xp+ "  XP", GamePanel.PWIDTH/2-sizeXP, sizeXP*2);
		dbg.setColor(Color.red);
		dbg.drawString(xp+ "  XP", GamePanel.PWIDTH/2-sizeXP-2, sizeXP*2-2);
		dbg.setFont(new Font("SansSerif", Font.BOLD, (int) 14));
		
		
		dbg.setColor(Color.yellow);
		dbg.setFont(new Font("SansSerif", Font.BOLD, (int) sizeXP));
		dbg.drawString("Level  " +CanvasGame.minhaNave.nivel, GamePanel.PWIDTH/2-sizeXP,sizeXP);
		dbg.setColor(Color.red);
		
		dbg.drawString("Level  " +CanvasGame.minhaNave.nivel, GamePanel.PWIDTH/2-sizeXP-3, sizeXP);
		dbg.setColor(Color.red);
		dbg.setFont(new Font("SansSerif", Font.BOLD, (int) 14));

	}
	
	
	public void ganhouXp(double x,double y,int _xp) {
		listaEfeitos.add(new Texto(_xp,x,y) );
		xp+=_xp;
		
	}
	public void evoluiu() {
		listaEfeitos.add(new Texto() );
	}
	
	public void ganhouLife(double x, double y, double life) {
		// TODO Auto-generated method stub
		listaEfeitos.add(new Texto(x,y,life) );
	}
	public void explosao(double x, double y, int velx, int vely) {
		// TODO Auto-generated method stub
		Constantes.bomb.run();

		Particula part=null;
		for(int B = 0; B < 100;B++){
			int modv = GamePanel.rnd.nextInt(200)+50;

			int pvx = 0;
			int pvy = 0;
		
				int a =B%4;
				switch (a) {
				
				case 0:
					pvx = velx + modv;
					pvy = vely - modv;
					break;
				case 1:
					pvx = -velx - modv;
					pvy = vely + modv;
					break;
				case 2:
					pvx = velx + modv;
					pvy = -vely + modv;
					break;
				case 3:
					pvx = -velx - modv;
					pvy = -vely - modv;
					break;
				
				
				}
			
			pvx = (int)(pvx*(0.1+0.25*GamePanel.rnd.nextFloat()));
			pvy = (int)(pvy*(0.1+0.25*GamePanel.rnd.nextFloat()));
			part = (Particula)new Explosao(x,y,pvx,pvy,GamePanel.rnd.nextInt(300)+200,Constantes.explosao,Constantes.explosao2);

//			switch (B%4) {
//			case 0:
//				part = (Particula)new Explosao(x+pvx/40,y+pvy/40,pvx/5,pvy/5,GamePanel.rnd.nextInt(600)+300,Constantes.explosao,Constantes.explosao2);
//
//				break;	
//			case 1:
//				part = (Particula)new Explosao(x,y,-pvx/5,pvy/5,GamePanel.rnd.nextInt(600)+300,Constantes.explosao,Constantes.explosao2);
//
//				break;
//			case 2:
//				part = (Particula)new Explosao(x,y,pvx/5,-pvy/5,GamePanel.rnd.nextInt(600)+300,Constantes.explosao,Constantes.explosao2);
//
//				break;
//			case 3:
//				part = (Particula)new Explosao(x,y,-pvx/5,-pvy/5,GamePanel.rnd.nextInt(600)+300,Constantes.explosao,Constantes.explosao2);
//
//				break;
//			default:
//				break;
//			}
//			if (B%4==0) {
//				part = (Particula)new Explosao(x,y,pvx/5,pvy/5,GamePanel.rnd.nextInt(300)+300,Constantes.explosao,Constantes.explosao2);
//			}
//			if (B%4==1) {
//				part = (Particula)new Explosao(x,y,-pvx/6,-pvy/6,GamePanel.rnd.nextInt(300)+300,Constantes.explosao,Constantes.explosao2);
//			}
//			if (B%4==2) {
//				part = (Particula)new Explosao(x,y,-pvx/6,-pvy/6,GamePanel.rnd.nextInt(300)+300,Constantes.explosao,Constantes.explosao2);
//			}
			if (part!=null) CanvasGame.particulas.add(part);
			vivo = false;
		}
		
	}

}
