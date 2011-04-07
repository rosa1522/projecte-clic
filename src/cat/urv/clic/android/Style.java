package cat.urv.clic.android;

public class Style {
	//Registre Font
	protected static class Font{
		protected String family;
		protected Boolean bold;
		protected Integer size;
		
		public Font() {
			this.family = "";
			this.bold = false;
			this.size = 0;
		}
	}
	
	//Registre Color
	protected static class Color{
		protected String foreground;
		protected String background;
		protected String inactive;
		protected String alternative;
		protected String border;
		
		public Color() {
			this.foreground = "";
			this.background = "";
			this.inactive = "";
			this.alternative = "";
			this.border = "";
		}
	}

	//Atributs d'Style
	protected String borderStroke;
	protected String markerStroke;
	protected Integer margin;
	protected Font font;
	protected Color color;
	
	public Style() {
		this.borderStroke = "";
		this.markerStroke = "";
		this.margin = 0;
		this.font = null;
		this.color = null;
	}

	public void setBorderStroke(String borderStroke) {
		this.borderStroke = borderStroke;
	}

	public void setMarkerStroke(String markerStroke) {
		this.markerStroke = markerStroke;
	}

	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
