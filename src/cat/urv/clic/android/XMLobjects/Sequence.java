package cat.urv.clic.android.XMLobjects;

public class Sequence {
	
	String[] taulaNoms;
	Integer index;
	
	public Sequence(Integer maxim){
		this.index = 0;
		this.taulaNoms = new String[maxim];
	}
	
	public void proximContador(){
		this.index = this.index + 1;
	}
	
	public void afegirItem(String name){
		this.taulaNoms[this.index] = name;
		proximContador();
	}
	
	public String[] getTaulaNoms(){
		return this.taulaNoms;
	}
	
	public Integer getIndex(){
		return this.index;
	}
}
