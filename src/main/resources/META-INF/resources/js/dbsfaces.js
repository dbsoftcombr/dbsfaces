/*Constantes*/
//                            stop   |    prevent     | prevent "same element"
//                          bubbling | default action | event handlers
//
//return false                 Yes           Yes             No
//preventDefault()             No            Yes             No
//stopPropagation()            Yes           No              No
//stopImmediatePropagation()   Yes           No              Yes

//Exemplo de load assyncrono
//  (function(d, s, id) {
//    var js, fjs = d.getElementsByTagName(s)[0];
//    if (d.getElementById(id)) return;
//    js = d.createElement(s); js.id = id;
//    js.src = "//connect.facebook.net/en_US/sdk.js";
//    fjs.parentNode.insertBefore(js, fjs);
//  }(document, 'script', 'facebook-jssdk'));
//  
var wsAnimationTime = 200;   

//var evt = (evt) ? evt : ((event) ? event : null); 
//var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 

$(document).on("keydown", function(e){
	if ((e.which == 13) && //ENTER
		(e.target.type=="text"))  {
		return false;
	} 
	if ((e.which == 8 //BACKSPACE
	  || e.which == 46) && //DELETE
		(e.target.type=="select-one" ||
		 e.target.type=="radio" ||
		 e.target.type=="submit" ||
		 e.target.type=="checkbox"))  {
			return false;
	} 
});

String.prototype.replaceAll = function(target, replacement) {
  return this.split(target).join(replacement);
};

//JQUERY PLUGINS===================================================================

(function($) {
    $.fn.hasScrollBar = function() {
        return this.get(0).scrollHeight > this.height();
    },
    $.fn.svgHasClass = function (pClassName) {
  	  return new RegExp('(\\s|^)' + pClassName + '(\\s|$)').test(this.get(0).getAttribute('class'));
    },
    $.fn.svgRemoveClass = function (pClassName) {
		this.each(function(){
	    	var xObject = this;
	    	if ($(this).svgHasClass(pClassName)) {
		    	var xRemovedClass = xObject.getAttribute('class').replace(new RegExp('(\\s|^)' + pClassName + '(\\s|$)', 'g'), '$2');
	    		xObject.setAttribute('class', xRemovedClass);
	    	}
		});
		return this;
    },
    $.fn.svgAddClass = function (pClassName) {
		this.each(function(){
	    	var xObject = this;
	    	if (!$(this).svgHasClass(pClassName)) {
	    		xObject.setAttribute('class', xObject.getAttribute('class') + ' ' + pClassName);
	    	}
		});
		return this;
    },
    $.fn.svgAttr = function (pAttribute, pValue) {
    	if (typeof pValue == "undefined"){
    		return this.get(0).getAttributeNS(null, pAttribute);
    	}else{
    		this.get(0).setAttributeNS(null, pAttribute, pValue);
    		return this;
    	}
	},
	//Retorna o total de segmentos de um path
	$.fn.svgGetPathTotalSegs = function () {
    	if (typeof this == "undefined"){
    		return 0;
    	}
   		return this.get(0).getPathSegAtLength(this.get(0).getTotalLength() + 10) + 1;
	}

    

})(jQuery);


//Encontra o parente mais próximo que possuir barra de rolagem
var scrollParent = $.fn.scrollParent = function( includeHidden ) {
	var position = this.css( "position" ),
		excludeStaticParent = position === "absolute",
		overflowRegex = includeHidden ? /(auto|scroll|hidden)/ : /(auto|scroll)/,
		scrollParent = this.parents().filter( function() {
			var parent = $( this );
			if ( excludeStaticParent && parent.css( "position" ) === "static" ) {
				return false;
			}
			return overflowRegex.test( parent.css( "overflow" ) + parent.css( "overflow-y" ) + parent.css( "overflow-x" ) );
		} ).eq( 0 );

	return position === "fixed" || !scrollParent.length ? $("body") : scrollParent;
//	return position === "fixed" || !scrollParent.length ? $( this[ 0 ].ownerDocument || document ) : scrollParent;
};

