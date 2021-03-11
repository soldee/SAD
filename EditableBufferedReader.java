import java.io.*;


public class EditableBufferedReader extends BufferedReader {

	private Reader in;
	private char[] buffer;

	public EditableBufferedReader(Reader in) {
		super(in);
		buffer = new char[100];
	}
	
	
	public static void setRaw() throws IOException{
		String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
		Runtime.getRuntime().exec(cmd);
	}
	
	public void unsetRaw() throws IOException{
		String[] cmd = {"/bin/sh", "-c", "stty cooked </dev/tty"};
		Runtime.getRuntime().exec(cmd);
	}
	
	
	//fletxa dalt: ^[[A 27 91 65
	//fletxa abaix: ^[[B 27 91 66
	//fletxa dreta: ^[[C 27 91 67
	//fletxa esquerra: ^[[D 27 91 68
	//home: ^[[H 27 91 72
	//end: ^[[F 27 91 70
	//insert: ^[[2~ 27 91 50
	//del: ^[[3~ 27 91 51
	//bksp: 127
	@Override
	public int read() throws IOException{
		this.setRaw();
		int n = super.read();
		if (n == 27){ //en cas que comenci amb escape charcater ens quedem amb el tercer int
			super.read();
			n = super.read();
		}
		this.unsetRaw();
		return n;
	}

	
	@Override
	public String readLine() throws IOException{
		String s = "";
		Line line = new Line();
		int n = this.read();
		while(n != 13) { //n!=M (enter)
			switch(n){
				case 65:
					line.fletxaDalt();
					break;
				case 66:
					line.fletxaBaix();
					break;
				case 67:
					line.fletxaDreta();
					break;
				case 68:
					line.fletxaEsquerra();
					break;
				case 72:
					line.home();
					break;
				case 70:
					line.end();
					break;
				case 50:
					line.insert();
					break;
				case 51:
					line.del();
					break;
				case 127:
					line.bksp();
					break;
				default:
					s += (char) n;
					break;
			}
			n = this.read();
		}
		return s;
	}
}



