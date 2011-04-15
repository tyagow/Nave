import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Constantes {
	
	//TIRO
	public static int TEMPO_TIRO = 500;
	public static int QuantidadeInimigos=50;
	public static int QuantidadeMeteoro=50;
	public static double fase=1;


	public static final ThreadSom fundo=new ThreadSom("/tiro.wav");

	public static final ThreadSom bomb=new ThreadSom("/BOMB.WAV");

	public static final ThreadSom nave=new ThreadSom("/nave.mp3");

	//METEORO 
	public static final int METEORO_GIGANTE = 1;
	public static final int METEORO_GRANDE = 2;
	public static final int METEORO_MEDIO = 3;
	public static final int METEORO_PEQUENO = 4;
	public static final int METEORO_MICRO = 5;
	public static final int METEORO_LOL = 6;

	public static final int TEMPO_TIRO_INIMIGO1 = 2000;

	public static final double AGILIDADE_INIMIGO1 = Math.PI/2; // 45graus /s quantidade de gruas por segundo

	public static final int DANO_TIRO = 10;

	public static final int NAVE_ACELERADOR_X = -112 ;
	public static final int NAVE_ACELERADOR_Y = -52;
	public static final int NAVE_ACELERADOR_SIZEX = 100;
	public static final int NAVE_ACELERADOR_SIZEY = 20;
	
	public static BufferedImage explosao;

	public static BufferedImage explosao2;

	public static final int NAVE_HIT_XP = 15;


	public static final double METEORO_XP_BASE = 5;



	public static final int XP_EVOLUCAO = 500;
	public static final int TEMPO_TIRO_INIMIGO2 = 5000;
	public static final int FASE_TROCA_INIMIGO = 4;
	public static final int TEMPO_DURACAO_VIDA = 50000;
	
	
	public static BufferedImage LoadImage(String filename){
		BufferedImage image = null;
		
		
		BufferedImage imgtmp;
		try {
			imgtmp = ImageIO.read( GamePanel.instance.getClass().getResource(filename));
			image = new BufferedImage(imgtmp.getWidth(), imgtmp.getHeight(), BufferedImage.TYPE_INT_ARGB);
			image.getGraphics().drawImage(imgtmp, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}
	public static boolean colidecircular(double X1,double Y1,double R1,double X2,double Y2,double R2){
		
		double dx = X1-X2;
		double dy = Y1-Y2;
		double sr = R1+R2;
		double d2 = dx*dx + dy*dy;
		
		if(d2<(sr*sr)){
			return true;
		}
		
		return false;
	}

}
