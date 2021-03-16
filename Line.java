import java.io.*;
import java.util.*;

class Line extends Observable {
	
	private int pos=0;
	public StringBuffer llargada= new StringBuffer(1000);

	
	public void fletxaDreta(){
		if(pos!=llargada.length()){
		this.pos++;
		System.out.print("\033[C");
		}
	}
	
	public void fletxaEsquerra(){
		if(pos!=0){
		this.pos--;
		System.out.print("\033[D");
		}
	}
	
	public void home(){
		for(int i=0;i<this.pos;i++){
		System.out.print("\033[D");
		} this.pos=0;
	}
	
	public void end(){
		for(int i=0;i<llargada.length()-this.pos;i++){
		System.out.print("\033[C")
		} this.pos=this.llargada.length();
	}
	
	public void insert(){
		System.out.print("\033[2");
	}
	
	public void del(){
		System.out.print("\033[3");
	}
	
	public void bksp(){
		System.out.print("\b");   //tirar enrere una línia el cursor
		System.out.print(" ");    //posar espai
		System.out.print("\b");   //tirar enrere un altre cop
	}
	public int getLength{

	return llargada.length();
	}
}
