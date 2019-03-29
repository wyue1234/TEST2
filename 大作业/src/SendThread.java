import java.io.*;
import java.net.*;
import java.util.*;

public class SendThread extends Thread{
	
	Router router;
	
	
	public SendThread(Router router) throws IOException{
		this.router=router;
	
		
	}
	
	public void sendConfig() throws IOException{
		InetAddress localIP = InetAddress.getLocalHost();
		byte[] buf = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(router.getMap1());  //把Map1发送传入输出流中
		buf = os.toByteArray();  
		
		Map<String,String[]> tempMap= new HashMap<>();
		tempMap.putAll(router.getMap1());
		
		String s ="0 0";
		
		
		tempMap.remove(s,tempMap.get(s)); //将标识映射移除Map集合，并遍历该集合寻找相邻节点端口号
		
		
		for(String key:tempMap.keySet()) {
			char receiveID = key.charAt(2);  //接收节点ID
			String[] s1 = tempMap.get(key);
			int port=Integer.parseInt(s1[1]);
			DatagramPacket sendConfig = new DatagramPacket(buf, buf.length,localIP,port);
			Router.ds.send(sendConfig);
			System.out.println(router.getID() + "发送配置文件到接收节点" + receiveID);
			
		}
	}
	
	
	public void run(){
		while(true){
			try{
				sendConfig();
				Properties p = new Properties();
				p.load(new FileInputStream("E:\\java-neon\\eclipse\\workspace\\大作业\\src\\assignment.properties"));
				String time = p.getProperty("sendConfigTime");
				sleep(Integer.parseInt(time)); //每隔1s重发配置文件
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
