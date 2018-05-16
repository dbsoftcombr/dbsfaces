package br.com.dbsoft.ui.component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UICommand;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreRenderViewEvent;

import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import br.com.dbsoft.ui.component.command.DBSUICommandHasMessage;
import br.com.dbsoft.ui.core.DBSFaces;

@ResourceDependencies({
	// Estas libraries serão carregadas junto com o projeto
	@ResourceDependency(library = "css", name = "dbsfaces.min.css", target = "head"),
	@ResourceDependency(library = "js", name = "jquery-3.1.1.min.js", target = "head"),
	@ResourceDependency(library = "js", name = "jquery.actual.min.js", target = "head"),
	@ResourceDependency(library = "js", name = "js.cookie.js", target = "head"),
	@ResourceDependency(library = "js", name = "tinycolor.js", target = "head"),
	@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
	@ResourceDependency(library = "js", name = "dbsfaces.min.js", target = "head")
//	@ResourceDependency(library = "js", name = "eventsource.js", target = "head")
})
public abstract class DBSUICommand extends UICommand implements IDBSUIComponentBase, ClientBehaviorHolder, SystemEventListener{
	
	public final static String FACET_MESSAGE = "_message";
 
	protected enum PropertyKeys {
		styleClass,
		style,
		update,
		execute,
		onclick,
		readOnly,
		tooltip,
		closeDialog,
        includeViewParams,
        outcome,
        disableClientWindow;
//		actionSourceClientId;

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
	
	
	public DBSUICommand() {
		 FacesContext xContext = FacesContext.getCurrentInstance();
		 xContext.getViewRoot().subscribeToViewEvent(PreRenderViewEvent.class,this);
	}
	
	@Override
	public String getFamily() {
		return DBSFaces.FAMILY;
	}
	
