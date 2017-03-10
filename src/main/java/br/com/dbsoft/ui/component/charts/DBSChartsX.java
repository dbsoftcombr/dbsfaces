package br.com.dbsoft.ui.component.charts;

import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;

import br.com.dbsoft.ui.component.DBSUIOutput;
import br.com.dbsoft.ui.component.chart.IDBSChartDelta;
import br.com.dbsoft.ui.core.DBSFaces;

@FacesComponent(DBSChartsX.COMPONENT_TYPE)
public class DBSChartsX extends DBSUIOutput implements NamingContainer{
	
	public final static String COMPONENT_TYPE = DBSFaces.DOMAIN_UI_COMPONENT + "." + DBSFaces.ID.CHARTSX;
	public final static String RENDERER_TYPE = COMPONENT_TYPE;


	public static enum TYPE {
		BAR 			("bar", true),
		LINE 			("line", true),	
	    PIE 			("pie", false);
		
		private String 	wName;
		private Boolean	wMatrix;
		
		private TYPE(String pName, Boolean pMatrix) {
			this.wName = pName;
			this.wMatrix = pMatrix;
		}

		public String getName() {
			return wName;
		}
		
		public String getStyleClass(){
			return "-" + wName;
		}
		/**
		 * Se é um gráfico de linhas ou colunas(Matrix X x Y)
		 * @return
		 */
		public Boolean isMatrix(){
			return wMatrix;
		}

		public static TYPE get(String pCode) {
			if (pCode == null){
				return LINE;
			}			
			pCode = pCode.trim().toLowerCase();
			switch (pCode) {
			case "bar":
				return BAR;
			case "line":
				return LINE;
			case "pie":
				return PIE;
			default:
				return LINE;
			}
		}	
	}
	

	protected enum PropertyKeys {
		type,
		caption,
		footer,
		valueFormatMask,
		showGrid,
		showGridValue,
		showLabel,
		showDelta,
		groupId,
		pieInternalCircleFator,
		deltaList;

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

	public DBSChartsX(){
		setRendererType(DBSChartsX.RENDERER_TYPE);
    }
	

	public String getType() {
		return (String) getStateHelper().eval(PropertyKeys.type, TYPE.BAR.getName());
	}
	
	public void setType(String pType) {
		getStateHelper().put(PropertyKeys.type, pType);
		handleAttribute("type", pType);
	}

	public String getCaption() {
		return (String) getStateHelper().eval(PropertyKeys.caption, null);
	}
	
	public void setCaption(String pCaption) {
		getStateHelper().put(PropertyKeys.caption, pCaption);
		handleAttribute("caption", pCaption);
	}

	public String getFooter() {
		return (String) getStateHelper().eval(PropertyKeys.footer, null);
	}
	
	public void setFooter(String pFooter) {
		getStateHelper().put(PropertyKeys.footer, pFooter);
		handleAttribute("footer", pFooter);
	}
	
	public Boolean getShowGrid() {
		return (Boolean) getStateHelper().eval(PropertyKeys.showGrid, true);
	}
	public void setShowGrid(Boolean pShowGrid) {
		getStateHelper().put(PropertyKeys.showGrid, pShowGrid);
		handleAttribute("showGrid", pShowGrid);
	}

	public Boolean getShowGridValue() {
		return (Boolean) getStateHelper().eval(PropertyKeys.showGridValue, true);
	}
	public void setShowGridValue(Boolean pShowGridValue) {
		getStateHelper().put(PropertyKeys.showGridValue, pShowGridValue);
		handleAttribute("showGridValue", pShowGridValue);
	}

	public Boolean getShowDelta() {
		return (Boolean) getStateHelper().eval(PropertyKeys.showDelta, false);
	}
	public void setShowDelta(Boolean pShowDelta) {
		getStateHelper().put(PropertyKeys.showDelta, pShowDelta);
		handleAttribute("showDelta", pShowDelta);
	} 

	public void setShowLabel(Boolean pShowLabel) {
		getStateHelper().put(PropertyKeys.showLabel, pShowLabel);
		handleAttribute("showLabel", pShowLabel);
	}
	
	public Boolean getShowLabel() {
		return (Boolean) getStateHelper().eval(PropertyKeys.showLabel, true);
	}
	
	public void setDeltaList(List<IDBSChartDelta> pDeltaList) {
		getStateHelper().put(PropertyKeys.deltaList, pDeltaList);
		handleAttribute("deltaList", pDeltaList);
	}
	
	/**
	 * Lista com os valores dos deltas
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IDBSChartDelta> getDeltaList() {
		return (List<IDBSChartDelta>) getStateHelper().eval(PropertyKeys.deltaList, null);
	}

	public Double getPieInternalCircleFator() {
		return (Double) getStateHelper().eval(PropertyKeys.pieInternalCircleFator, 2D);
	}
	public void setPieInternalCircleFator(Double pPieInternalCircleFator) {
		getStateHelper().put(PropertyKeys.pieInternalCircleFator, pPieInternalCircleFator);
		handleAttribute("pieInternalCircleFator", pPieInternalCircleFator);
	}

	public String getValueFormatMask() {
		return (String) getStateHelper().eval(PropertyKeys.valueFormatMask, "");
	}
	public void setValueFormatMask(String pValueFormatMask) {
		getStateHelper().put(PropertyKeys.valueFormatMask, pValueFormatMask);
		handleAttribute("valueFormatMask", pValueFormatMask);
	}

	public String getGroupId() {
		return (String) getStateHelper().eval(PropertyKeys.groupId, null);
	}
	public void setGroupId(String pGroupId) {
		getStateHelper().put(PropertyKeys.groupId, pGroupId);
		handleAttribute("groupId", pGroupId);
	}




}