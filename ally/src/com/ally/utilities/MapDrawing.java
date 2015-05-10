package com.ally.utilities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.ally.structures.routes.Routes;
import com.ally.structures.routes.Segments;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapDrawing {

	@SuppressLint("NewApi")
	public static void drawRoute(Routes route, GoogleMap myMap) {
		Segments segment;
		List<LatLng> path;
		PolylineOptions polyline;
		// Draw start and end point
		drawStartEnd(route, myMap);
		// Get all segments from route
		for (int i = 0; i < route.getSegmentsSize(); i++) {

			segment = route.getSegment(i);
			String st_polyline = segment.getPolyline();
			// Decode polylin from segment(i) if exist
			if( st_polyline != null ){
				path = decodePoly(st_polyline);
				// Create polyline type with decoded polyline
				polyline = new PolylineOptions().addAll(path);
				// Draw polyline
				drawPolilyne(polyline, segment.getColor(), myMap);
			}
		}
	}

	// Draw Polyline in map
	private static void drawPolilyne(PolylineOptions options, String color, GoogleMap myMap) {
		myMap.addPolyline(options.color(Color.parseColor(color)));
	}

	// Draw start and end point
	private static void drawStartEnd(Routes route, GoogleMap myMap) {
		int size = route.getSegmentsSize();
		LatLng latlng;
		// Draw start
		if (size > 0) {
			Segments segment = route.getSegment(0);
			// Get number of stops
			int nstops = segment.getStopsSize();
			// Get first Stop
			if (nstops > 0) {
				latlng = new LatLng(segment.getStop(0).getLat(), segment.getStop(0).getLng());
				setMarker(latlng, "Start", myMap);				
		        //Zoom to the start position
		        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12.0f));	
			}
			// Draw end
			segment = route.getSegment(size - 1);
			// Get number of stops
			nstops = segment.getStopsSize();
			if (nstops > 0) {
				latlng = new LatLng(segment.getStop(nstops - 1).getLat(), segment.getStop(nstops - 1).getLng());
				setMarker(latlng, "End", myMap);
			}
		}

	}
	
	// Set marker at position = position with title = title in myMap
	private static void setMarker(LatLng position, String title, GoogleMap myMap) {		
		myMap.addMarker(new MarkerOptions().position(position)
				.title(title) // Title
				.snippet("") // Extra Info
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))); // Color
	}

	//Decode polyline
	private static List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

}
