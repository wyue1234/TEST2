import java.io.*;
import java.net.*;
import java.util.*;

public class UDPService{
	private Router router;
	
	
	public UDPService(Router router) throws IOException {
		this.router=router;
	}
	
	
	
	public void service() throws IOException, ClassNotFoundException {  //�հ�����
		System.out.println("�յ����ݰ����߳�����");
		
		Thread ct = new ServiceThread(router,Router.ds);
		ct.start();
		
		DijkstraThread dt = new DijkstraThread(router);
    	
		dt.start();
	}
	
}
