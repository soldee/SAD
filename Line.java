import java.util.ArrayList;

//Model
class Line {
    private int pos;
	private boolean insert;
	private ArrayList<Character> buffer;


	public Line() {
		insert = false;
		buffer = new ArrayList<>(100);
		pos = 0;
	}

	public int getPos() {
		return pos;
	}
	
	public ArrayList<Character> getBuffer() {
		return buffer;
	}

	public boolean getInsert() {
		return insert;
	}

	public String getString() {
		String text = "";
		
		for (char c : buffer)
			text += c;

		return text;
	}


	public void insertChar(char c){

		if (insert && pos<buffer.size()) {
			buffer.set(pos, c);
			pos++;
			return;
		}
		buffer.add(pos, c);
		pos++;
	}

	

	public void fletxaDreta() {
		if (pos != buffer.size()) {
			pos++;
		}
	}
	


	public void fletxaEsquerra() {
		if (pos != 0) {
			pos--;
		}
	}

	

	public void home() {
		pos = 0;
	}

	

	public void end() {
		pos = buffer.size();
	}
	


	public void insert() {
		insert = !insert;
	}


	
	public void delete() {
		if (pos == buffer.size()) {
			return;
		}
		buffer.remove(pos);
	}

	

	public void bksp() {
		if (pos == 0) {
			return;
		}
		pos--;
		buffer.remove(pos);
	}
}