//DBSFACES===========================================================

dbsfaces = {
	CSS : {
		MODIFIER : {
			CLOSABLE : " -closable ",
			CLOSED : " -closed ",
			SELECTED : " -selected ",
			SELECTABLE : " -selectable ",
			TITLE : " -title ",
			LABEL : " -label ",
			ICON : " -icon ",
			ICONCLOSE : " -iconclose ",
			DISABLED : " -disabled "
		}
	},
	
	ID: {
		DIALOG: "dialog",
		INPUTTEXT: "inputText"
	},
	
	EVENT: {
		ON_AJAX_BEGIN: "dbs_ON_AJAX_BEGIN",
		ON_AJAX_COMPLETE: "dbs_ON_AJAX_COMPLETE",
		ON_AJAX_SUCCESS: "dbs_ON_AJAX_SUCESS",
		ON_AJAX_ERROR: "dbs_ON_AJAX_ERROR",
		ON_ROW_SELECTED: "select.dataTable",
		ON_TRANSITION_END: "webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend",
		ON_ANIMATION_END: "webkitAnimationEnd oanimationend msAnimationEnd animationend",
		ON_ANIMATION_INTERATION: "webkitAnimationIteration animationiteration"
	}	
}

var wAjaxTimeout;

dbsfaces.sound = {
	beep : function(){
		$("#dbs_beep").remove();
		$("body").append('<audio id="dbs_beep" src="sound/alert.mp3" preload autoplay/>');
//		$("body").append('<embed id="dbs_beep" src="sound/alert.mp3" showcontrols="false" hidden="true" autostart="true" loop="false" />');
	}
}

