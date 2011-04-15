import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ControladorDeDirecao {
	
	ArrayList<Direcionador> direcionadores = new ArrayList<Direcionador>();
	
	public ControladorDeDirecao(String filename){
		
		InputStream in = GamePanel.instance.getClass().getResourceAsStream(filename);
			
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			
		String str = "";
			
			
		try {
			while((str = bf.readLine())!=null){
				if(str.charAt(0)!='#'){
					String strs[] = str.split(";");
						
					
						
					int codigo = Integer.parseInt(strs[0]); 
					int ix = Integer.parseInt(strs[1]);
					int iy = Integer.parseInt(strs[2]);
					//int idir = Integer.parseInt(strs[3]);
					System.out.println(strs[1]);	
					Direcionador direcionador = new Direcionador();
										
					direcionador.cod = codigo;
					direcionador.x = ix;
					direcionador.y = iy;
					//direcionador.dir = idir;
					
					System.out.println(" "+codigo+" "+ix+" "+iy);
						
					direcionadores.add(direcionador);
					//System.out.println(direcionadores.size());
					
	
				}
			}
		} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}



public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
//	
//	for(int i = 0; i < direcionadores.size(); i++){
//		
//		Direcionador direc = direcionadores.get(i);
//		
//		int px = (int)(direc.x*16)-XMundo;
//		int py = (int)(direc.y*16)-YMundo;
//		
//		dbg.setColor(Color.GREEN);
//		dbg.drawRect(direc.x, direc.y, 16, 16);
//		
//		System.out.println(i+" "+px+" "+py+"  "+direcionadores.size()+ " "+direc.x+" "+direc.y);
//	
//	}
	
}

}



