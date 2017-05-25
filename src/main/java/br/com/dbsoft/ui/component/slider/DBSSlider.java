package br.com.dbsoft.ui.component.slider;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.FacesComponent;

import br.com.dbsoft.ui.component.DBSUIInput;
import br.com.dbsoft.ui.core.DBSFaces;

@FacesComponent(DBSSlider.COMPONENT_TYPE)
public class DBSSlider extends DBSUIInput {

	public final static String COMPONENT_TYPE = DBSFaces.DOMAIN_UI_COMPONENT + "." + DBSFaces.ID.SLIDER;
	public final static String RENDERER_TYPE = COMPONENT_TYPE;
	
	protected enum PropertyKeys {
		type,
		orientation,
		maxValue,
		minValue,
		decimalPlaces,
		animated,
		tooltip,
		valuesList,
		invertValuesListPosition,
		contentAlignment,
		obs;
		
		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		@Override
		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
		}
	}
	
	public static enum TYPE {
		VALUES 				("v"),
		STEPS 				("s"),	
	    OPTIONS				("o");
		
		private String 	wName;
		
		private TYPE(String pName) {
			this.wName = pName;
		}

		public String getName() {
			return wName;
		}

		public String getStyleClass() {
			return " -" + wName + " ";
		}

		public static TYPE get(String pType) {
			if (pType == null){
				return VALUES;
			}			
			pType = pType.trim().toLowerCase();
	    	for (TYPE xP:TYPE.values()) {
	    		if (xP.getName().equals(pType)){
	    			return xP;
	    		}
	    	}
	    	return VALUES;
		}	
	}

	public static enum ORIENTATION {
		HORIZONTAL 			("h"),
		VERTICAL 			("v");
		
		private String 	wName;
		
		private ORIENTATION(String pName) {
			this.wName = pName;
		}

		public String getName() {
			return wName;
		}

		public String getStyleClass() {
			return " -" + wName + " ";
		}

		public static ORIENTATION get(String pType) {
			if (pType == null){
				return HORIZONTAL;
			}			
			pType = pType.trim().toLowerCase();
	    	for (ORIENTATION xP:ORIENTATION.values()) {
	    		if (xP.getName().equals(pType)){
	    			return xP;
	    		}
	    	}
	    	return HORIZONTAL;
		}	
	
	}
	
	public static enum CONTENT_ALIGNMENT {
		TOP 			("t"),
	    BOTTOM 			("b"),
		LEFT 			("l"),
	    RIGHT 			("r"),
		CENTER 			("c");	
		
		private String 	wName;
		
		private CONTENT_ALIGNMENT(String pName) {
			this.wName = pName;
		}

		public String getName() {
			return wName;
		}

		public static CONTENT_ALIGNMENT get(String pCode) {
			if (pCode == null){
				return CENTER;
			}			
			pCode = pCode.trim().toLowerCase();
	    	for (CONTENT_ALIGNMENT xCA:CONTENT_ALIGNMENT.values()) {
	    		if (xCA.getName().equals(pCode)){
	    			return xCA;
	    		}
	    	}
	    	return null;
		}	
	}

	public DBSSlider(){
		setRendererType(DBSSlider.RENDERER_TYPE);
    }
	
	public String getType() {
		return (String) getStateHelper().eval(PropertyKeys.type, TYPE.VALUES.getName());
	}
	
	public void setType(String pType) {
		getStateHelper().put(PropertyKeys.type, pType);
		handleAttribute("type", pType);
	}

	public String getOrientation() {
		return (String) getStateHelper().eval(PropertyKeys.orientation, ORIENTATION.HORIZONTAL.getName());
	}
	
	public void setOrientation(String pOrientation) {
		getStateHelper().put(PropertyKeys.orientation, pOrientation);
		handleAttribute("orientation", pOrientation);
	}
	
	public Double getMaxValue() {
		return (Double) getStateHelper().eval(PropertyKeys.maxValue, 100D);
	}
	
	public void setMaxValue(Double pMaxValue) {
		getStateHelper().put(PropertyKeys.maxValue, pMaxValue);
		handleAttribute("maxValue", pMaxValue);
	}

	public Double getMinValue() {
		return (Double) getStateHelper().eval(PropertyKeys.minValue, 0D);
	}
	
	public void setMinValue(Double pMinValue) {
		getStateHelper().put(PropertyKeys.minValue, pMinValue);
		handleAttribute("minValue", pMinValue);
	}


	public void setAnimated(Boolean pAnimated) {
		getStateHelper().put(PropertyKeys.animated, pAnimated);
		handleAttribute("animated", pAnimated);
	}
	
	public Boolean getAnimated() {
		return (Boolean) getStateHelper().eval(PropertyKeys.animated, false);
	}	

	public String getObs() {
		return (String) getStateHelper().eval(PropertyKeys.obs, null);
	}
	
	public void setObs(String pObs) {
		getStateHelper().put(PropertyKeys.obs, pObs);
		handleAttribute("obs", pObs);
	}

	public void setInvertValuesListPosition(Boolean pInvertValuesListPosition) {
		getStateHelper().put(PropertyKeys.invertValuesListPosition, pInvertValuesListPosition);
		handleAttribute("invertListValuesPosition", pInvertValuesListPosition);
	}
	
	public Boolean getInvertValuesListPosition() {
		return (Boolean) getStateHelper().eval(PropertyKeys.invertValuesListPosition, false);
	}	

	public Object getValuesList() {
		return getStateHelper().eval(PropertyKeys.valuesList, Arrays.asList());
	}
	
	@SuppressWarnings("unchecked")
	public void setValuesList(Object pValuesList) {
		if (pValuesList == null){return;}
		if (pValuesList instanceof List){
			pvSetValuesList((List<String>) pValuesList);
		}else{
			List<String> xValuesList = Arrays.asList(((String) pValuesList).split("\\s*;\\s*"));
			pvSetValuesList(xValuesList);
		}
	}

	public void pvSetValuesList(List<String> pValuesList) {
		getStateHelper().put(PropertyKeys.valuesList, pValuesList);
		handleAttribute("valuesList", pValuesList);
	}
	
	
	public String getContentAlignment() {
		return (String) getStateHelper().eval(PropertyKeys.contentAlignment, null);
	}
	
	public void setContentAlignment(String pContentAlignment) {
		if (CONTENT_ALIGNMENT.get(pContentAlignment) == null){
			System.out.println("ContentAlignment invalid\t:" + pContentAlignment);
			return;
		}
		getStateHelper().put(PropertyKeys.contentAlignment, pContentAlignment);
		handleAttribute("contentAlignment", pContentAlignment);
	}

	public void setDecimalPlaces(Integer pDecimalPlaces) {
		getStateHelper().put(PropertyKeys.decimalPlaces, pDecimalPlaces);
		handleAttribute("decimalPlaces", pDecimalPlaces);
	}
	public Integer getDecimalPlaces() {
		return (Integer) getStateHelper().eval(PropertyKeys.decimalPlaces, 0);
	}	

	
}
