package br.com.dbsoft.ui.component.button;

import javax.faces.event.ActionEvent;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.sun.faces.renderkit.RenderKitUtils;

import br.com.dbsoft.ui.component.DBSRenderer;
import br.com.dbsoft.ui.core.DBSFaces;
import br.com.dbsoft.util.DBSBoolean;

@FacesRenderer(componentFamily=DBSFaces.FAMILY, rendererType=DBSButton.RENDERER_TYPE)
public class DBSButtonRenderer extends DBSRenderer {
	
	private Boolean wTimerVerify = false;
	
    @Override
	public void decode(FacesContext pContext, UIComponent pComponent) {
    	if (!pComponent.isRendered()){return;}
        DBSButton 	xButton = (DBSButton) pComponent;
		String 		xClientId = xButton.getClientId(pContext);
        if(xButton.getReadOnly()) {
            return;
        }
//        if(xButton.isDisabled() || xButton.isReadonly()) {
//            return;
//        }
        
        decodeBehaviors(pContext, xButton);
        
		if (RenderKitUtils.isPartialOrBehaviorAction(pContext, xClientId) || /*Chamada Ajax*/
			pContext.getExternalContext().getRequestParameterMap().containsKey(xClientId)) { 	/*Chamada Sem Ajax*/
			xButton.queueEvent(new ActionEvent(xButton));
		}
//        if(xButton.isDisabled()) {
//            return;
//        }

		/*
		ExternalContext external = context.getExternalContext();
           Map<String, String> params = external.getRequestParameterMap();
           String behaviorEvent = params.get("javax.faces.behavior.event");
   
           if (behaviorEvent != null) {
               List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);
   
               if (behaviors.size() > 0) {
                   String behaviorSource = params.get("javax.faces.source");
                  String clientId = getClientId(context);
                  if (behaviorSource != null && behaviorSource.equals(clientId)) {
                      for (ClientBehavior behavior: behaviorsForEvent) {
                          behavior.decode(context, this);
                      }
                  }
               }
           }	
		*/
    }

