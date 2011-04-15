import java.awt.Graphics2D;


public abstract class Objeto {
	
	double X,Y,life;
	int vida;
	boolean vivo;
	public abstract void SimulaSe(int DiffTime);
	public abstract void DesenhaSe(Graphics2D dbg,int XMundo,int YMundo);
	
}