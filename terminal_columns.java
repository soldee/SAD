import java.io.BufferedReader;
import java.io.InputStreamReader;

public class terminal_columns {
	public static void main(String args[]) {
		String s;
		Process p;
		try {
			p = Runtime.getRuntime().exec("tput cols");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while((s = br.readLine()) != null)
				System.out.println(s);
		} catch (Exception e) {}
	}
}
