import java.io.FileInputStream;
import java.util.Properties;

public class DijkstraThread extends Thread{
	
	Router router;
	
	DijkstraThread(Router router){
		this.router=router;
	}
	
	/*synchronized*/ void Dijkstra(Router router) { 
		
		MatrixDG matrix=new MatrixDG(router.getMap2());
		int[] prev = new int[matrix.getVertexNum()];
	    int[] dist = new int[matrix.getVertexNum()];
		matrix.dijkstra(matrix.getRouter(router),prev,dist);
		//System.out.println(matrix.getRouter(router));
	}
	
	
	public void run() {

		while(true){
			try {
				Properties p = new Properties();
				p.load(new FileInputStream("E:\\java-neon\\eclipse\\workspace\\大作业\\src\\assignment.properties"));
				String time = p.getProperty("sendPathTime");
				sleep(Integer.parseInt(time));
				
				Dijkstra(router);

			} catch (Exception e) {
				// TODO 自动生成的 catch块
				e.printStackTrace();
			}
		}
	}
}
