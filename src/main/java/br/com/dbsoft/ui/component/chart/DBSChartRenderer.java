package br.com.dbsoft.ui.component.chart;

import java.awt.geom.Point2D;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.sun.faces.renderkit.RenderKitUtils;

import br.com.dbsoft.ui.component.DBSPassThruAttributes;
import br.com.dbsoft.ui.component.DBSPassThruAttributes.Key;
import br.com.dbsoft.ui.component.chartvalue.DBSChartValue;
import br.com.dbsoft.ui.component.DBSRenderer;
import br.com.dbsoft.ui.component.chart.DBSChart.TYPE;
import br.com.dbsoft.ui.component.charts.DBSCharts;
import br.com.dbsoft.ui.core.DBSFaces;
import br.com.dbsoft.util.DBSNumber;
import br.com.dbsoft.util.DBSObject;


@FacesRenderer(componentFamily=DBSFaces.FAMILY, rendererType=DBSChart.RENDERER_TYPE)
public class DBSChartRenderer extends DBSRenderer {

	
	@Override
	public void decode(FacesContext pContext, UIComponent pComponent) {
	}

	@Override
	public boolean getRendersChildren() {
		return true; //True=Chama o encodeChildren abaixo e interrompe a busca por filho pela rotina renderChildren
	}
	
    @Override
    public void encodeChildren(FacesContext pContext, UIComponent pComponent) throws IOException {
        //É necessário manter está função para evitar que faça o render dos childrens
    	//O Render dos childrens é feita do encode
    }

	@Override
	public void encodeBegin(FacesContext pContext, UIComponent pComponent) throws IOException {
		if (!pComponent.isRendered()){return;}
		DBSChart xChart = (DBSChart) pComponent;
		if (xChart.getType()==null){return;}
		ResponseWriter xWriter = pContext.getResponseWriter();
		String 		xClass = DBSFaces.CSS.CHART.MAIN;
		TYPE 		xType = DBSChart.TYPE.get(xChart.getType());
		DBSCharts	xCharts;
		if (!(xChart.getParent() instanceof DBSCharts)){
			return;
		}
		xCharts =  (DBSCharts) xChart.getParent();
		
		if (xChart.getStyleClass()!=null){
			xClass += xChart.getStyleClass() + " ";
		}

		String xClientId = xChart.getClientId(pContext);
//		xChart.restoreState(pContext, xChart.getSavedState());

		xWriter.startElement("g", xChart);
			DBSFaces.setAttribute(xWriter, "id", xClientId, null);
			DBSFaces.setAttribute(xWriter, "name", xClientId, null);
			DBSFaces.setAttribute(xWriter, "class", xClass.trim(), null);
			DBSFaces.setAttribute(xWriter, "style", xChart.getStyle(), null);
			DBSFaces.setAttribute(xWriter, "type", xChart.getType(), null);
			DBSFaces.setAttribute(xWriter, "index", xChart.getIndex(), null);
			DBSFaces.setAttribute(xWriter, "cs", xChart.getColumnScale(), null);
			if (xType == TYPE.LINE
			 || xType == TYPE.PIE){
				if (xChart.getShowDelta()){ //Artificio para padronizar o false como não existindo o atributo(comportamento do chrome)
					DBSFaces.setAttribute(xWriter, "showdelta", xChart.getShowDelta(), null);
				}
			}
			RenderKitUtils.renderPassThruAttributes(pContext, xWriter, xChart, DBSPassThruAttributes.getAttributes(Key.CHART));
			
			encodeClientBehaviors(pContext, xChart);
			
			if (xType == TYPE.LINE
			 || xType == TYPE.BAR){
				//Divisão onde serão desenhadas as linhas que ligam os pontos no gráfico por linha.
				pvEncodePathGroup(xCharts, xChart, xWriter);
			}
			//Divisão para exibição do delta   
			if (xChart.getShowDelta()){
				xWriter.startElement("g", xChart);
					DBSFaces.setAttribute(xWriter, "class", "-delta", null);
					if (xType == TYPE.PIE){
						pvEncodePieDeltaTextPaths(xCharts, xChart, xWriter);
					}else if (xType == TYPE.LINE){
					//Divisão onde serão desenhadas as linhas que ligam os pontos no gráfico por linha.
						pvEncodeLineDeltaAutoSelection(xCharts, xChart, xWriter);
					}
				xWriter.endElement("g");
			}
			//Valores-------------------------
			//Se não foi informado DBSResultSet
			if (DBSObject.isEmpty(xChart.getVar())
			 || DBSObject.isEmpty(xChart.getValueExpression("value"))){
				pvEncodeChartValue(pContext, xChart);
			}else{
				pvEncodeResultSetChartValue(pContext, xChart, xWriter);
			}

			pvEncodeJS(xClientId, xWriter);
			
		xWriter.endElement("g");
	}
	
