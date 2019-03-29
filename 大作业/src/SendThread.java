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
		oos.writeObject(router.getMap1());  //��Map1���ʹ����������
		buf = os.toByteArray();  
		
		Map<String,String[]> tempMap= new HashMap<>();
		tempMap.putAll(router.getMap1());
		
		String s ="0 0";
		
		
		tempMap.remove(s,tempMap.get(s)); //����ʶӳ���Ƴ�Map���ϣ��������ü���Ѱ�����ڽڵ�˿ں�
		
		
		for(String key:tempMap.keySet()) {
			char receiveID = key.charAt(2);  //���սڵ�ID
			String[] s1 = tempMap.get(key);
			int port=Integer.parseInt(s1[1]);
			DatagramPacket sendConfig = new DatagramPacket(buf, buf.length,localIP,port);
			Router.ds.send(sendConfig);
			System.out.println(router.getID() + "���������ļ������սڵ�" + receiveID);
			
		}
	}
	
	
	public void run(){
		while(true){
			try{
				sendConfig();
				Properties p = new Properties();
				p.load(new FileInputStream("E:\\java-neon\\eclipse\\workspace\\����ҵ\\src\\assignment.properties"));
				String time = p.getProperty("sendConfigTime");
				sleep(Integer.parseInt(time)); //ÿ��1s�ط������ļ�
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