dbsfaces.svg = {
	gsvg: function(pComponent, pX, pY, pWidth, pHeight, pStyleClass, pStyle){
		var xG = dbsfaces.svg.g(pComponent, pX, pY, pWidth, pHeight, pStyleClass, pStyle);
		return dbsfaces.svg.svg(xG, null, null, null, null, null, null);
	},
	g: function(pComponent, pX, pY, pWidth, pHeight, pStyleClass, pStyle){
		var xG = dbsfaces.svg.createElement('g');
		dbsfaces.svg.setDefaultAttr(xG, pStyleClass, pStyle, null);
		if (pX != null){
			xG.svgAttr("x", pX);
		}
		if (pY != null){
			xG.svgAttr("y", pY);
		}
		if (pWidth != null){
			xG.svgAttr("width", pWidth);
		}
		if (pHeight != null){
			xG.svgAttr("height", pHeight);
		}
		pComponent.append(xG);
		return xG;
	},

	svg: function(pComponent, pX, pY, pWidth, pHeight, pStyleClass, pStyle){
		var xSVG = dbsfaces.svg.createElement('svg');
		dbsfaces.svg.setDefaultAttr(xSVG, pStyleClass, pStyle, null);
		if (pX != null){
			xSVG.svgAttr("x", pX);
		}
		if (pY != null){
			xSVG.svgAttr("y", pY);
		}
		if (pWidth != null){
			xSVG.svgAttr("width", pWidth);
		}
		if (pHeight != null){
			xSVG.svgAttr("height", pHeight);
		}
		pComponent.append(xSVG);
		return xSVG;
	},
	
	path: function(pComponent, pData, pStyleClass, pStyle, pFill){
		var xPath = dbsfaces.svg.createElement('path');
		dbsfaces.svg.setDefaultAttr(xPath, pStyleClass, pStyle, pFill);
		xPath.svgAttr("d", pData);
		pComponent.append(xPath);
		return xPath;
	},
	
	use: function(pComponent, pHRef, pStyleClass, pStyle){
		var xUse = dbsfaces.svg.createElement('use');
		dbsfaces.svg.setDefaultAttr(xUse, pStyleClass, pStyle, null);
		xUse.get(0).setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "#" + pHRef);
//		xUse.svgAttr("xlink:href", "#" + pHRef);
		pComponent.append(xUse);
		return xUse;
	},

	line: function(pComponent, pX1, pY1, pX2, pY2, pStyleClass, pStyle){
		var xLine = dbsfaces.svg.createElement('line');
		dbsfaces.svg.setDefaultAttr(xLine, pStyleClass, pStyle, null);
		xLine.svgAttr("x1", pX1)
			 .svgAttr("y1", pY1)
			 .svgAttr("x2", pX2)
			 .svgAttr("y2", pY2);
		pComponent.append(xLine);
		return xLine;
	},
	
	rect: function(pComponent, pX, pY, pWidth, pHeight, pRX, pRY, pStyleClass, pStyle, pFill){
		var xRect = dbsfaces.svg.createElement('rect');
		dbsfaces.svg.setDefaultAttr(xRect, pStyleClass, pStyle, pFill);
		if (pX != null){
			xRect.svgAttr("x", pX);
		}
		if (pY != null){
			xRect.svgAttr("y", pY);
		}
		if (pRX != null){
			xRect.svgAttr("rx", pRX);
		}
		if (pRY != null){
			xRect.svgAttr("ry", pRY);
		}
		xRect.svgAttr("height", pHeight)
			 .svgAttr("width", pWidth);
		pComponent.append(xRect);
		return xRect;
	},
	
	ellipse: function(pComponent, pCX, pCY, pRX, pRY, pStyleClass, pStyle, pFill){
		var xEllipse = dbsfaces.svg.createElement('ellipse');
		dbsfaces.svg.setDefaultAttr(xEllipse, pStyleClass, pStyle, pFill);
		xEllipse.svgAttr("cx", pCX)
			    .svgAttr("cy", pCY)
			    .svgAttr("ry", pRX)
			    .svgAttr("rx", pRY);
		pComponent.append(xEllipse);
		return xEllipse;
	},
	
	text: function(pComponent, pX, pY, pText, pStyleClass, pStyle, pFill){
		var xText = dbsfaces.svg.createElement('text');
		dbsfaces.svg.setDefaultAttr(xText, pStyleClass, pStyle, pFill);
		if (pX != null){
			xText.svgAttr("x", pX);
		}
		if (pY != null){
			xText.svgAttr("y", pY);
		}
		xText.text(pText);
		pComponent.append(xText);
		return xText;
	},
	
	linearGradient: function(pComponent, pId){
		var xElement = dbsfaces.svg.createElement('linearGradient');
		pComponent.append(xElement);
		if (pId != null){
			xElement.svgAttr("id", pId);
		}
		return xElement;
	},
	
	marker: function(pComponent, pId, pRefX, pRefY){
		var xElement = dbsfaces.svg.createElement('marker');
		pComponent.append(xElement);
		if (pId != null){
			xElement.svgAttr("id", pid);
		}
		if (pRefX != null){
			xElement.svgAttr("refx", pRefX);
		}
		if (pRefY != null){
			xElement.svgAttr("refy", pRefY);
		}
		return xElement;
	},

	stop: function(pComponent, pOffset, pStopColor){
		var xElement = dbsfaces.svg.createElement('stop');
		if (pOffset != null){
			xElement.svgAttr("offset", pOffset);
		}
		if (pStopColor != null){
			xElement.svgAttr("stop-color", pStopColor);
		}
		pComponent.append(xElement);
		return xElement;
	},
	
	createElement: function(pTag){
		var xElement = $(document.createElementNS('http://www.w3.org/2000/svg', pTag));
		return xElement;
	},

	setDefaultAttr: function(pComponent, pStyleClass, pStyle, pFill){
		if (pStyleClass != null){
			pComponent.svgAttr("class", pStyleClass);
		}
		if (pStyle != null){
			pComponent.svgAttr("style", pStyle);
		}
		if (pFill != null){
			pComponent.svgAttr("fill", pFill);
		}
	}
	
}