	/**
	 * Divisão onde serão desenhadas as linhas que ligam os pontos no gráfico por linha.
	 * O desenho é efetuado via JS no chartValue
	 * @param pCharts
	 * @param pChart
	 * @param pWriter
	 * @throws IOException
	 */
	private void pvEncodePathGroup(DBSCharts pCharts, DBSChart pChart, ResponseWriter pWriter) throws IOException{
		Integer		xChartsWidth;
		Integer 	xChartsHeight;
		xChartsWidth = pCharts.getChartWidth() + pCharts.getPadding();
		xChartsHeight = pCharts.getChartHeight() + pCharts.getPadding();
		pWriter.startElement("g", pChart);
			DBSFaces.setAttribute(pWriter, "class", "-path", null);
			//Area que irá captura o mousemove
			DBSFaces.encodeSVGRect(pChart, pWriter, pCharts.getPadding() / 2D, pCharts.getPadding() / 2D, xChartsWidth.toString(), xChartsHeight.toString(), DBSFaces.CSS.MODIFIER.MASK.trim(), null, null);
		pWriter.endElement("g");
	}

	private void pvEncodeLineDeltaAutoSelection(DBSCharts pCharts, DBSChart pChart, ResponseWriter pWriter) throws IOException{
		pWriter.startElement("foreignObject", pChart);
//			DBSFaces.setAttribute(pWriter, "requiredExtensions", "http://www.w3.org/1999/xhtml", null);
//			pWriter.writeAttribute("xmlns","http://www.w3.org/1999/xhtml", null);
			DBSFaces.setAttribute(pWriter, "xmlns","http://www.w3.org/1999/xhtml", null);
			DBSFaces.setAttribute(pWriter, "class", "-autoSelection", null);
			DBSFaces.setAttribute(pWriter, "height", "1", null);
			DBSFaces.setAttribute(pWriter, "width", "1", null);
			DBSFaces.setAttribute(pWriter, "x", "0px", null);
			DBSFaces.setAttribute(pWriter, "y", "0px", null);
			DBSFaces.setAttribute(pWriter, "style", "overflow:visible;", null);
			pWriter.startElement("div", pChart);
			DBSFaces.setAttribute(pWriter, "xmlns", "http://www.w3.org/1999/xhtml", null);
				DBSFaces.setAttribute(pWriter, "style", "border-color: black; border-style: solid; border-radius: 3px;border-width: .3em; background-color:rgba(250,250,250,.8); display:block; top:90px; left: 212px; position: absolute;", null);
				pWriter.write("1D  7D  30D  90D  180D  320D  720D");
			pWriter.endElement("div");
		pWriter.endElement("foreignObject");
	}
	
