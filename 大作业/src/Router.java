
import java.io.*;
import java.net.DatagramSocket;
import java.util.*;

public class Router {
	private char routerID; //�ڵ�ID
    private int routerPort; //�ڵ�˿ں�
    String path;  //�����ļ�·��
    int maxHop = 6; //�����������
    static DatagramSocket ds;
    private Map<String,String[]> map1=new HashMap<String,String[]>(); //����ID�����ڽڵ�ID + ·�����ȡ��˿ں�
    private Map<String,String[]> map2=new HashMap<String,String[]>(); //����ID�����ڽڵ�ID + ·�����ȡ��˿ں�
    
//    A B 2 2001
//    A C 5 2002
//    A D 1 2003
//    
//    
//    B A 2 2000
//    B 
    
    
    
    
    public Router(char ID,int port,String path)throws IOException{
        routerID=ID;
        this.routerPort=port;
        ds = new DatagramSocket(routerPort);
        this.path = path;
        File file=new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String n=br.readLine();  //��ȡ���ڽڵ����
        
        String s = "0 0";
        String[] sa1 = {Integer.toString(port),Integer.toString(maxHop)};
        map1.put(s, sa1);  //0 0 port maxHop������¼Դ�˿ںź��������
        for(int i=0;i<Integer.parseInt(n);i++) {
        	String[] sa2=br.readLine().split(" ");
        	String s1 = ID + " " + sa2[0]; //����ID ���ڽڵ�ID
        	String[] s2 = {sa2[1],sa2[2]};
        	map1.put(s1, s2);
        	map2.put(s1, s2);
        }
        
        fr.close();
        br.close();
    }
    
//    public DatagramSocket getDS(){
//    	return ds;
//    }
    
    public char getID(){
    	return routerID;
    }
    
    public int getRouterPort() {
    	return routerPort;
    }
    
   


	public Map<String, String[]> getMap1() {
		return map1;
	}

	public Map<String, String[]> getMap2() {
		return map2;
	}


	public String getPath() {
    	return path;
    }
    
    public int[][] matrix(){
    	
		return null;
    	
    }
    
    public void Dijkstra(){
        //�������·��
    }
    
//    public static void main(String[] args) throws Exception{
//    	Router router = new Router(args[0].charAt(0), Integer.parseInt(args[1]), args[2]);
//    	UDPService  us = new UDPService(router);
//    	us.service();
//    }
}


