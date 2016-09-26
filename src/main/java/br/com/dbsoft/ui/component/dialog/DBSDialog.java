package br.com.dbsoft.ui.component.dialog;

import java.util.Arrays;
import java.util.Collection;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;

import br.com.dbsoft.ui.component.DBSUIComponentBase;
import br.com.dbsoft.ui.core.DBSFaces;
import br.com.dbsoft.util.DBSObject;


@FacesComponent(DBSDialog.COMPONENT_TYPE)
public class DBSDialog extends DBSUIComponentBase implements NamingContainer{  
	public final static String COMPONENT_TYPE = DBSFaces.DOMAIN_UI_COMPONENT + "." + DBSFaces.ID.DIALOG;
	public final static String RENDERER_TYPE = COMPONENT_TYPE;
	

	public final static String FACET_HEADER = "header";
	public final static String FACET_FOOTER = "footer";

	protected enum PropertyKeys {
		styleClass,
		style,
		iconClass,
		location,
		closeTimeout,
		contentPadding,
		contentStyleClass,
		contentVerticalAlign,
		contentHorizontalAlign,
		open;

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
	
	/**
	 * ENUM DE LOCATION
	 * @author jose.avila@dbsoft.com.br
	 *
	 */
	public static enum LOCATION {
		//VERTICAL
		TOP_LEFT_VERTICAL		("tlv",true,true,true),
		TOP_RIGHT_VERTICAL		("trv",true,false,true),
		BOTTOM_LEFT_VERTICAL	("blv",false,true,true),
		BOTTOM_RIGHT_VERTICAL	("brv",false,false,true),
		//HORIZONTAL
		TOP_LEFT_HORIZONTAL 	("tlh",true,true,false),
		TOP_RIGHT_HORIZONTAL 	("trh",true,false,false),
		BOTTOM_LEFT_HORIZONTAL 	("blh",false,true,false),
		BOTTOM_RIGHT_HORIZONTAL ("brh",false,false,false), //
		//CENTER
		TOP_LEFT_CENTER			("tlc",true,true,false),
		TOP_RIGHT_CENTER		("trc",true,false,false),
		BOTTOM_LEFT_CENTER		("blc",false,true,false),
		BOTTOM_RIGHT_CENTER		("brc",false,false,false); //
		
		private String 	wCode;
		private Boolean	wIsTop;
		private Boolean	wIsLeft;
		private Boolean	wIsVertical;
		
		private LOCATION(String pCode, Boolean pIsTop, Boolean pIsLeft, Boolean pIsVertical) {
			wCode = pCode;
			wIsTop = pIsTop;
			wIsLeft = pIsLeft;
			wIsVertical = pIsVertical;
		}

		public String getCode() {
			return wCode;
		}
		public String getCSS() {
			return " -" + wCode +" ";
		}
		public Boolean getIsTop(){
			return wIsTop;
		}
		public Boolean getIsLeft(){
			return wIsLeft;
		}
		public Boolean getIsVertical(){
			return wIsVertical;
		}
		
		public static LOCATION get(String pCode) {
			if (pCode == null){
				return TOP_LEFT_HORIZONTAL;
			}			
			pCode = pCode.trim().toLowerCase();
			switch (pCode) {
			case "tlh":
				return TOP_LEFT_HORIZONTAL;
			case "tlv":
				return TOP_LEFT_VERTICAL;
			case "trh":
				return TOP_RIGHT_HORIZONTAL;
			case "trv":
				return TOP_RIGHT_VERTICAL;
			case "blh":
				return BOTTOM_LEFT_HORIZONTAL;
			case "blv":
				return BOTTOM_LEFT_VERTICAL;
			case "brh":
				return BOTTOM_RIGHT_HORIZONTAL;
			case "brv":
				return BOTTOM_RIGHT_VERTICAL;
			case "tlc":
				return TOP_LEFT_CENTER;
			case "trc":
				return TOP_RIGHT_CENTER;
			case "blc":
				return BOTTOM_LEFT_CENTER;
			case "brc":
				return BOTTOM_RIGHT_CENTER;
			default:
				return TOP_LEFT_HORIZONTAL;
			}
		}
		
