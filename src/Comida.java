import java.awt.Graphics2D;


public abstract class Comida {
	
	double X,Y,life;
	
	public abstract void SimulaSe(int DiffTime);
	public abstract void DesenhaSe(Graphics2D dbg,int XMundo,int YMundo);
	
}