dbsfaces.url = {
	//Retorna os parametros da Url em um objeto
	getParams : function(){
		var xQueryString = {};
		var xQuery = window.location.search.substring(1);
		var xVars = xQuery.split("&");
		for (var i=0;i<xVars.length;i++) {
			var xPair = xVars[i].split("=");
			var xParam = xPair[0].toLowerCase();
		    // If first entry with this name
			if (typeof xQueryString[xParam] === "undefined") {
			  xQueryString[xParam] = decodeURIComponent(xPair[1]);
			    // If second entry with this name
			} else if (typeof xQueryString[xParam] === "string") {
			  var xArr = [ xQueryString[xParam],decodeURIComponent(xPair[1]) ];
			  xQueryString[xParam] = xArr;
			    // If third or later entry with this name
			} else {
			  xQueryString[xParam].push(decodeURIComponent(xPair[1]));
			}
		}
		return xQueryString;
	},
	
	//Copia os valore dos parametros da URL para os campos quando o id for iqual ao nome do parametro.
	setInputsValuesFromParams:function(){
		var wParams = dbsfaces.url.getParams();
		for (var xProperty in wParams) {
		    if (wParams.hasOwnProperty(xProperty)){
		    	var xInput = document.getElementById(xProperty);
		    	if (xInput != null){
		    		xInput.value = wParams[xProperty];
		    	}
		    }
		}
	}
}

