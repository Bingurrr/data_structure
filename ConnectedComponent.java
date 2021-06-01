import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static int[] parent;
    public static void main(String[] args) throws IOException {
        
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	int N = Integer.parseInt(st.nextToken());
    	int M = Integer.parseInt(st.nextToken());
    	int[] parent;
        int ans = 0;
        parent = new int[N];
        for(int i = 0; i < N; i++){
            parent[i] = -1;
        }
		for (int i = 0; i < M; i++) {
			StringTokenizer st2 = new StringTokenizer(br.readLine());
    		int u = Integer.parseInt(st2.nextToken());
    		int v = Integer.parseInt(st2.nextToken());
            int p1 = find(u);
            int p2 = find(v);
            union(p1,p2);
    	}
        
    	for(int i = 0; i < N; i ++){
            if(parent[i] == -1) ans++;
        }
    	br.close();
    	
        System.out.println(ans);
    }
    
    
    public static int find(int curr){
        if(parent[curr] == -1) return curr;
        while(parent[curr] != -1){
            curr = parent[curr];
        }
        return curr;
    }
    public static void union(int p1, int p2){
        if(p1 == p2){
            return;
        }
        else if(p1 > p2){
            parent[p1] = p2;
        }
        else{
            parent[p2] = p1;
        }
    }
     

}
