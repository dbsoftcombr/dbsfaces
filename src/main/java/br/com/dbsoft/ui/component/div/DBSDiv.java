package br.com.dbsoft.ui.component.div;

import javax.faces.component.FacesComponent;


import br.com.dbsoft.ui.component.DBSUIComponentBase;
import br.com.dbsoft.ui.core.DBSFaces;

@FacesComponent(DBSDiv.COMPONENT_TYPE)
public class DBSDiv extends DBSUIComponentBase {
	
	public final static String COMPONENT_TYPE = DBSFaces.DOMAIN_UI_COMPONENT + "." + DBSFaces.ID.DIV;
	public final static String RENDERER_TYPE = COMPONENT_TYPE;

	protected enum PropertyKeys {
		styleClass,
		style,
		selectable;

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
    public DBSDiv(){
		setRendererType(DBSDiv.RENDERER_TYPE);
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
	
	public void setSelectable(Boolean pSelectable) {
		getStateHelper().put(PropertyKeys.selectable, pSelectable);
		handleAttribute("selectable", pSelectable);
	}
	
	public Boolean getSelectable() {
		return (Boolean) getStateHelper().eval(PropertyKeys.selectable, true);
	}		
}