dbsfaces.ui = {
	moveToFront: function(pElement){
		var xE = pElement;
		if (xE instanceof jQuery){
			xE = pElement.get(0);
		}
		xE.parentElement.appendChild(xE);
	},
	moveToBack: function(pElement){
		var xE = pElement;
		if (xE instanceof jQuery){
			xE = pElement.get(0);
		}
		xE.parentElement.insertBefore(xE, xE.parentElement.childNodes[0]);
	},
	/*Exibe a imagem de que indica que está aguardando o recebimento dos dados*/
	showLoading : function(pId, pShow){
		
		var xId = "dbs_ajaximg_" + $.trim(pId);
		//Sempre remove loading se já existir 
		
		//Exibe loading
	    if (pShow){
			if ($("#" + xId).length == 0){
				$('body').append("<span id='" + xId + "' class='-loading -large'/>");
			}
	    }else{
			if ($("#" + xId).length > 0){
		    	$("#" + xId).remove();
			}
	    }
	},
	
	showLoadingError : function(pId){
		var xId = "dbs_ajaximg_" + $.trim(pId);
		$("#" + xId).fadeOut(2000, function(){ 
			$("#" + xId).remove();
//			$("div .-loading").remove(); Comentado em 08/abr/15 - Ricardo: Excluid código até a certeza que código não é mais necessário.
		});
	},
	
	getRectangle : function(obj) {
		var xE = obj;
		if (!(obj instanceof jQuery)){
			xE = $(obj);
		}
	   var off = xE.offset();

	   return {
	          top: off.top,
	          left: off.left,
	          height: xE.outerHeight(),
	          width: xE.outerWidth()
	   };
	},

	isInsideRectangle : function(pX, pY, pRect) {
        if ((pX > pRect.left && pX < (pRect.left + pRect.width))
            && (pY > pRect.top && pY < (pRect.top + pRect.height))){
        	return true;
        }else{
	        return false;
        }
	},	

	isOverlappingRectagle : function(pRectA, pRectB) {
		if (dbsfaces.ui.isInsideRectangle(pRectA.left, pRectA.top, pRectB) ||
			dbsfaces.ui.isInsideRectangle(pRectA.left + pRectA.width, pRectA.top, pRectB) ||
			dbsfaces.ui.isInsideRectangle(pRectA.left, pRectA.top + pRectA.height, pRectB) ||
			dbsfaces.ui.isInsideRectangle(pRectA.left + pRectA.width, pRectA.top + pRectA.height, pRectB)){
			return true;
		}else{
			return false;
		}
	},
	
	//Posiciona do próximo campo dentro do container informado
	focusOnFirstInput: function(pContainer){
		//Seta foco no primeiro campo de input
		var xEle = pContainer.find("input:first, select:first, textarea:first");
		if (xEle.length != 0 ){
			xEle.get(0).focus();
		}
	},
	//Seleciona todo o texto
	selectAll: function(pObj){
		//timeout para evitar que o click desmarque o item selecionado
		setTimeout( function(){
			dbsfaces.ui.selectRange(pObj, 0, $(pObj).get(0).value.length);
		}, 1 );
	},
	//Retira a seleção de qualquer texto
	selectNone: function(pObj){
		dbsfaces.ui.selectRange(pObj, 0, 0);
	},
	//Seleciona da posição atual até o final do texto
	selectEnd: function(pObj){
		dbsfaces.ui.selectRange(pObj, $(pObj).get(0).selectionStart, $(pObj).get(0).value.length);
	},
	//Seleciona intervalo
	selectRange: function(pObj, pStart, pEnd){
		$(pObj).get(0).setSelectionRange(pStart, pEnd);
	},

	cssAllBrowser: function(e, pAtribute, pValue){
		var xE = e;
		if (!(xE instanceof jQuery)){
			xE = $(e);
		}
		pAtribute = pAtribute.trim();
		pValue = pValue.trim();
		xE.css("-webkit-" + pAtribute, pValue)
		  .css("-moz-" + pAtribute, pValue)
		  .css("-ms-" + pAtribute, pValue)
		  .css("-o-" + pAtribute, pValue)
		  .css(pAtribute, pValue);
	},

	cssFilterDropShadow: function(e, pValue){
		var xValue = "drop-shadow(" + pValue + ")";
		dbsfaces.ui.cssFilter(e, xValue);
	},
	
	cssFilterBlur: function(e, pValue){
		var xValue = "blur(" + pValue + "px)";
		dbsfaces.ui.cssFilter(e, xValue);
	},

	cssFilterOpacity: function(e, pValue){
		var xValue = "opacity(" + pValue + ")";
		dbsfaces.ui.cssFilter(e, xValue);
	},

	cssFilter: function(e, pValue){
		dbsfaces.ui.cssAllBrowser(e, "filter", pValue);
	},
	
	cssTransform: function(e, pValue){
		dbsfaces.ui.cssAllBrowser(e, "transform", pValue);
	},
	
	cssTransition: function(e, pValue){
		dbsfaces.ui.cssAllBrowser(e, "transition", pValue);
	},

	cssAnimation: function(e, pValue){
		dbsfaces.ui.cssAllBrowser(e, "animation", pValue);
	},

	//Retorna valores do transform
	getTransform:function(e){
		var xSt = window.getComputedStyle(xE.get(0), null);
		var xTr = xSt.getPropertyValue("-webkit-transform") ||
		          xSt.getPropertyValue("-moz-transform") ||
		          xSt.getPropertyValue("-ms-transform") ||
		          xSt.getPropertyValue("-o-transform") ||
		          xSt.getPropertyValue("transform") ||
		          "erro";
		return xTr;
	},
	//Retorna valores do Z definido no transform
	getTransformZ: function(e){
		var xTr = this.getTransformMatrix(e);
		if (xTr == null
		 || xTr == ""){
			return 0;
		}else{
			xTr = xTr.slice(4,5);
			if (xTr == ""){
				xTr = 0;
			}
		}
		return parseFloat(xTr);
	},
	//Retorna valores do X definido no transform
	getTransformX: function(e){
		var xTr = this.getTransformMatrix(e);
		if (xTr == null
		 || xTr == ""){
			return 0;
		}else{
			xTr = xTr.slice(2,3);
			if (xTr == ""){
				xTr = 0;
			}
		}
		return parseFloat(xTr);
	},
	//Retorna valores do Y definido no transform
	getTransformY: function(e){
		var xTr = this.getTransformMatrix(e);
		if (xTr == null
		 || xTr == ""){
			return 0;
		}else{
			xTr = xTr.slice(3,4);
			if (xTr == ""){
				xTr = 0;
			}
		}
		return parseFloat(xTr);
	},

	getTransformMatrix3d: function(e){
		var xTr = this.getTransformMatrix(e);
		if (xTr == null){
			return "";
		}else{
			return xTr.slice(2,5);
		}
	},
	getTransformMatrix: function(e){
		var xTr = dbsfaces.ui.getTransform(e);
		xTr = xTr.match(/matrix(?:(3d)\(-{0,1}\d+\.?\d*(?:, -{0,1}\d+\.?\d*)*(?:, (-{0,1}\d+\.?\d*))(?:, (-{0,1}\d+\.?\d*))(?:, (-{0,1}\d+\.?\d*)), -{0,1}\d+\.?\d*\)|\(-{0,1}\d+\.?\d*(?:, -{0,1}\d+\.?\d*)*(?:, (-{0,1}\d+\.?\d*))(?:, (-{0,1}\d+\.?\d*))\))/);
		return xTr;
	},
	//	Retorna fator mínimo para ajuste de tamanho conforme largura e altura da tela atual em relação a tela desejada
	aspectRatio: function(pWindow, pBaseWidth, pBaseHeight){
		var xBaseRatio = pBaseWidth / pBaseHeight;
		var xWidth = pWindow.width();
		var xHeight = pWindow.height();
		var xUseWidth = ((xWidth / xHeight) < xBaseRatio); 
		var xRatio;
		if (xUseWidth){
			xRatio = xWidth / pBaseWidth;
		}else{
			xRatio = xHeight / pBaseHeight;
		}
//		console.log(xRatio + "\t" + xWidth + "\t" + xHeight + "\t" +  xBaseRatio + "\t" + pBaseWidth + "\t" + pBaseHeight);
		return xRatio;
	},

	//Dispara evento click
	ajaxTriggerClick: function(e){
		if ($(e.source).length == 0){
			return;
		}
		if (e.status == "success"){
			$(e.source).trigger("click");
		}
	},
	
	ajaxTriggerLoaded: function(e){
		if ($(e.source).length == 0){
			return;
		}
		if (e.status == "success"){
			$(e.source).trigger("loaded");
		}
	},	

	//Captura evento ajax dbsoft
	ajaxShowLoading : function(pSelector){
//		console.log("ajaxShowLoading:" + pSelector);
		$(pSelector).off(dbsfaces.EVENT.ON_AJAX_BEGIN);
		$(pSelector).on(dbsfaces.EVENT.ON_AJAX_BEGIN, function(e){
			dbsfaces.ui.showLoading("main",true);
		});
		$(pSelector).off(dbsfaces.EVENT.ON_AJAX_COMPLETE);
		$(pSelector).on(dbsfaces.EVENT.ON_AJAX_COMPLETE, function(e){
			//Reinicia a contagem do timeout a cada complete, já que existe respostas ajax em andamento
			window.clearTimeout(wAjaxTimeout);
			wAjaxTimeout = window.setTimeout(function(e){
				dbsfaces.ui.showLoadingError("main");
			}, 1000); //Time de delay para efetuar a chamada acima(showLoadingError). A chamada será cancelada em caso de sucesso. 	
		});

		$(pSelector).off(dbsfaces.EVENT.ON_AJAX_SUCCESS);
		$(pSelector).on(dbsfaces.EVENT.ON_AJAX_SUCCESS, function(e){
			window.clearTimeout(wAjaxTimeout); //Cancela o timeout definido no evento COMPLETE, cancelando a respectiva chamada ao showLoadingError.
			dbsfaces.ui.showLoading("main",false);
		});
	},
	
	getAllEvents: function(e) {
	    var xResult = [];
	    for (var xKey in e) {
	        if (xKey.indexOf('on') === 0) {
	        	xResult.push(xKey.slice(2));
	        }
	    }
	    return xResult.join(' ');
	},
	
	recreateElement: function(e){
		var xE = e;
		if (!(e instanceof jQuery)){
			xE = $(e);
		}
	    var xNew = xE.clone(true);
	    xE.before(xNew);       
	    xE.remove();
	    return xNew;
	},
	
	getDelayFromTextLength: function(pText){
		var xTime = pText.length;
		xTime = (xTime / 2) * 200;
		return xTime;
	}

}