	/**
	 * Paths que serão utilizados para posicionar os valores do delta/somatório
	 * @param pCharts
	 * @param pChart
	 * @param pWriter
	 * @throws IOException
	 */
	private void pvEncodePieDeltaTextPaths(DBSCharts pCharts, DBSChart pChart, ResponseWriter pWriter) throws IOException{
		Double xMiddleRadius = (pCharts.getPieChartWidth() / 2) + pChart.getPieChartRelativeRadius(pCharts, pCharts.getPieChartWidth());
		Point2D xPathPoint1 = new Point2D.Double();
		Point2D xPathPoint2 = new Point2D.Double();
		//Arco a esquerda
		xPathPoint1 = DBSNumber.circlePoint(pCharts.getCenter(), xMiddleRadius, 51 * DBSNumber.PIDiameterFactor);
		xPathPoint2 = DBSNumber.circlePoint(pCharts.getCenter(), xMiddleRadius, 99 * DBSNumber.PIDiameterFactor);
		pvEncodePieDeltaTextPath(pChart, pWriter, "_l",  xMiddleRadius, xPathPoint1, xPathPoint2);
		//Arco a direita
		xPathPoint1 = DBSNumber.circlePoint(pCharts.getCenter(), xMiddleRadius, 1 * DBSNumber.PIDiameterFactor);
		xPathPoint2 = DBSNumber.circlePoint(pCharts.getCenter(), xMiddleRadius, 49 * DBSNumber.PIDiameterFactor);
		pvEncodePieDeltaTextPath(pChart, pWriter, "_r", xMiddleRadius, xPathPoint1, xPathPoint2);
	}

	/**
	 * Encode do path
	 * @param pChart
	 * @param pWriter
	 * @param pSide
	 * @param pMiddleRadius
	 * @param pPathPoint1
	 * @param pPathPoint2
	 * @throws IOException
	 */
	private void pvEncodePieDeltaTextPath(DBSChart pChart, ResponseWriter pWriter, String pSide, Double pMiddleRadius, Point2D pPathPoint1, Point2D pPathPoint2) throws IOException{
		StringBuilder xPath = new StringBuilder();
	    xPath.append("M" + pPathPoint1.getX() + "," + pPathPoint1.getY()); //Ponto inicial do arco 
		xPath.append("A" + pMiddleRadius + "," + pMiddleRadius + " 0 0 1 " + pPathPoint2.getX() + "," + pPathPoint2.getY()); //Arco externo até o ponto final  
		DBSFaces.encodeSVGPath(pChart, pWriter, xPath.toString(), "-path" + pSide, null, "id=" + pvGetDeltaPathId(pChart, pSide) + "; fill=none;");
	}
	
	private void pvEncodeJS(String pClientId, ResponseWriter pWriter) throws IOException{
		DBSFaces.encodeJavaScriptTagStart(pWriter);
		String xJS = "$(document).ready(function() { \n" +
				     " var xChartId = '#' + dbsfaces.util.jsid('" + pClientId + "'); \n " + 
				     " dbs_chart(xChartId); \n" +
                     "}); \n"; 
		pWriter.write(xJS);
		DBSFaces.encodeJavaScriptTagEnd(pWriter);		
	}
	
	/**
	 * Encode do corpo da tabela contendo as linhas com os dados
	 * @param pContext
	 * @param pChart
	 * @param pWriter
	 * @throws IOException
	 */
	private void pvEncodeResultSetChartValue(FacesContext pContext, DBSChart pChart, ResponseWriter pWriter) throws IOException {
        int xRowCount = pChart.getRowCount(); 
		pChart.setRowIndex(-1);
		//Loop por todos os registros lidos
		//Lido de forma decrescentes por o saveState e restoreState invertou
		//a ordem da consulta
		for (int xRowIndex = 0; xRowIndex < xRowCount; xRowIndex++) {
        	pChart.setRowIndex(xRowIndex);
        	//Loop no componente filho contendo as definições dos valores
			for (UIComponent xC : pChart.getChildren()){
				if (xC instanceof DBSChartValue){
					DBSChartValue xChartValue = (DBSChartValue) xC;
					if (xChartValue.isRendered()){
						xChartValue.restoreState(FacesContext.getCurrentInstance(), xChartValue.getSavedState());
						xChartValue.encodeAll(pContext);
					}
				}
			}
        }
        pChart.setRowIndex(-1);
	}
	
	private void pvEncodeChartValue(FacesContext pContext, DBSChart pChart) throws IOException {
		DBSFaces.renderChildren(pContext, pChart);
	}
	
	private String pvGetDeltaPathId(DBSChart pChart,String pSide){
		return pChart.getClientId() + "_deltapath" + pSide;
	}

}
