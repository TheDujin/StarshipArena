import java.time.Instant;
import java.util.ArrayList;

public class ShipRenderer {
	
	double[] vertices = new double[8];
	double[] textureCoords = new double[8];
	Point currentShipTrueCenter = new Point();
	Model box = new Model(vertices, textureCoords, new int[]{0, 1, 2, 2, 1, 3});
	static Texture HPColors = new Texture("hpbar_colors.png");
	static Texture haloTexture = new Texture("ships_halo.png");
	static Texture FOWTexture = new Texture("FOW_halo.png");
	static Texture rangeTexture = new Texture("range_halo.png");
	static Texture fighter_sprites = new Texture("fighter_sprites.png");
	static Texture interceptor_sprites = new Texture("interceptor_sprites.png");
	static Texture missileship_sprites = new Texture("missileship_sprites.png");
	

//function is broken
//	public void drawAllShips(ArrayList<Starship> allShips){
//		fighter_sprites.bind();
//		for (Starship s : allShips) {
//			if(s instanceof Fighter){
//				s.setTexture();
//				setTextureCoords(s.getTextureCoords());
//				setRotatedModel(s.vertices);
//			}
//		}
//		interceptor_sprites.bind();
//		for (Starship s : allShips) {
//			if(s instanceof Interceptor){
//				s.setTexture();
//				setTextureCoords(s.getTextureCoords());
//				setRotatedModel(s.vertices);
//			}
//		}
//		missileship_sprites.bind();
//		for (Starship s : allShips) {
//			if(s instanceof Missileship){
//				s.setTexture();
//				setTextureCoords(s.getTextureCoords());
//				setRotatedModel(s.vertices);
//			}
//		}
//	}
	
	public void drawShip(Starship s){
		s.setTexture();
		setTextureCoords(s.getTextureCoords());
		setRotatedModel(s.vertices);
	}
	
	public void drawShipIcon(Starship s){
		s.setIconTexture();
		setTextureCoords(s.getTextureCoords());
		setRotatedModel(s.vertices);
	}
	public void drawAllHPBars(ArrayList<Starship> selected){
		HPColors.bind();
		for (Starship s : selected) {
			updateTrueCenter(s);
			drawHPBar(s);
		}
	}
	
	public void drawHPBar(Starship s){	
		setColor(-1); //base color
		setModel(currentShipTrueCenter.X() - (s.getMaxHealth() / 2) * 3, currentShipTrueCenter.Y() - s.getClickRadius(), currentShipTrueCenter.X() + (s.getMaxHealth() / 2) * 3, currentShipTrueCenter.Y() - s.getClickRadius() - 10);
	
		setColor(s.getHealth()/s.getMaxHealth()); //color based on hp remaining
		setModel(currentShipTrueCenter.X() - (s.getMaxHealth() / 2) * 3, currentShipTrueCenter.Y() - s.getClickRadius(), currentShipTrueCenter.X() + (s.getHealth() - s.getMaxHealth() / 2) * 3, currentShipTrueCenter.Y() - s.getClickRadius() - 10);
	}
	
	public void drawAllShipHalos(ArrayList<Starship> selected){
		haloTexture.bind();
		setTextureCoords(0, 0, 1, 1);
		for (Starship s : selected) {
			updateTrueCenter(s);
			setModel(currentShipTrueCenter.X() - s.getHaloSize(), currentShipTrueCenter.Y() + s.getHaloSize(),
					currentShipTrueCenter.X() + s.getHaloSize(), currentShipTrueCenter.Y() - s.getHaloSize());
		}
	}
	
	public void drawAllFOW(ArrayList<Starship> ships){
		FOWTexture.bind();
		setTextureCoords(0, 0, 1, 1);
		for (Starship s : ships) {
			updateTrueCenter(s);
			setModel(currentShipTrueCenter.X() - s.getRadarRange(), currentShipTrueCenter.Y() + s.getRadarRange(),
					currentShipTrueCenter.X() + s.getRadarRange(), currentShipTrueCenter.Y() - s.getRadarRange());
		}
	}
	
	public void drawAllScan(ArrayList<Starship> ships){
		rangeTexture.bind();
		setTextureCoords(0, 0, 1, 1);
		for (Starship s : ships) {
			updateTrueCenter(s);
			setModel(currentShipTrueCenter.X() - s.getScanRange(), currentShipTrueCenter.Y() + s.getScanRange(),
					currentShipTrueCenter.X() + s.getScanRange(), currentShipTrueCenter.Y() - s.getScanRange());
		}
	}
	
	public void setColor(double percentage){
		if(percentage == -1){
			setTextureCoords(0, 0, 0.5, 0.333);
		}
		else if (percentage > 0.8) {
			setTextureCoords(0, 0.333, 0.5, 0.667);
		}
		else if (percentage > 0.6) {
			setTextureCoords(0, 0.667, 0.5, 1);
		}
		else if (percentage > 0.4) {
			setTextureCoords(0.5, 0, 1, 0.333);
		}
		else if (percentage > 0.2) {
			setTextureCoords(0.5, 0.333, 1, 0.667);
		}
		else{
			setTextureCoords(0.5, 0.667, 1, 1);
		}
	}
	
	public void setTextureCoords(double x1, double y1, double x2, double y2){
		textureCoords[0] = x1;
		textureCoords[1] = y1;
		textureCoords[2] = x1;
		textureCoords[3] = y2;
		textureCoords[4] = x2;
		textureCoords[5] = y1;
		textureCoords[6] = x2;
		textureCoords[7] = y2;
		box.setTextureCoords(textureCoords);
	}
	
	public void setTextureCoords(double[] vertex){
		textureCoords[0] = vertex[0];
		textureCoords[1] = vertex[1];
		textureCoords[2] = vertex[0];
		textureCoords[3] = vertex[3];
		textureCoords[4] = vertex[2];
		textureCoords[5] = vertex[1];
		textureCoords[6] = vertex[2];
		textureCoords[7] = vertex[3];
		box.setTextureCoords(textureCoords);
	}
	
	public void setModel(double x1, double y1, double x2, double y2){
		vertices[0] = x1;
		vertices[1] = y1;
		vertices[2] = x1;
		vertices[3] = y2;
		vertices[4] = x2;
		vertices[5] = y1;
		vertices[6] = x2;
		vertices[7] = y2;
		box.render(vertices);
	}
	
	public void setRotatedModel(Point[] points){
		System.out.println(points[0].X() + " " + points[0].Y() + " " + points[1].X() + " " + points[1].Y()+ " " + points[2].X() + " " + points[2].Y()+ " " + points[3].X() + " " + points[3].Y());
		vertices[0] = points[0].X();
		vertices[1] = points[0].Y();
		vertices[2] = points[1].X();
		vertices[3] = points[1].Y();
		vertices[4] = points[2].X();
		vertices[5] = points[2].Y();
		vertices[6] = points[3].X();
		vertices[7] = points[3].Y();
		box.render(vertices);
	}
	
	public void setRotatedModel(double[] vertices){
		box.render(vertices);
	}
	
	public void updateTrueCenter(Starship s){
		currentShipTrueCenter.setX(s.getX() + s.getXOff());
		currentShipTrueCenter.setY(s.getY() + s.getYOff());
		currentShipTrueCenter.rotatePoint(s.getX(), s.getY(), s.getAngle());
	}

}