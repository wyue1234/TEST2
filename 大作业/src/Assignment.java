

public class Assignment {
	public static void main(String[] args) throws Exception{
		Router router = new Router(args[0].charAt(0), Integer.parseInt(args[1]), args[2]);
		
		SendThread st = new SendThread(router); //启动一个线程定期发送config信息
		st.start();
		UDPService  us = new UDPService(router);
    	us.service();
		
    	
    	
	}
}
