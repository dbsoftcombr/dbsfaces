package br.com.dbsoft.ui.bean;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.dbsoft.annotation.DBSEager;

/**
 * Bean básico com controle de dependência entre bean para quando um bean master for finalizado, os beans slaves também sejam.
 * @author ricardo.villar
 */
public abstract class DBSBean implements Serializable{
 
	private static final long serialVersionUID = -5273728796912868413L;
	private static final long wConversationTimeout = 600000;  //10 minutos

	protected 	Logger 						wLogger =  Logger.getLogger(this.getClass());

	@Inject
	private 	Conversation				wConversation;

	private   	DBSBean						wMasterBean = null;
	private 	List<DBSBean>				wSlavesBean = new ArrayList<DBSBean>();
	private 	Locale						wLocale;
	
	
	//--------------------------------------------------------------------------------------
	//Código para impedir o erro de 'Cannot create a session after the response has been committed'
	//que ocorre em algumas situações que a página(como resultado da quantidade de registros do ResultDataModel) por conter muitos dados
	//o que faz que por algum motivo o JSF envie algum resposta no momento que não deveria, principalmente com @ResquestScoped
	@PostConstruct
	void pvInitializeClass() {
		if (FacesContext.getCurrentInstance() == null){
			Boolean xIgnorer = false;
			//Desconsidera não haver FacesContext ativo quando class for DBSEager.
		    Annotation[] xAs = this.getClass().getAnnotations();
		     if(xAs.length != 0) {
		        for(Annotation xA : xAs) {
	        	   if (xA.annotationType().equals(DBSEager.class)){
	        		   xIgnorer = true;
	        		   break;
		           }
		        }
		     }
		    if (!xIgnorer){
				wLogger.warn(this.getClass().getCanonicalName() + " chamado sem haver FacesContext.");
		    }
		}else{ 
			FacesContext xFC = FacesContext.getCurrentInstance();
			if(xFC.getExternalContext().getSession(false) == null){
				xFC.getExternalContext().getSession(true);
			}
			pvGetUserLocate();
		}
		//Se tiver anotação 'ConversationScoped', inicia a conversação
		conversationBegin();
		initializeClass();
	}
	
	/**
	 * Inicia conversação caso exista a anotação 'ConversationScoped' na class.
	 * É necessário que o componente form seja atualizado via ajax ou refresh da página
	 */
	public void conversationBegin(){
		for (Annotation xAnnotation:this.getClass().getDeclaredAnnotations()){
			if (xAnnotation.annotationType() == ConversationScoped.class){
				if (wConversation.isTransient()){
					wConversation.begin();
					wConversation.setTimeout(wConversationTimeout);
				}
				break;
			}
		}
	}
	
	/**
	 * Retorna o ID da conversação
	 * @return
	 */
	public String getCID(){
		return wConversation.getId();
	}
	
	/**
	 * Encerra a conversação
	 */
	public void endConversation(){
		wConversation.end();
	}
	
	private void pvGetUserLocate(){
//		for( Cookie cookie : FacesContext.getCurrentInstance().getExternalContext( httpServletRequest.getCookies() ) {
//		    System.out.println( cookie.getName() + " - " + cookie.getValue() );
//		}
//		String cookieValue_Language = new Locale( "tr", "TR" ).getLanguage();
//		Cookie localeCookie_lang = new Cookie( "locale", cookieValue_Language );
//		response.addCookie( localeCookie_lang );
//		Iterator xLocales = (Iterator) FacesContext.getCurrentInstance().getExternalContext().getRequestLocales();
//		while (xLocales.){e
//			Locale xLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocales().next();
//			System.out.println(xLocale);
//		}
//		System.out.println(FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
//		System.out.println(FacesContext.getCurrentInstance().getViewRoot().getLocale());
//		System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
//		setLocale(FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
		setLocale(FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
	}
	
	@PreDestroy
	void pvFinalizeClass(){
		finalizeClass();
	}
	
	
	//Public -------------------------------------------------------------
	
	/**
	 * Crudbean que será o principal. Responsável por apagar da memória os crudbean escravaos que existem
	 * @return
	 */
	public DBSBean getMasterBean() {
		return wMasterBean;
	}
	
	public void setLocaleCode(String pLocale){
		wLocale = new Locale(pLocale);
		setLocale(wLocale);
	}
	public String getLocaleCode(){
		return wLocale.toString();
	}	
	
    public String getLanguage() {
        return wLocale.getLanguage();
    }

    public void setLanguage(String pLanguage) {
    	setLocale(new Locale(pLanguage));
    }
    
	public void setLocale(Locale pLocale){
//		HttpServletResponse xR = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		xR.setLocale(pLocale);
//		xR.addHeader("Content-Language", "pt-BR");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(pLocale);
	}
	
	public Locale getLocale(){
		return wLocale;
	}
	/**
	 * Lista de Bean escravos dentro deste.
	 * Os bean escravos serão retirados da memória quando o master for destruido
	 * @return
	 */
	public List<DBSBean> getSlavesBean() {
		return wSlavesBean;
	}
	
	
	/**
	 * Bean que será o principal. Responsável por apagar da memória os bean escravos que a ele vinculados
	 */
	public void setMasterBean(DBSBean pBean) {
		wMasterBean = pBean;
		if (!pBean.getSlavesBean().contains(this)){
			pBean.getSlavesBean().add(this);
		}
	}
	

	/**
	 * Define o tempo máximo sem atividade para invalidar a seção. O padrão é de 600 segundos(10 minutos).<br/> 
	 * Tempo em segundos.
	 * @return
	 */
	public Integer getMaxInactiveInterval() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMaxInactiveInterval();
	}

	/**
	 * Define o tempo máximo sem atividade para invalidar a seção. O padrão é de 600 segundos(10 minutos).
	 * @param pMaxInactiveInterval Tempo em segundos
	 */
	public void setMaxInactiveInterval(Integer pMaxInactiveInterval) {
		FacesContext.getCurrentInstance().getExternalContext().setSessionMaxInactiveInterval(pMaxInactiveInterval);
	};
	
	/**
	 * Método após a inicialização do bean.
	 * Ao sobre escrever este método, deve-se estar atendo em chamar o <b>super</b>.
	 */
	protected void initializeClass(){};
	
	/**
	 * Método após a finalização do bean.
	 * Ao sobre escrever este método, deve-se estar atendo em chamar o <b>super</b>.
	 */
	protected void finalizeClass(){}

	
}