		public static LOCATION get(String pLocation, String pContentVerticalAlign, String pContentHorizontalAlign) {
			StringBuilder xLocation = new StringBuilder();
			
			if (DBSObject.isEmpty(pContentVerticalAlign)) {
				xLocation.append("t");
			}else{
				xLocation.append(pContentVerticalAlign.substring(0, 1).toLowerCase());
			}
			if (DBSObject.isEmpty(pContentHorizontalAlign)) {
				xLocation.append("l");
			}else{
				xLocation.append(pContentHorizontalAlign.substring(0, 1).toLowerCase());
			}
			if (DBSObject.isEmpty(pLocation)) {
				xLocation.append("v");
			}else{
				xLocation.append(pLocation.substring(0, 1).toLowerCase());
			}
			return get(xLocation.toString());			
		}
	}

    public DBSDialog(){
		setRendererType(DBSDialog.RENDERER_TYPE);
    }

    
	public String getStyle() {
		return (String) getStateHelper().eval(PropertyKeys.style, null);
	}
	
	public void setStyle(String pStyle) {
		getStateHelper().put(PropertyKeys.style, pStyle);
		handleAttribute("style", pStyle);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(PropertyKeys.styleClass, null);
	}
	
	public void setStyleClass(String pStyleClass) {
		getStateHelper().put(PropertyKeys.styleClass, pStyleClass);
		handleAttribute("styleClass", pStyleClass);
	}
	
	public String getIconClass() {
		return (String) getStateHelper().eval(PropertyKeys.iconClass, null);
	}
	
	public void setIconClass(String pIconClass) {
		getStateHelper().put(PropertyKeys.iconClass, pIconClass);
		handleAttribute("iconClass", pIconClass);
	}
	
	public Boolean getOpen() {
		return (Boolean) getStateHelper().eval(PropertyKeys.open, false);
	}
	
	public void setOpen(Boolean pOpen) {
		getStateHelper().put(PropertyKeys.open, pOpen);
		handleAttribute("open", pOpen);
	}
	
	public String getLocation() {
		return (String) getStateHelper().eval(PropertyKeys.location, LOCATION.TOP_LEFT_HORIZONTAL.getCode());
	}
	
	public void setLocation(String pLocation) {
		getStateHelper().put(PropertyKeys.location, pLocation);
		handleAttribute("location", pLocation);
	}
	
	public String getCloseTimeout() {
		return (String) getStateHelper().eval(PropertyKeys.closeTimeout, "0");
	}
	
	public void setCloseTimeout(String pCloseTimeout) {
		getStateHelper().put(PropertyKeys.closeTimeout, pCloseTimeout);
		handleAttribute("closeTimeout", pCloseTimeout);
	}

	public String getContentPadding() {
		return (String) getStateHelper().eval(PropertyKeys.contentPadding, "0.4em");
	}
	public void setContentPadding(String pContentPadding) {
		getStateHelper().put(PropertyKeys.contentPadding, pContentPadding);
		handleAttribute("contentPadding", pContentPadding);
	}

	public String getContentStyleClass() {
		return (String) getStateHelper().eval(PropertyKeys.contentStyleClass, "");
	}
	
	public void setContentStyleClass(String pContentStyleClass) {
		getStateHelper().put(PropertyKeys.contentStyleClass, pContentStyleClass);
		handleAttribute("contentStyleClass", pContentStyleClass);
	}
	
	public String getContentVerticalAlign() {
		return (String) getStateHelper().eval(PropertyKeys.contentVerticalAlign, null);
	}
	
	public void setContentVerticalAlign(String pContentVerticalAlign) {
		getStateHelper().put(PropertyKeys.contentVerticalAlign, pContentVerticalAlign);
		handleAttribute("contentVerticalAlign", pContentVerticalAlign);
	}
	
	public String getContentHorizontalAlign() {
		return (String) getStateHelper().eval(PropertyKeys.contentHorizontalAlign, null);
	}
	
	public void setContentHorizontalAlign(String pContentHorizontalAlign) {
		getStateHelper().put(PropertyKeys.contentHorizontalAlign, pContentHorizontalAlign);
		handleAttribute("contentHorizontalAlign", pContentHorizontalAlign);
	}

	@Override
    public String getDefaultEventName()
    {
        return "click";
    }
	
	@Override
	public Collection<String> getEventNames() {
		return Arrays.asList("click", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"); 
	}



}