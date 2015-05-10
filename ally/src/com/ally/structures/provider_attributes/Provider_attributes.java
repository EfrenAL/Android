package com.ally.structures.provider_attributes;

import org.json.JSONObject;

import com.ally.interfaces.Parser;
import com.ally.utilities.Utilities;

public class Provider_attributes implements Parser {
	//Attributes members
	private Vbb vbb;
	private Drivenow drivenow;
	private Car2go car2go;
	private Google google;
	private Nextbike nextbike;
	private Callabike callabike;
	// Constants
	static final String vbb_name = "vbb";
	static final String drivenow_name = "drivenow";
	static final String car2go_name = "car2go";
	static final String google_name = "google";
	static final String nextbike_name = "nextbike";
	static final String callabike_name = "callabike";

	public Provider_attributes() {
		vbb = new Vbb();
		drivenow = new Drivenow();
		car2go = new Car2go();
		google = new Google();
		nextbike = new Nextbike();
		callabike = new Callabike();
	}

	@Override
	public void setElements(JSONObject Object) {
		//Load object vbb from Json
		vbb.setElements(Utilities.getObject(Object, vbb_name));
		//Load object drivenow from Json
		drivenow.setElements(Utilities.getObject(Object, drivenow_name));
		//Load object car2go from Json
		car2go.setElements(Utilities.getObject(Object, car2go_name));
		//Load object google from Json
		google.setElements(Utilities.getObject(Object, google_name));
		//Load object nextbike from Json
		nextbike.setElements(Utilities.getObject(Object, nextbike_name));
		//Load object callbike from Json
		callabike.setElements(Utilities.getObject(Object, callabike_name));

	}

	@Override
	public void setArray(JSONObject Object) {
	}

}
