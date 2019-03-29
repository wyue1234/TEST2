import java.util.*;

public class MatrixDG {
	private int edgeNum = 0;    //图的边数目
	private char[] vertex; //图的顶点数组
	private int[][] matrix;  //邻接矩阵
	private static final int INF = Integer.MAX_VALUE;   // 最大值
	
	
	
	public MatrixDG(Map<String,String[]> map) {
		int i=0;
		int j=0;
		Set<Character> set=new TreeSet<Character>();
		for(String key : map.keySet()) {
			String[] s=key.split(" ");
			set.add(s[0].charAt(0));
		}
		
		int vertexNum = set.size();
		vertex = new char[vertexNum];
		Iterator<Character> it = set.iterator();  
		while (it.hasNext()) {  
		  char str = it.next();  
		  this.vertex[i++]= str;
		}  
		
//		for(Character c: set) {
//			this.vertex[i++]=(char)c;
//		}  //-----------------------------初始化顶点
		
		this.matrix = new int[vertexNum][vertexNum];
		for(i=0;i<vertexNum;i++){
			for(int k=0;k<vertexNum;k++){
				this.matrix[i][k] = INF;
			}
		}
		
//		  A B
//		  A C
//		  B A
//		  B C
//		  C A
//		  C B
		
		for(i=0;i<vertexNum;i++) {
			matrix[i][i] = 0;
			for(String key : map.keySet()){
				char c1 = key.charAt(0); //起始节点
				if(c1==vertex[i]) {
					char c2 = key.charAt(2); //终止节点
					
					for(j=0;j<vertexNum;j++){
						if(vertex[j] == c2){
							String[] s = map.get(key);
							int length = Integer.parseInt(s[0]);
							this.matrix[i][j] = length;
						}
					}
					
				}
			}
			
			
		} //-------------------------------初始化边
		
		edgeNum = map.size()/2;   //-----------统计边的数量
		
		
	}
	
	public int getEdgeNum(){
		return edgeNum;
	}
	
	
	public int getVertexNum() {
		return vertex.length;
	}
	
	public int getRouter(Router router) {
		for(int i=0;i<vertex.length;i++) {
			if(router.getID()==vertex[i]) {
				return i;
			}
		}
		return -1;
	}

	
	public void dijkstra(int v,int[] pre,int[] dis){
		boolean[] flags = new boolean[vertex.length];  //顶点的最短路径是否获取到
		
		for(int i=0;i<vertex.length;i++){
			flags[i] = false;
			pre[i] = v;
			dis[i] = matrix[i][v];
		}
		flags[v] = true;
		dis[v] = 0;
		
		int j = 0;
		int cnt = vertex.length-1;
		while(cnt-->0){
			int distance = INF;
			for(int i=0;i<vertex.length;i++){
				if(flags[i]==false&& dis[i]<distance){
					distance = dis[i];
					j = i;
				}
			}
			flags[j] = true;
			
			for(int i=0;i<vertex.length;i++){
				int temp = (matrix[i][j]==INF ? INF : (distance + matrix[i][j]));
				if(temp<dis[i]&&flags[i]==false){
					dis[i] = temp;
					pre[i] = j;
				}
			}
		}
		
		for(int i=0;i<vertex.length;i++){
			if(i != v) {
			int k = i;
			String s=String.valueOf(vertex[k]);
			while(vertex[k] != vertex[v]) {
				s = vertex[pre[k]] + s;
				k = pre[k];
			  }
			System.out.println("least-cost path to node "+ vertex[i]+": "+s+" and the cost is "+dis[i]*1.0);
			}
		}
	}
	
	public static void main(String[] args) {
        
        Map<String,String[]> map=new HashMap<String,String[]>();
        
        String[] s5= {"2","2004"};
        map.put("B C", s5);
        String[] s6= {"2","2004"};
        map.put("C B", s6);
        
        String[] s1= {"1","2001"};
        map.put("A B", s1);
        String[] s4= {"1","2004"};
        map.put("B A", s4);
        
        String[] s2= {"5","2002"};
        map.put("A C", s2);
        String[] s3= {"5","2003"};
        map.put("C A", s3);
       
       
        
        MatrixDG pG = new MatrixDG(map);

        int[] prev = new int[pG.vertex.length];
        int[] dist = new int[pG.vertex.length];
        pG.dijkstra(1, prev, dist);
    }
} 