	@Override
	public void handleAttribute(String name, Object value) {
		DBSFaces.handleAttribute(name, value, this);
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
	
	public void setUpdate(String pUpdate) {
		getStateHelper().put(PropertyKeys.update, pUpdate);
		handleAttribute("update", pUpdate);
	}
	
	public String getUpdate() {
		return (String) getStateHelper().eval(PropertyKeys.update, null);
	}		

	public void setExecute(String pExecute) {
		getStateHelper().put(PropertyKeys.execute, pExecute);
		handleAttribute("execute", pExecute);
	}

	public String getExecute() {
		return (String) getStateHelper().eval(PropertyKeys.execute, null);
	}
	
	public void setonclick(String ponclick) {
		getStateHelper().put(PropertyKeys.onclick, ponclick);
		handleAttribute("onclick", ponclick);
	}
	
	public String getonclick() {
		return (String) getStateHelper().eval(PropertyKeys.onclick, null);
	}	

	
	public void setReadOnly(Boolean pReadOnly) {
		getStateHelper().put(PropertyKeys.readOnly, pReadOnly);
		handleAttribute("readOnly", pReadOnly);
	}
	
	public Boolean getReadOnly() {
		return (Boolean) getStateHelper().eval(PropertyKeys.readOnly, false);
	}	
	
	public void setCloseDialog(Boolean pCloseDialog) {
		getStateHelper().put(PropertyKeys.closeDialog, pCloseDialog);
		handleAttribute("closeDialog", pCloseDialog);
	}
	
	public Boolean getCloseDialog() {
		return (Boolean) getStateHelper().eval(PropertyKeys.closeDialog, false);
	}	


	/**
//	 * ClientId do componente que originou a action que disparou o encode deste componente
//	 * @param pActionSourceClientId
//	 */
//	public void setActionSourceClientId(String pActionSourceClientId) {
//		getStateHelper().put(PropertyKeys.actionSourceClientId, pActionSourceClientId);
//		handleAttribute("actionSourceClientId", pActionSourceClientId);
//	}
//
//	/**
//	 * ClientId do componente que originou a action que disparou o encode deste componente
//	 * @return
//	 */
//	public String getActionSourceClientId() {
//		return (String) getStateHelper().eval(PropertyKeys.actionSourceClientId, null);
//	}

	public String getTooltip() {
		return (String) getStateHelper().eval(PropertyKeys.tooltip, null);
	}

	public void setTooltip(String pTooltip) {
		getStateHelper().put(PropertyKeys.tooltip, pTooltip);
		handleAttribute("tooltip", pTooltip);
	}	

    /**
     * <p class="changed_added_2_0">Return whether or not the view
     * parameters should be encoded into the target url.</p>
     *
     * @since 2.0
     */
    public boolean isIncludeViewParams() {
        return (Boolean) getStateHelper().eval(PropertyKeys.includeViewParams, false);
    }

    /**
     * <p class="changed_added_2_0">Set whether or not the page
     * parameters should be encoded into the target url.</p>
     *
     * @param pIncludeViewParams The state of the switch for encoding
     * page parameters
     *
     * @since 2.0
     */
    public void setIncludeViewParams(boolean pIncludeViewParams) {
        getStateHelper().put(PropertyKeys.includeViewParams, pIncludeViewParams);
		handleAttribute("includeViewParams", pIncludeViewParams);
    }
    
    /**
     * <p class="changed_added_2_2">Return whether or not the client window
     * should be encoded into the target url.</p>
     *
     * @since 2.0
     */
    public boolean isDisableClientWindow() {
        return (Boolean) getStateHelper().eval(PropertyKeys.disableClientWindow, false);
    }
    
    /**
     * <p class="changed_added_2_2">Set whether or not the client window
     * should be encoded into the target url.</p>
     * 
     * @param pDisableClientWindow if @{code true}, the client window will not be included
     * in this outcome target.
     * 
     * @since 2.2
     */

    public void setDisableClientWindow(boolean pDisableClientWindow) {
        getStateHelper().put(PropertyKeys.disableClientWindow, pDisableClientWindow);
		handleAttribute("disableClientWindow", pDisableClientWindow);
    }



    /**
     * <p class="changed_added_2_0">Returns the <code>outcome</code>
     * property of the <code>UIOutcomeTarget</code>. This value is
     * passed to the {@link javax.faces.application.NavigationHandler}
     * when resolving the target url of this component.</p>
     *
     * @since 2.0
     */
    public String getOutcome() {
		return (String) getStateHelper().eval(PropertyKeys.outcome, null);
    }

    /**
     * <p class="changed_added_2_0">Sets the <code>outcome</code>
     * property of the <code>UIOutcomeTarget</code>.  This value is
     * passed to the NavigationHandler when resolving the target url of
     * this component.</p>
     *
     * @since 2.0
     *
     * @param outcome the navigation outcome
     */
	public void setOutcome(String pOutcome) {
		getStateHelper().put(PropertyKeys.outcome, pOutcome);
		handleAttribute("outcome", pOutcome);
	}	

	@Override
	public String getDefaultEventName()
	{
	    return "action";
	}
	
	@Override
	public Collection<String> getEventNames() {
		return Arrays.asList("action","click", "blur", "change", "click", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "select", "valueChange"); 
	}
	
	@Override
	public void encodeBegin(FacesContext pContext) throws IOException {
		//Chama encode padrão
		super.encodeBegin(pContext);
		//Encode do indicador que há mensagem
		String 					xId = getId() + FACET_MESSAGE;
		DBSUICommandHasMessage 	xCmdMsg = (DBSUICommandHasMessage) getFacet(xId);
		if (xCmdMsg == null){return;}
		xCmdMsg.encodeAll(pContext);
	}
	
	@Override
	public void processEvent(SystemEvent pEvent) throws AbortProcessingException {
//		if (pEvent.getSource() instanceof UIComponent){
//			UIComponent xComponent = (UIComponent) pEvent.getSource();
//			System.out.println("DBSUICommand SystemEvent\t" + getClientId() + "\t#1 " + pEvent.getClass().getName() + "\t" + xComponent.getClass());
//		}else{
//			System.out.println("DBSUICommand SystemEvent\t" + getClientId() + "\t#1 " + pEvent.getClass().getName());
//		}
		if (getActionExpression() == null){return;}
		FacesContext 			xContext = FacesContext.getCurrentInstance();
		String 					xId = getId() + FACET_MESSAGE;
		//Recupera componente utilizado para indicar se existe mensagem
		DBSUICommandHasMessage 	xCmdMsg = (DBSUICommandHasMessage) getFacet(xId); 
//			System.out.println(getClientId() + "\t#1 Criou " + pEvent.getClass().getName());
		//Cria componente com JS que será utilizado para indicar se existe mensagem
		if (xCmdMsg == null){
			xCmdMsg = (DBSUICommandHasMessage) xContext.getApplication().createComponent(DBSUICommandHasMessage.COMPONENT_TYPE);
			xCmdMsg.setId(xId);
			getFacets().put(xId, xCmdMsg);
		}
	}
	
	@Override
	public boolean isListenerForSource(Object pSource) {
//		return pSource.equals(this) || pSource.getClass().isAssignableFrom(UIViewRoot.class);
//		System.out.println("isListenerForSource\t" + pSource.getClass());
//		return pSource.getClass().isAssignableFrom(UIViewRoot.class);
		//Como o evento capturado é PreRenderViewEvent, o source sempre será ViewRoot.
		return true;
	}


}
