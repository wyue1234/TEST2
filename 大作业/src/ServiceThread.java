import java.io.*;
import java.net.*;
import java.util.*;

public class ServiceThread extends Thread{
	Router router;
	DatagramSocket ds;
	DatagramPacket dp;
	
	ServiceThread(Router router,DatagramSocket s){
		this.router = router;
		this.ds = s;
	}
	
	
	public void sendPacket(DatagramPacket dp) throws IOException, ClassNotFoundException{
		DatagramPacket dp_receive = dp;
		
		InetAddress localIP = InetAddress.getLocalHost();
		
		byte[] buf = dp_receive.getData();
		ByteArrayInputStream bis = new ByteArrayInputStream(buf);
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		
		@SuppressWarnings("unchecked")
		Map<String,String[]> tempMap= (HashMap<String, String[]>) ois.readObject();
		
		String s = "0 0";
		String[] sa = tempMap.get(s);
		int senderPort=Integer.parseInt(sa[0]); //谁发给它的数据包的端口号
		sa[0]=Integer.toString(router.getRouterPort()); //自身ID标识
		sa[1]=Integer.toString((Integer.parseInt(sa[1])-1)); //将最大跳数减一
		tempMap.put(s,sa);  //再次put到数据包进行发送来标识当前发送方ID和当前最大跳数
		
		byte[] buff = null;
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(tempMap);
		buff=os.toByteArray();
		
	
		
		
		Map<String,String[]> sendMap = new HashMap<String,String[]>();
		sendMap.putAll(router.getMap1());
		
		sendMap.remove(s);	
		
		for(String key:sendMap.keySet()) {
			String[] s1=sendMap.get(key); 
			char receiveID = key.charAt(2);  //接收节点ID

			int port=Integer.parseInt(s1[1]); //获取目的节点的端口号
			
			int maxHop = Integer.parseInt(sa[1]);
			if(maxHop > 0 && port!=senderPort ) { //保证数据包还有剩余跳数以及不发送到刚才向它发送该数据包的节点
				DatagramPacket sendConfig = new DatagramPacket(buff, buff.length,localIP,port);
				ds.send(sendConfig);
				System.out.println(router.getID() + "发送数据包到" + receiveID + ",剩余跳数:" + maxHop);
				
			}
		}

		
	}
	
	public DatagramPacket receive() throws IOException, ClassNotFoundException {
		// 创建字节数组来接受数据包
		byte[] buf = new byte[1024];
		DatagramPacket dp_receive = new DatagramPacket(buf, buf.length);

		Router.ds.receive(dp_receive);
		buf = dp_receive.getData(); // 获得缓冲数组

		byte[] buff = new byte[buf.length];
		System.arraycopy(buf, 0, buff, 0, buf.length);

		ByteArrayInputStream bis = new ByteArrayInputStream(buff);
		ObjectInputStream ois = new ObjectInputStream(bis);

		@SuppressWarnings("unchecked")
		Map<String, String[]> tempMap = (HashMap<String, String[]>) ois.readObject();

		String s = "0 0";

		// String[] sa = tempMap.get(s);
		// int port = Integer.parseInt(sa[0]); //得到发送方的端口号

		int port = dp_receive.getPort(); // 得到发送方的端口号

		System.out.println(router.getID() + "收到端口" + port + "发来的数据包");
		tempMap.remove(s);

		synchronized (this) {
			router.getMap2().putAll(tempMap);

		}

		return dp_receive;

	}
	 
	public void run(){
		try{
			while(true){
				byte[] buf = new byte[1024];
				DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
				dp_receive = receive();
				sendPacket(dp_receive);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