	@Override
	public boolean getRendersChildren() {
		return true; //True=Chama o encodeChildren abaixo e interrompe a busca por filho pela rotina renderChildren
	}
	
    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        //É necessário manter está função para evitar que faça o render dos childrens
    	//O Render dos childrens é feita do encode
    }

	
	@Override
	public void encodeBegin(FacesContext pContext, UIComponent pComponent)
			throws IOException {
		if (!pComponent.isRendered()){return;}
		DBSButton xButton = (DBSButton) pComponent;
		ResponseWriter xWriter = pContext.getResponseWriter();
		String xClientId = xButton.getClientId(pContext);
		String xOnClick = null;
		String xExecute = "";
		wTimerVerify = xButton.getTimerVerify();
		if (xButton.getExecute() == null){
			xExecute = getFormId(pContext, pComponent); 
		}else{
			xExecute = xButton.getExecute();
		}
		
		//if (xButton.getUpdate()!=null){
			xOnClick = DBSFaces.getSubmitString(xButton, DBSFaces.HTML.EVENTS.ONCLICK, xExecute, xButton.getUpdate());
		//}
		
		if (xButton.getReadOnly()){
			xWriter.startElement("div", xButton);
		}else{
			xWriter.startElement("button", xButton);
		}
			xWriter.writeAttribute("id", xClientId, "id");
			xWriter.writeAttribute("name", xClientId, "name");
			DBSFaces.setAttribute(xWriter, "class", xButton.getStyleClass(),DBSFaces.CSS.BUTTON.MAIN);
			DBSFaces.setAttribute(xWriter, "style",xButton.getStyle(), null);
			DBSFaces.setAttribute(xWriter, "value",xButton.getValue(), null);
			if (xButton.getDisabled()){
				DBSFaces.setAttribute(xWriter, "disabled","disabled", null);
			}
			
			if (!xButton.getReadOnly()){
//Comentado para sempre considerar o onclick criado acima. Forçando uma chamada ajax, desde que tenha sido informado o update.				
//				if (xButton.getActionExpression() != null || 
//					xButton.getonclick() != null){				
//					xWriter.writeAttribute("ontouchstart", "", "ontouchstart"); //Para ipad ativar o css:ACTIVE
					xWriter.writeAttribute("type", "submit", "type");
					//if (xButton.getUpdate()!=null){
						DBSFaces.setAttribute(xWriter, DBSFaces.HTML.EVENTS.ONCLICK, xOnClick, null); 
					//}
					encodeClientBehaviors(pContext, xButton);
//				}			
			}
			if (xButton.getIconClass()!=null || xButton.getLabel()!=null){
				pvEncodeTable(xButton, xWriter);	
			}
			
			renderChildren(pContext, xButton);
			DBSFaces.encodeTooltip(pContext, xButton, xButton.getTooltip());
		if (xButton.getReadOnly()){
			xWriter.endElement("div");
		}else{
			xWriter.endElement("button");
		}
		pvEncodeJS(xWriter, xClientId);
	}
	
	private void pvEncodeTable(DBSButton pButton, ResponseWriter pWriter) throws IOException{
		pWriter.startElement("table", pButton);
			pWriter.writeAttribute("cellspacing", "0px", null);
			pWriter.writeAttribute("cellpadding", "0px", null);
			pWriter.startElement("tbody", pButton);
				pWriter.startElement("tr", pButton);
					if (pButton.getIconClass()!=null && pButton.getIconClass()!="") {
						pWriter.startElement("td", pButton);
							pWriter.writeAttribute("class", DBSFaces.CSS.NOT_SELECTABLE, null);
							pvEncodeIcon(pButton, pWriter);
						pWriter.endElement("td");
					}
					if (pButton.getLabel()!=null && pButton.getLabel()!="") {
						pWriter.startElement("td", pButton);
							pWriter.writeAttribute("class", DBSFaces.CSS.NOT_SELECTABLE, null);
							pvEncodeLabel(pButton, pWriter);
						pWriter.endElement("td");
					}
				pWriter.endElement("tr");
			pWriter.endElement("tbody");
		pWriter.endElement("table");
	}	

	private void pvEncodeIcon(DBSButton pButton, ResponseWriter pWriter) throws IOException{
		String xClass = DBSFaces.CSS.NOT_SELECTABLE + " " + DBSFaces.CSS.MODIFIER.ICON + " " +  pButton.getIconClass();
		pWriter.startElement("span", pButton);
			pWriter.writeAttribute("class", xClass, null);
		pWriter.endElement("span");
	}

	private void pvEncodeLabel(DBSButton pButton, ResponseWriter pWriter) throws IOException{
		String xClass = DBSFaces.CSS.NOT_SELECTABLE +  " " + DBSFaces.CSS.MODIFIER.LABEL;
		pWriter.startElement("span", pButton);
			pWriter.writeAttribute("class", xClass, null);
			//Adiciona espaço extra entre o icone e o texto
			if (pButton.getIconClass()!=null){ 
				pWriter.writeAttribute("style", "padding-left:2px;", null); 
			}
			pWriter.write(pButton.getLabel());
		pWriter.endElement("span");
	}	
	
	/**
	 * Encode do código JS necessário para o componente
	 * @param pWriter
	 * @param pClientId
	 * @throws IOException
	 */
	private void pvEncodeJS(ResponseWriter pWriter, String pClientId) throws IOException {
		DBSFaces.encodeJavaScriptTagStart(pWriter);
		String xTimerVerify = "";
		if (DBSBoolean.toBoolean(wTimerVerify)) {
			xTimerVerify = " clearInterval(meuTimer); \n";
		}
		String xJS = "$(document).ready(function() { \n" +
				     " var xButtonId = '#' + dbsfaces.util.jsid('" + pClientId + "'); \n " + 
				     " dbs_button(xButtonId); \n" +
                     "}); \n" + xTimerVerify;
		pWriter.write(xJS);
		DBSFaces.encodeJavaScriptTagEnd(pWriter);	
	}

}


