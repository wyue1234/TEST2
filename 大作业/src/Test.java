import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class Test {
	public static void main(String[] args) throws IOException {
		Map<String,Integer> map = new HashMap<>();
		String s1 = "a b";
		String s2 = "b c";
		String s3 = "c d";
		
		map.put(s1, 1);
		map.put(s2, 4);
		map.put(s3, 6);
		for (String key : map.keySet()) {
		    System.out.println(key + " £º" + map.get(key));
		}
	}
		
		
		
//		
//		File file = new File("C:\\Users\\asus\\Desktop\\a.txt");
//		FileInputStream fis = new FileInputStream(file);
//		DataInputStream dis = new DataInputStream(fis);
//		String n = dis.readLine();
//		for(int i=0;i<Integer.parseInt(n);i++){
//			String s  = dis.readLine();
//			System.out.println(s);
//		}
//	}
}
