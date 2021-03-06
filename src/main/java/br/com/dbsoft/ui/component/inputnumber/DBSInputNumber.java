package br.com.dbsoft.ui.component.inputnumber;

import javax.faces.component.FacesComponent;

import br.com.dbsoft.ui.component.DBSUIInputText;
import br.com.dbsoft.ui.core.DBSFaces;
import br.com.dbsoft.util.DBSNumber;

@FacesComponent(DBSInputNumber.COMPONENT_TYPE)
public class DBSInputNumber extends DBSUIInputText {

	public final static String COMPONENT_TYPE = DBSFaces.DOMAIN_UI_COMPONENT + "." + DBSFaces.ID.INPUTNUMBER;
	public final static String RENDERER_TYPE = COMPONENT_TYPE;

	private static Double wMaxValue = 999999999999999D; //trilhões
	private static Double wMinValue = -wMaxValue;
	
	protected enum PropertyKeys {
		secret,
		size,
		decimalPlaces,
		leadingZero,
		separateThousand,
		maxValue,
		minValue,
		autocomplete,
		currencySymbol,
		onFocusSelectAll;

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
	
    public DBSInputNumber(){
		setRendererType(DBSInputNumber.RENDERER_TYPE);
    }
	
	public void setSecret(Boolean pSecret) {
		getStateHelper().put(PropertyKeys.secret, pSecret);
		handleAttribute("secret", pSecret);
	}
	public Boolean getSecret() {
		return (Boolean) getStateHelper().eval(PropertyKeys.secret, false);
	}	

	public void setDecimalPlaces(Integer pDecimalPlaces) {
		pDecimalPlaces = DBSNumber.abs(pDecimalPlaces);
		getStateHelper().put(PropertyKeys.decimalPlaces, pDecimalPlaces);
		handleAttribute("decimalPlaces", pDecimalPlaces);
	}
	public Integer getDecimalPlaces() {
		return (Integer) getStateHelper().eval(PropertyKeys.decimalPlaces, 2);
	}	

	public void setMaxValue(Double pMaxValue) {
		getStateHelper().put(PropertyKeys.maxValue, pMaxValue);
		handleAttribute("maxValue", pMaxValue);
	}
	public Double getMaxValue() {
		return (Double) getStateHelper().eval(PropertyKeys.maxValue, wMaxValue);
	}	

	public void setMinValue(Double pMinValue) {
		getStateHelper().put(PropertyKeys.minValue, pMinValue);
		handleAttribute("minValue", pMinValue);
	}
	public Double getMinValue() {
		return (Double) getStateHelper().eval(PropertyKeys.minValue, wMinValue);
	}	
	
	public void setSize(Integer pSize) {
		pSize = DBSNumber.abs(pSize);
		getStateHelper().put(PropertyKeys.size, pSize);
		handleAttribute("size", pSize);
	}

	public Integer getSize() {
		return (Integer) getStateHelper().eval(PropertyKeys.size, null);
	}
	
	public void setLeadingZero(Boolean pLeadingZero) {
		getStateHelper().put(PropertyKeys.leadingZero, pLeadingZero);
		handleAttribute("leadingZero", pLeadingZero);
	}
	public Boolean getLeadingZero() {
		return (Boolean) getStateHelper().eval(PropertyKeys.leadingZero, false);
	}	

	public void setSeparateThousand(Boolean pSeparateThousand) {
		getStateHelper().put(PropertyKeys.separateThousand, pSeparateThousand);
		handleAttribute("separateThousand", pSeparateThousand);
	}
	public Boolean getSeparateThousand() {
		return (Boolean) getStateHelper().eval(PropertyKeys.separateThousand, true);
	}	
	
	public void setAutocomplete(String pAutocomplete) {
		getStateHelper().put(PropertyKeys.autocomplete, pAutocomplete);
		handleAttribute("autocomplete", pAutocomplete);
	}
	public String getAutocomplete() {
		return (String) getStateHelper().eval(PropertyKeys.autocomplete, "off");
	}	
	
	public void setCurrencySymbol(String pCurrencySymbol) {
		getStateHelper().put(PropertyKeys.currencySymbol, pCurrencySymbol);
		handleAttribute("currencySymbol", pCurrencySymbol);
	}
	public String getCurrencySymbol() {
		return (String) getStateHelper().eval(PropertyKeys.currencySymbol, null);
	}

	/**
	 * Retorna o valor convertido para o tipo Double
	 * @return
	 */
	public Double getValueDouble(){
		return DBSNumber.toDouble(super.getValue());
	}
	
	public void setOnFocusSelectAll(Boolean pOnFocusSelectAll) {
		getStateHelper().put(PropertyKeys.onFocusSelectAll, pOnFocusSelectAll);
		handleAttribute("onFocusSelectAll", pOnFocusSelectAll);
	}
	public Boolean getOnFocusSelectAll() {
		return (Boolean) getStateHelper().eval(PropertyKeys.onFocusSelectAll, false);
	}	

}