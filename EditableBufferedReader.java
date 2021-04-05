import java.io.*;


public class EditableBufferedReader extends BufferedReader {

	private Reader in;

	public EditableBufferedReader(Reader in) {
		super(in);
	}
	
	
	public static void setRaw() throws IOException{
		String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
		Runtime.getRuntime().exec(cmd);
	}
	
	public void unsetRaw() throws IOException{
		String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
		Runtime.getRuntime().exec(cmd);
	}
	
	@Override
	public int read() throws IOException{
		int c;
		
		c = super.read();
		if (c != 27)
			return c;

		c = super.read();
		c = super.read();
		switch (c) {
			case 'A':
				return Key.UP;
			case 'B':
				return Key.DOWN;
			case 'C':
				return Key.RIGHT;
			case 'D':
				return Key.LEFT;
			case 'H':
				return Key.HOME;
			case 'F':
				return Key.END;
			case '2':
				super.read();
				return Key.INSERT;
			case '3':
				super.read();
				return Key.DEL;
			default:
				return c;
		}
	}	

	
	@Override
	public String readLine() throws IOException{
		this.setRaw();
		String s = "";
		Line line = new Line();
		int n = this.read();
		while(n != 13) { //n!=M (enter)
			switch(n){
				case Key.UP:
					break;
				case Key.DOWN:
					break;
				case Key.RIGHT:
					line.fletxaDreta();
					break;
				case Key.LEFT:
					line.fletxaEsquerra();
					break;
				case Key.HOME:
					line.home();
					break;
				case Key.END:
					line.end();
					break;
				case Key.INSERT:
					line.insert();
					break;
				case Key.DEL:
					line.delete();
					break;
				case Key.BKSP:
					line.bksp();
					break;
				default:
					line.insertChar((char) n);
					break;
			}
			n = this.read();
		}
		this.unsetRaw();
		return line.getString();
	}
}
