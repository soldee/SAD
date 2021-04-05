import java.io.*;
import java.util.*;

class Line{
	
	private int pos;
	private boolean insert;
	private ArrayList<Character> buffer;


	public Line() {
		insert = false;
		buffer = new ArrayList<>(100);
		pos = 0;
	}


	public void insertChar(char c){

		System.out.print(c);

		if (insert && pos<buffer.size()) {
			buffer.set(pos, c);
			pos++;
			return;
		}

		else {
			System.out.print("\033[s");

			for (int i=pos; i<buffer.size(); i++)
				System.out.print(buffer.get(i));
			
			System.out.print("\033[u");
		}
		buffer.add(pos, c);
		pos++;
	}


	public String getString() {
		String text = "";
		
		for (char c : buffer)
			text += c;

		return text;
	}
	

	public void fletxaDreta() {
		if (pos != buffer.size()) {
			pos++;
			System.out.print("\033[C");
		}
	}
	

	public void fletxaEsquerra() {
		if (pos != 0) {
			pos--;
			System.out.print("\033[D");
		}
	}

	
	public void home() {
		pos = 0;
		System.out.print("\033[G");
	}

	
	public void end() {
		for (int i=0; i<buffer.size()-this.pos; i++) {
			System.out.print("\033[C");
		} 
		pos = buffer.size();
	}
	

	public void insert() {
		insert = !insert;
	}

	
	public void delete() {
		if (pos == buffer.size()) {
			return;
		}

		System.out.print("\033[s");

		for (int i=pos+1; i<buffer.size(); i++)
			System.out.print(buffer.get(i));
			
		System.out.print(" ");
		System.out.print("\033[u");	
		
		buffer.remove(pos);
	}

	
	public void bksp() {
		if (pos == 0) {
			return;
		}

		if (pos == buffer.size()) {
			System.out.print("\b \b");
		}
		else {
			System.out.print("\b");
			System.out.print("\033[s");

			for (int i=pos; i<buffer.size(); i++)
				System.out.print(buffer.get(i));
			
			System.out.print(" ");
			System.out.print("\033[u");	
		}
		pos--;
		buffer.remove(pos);
	}
}




