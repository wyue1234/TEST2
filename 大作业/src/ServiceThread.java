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
		int senderPort=Integer.parseInt(sa[0]); //˭�����������ݰ��Ķ˿ں�
		sa[0]=Integer.toString(router.getRouterPort()); //����ID��ʶ
		sa[1]=Integer.toString((Integer.parseInt(sa[1])-1)); //�����������һ
		tempMap.put(s,sa);  //�ٴ�put�����ݰ����з�������ʶ��ǰ���ͷ�ID�͵�ǰ�������
		
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
			char receiveID = key.charAt(2);  //���սڵ�ID

			int port=Integer.parseInt(s1[1]); //��ȡĿ�Ľڵ�Ķ˿ں�
			
			int maxHop = Integer.parseInt(sa[1]);
			if(maxHop > 0 && port!=senderPort ) { //��֤���ݰ�����ʣ�������Լ������͵��ղ��������͸����ݰ��Ľڵ�
				DatagramPacket sendConfig = new DatagramPacket(buff, buff.length,localIP,port);
				ds.send(sendConfig);
				System.out.println(router.getID() + "�������ݰ���" + receiveID + ",ʣ������:" + maxHop);
				
			}
		}

		
	}
	
	public DatagramPacket receive() throws IOException, ClassNotFoundException {
		// �����ֽ��������������ݰ�
		byte[] buf = new byte[1024];
		DatagramPacket dp_receive = new DatagramPacket(buf, buf.length);

		Router.ds.receive(dp_receive);
		buf = dp_receive.getData(); // ��û�������

		byte[] buff = new byte[buf.length];
		System.arraycopy(buf, 0, buff, 0, buf.length);

		ByteArrayInputStream bis = new ByteArrayInputStream(buff);
		ObjectInputStream ois = new ObjectInputStream(bis);

		@SuppressWarnings("unchecked")
		Map<String, String[]> tempMap = (HashMap<String, String[]>) ois.readObject();

		String s = "0 0";

		// String[] sa = tempMap.get(s);
		// int port = Integer.parseInt(sa[0]); //�õ����ͷ��Ķ˿ں�

		int port = dp_receive.getPort(); // �õ����ͷ��Ķ˿ں�

		System.out.println(router.getID() + "�յ��˿�" + port + "���������ݰ�");
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
