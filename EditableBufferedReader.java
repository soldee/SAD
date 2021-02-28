import java.io.BufferedReader;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

	Reader in;

	public EditableBufferedReader(Reader in) {
		super(in, buffer);
	}
	
	public static void setRaw() {
		try {
			Process p = Runtime.getRuntime().exec("stty raw -echo");
		} catch(Exception e) {}
	}
	
	public void unsetRaw() {
		try {
			Process p = Runtime.getRuntime().exec("stty -raw echo");
		} catch(Exception e) {}
	}
	
	public static void main(String args[]) {
		setRaw();
	}
	
	@Override
	public int read() {
		int num = 0;
		//char[] c = in.buf;
		return num;
	}
	
	@Override
	public String readLine() {
		String s = "";
		
		return s;
	}
}
