
import java.io.*;
import java.net.DatagramSocket;
import java.util.*;

public class Router {
	private char routerID; //节点ID
    private int routerPort; //节点端口号
    String path;  //配置文件路径
    int maxHop = 6; //设置最大跳数
    static DatagramSocket ds;
    private Map<String,String[]> map1=new HashMap<String,String[]>(); //自身ID、相邻节点ID + 路径长度、端口号
    private Map<String,String[]> map2=new HashMap<String,String[]>(); //自身ID、相邻节点ID + 路径长度、端口号
    
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
        String n=br.readLine();  //读取相邻节点个数
        
        String s = "0 0";
        String[] sa1 = {Integer.toString(port),Integer.toString(maxHop)};
        map1.put(s, sa1);  //0 0 port maxHop用来记录源端口号和最大跳数
        for(int i=0;i<Integer.parseInt(n);i++) {
        	String[] sa2=br.readLine().split(" ");
        	String s1 = ID + " " + sa2[0]; //自身ID 相邻节点ID
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
        //计算最短路径
    }
    
//    public static void main(String[] args) throws Exception{
//    	Router router = new Router(args[0].charAt(0), Integer.parseInt(args[1]), args[2]);
//    	UDPService  us = new UDPService(router);
//    	us.service();
//    }
}


