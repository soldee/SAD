import java.util.Observer;
import java.util.Observable;


//View
public class Console implements Observer{
    
    private Line line;

    public Console (Line line){
        this.line = line;
    }


    @Override
    public void update(Observable obs, Object o){
        int option = (int) o;
        switch (option) {
            case Key.RIGHT:
                fletxaDreta();
                return;
            case Key.LEFT:
                fletxaEsquerra();
                return;
            case Key.HOME:
                home();
                return;
            case Key.END:
                end();
                return;       
            case Key.DEL:
                delete();
                return; 
            case Key.BKSP:
                bksp();
                return;
            default:
                insertChar((char) option);
                return;
        }
    }


    public void insertChar(char c){

		System.out.print(c);

		if (line.getInsert() && line.getPos()<line.getBuffer().size()) {
			return;
		}

		System.out.print("\033[s");
            
		for (int i=line.getPos(); i<line.getBuffer().size(); i++)
			System.out.print(line.getBuffer().get(i));
			
		System.out.print("\033[u");
	}

	

	public void fletxaDreta() {
		if (line.getPos() != line.getBuffer().size()) {
			System.out.print("\033[C");
		}
	}
	


	public void fletxaEsquerra() {
		if (line.getPos() != 0) {
			System.out.print("\033[D");
		}
	}

	

	public void home() {
		System.out.print("\033[G");
	}

	

	public void end() {
		for (int i=line.getPos(); i<line.getBuffer().size(); i++) {
			System.out.print("\033[C");
		}
	}


	
	public void delete() {
		if (line.getPos() == line.getBuffer().size()) {
			return;
		}

		System.out.print("\033[s");

		for (int i=line.getPos()+1; i<line.getBuffer().size(); i++)
			System.out.print(line.getBuffer().get(i));
			
		System.out.print(" ");
		System.out.print("\033[u");	
	}

	

	public void bksp() {
		if (line.getPos() == 0) {
			return;
		}

		if (line.getPos() == line.getBuffer().size()) {
			System.out.print("\b \b");
		}
		else {
			System.out.print("\b");
			System.out.print("\033[s");

			for (int i=line.getPos(); i<line.getBuffer().size(); i++)
				System.out.print(line.getBuffer().get(i));
			
			System.out.print(" ");
			System.out.print("\033[u");	
		}
	}



}
