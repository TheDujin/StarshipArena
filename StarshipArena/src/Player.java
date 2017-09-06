import java.util.ArrayList;


public class Player {
	
	StarshipArena game;
	String team;
	Planet selectedPlanet = null;
	ArrayList<Starship> visibleShips = new ArrayList<Starship>();
	ArrayList<Planet> visiblePlanets = new ArrayList<Planet>();
	
	Player(StarshipArena mygame, String myteam){
		game = mygame;
		team = myteam;
	}
	
	public void setSelectedPlanet(Planet p){
		selectedPlanet = p;
	}
	
	public Planet getSelectedPlanet(){
		return selectedPlanet;
	}
	
	public String getTeam(){
		return team;
	}
	
	public ArrayList<Starship> getControlledShips(){
		ArrayList<Starship> myShips = new ArrayList<>();
		ArrayList<Starship> allShips = game.getAllShips();
		for (int i = 0; i < allShips.size(); i++) {
			if(allShips.get(i).getTeam().equals(getTeam())){
				myShips.add(allShips.get(i));
			}
		}
		return myShips;
	}
	
	public ArrayList<Planet> getControlledPlanets(){
		ArrayList<Planet> myPlanets = new ArrayList<>();
		ArrayList<Planet> allPlanets = game.getAllPlanets();
		for (int i = 0; i < allPlanets.size(); i++) {
			if(allPlanets.get(i).getTeam().equals(getTeam())){
				myPlanets.add(allPlanets.get(i));
			}
		}
		return myPlanets;
	}
	
	public int costOfIdleShips() {
		int costOfShips = 1;
		ArrayList<Starship> myShips = getControlledShips();
		for (int i = 0; i < myShips.size(); i++) {
			if (myShips.get(i) instanceof Fighter && myShips.get(i).locationTarget == null) costOfShips += 5;
			else if (myShips.get(i) instanceof Interceptor && myShips.get(i).locationTarget == null) costOfShips += 20;
			else if (myShips.get(i) instanceof Missileship&& myShips.get(i).locationTarget == null) costOfShips += 40;
		}
		return costOfShips;
	}
	
	public void checkVisible() {
		for (int i = 0; i < visibleShips.size(); i++) {
			//TODO If and when we implement static structures (e.g. buildings), don't remove them here, unless they are destroyed.
			visibleShips.remove(i);
			i--;
		}
		for (int i = 0; i < visiblePlanets.size(); i++) {
			visiblePlanets.remove(i);
			i--;
		}
		loop:
			//Loop through Starships
		for (int i = 0; i < game.ships.size(); i++) {
			Starship ship = game.ships.get(i);
			//If Fog Of War is off, everything is visible
			if(game.fog == false){
				visibleShips.add(ship);
				continue;
			}
			if(ship.getTeam().equals(this.getTeam())){
				//If ship is on our team, it is visible
				visibleShips.add(ship);
				continue;
			}
			Planet currentPlanet;
			for (int j = 0; j < this.getControlledPlanets().size(); j++) {
				//If any of our planets can see it (distance from ship to planet < planet's radar range), ship is visible
				currentPlanet = this.getControlledPlanets().get(j);
				if(game.distance(ship.getX(), ship.getY(), currentPlanet.getX(), currentPlanet.getY()) <= currentPlanet.getRadarRange()){
					visibleShips.add(ship);
					continue loop;
				}
			}
			Starship currentShip;
			//If any of our starships can see it, ship is visible
			for (int s = 0; s < this.getControlledShips().size(); s++) {
				currentShip = this.getControlledShips().get(s);
				if(game.distance(ship.getX(), ship.getY(), currentShip.getX(), currentShip.getY()) <= currentShip.getRadarRange()){
					visibleShips.add(ship);
					continue loop;
				}
			}
		}
		//Visibility of planets checked by similar argument
		loop2:
		for (int i = 0; i < game.planets.size(); i++) {
			Planet planet = game.planets.get(i);
			if(game.fog == false){
				visiblePlanets.add(planet);
				continue;
			}
			if(planet.getTeam().equals(this.getTeam())){
				visiblePlanets.add(planet);
				continue;
			}
			Planet currentPlanet;
			for (int j = 0; j < this.getControlledPlanets().size(); j++) {
				currentPlanet = this.getControlledPlanets().get(j);
				if(game.distance(planet.getX(), planet.getY(), currentPlanet.getX(), currentPlanet.getY()) <= currentPlanet.getRadarRange()){
					visiblePlanets.add(planet);
					continue loop2;
				}
			}
			Starship currentShip;
			for (int s = 0; s < this.getControlledShips().size(); s++) {
				currentShip = this.getControlledShips().get(s);
				if(game.distance(planet.getX(), planet.getY(), currentShip.getX(), currentShip.getY()) <= currentShip.getRadarRange()){
					visiblePlanets.add(planet);
					continue loop2;
				}
			}
		}
	}

}
