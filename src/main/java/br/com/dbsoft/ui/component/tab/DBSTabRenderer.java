package br.com.dbsoft.ui.component.tab;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import br.com.dbsoft.ui.component.DBSRenderer;
import br.com.dbsoft.ui.component.tabpage.DBSTabPage;
import br.com.dbsoft.ui.core.DBSFaces;
import br.com.dbsoft.ui.core.DBSFaces.CSS;


@FacesRenderer(componentFamily=DBSFaces.FAMILY, rendererType=DBSTab.RENDERER_TYPE)
public class DBSTabRenderer extends DBSRenderer {

	@Override
	public void decode(FacesContext pContext, UIComponent pComponent) {
		if (!pComponent.isRendered()){return;}
		DBSTab xTab = (DBSTab) pComponent;
		//Configura página corrente após o submit
		if (xTab.getShowTabPageOnClick()){
			String xSelectedTabPage = pContext.getExternalContext().getRequestParameterMap().get(xTab.getInputId(true));
			if (xSelectedTabPage!=null &&
				!xSelectedTabPage.equals("")){
				xTab.setSelectedTabPage(xSelectedTabPage);
				xTab.setValue(xSelectedTabPage);
			}
		}
	}

	
	@Override
	public void encodeBegin(FacesContext pContext, UIComponent pComponent)
			throws IOException {
		//System.out.println("RENDER TAB #############################");
		if (!pComponent.isRendered()){return;}
		DBSTab xTab = (DBSTab) pComponent;
		ResponseWriter xWriter = pContext.getResponseWriter();
		String xClientId = xTab.getClientId(pContext);
		String xClass = CSS.TAB.MAIN;
		//Recupera a tabpage corrente para evitar que mude quando ocorrer o post
		String xSelectedTabPage;
		if (xTab.getShowTabPageOnClick()){
			xSelectedTabPage = pContext.getExternalContext().getRequestParameterMap().get(xTab.getInputId(true));
			if (xSelectedTabPage!=null &&
				!xSelectedTabPage.equals("")){
				xTab.setSelectedTabPage(xSelectedTabPage);
			}
		}
		
		xSelectedTabPage = xTab.getSelectedTabPage();
		
		if (xTab.getStyleClass()!=null){
			xClass += " " + xTab.getStyleClass();
		}
		
		xWriter.startElement("div", xTab);
			DBSFaces.encodeAttribute(xWriter, "id", xClientId);
			DBSFaces.encodeAttribute(xWriter, "name", xClientId);
			DBSFaces.encodeAttribute(xWriter, "class", xClass);
			if (!xTab.getShowTabPageOnClick()){
				DBSFaces.encodeAttribute(xWriter, "showTabPageOnClick", "false");
			}
			DBSFaces.encodeAttribute(xWriter, "style", xTab.getStyle());
			//Container
			xWriter.startElement("div", xTab);
				DBSFaces.encodeAttribute(xWriter, "class", CSS.MODIFIER.CONTAINER);
				//Abas com os título ========================================================================
				xWriter.startElement("div", xTab);
					DBSFaces.encodeAttribute(xWriter, "class", "-tabs");
//					for (int xI=xTab.getChildren().size()-1; xI >= 0; xI--){
					for (int xI=0; xI <= xTab.getChildren().size()-1; xI++){	
						if (xTab.getChildren().get(xI) instanceof DBSTabPage){
							DBSTabPage xPage = (DBSTabPage) xTab.getChildren().get(xI);
							if (xPage.isRendered()){
								xWriter.startElement("div", xTab);  
									String xPageId = xPage.getAttributes().get("id").toString();
									String xPageClientId = xClientId + DBSFaces.ID_SEPARATOR + xPageId;

									DBSFaces.encodeAttribute(xWriter, "id", xPageClientId + "_aba");
									DBSFaces.encodeAttribute(xWriter, "name", xPageClientId + "_aba");
									DBSFaces.encodeAttribute(xWriter, "class", "-tab" + DBSFaces.CSS.THEME.FC + DBSFaces.CSS.THEME.INVERT +  CSS.NOT_SELECTABLE);	
									DBSFaces.encodeAttribute(xWriter, "tabPage", xPageClientId);
									
									encodeClientBehaviors(pContext, xPage);
									
									//Marca a primiera aba como selecionada
									if (xI==0){
										if (xSelectedTabPage.equals("")){
											xSelectedTabPage = xPageClientId;
											xTab.setSelectedTabPage(xPageClientId);
										}
									}

//									if (xPageClientId.equals(xSelectedTabPage)){
//										xPage.setSelected(true);
//										DBSFaces.setAttribute(xWriter, "class", CSS.MODIFIER.SELECTED, null);
//									}
									
									xWriter.startElement("a", xTab);
										if (xPage.getAjax()){
											DBSFaces.encodeAttribute(xWriter, "style", "opacity:0.2");
										}
	//									xWriter.writeAttribute("ontouchstart", "javascript:void(0)", "ontouchstart"); //Para ipad ativar o css:ACTIVE
	//									xWriter.writeAttribute("href", "#", "href"); //Para ipad ativar o css:ACTIVE
										xWriter.startElement("span", xTab);
											DBSFaces.encodeAttribute(xWriter, "class", CSS.MODIFIER.ICON + xPage.getIconClass());
										xWriter.endElement("span");
										xWriter.startElement("span", xTab);
											DBSFaces.encodeAttribute(xWriter, "class", CSS.MODIFIER.CAPTION);
											xWriter.write(xPage.getCaption());
										xWriter.endElement("span");
									xWriter.endElement("a");
									if (xPage.getAjax()){
										xWriter.startElement("span", xTab);
											DBSFaces.encodeAttribute(xWriter, "class", "loading_container");
											xWriter.startElement("span", xTab);
												DBSFaces.encodeAttribute(xWriter, "class", CSS.MODIFIER.LOADING);
											xWriter.endElement("span");
										xWriter.endElement("span");
									}
								xWriter.endElement("div");
							}
						}
					}
				xWriter.endElement("div");
				
				//Conteúdo da páginas ======================================================================================
				xWriter.startElement("div", xTab);
					DBSFaces.encodeAttribute(xWriter, "class", "-tabPage");
					xWriter.startElement("div", xTab);
						DBSFaces.encodeAttribute(xWriter, "class", CSS.MODIFIER.CONTENT);

						//Input para salvar a pagina selecionada ====================================================
						HtmlInputHidden xInput = (HtmlInputHidden) xTab.getFacet("input");
						if (xInput == null){
							xInput = (HtmlInputHidden) pContext.getApplication().createComponent(HtmlInputHidden.COMPONENT_TYPE);
							xInput.setId(xTab.getInputId(false));
							xTab.getFacets().put("input", xInput);
						}
						xInput.setValue(xSelectedTabPage);
						xInput.encodeAll(pContext);

						//Encode das páginas filhas, sem o conteúdo, pois será posteriormente chamado via atualização ajax
//						for (int xI=xTab.getChildren().size()-1; xI >= 0; xI--){
						for (int xI=0; xI <= xTab.getChildren().size()-1; xI++){	
							if (xTab.getChildren().get(xI) instanceof DBSTabPage){
								DBSTabPage xPage = (DBSTabPage) xTab.getChildren().get(xI);
								if (xPage.isRendered()){
									xPage.encodeBegin(pContext);
									//Ignora o encode do conteúdo da página
									if (!xPage.getAjax()){
										xPage.encodeChildren(pContext);
									}
									xPage.encodeEnd(pContext);
								}
							}
						}
					
//						renderChildren(pContext, xTab);
	
					xWriter.endElement("div");
				xWriter.endElement("div");
			xWriter.endElement("div");
		xWriter.endElement("div");

		//Chamada assincrona para carregar a página	
		for (int xI=0; xI <= xTab.getChildren().size()-1; xI++){
			if (xTab.getChildren().get(xI) instanceof DBSTabPage){
				DBSTabPage xPage = (DBSTabPage) xTab.getChildren().get(xI);
				if (xPage.isRendered()){
					if (xPage.getAjax()){
						DBSFaces.encodeJavaScriptTagStart(pComponent, xWriter);
						String xJS = "setTimeout(function(){" +
										"dbsfaces.ajax.request('" + xPage.getClientId() + "_aba" + "', '" + xTab.getInputId(true) + "', '" + xPage.getClientId() + "', dbsfaces.onajax, dbsfaces.onajaxerror);" +
										"}, 0);";
//						String xJS = "setTimeout(function(){" +
//												"jsf.ajax.request('" + xPage.getClientId() + "_aba" + "', 'update', {render:'" + xPage.getClientId() + "', execute:'" + xTab.getInputId(true) + "', onevent:dbsfaces.onajax, onerror:dbsfaces.onajaxerror});" +
//														   "}, 0);";
						xWriter.write(xJS);
						DBSFaces.encodeJavaScriptTagEnd(xWriter);	
					}
				}
			}
		}

		pvEncodeJS(pComponent, xWriter);
	}
	
	/**
	 * Encode do código JS necessário para o componente
	 * @param pWriter
	 * @param pClientId
	 * @throws IOException
	 */
	private void pvEncodeJS(UIComponent pComponent, ResponseWriter pWriter) throws IOException {
		DBSFaces.encodeJavaScriptTagStart(pComponent, pWriter);
		String xJS = "$(document).ready(function() { \n" +
				     " var xTabId = dbsfaces.util.jsid('" + pComponent.getClientId() + "'); \n " + 
				     " dbs_tab(xTabId); \n" +
                     "}); \n"; 
		pWriter.write(xJS);
		DBSFaces.encodeJavaScriptTagEnd(pWriter);	
	}
	
	

}
