package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
	    
//	    Location valloc = new Location(11.58, 37.38);
//	    Feature valEq = new PointFeature(valloc);
	    
//	    valEq.addProperty("title", "Viladelvia, Chille");
//	    valEq.addProperty("magnitude", "9.5");
//	    valEq.addProperty("date", "May 22, 1960");
	    
//	    Bahir Dar Earth quake
	    Location bdrloc = new Location(11.58, 37.38);
	    PointFeature bdrftr = new PointFeature(bdrloc);
	    bdrftr.addProperty("title", "Bahir Dar, Ethiopia");
	    bdrftr.addProperty("magnitude", "0.2");
	    bdrftr.addProperty("date", "March 15, 2001");
	    bdrftr.addProperty("year", 2001);
	    
//	    Alaska earth quake
	    Location alloc = new Location(64.08,-149.73);
	    PointFeature alftr = new PointFeature(alloc);
	    alftr.addProperty("title", "Alaska");
	    alftr.addProperty("magnitude", "5.7");
	    alftr.addProperty("date", "March 15, 2002");
	    alftr.addProperty("year", 2002);
	    
	    // Chille earth quake
	    Location chileloc = new Location(-38.14f, -73.03f);
	    PointFeature chileftr = new PointFeature(chileloc);
	    chileftr.addProperty("title", "Viladelvia, Chille");
	    chileftr.addProperty("magnitude", "9.5");
	    chileftr.addProperty("date", "May 22, 1960");
	    chileftr.addProperty("year", 1960);
	    
	    // Colorado Earth quake
	    Location colloc = new Location(38.18,-104.19);
	    PointFeature colftr = new PointFeature(colloc);
	    colftr.addProperty("title", "Denver, Colorado");
	    colftr.addProperty("magnitude", "6.5");
	    colftr.addProperty("date", "May 22, 1992");
	    colftr.addProperty("year", 1992);
	    
//	    Japan Earth Quake
	    Location japloc = new Location(35.67,139.08);
	    PointFeature japftr = new PointFeature(japloc);
	    japftr.addProperty("title", "Tokyo, Japan");
	    japftr.addProperty("magnitude", "10.2");
	    japftr.addProperty("date", "March 15, 2006");
	    japftr.addProperty("year", 2006);
	    
	    
	    
	    List<PointFeature> bigEqs = new ArrayList<PointFeature>();
	    bigEqs.add(bdrftr);
	    bigEqs.add(chileftr);
	    bigEqs.add(colftr);
	    bigEqs.add(alftr);
	    bigEqs.add(japftr);
	    
//	    Marker val = new SimplePointMarker(valloc, valEq.getProperties());
//	    map.addMarker(val);
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();
	    
	    for(PointFeature eq: bigEqs) {
	    	markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
	    }
	    
	    // customizing the display of the markers based on the year
	    int yellow = color(255, 255, 0);
	    int gray = color(150, 150, 150);
	    
	    for(Marker mk: markers) {
	    	if((int) mk.getProperty("year") > 2000) {
	    		mk.setColor(yellow);
	    	}
	    	else {
	    		mk.setColor(gray);
	    	}
	    }

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		//System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
		
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
	
	}
}
