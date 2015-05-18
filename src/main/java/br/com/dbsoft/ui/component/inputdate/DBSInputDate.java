package br.com.dbsoft.ui.component.inputdate;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.faces.component.FacesComponent;

import br.com.dbsoft.ui.component.DBSUIInput;
import br.com.dbsoft.ui.core.DBSFaces;
import br.com.dbsoft.util.DBSDate;

@FacesComponent(DBSInputDate.COMPONENT_TYPE)
public class DBSInputDate extends DBSUIInput {

	public final static String COMPONENT_TYPE = DBSFaces.DOMAIN_UI_COMPONENT + "." + DBSFaces.ID.INPUTDATE;
	public final static String RENDERER_TYPE = COMPONENT_TYPE;
	public static class TYPE{
		public static String DATE = "date";
		public static String TIME = "time";
		public static String DATETIME = "datetime";
		public static String TIMES = "times";
	}
	protected enum PropertyKeys {
		type,
		autocomplete,
		maxDate,
		minDate;

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
	

	
    public DBSInputDate(){
		setRendererType(DBSInputDate.RENDERER_TYPE);
    }
	
	public String getType() {
		return (String) getStateHelper().eval(PropertyKeys.type, TYPE.DATE);
	}
	
	public void setType(String pType) {
		getStateHelper().put(PropertyKeys.type, pType);
		handleAttribute("type", pType);
	}

	
	public void setAutocomplete(String pAutocomplete) {
		getStateHelper().put(PropertyKeys.autocomplete, pAutocomplete);
		handleAttribute("autocomplete", pAutocomplete);
	}
	public String getAutocomplete() {
		return (String) getStateHelper().eval(PropertyKeys.autocomplete, "off");
	}	

	public Date getMinDate() {
		return (Date) getStateHelper().eval(PropertyKeys.minDate, null);
	}
	public void setMinDate(Date pMinDate) {
		getStateHelper().put(PropertyKeys.minDate, pMinDate);
		handleAttribute("minDate", pMinDate);
	}

	public Date getMaxDate() {
		return (Date) getStateHelper().eval(PropertyKeys.maxDate, null);
	}
	public void setMaxDate(Date pMaxDate) {
		getStateHelper().put(PropertyKeys.maxDate, pMaxDate);
		handleAttribute("maxDate", pMaxDate);
	}
	
	@Override
	public Object getValue(){
		if (this.getType().equals(DBSInputDate.TYPE.DATE)){
			return getDate();
		}else if (this.getType().equals(DBSInputDate.TYPE.TIME)){
			return getTime();
		}else if (this.getType().equals(DBSInputDate.TYPE.TIMES)){
			return getTimes();
		}else if (this.getType().equals(DBSInputDate.TYPE.DATETIME)){
			return getTimestamp();
		}else{
			return super.getValue();
		}
	}
	
	public String getDay(){
		if (super.getValue()==null){
			return "";
		}
		return String.format("%02d",DBSDate.getDay(getDate()));
	}
	public String getMonth(){
		if (super.getValue()==null){
			return "";
		}
		return String.format("%02d",DBSDate.getMonth(getDate()));
	}
	public String getYear(){
		if (super.getValue()==null){
			return "";
		}
		return String.format("%02d",DBSDate.getYear(getDate()));
	}
	public String getHour(){
		if (super.getValue()==null){
			return "";
		}
		return String.format("%02d",DBSDate.getHour(getTimestamp()));
	}
	public String getMinute(){
		if (super.getValue()==null){
			return "";
		}
		return String.format("%02d",DBSDate.getMinute(getTimestamp()));
	}
	public String getSecond(){
		if (super.getValue()==null){
			return "";
		}
		return String.format("%02d",DBSDate.getSecond(getTimestamp()));
	}
	
	public Date getDate(){
		return DBSDate.toDate(super.getValue());
	}

	public Time getTime(){
		return DBSDate.toTime(getHour(), getMinute(), "0");
	}

	public Time getTimes(){
		return DBSDate.toTime(getHour(), getMinute(), getSecond());
	}

	public Timestamp getTimestamp(){
		return DBSDate.toTimestamp(super.getValue());
	}
	

}