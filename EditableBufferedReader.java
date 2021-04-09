import java.io.*;
import java.util.Observable;

//Controller
public class EditableBufferedReader extends BufferedReader {

    EditableBufferedReaderObservable buf;

    public EditableBufferedReader(Reader in) {
        super(in);
        this.buf = new EditableBufferedReaderObservable();
    }

    public int read() throws IOException{
        return buf.read();
    }

    public String readLine() throws IOException{
        return buf.readLine();
    }

    
    class EditableBufferedReaderObservable extends Observable {

        Line line;
        Console console;

        public EditableBufferedReaderObservable() {
            this.line = new Line();
            this.console = new Console(line);
            addObserver(console);
        }


        public void setRaw() throws IOException{
            String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        }
    

        public void unsetRaw() throws IOException{
            String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        }

        
        
        public int read() throws IOException{
            int c;
            
            c = EditableBufferedReader.super.read();
            if (c != 27)
                return c;

            c = EditableBufferedReader.super.read();
            c = EditableBufferedReader.super.read();
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
                    EditableBufferedReader.super.read();
                    return Key.INSERT;
                case '3':
                    EditableBufferedReader.super.read();
                    return Key.DEL;
                default:
                    return c;
            }
        }	

        
        
        public String readLine() throws IOException{
            this.setRaw();
            int n = this.read();

            while(n != 13) { //n!=M (enter)
                setChanged();

                switch(n){
                    case Key.UP:
                        break;
                    case Key.DOWN:
                        break;
                    case Key.RIGHT:
                        notifyObservers(Key.RIGHT);
                        line.fletxaDreta();
                        break;
                    case Key.LEFT:
                        notifyObservers(Key.LEFT);
                        line.fletxaEsquerra();
                        break;
                    case Key.HOME:
                        notifyObservers(Key.HOME);
                        line.home();
                        break;
                    case Key.END:
                        notifyObservers(Key.END);
                        line.end();
                        break;
                    case Key.INSERT:
                        line.insert();
                        break;
                    case Key.DEL:
                        notifyObservers(Key.DEL);
                        line.delete();
                        break;
                    case Key.BKSP:
                        notifyObservers(Key.BKSP);
                        line.bksp();
                        break;
                    default:
                        notifyObservers(n);
                        line.insertChar((char) n);
                        break;
                }
                n = this.read();
            }

            this.unsetRaw();
            return line.getString();
	    }

    }

}