//Exibe janela ajustando a localização de form a não ultrapassar os limites da janela principal


dbsfaces.util = {
	/*Verifica se elemento existe*/
	exists: function(pId){
		if(!dbsfaces.util.isEmpty(pId)){
			if ($(pId).length>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	},

	//Retorna vazio se for nulo
	getNotNull: function(pValue, pDefaultValue){
		if(pValue == null){
			return pDefaultValue;
		}
	},

	//Verifica se � nulo ou vazio
	isEmpty: function(pValue){
		if(pValue == null){
			return true;
		}
		if(pValue == ''){
			return true;
		}
		return false;
	},
	
	/*dispara evento para o elemento informado*/
	trigger: function (pEventName, pId){
		if (dbsfaces.util.exists(pId) && 
			!dbsfaces.util.isEmpty(pEventName)){
				//Cria evento com parametro para indicar que é dbsoft
				var xEvent = $.Event(pEventName, {dbs: true});
				//Dispara evento depois de fechar 
				$(pId).trigger(xEvent);
				return xEvent;
		}else{
			alert('Parametros inválidos! pId=' + pId + ' pEventName=' + pEventName + ' dbsFaces.trigger');
			return false;
		}
	},
	
	isiOS: function(){
		var xNav = navigator.userAgent.toLowerCase();
		if(xNav.match(/iphone/i) || 
		   xNav.match(/ipod/i) ||
		   xNav.match(/ipad/i)) {
			return true;
		};
	    return false;
	},
	
	isAndroid: function(){
		var xNav = navigator.userAgent.toLowerCase();
		if(xNav.match(/android/i)) {
			return true;
		};
	    return false;
	},

	isBlackBerry: function(){
		var xNav = navigator.userAgent.toLowerCase();
		if(xNav.match(/blackberry/i)) {
			return true;
		};
	    return false;
	},

	isMobile: function(){
	    if (this.isiOS()
	     || this.isBlackBerry()
	     || this.isAndroid()){
	    	return true;
	    }
	    return false;
	},
	
	supports: function(pProp){
	   var div = document.createElement('div'),
	      vendors = 'Khtml Ms O Moz Webkit'.split(' '),
	      len = vendors.length;
	 
      if ( pProp in div.style ) return true;
 
      pProp = pProp.replace(/^[a-z]/, function(val) {
         return val.toUpperCase();
      });
 
      while(len--) {
         if ( vendors[len] + pProp in div.style ) {
            // browser supports box-shadow. Do what you need.
            // Or use a bang (!) to test if the browser doesn't.
            return true;
         } 
      }
      return false;
	}
}


dbsfaces.util.jsid = function(pClientId){
	//Retira dois pontos(:) do inicio
	pClientId = pClientId.replace(/^(:)/,"");
	//Convert os outros dois pontos(:)  em escaped caracter
	return pClientId.replace(/:/g,"\\:");
}

dbsfaces.date = {
	isDate:function(pYear, pMonth, pDay, pHour, pSeconds, pMilliseconds){
		pMonth = pMonth - 1;
		var xD =  new Date(pYear, pMonth, pDay, pHour, pSeconds, pMilliseconds);
		if (xD.getDate() != pDay){
			return "";
		}
		if (xD.getMonth() != pMonth){
			return "";
		}
		if (xD.getFullYear() != pYear){
			return "";
		}
		return xD;
	}
}

dbsfaces.math = {
	round: function(pValue, pDecimals){
		var xP = Math.pow(10, pDecimals);
		var xValue = pValue * xP;
		xValue = Math.round(pValue * xP);
		return dbsfaces.math.trunc(xValue / xP, pDecimals);
	},
	
	trunc: function(pValue, pDecimals){
		var xP = Math.pow(10, pDecimals);
		var xValue = pValue * xP;
		xValue = Math[xValue < 0 ? 'ceil' : 'floor'](xValue);
		return xValue / xP;
	}
};

dbsfaces.number = {
	isNumber: function(pVal){
		return !isNaN(parseFloat(pVal)) && isFinite(pVal);
	},
	
	sizeInBytes: function(pVal){
		if (typeof(pVal) != 'undefined'){
			var xVal = pVal.trim().toUpperCase();
			var xI = xVal.indexOf("KB");
			var xN = 1024;
			if (xI == -1){
				xI = xVal.indexOf("MB");
				if (xI != -1){
					xN *= xN;
				}else{
					xI = xVal.indexOf("GB");
					if (xI != -1){
						xN *= xN;
					}else{
						xI = xVal.indexOf("TB");
						if (xI != -1){
							xN *= xN;
						}else{
							xI = xVal.indexOf("B");
							if (xI == -1){
								xI = xVal.length;
							}
							xN = 1;
						}
					}
				}
			}
			xVal = xVal.substr(0, xI);
			if (dbsfaces.number.isNumber(xVal)){
				return xVal * xN;
			}
		}
		return 0;
	},
	
	getOnlyNumber: function(pValue){
//		var xValue = "";
//		for (var i = 0; i < pValue.length; i++){
//			if (dbsfaces.number.isNumber(pValue.charAt(i))){
//				xValue = xValue + pValue.charAt(i);
//			}
//		}
//		return xValue;
		return pValue.replace(/[^-\d\.]/g, '');
 	}
};

dbsfaces.format = {
	number: function(pValue, pDecimals){
		pValue = dbsfaces.math.round(pValue, pDecimals);
	    if ((1.1).toLocaleString().indexOf(".") >= 0) {
	    	
//	        return pValue.toString().split("/(?=(?:\d{3})+(?:\.\d{2}))/g").join( "," );
	    	return pValue.toString().split("/(?=(?:\d{3})+(?:\.|$))/g").join( "," );
	    }
	    else {
	        return pValue.toString().split("/(?=(?:\d{3})+(?:,|$))/g").join( "." );
//	        return pValue.toString().split("/(?=(?:\d{3})+(?:,\d{2}))/g").join( "." );
	    }
//	   return pValue.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
	}
};

	
dbsfaces.string = {
	fromCharCode: function(pVal){
		//Ajuste para os códigos numéricos retornados pelo teclado estendido.
		return String.fromCharCode((96 <= pVal && pVal <= 105)? pVal-48 : pVal);
	}	
};

		
//Monitora evento ajax recebido e dispara evento dbsoft
dbsfaces.onajax = function(e){
	if ($(e.source).length == 0){
		return;
	}
	if (e.status == "begin"){
		$(e.source).trigger(dbsfaces.EVENT.ON_AJAX_BEGIN);
	}else if (e.status == "complete"){
		$(e.source).trigger(dbsfaces.EVENT.ON_AJAX_COMPLETE);
	}else if (e.status == "success"){
		$(e.source).trigger(dbsfaces.EVENT.ON_AJAX_SUCCESS);
	}
};

dbsfaces.onajaxerror = function(e){
	$(e.source).trigger(dbsfaces.EVENT.ON_AJAX_ERROR);
	return false;
};

$.fn.focusNextInputField = function() {
    return this.each(function() {
        var fields = $(this).parents('form:eq(0),body').find('button,input,textarea,select');
        var index = fields.index( this );
        if ( index > -1 && ( index + 1 ) < fields.length ) {
            fields.eq( index).focus();
        }
        return false;
    });
};
