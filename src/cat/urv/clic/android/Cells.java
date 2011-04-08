package cat.urv.clic.android;

public class Cells {

	protected Integer rows;
	protected Integer cols;
	protected Boolean border;
	protected String imatge;
	protected Style style;
	//protected HashMap<String,String> cell;
	
	public Cells() {
		this.rows = 4;
		this.cols = 4;
		this.border = false;
		this.imatge = "";
		this.style = null;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	public void setBorder(Boolean border) {
		this.border = border;
	}

	public void setImatge(String imatge) {
		this.imatge = imatge;
	}

	public void setStyle(Style style) {
		this.style = style;
	}
}
