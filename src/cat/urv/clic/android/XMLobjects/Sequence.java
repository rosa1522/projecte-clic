package cat.urv.clic.android.XMLobjects;

public class Sequence {
	
	Item[] taulaItem;
	Integer index;
	
	public Sequence(Integer maxim){
		this.index = 0;
		this.taulaItem = new Item[maxim];
	}
	
	public void proximContador(){
		this.index = this.index + 1;
	}
	
	public void afegirItem(Item item){
		System.out.println("Abansssss");
		this.taulaItem[this.index] = item;
		proximContador();
		System.out.println("Despresssssssssssssssssss");
	}
}
