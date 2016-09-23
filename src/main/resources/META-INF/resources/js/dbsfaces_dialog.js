dbs_dialog = function(pId) {
	var xDialog = $(pId);

	$(window).resize(function(e){
		/*Delay para aguarda o final da transição*/
		setTimeout(function(){
			dbsfaces.dialog.resized(xDialog);
		},300);
	});

	dbsfaces.dialog.initialize(xDialog);

	$(pId + ":not([disabled]) > .-container > .-icon").on("click mousedown touchstart", function(e){
//		console.log("icon mousedown touchstart");
		dbsfaces.dialog.show(xDialog);
		return false;
	});

	$(pId + ":not([disabled]) > .-container > .-mask").on("click mousedown touchstart", function(e){
//		console.log("mask mousedown touchstart");
		dbsfaces.dialog.show(xDialog);
		return false;
	});
	
	$(pId + " > .-container > .-mask").on("mousewheel touchmove", function(e){
//		console.log("mask mousewheel touchmove");
		e.stopImmediatePropagation();
		return false;
	});

	$(pId + "[disabled] > .-container > .-content").on("mousewheel touchmove", function(e){
		return false;
	});

	$(pId + ":not([disabled]) > .-container > .-content").on("mousewheel touchmove", function(e){
//		console.log("content mousewheel touchmove \t" + $(e.originalEvent.srcElement).attr("class"));
		//Se não parte do conteúdo
		if (xDialog.data("sub_content").has(e.originalEvent.srcElement).length){
			return true;
    	}else{
    		return false;
    	}
//    	e.stopPropagation();
		e.stopImmediatePropagation();
	});

	if (!xDialog.data("c")) {
		$(pId + ":not([disabled]) > .-container > .-content").touchwipe({
		     wipeLeft: function() {return dbsfaces.dialog.whipe(xDialog, "l");},
		     wipeRight: function() {return dbsfaces.dialog.whipe(xDialog, "r");},
		     wipeUp: function() {return dbsfaces.dialog.whipe(xDialog, "u");},
		     wipeDown: function() {return dbsfaces.dialog.whipe(xDialog, "d");},
		     min_move_x: 50,
		     min_move_y: 50,
		     preventDefaultEvents: false
		});
	}

	$(pId + ":not([disabled]) > .-container > .-content > .-iconclose").on("click mousedown touchstart", function(e){
//		console.log("iconclose mousedown touchstart");
		dbsfaces.dialog.show(xDialog);
		return false;
	});

	/*Após abanimação de arir ou fechar*/
	$(pId + ":not([disabled]) > .-container > .-content").on(dbsfaces.EVENT.ON_TRANSITION_END, function(e){
//		console.log("end transition\t");
		//Foi fechado
		if ($(this).closest(".dbs_dialog").hasClass("-closed")){
			xDialog.trigger("closed");
			$(this).removeClass("-opened").addClass("-closed");
		//Foi aberto
		}else{
			$(this).removeClass("-closed").addClass("-opened");
			xDialog.trigger("opened");
		}
	});
	
	$(pId + ":not([disabled]) > .-container > .-content > .-header > .-content > .-iconcloseCentral").on("click mousedown touchstart", function(e){
//		console.log("mask mousedown touchstart");
		dbsfaces.dialog.show(xDialog);
		return false;
	});
	
	var xOpened = xDialog.attr('opened');
	if (xOpened == "true") {
		dbsfaces.dialog.show(xDialog);
		return false;
	}
};

dbsfaces.dialog = {
	whipe: function(pDialog, pDirection){
//		alert(pDirection + "\t" + pDialog.data("v") + "\t" + pDialog.data("l"));
		if ((pDirection=="l"
		  && pDialog.data("l")
		  && pDialog.data("v"))
		  
		 || (pDirection=="r"
		  && !pDialog.data("l")
		  && pDialog.data("v"))
		  
		 || (pDirection=="u"
		  && pDialog.data("t")
		  && !pDialog.data("v"))
		  
		 || (pDirection=="d"
		  && !pDialog.data("t")
	      && !pDialog.data("v"))){
			dbsfaces.dialog.show(pDialog);
		} else {
			//TODO FAZER O SCROLL
//			alert(pDirection + "\t" + pDialog.data("v") + "\t" + pDialog.data("l"));
			return false;
		}
	},

	initialize: function(pDialog, pTimeout){
		dbsfaces.dialog.pvInitializeData(pDialog);
		dbsfaces.dialog.pvInitializeLayout(pDialog);
	},
	pvInitializeData: function(pDialog){
		var xTop = pDialog.hasClass("-tlh")
		        || pDialog.hasClass("-tlv")
		        || pDialog.hasClass("-trh")
		        || pDialog.hasClass("-trv");
		
		var xLeft = pDialog.hasClass("-tlh")
		         || pDialog.hasClass("-tlv")
		         || pDialog.hasClass("-blh")
		         || pDialog.hasClass("-blv");

		var xVertical = pDialog.hasClass("-tlv")
		         	 || pDialog.hasClass("-trv")
		         	 || pDialog.hasClass("-blv")
		         	 || pDialog.hasClass("-brv");
		
		var xCenter = pDialog.hasClass("-tlc")
		    	   || pDialog.hasClass("-trc")
		    	   || pDialog.hasClass("-blc")
		    	   || pDialog.hasClass("-brc");
		
		pDialog.data("t", xTop);
		pDialog.data("l", xLeft);
		pDialog.data("v", xVertical);
		pDialog.data("c", xCenter)
		pDialog.data("container", pDialog.children(".-container"));;
		pDialog.data("content", pDialog.data("container").children(".-content"));
		pDialog.data("icon", pDialog.data("container").children(".-icon"));
		pDialog.data("mask", pDialog.data("container").children(".-mask"));
		pDialog.data("sub_container", pDialog.data("content").children(".-sub_container"));
		pDialog.data("sub_content", pDialog.data("sub_container").find("> div > div > .-sub_content"));
		pDialog.data("header", pDialog.data("content").children(".-header"));
		pDialog.data("footer", pDialog.data("content").children(".-footer"));
		pDialog.data("iconclose", pDialog.data("content").children(".-iconclose"));
		pDialog.data("padding", parseFloat(pDialog.data("mask").css("padding-left")));
		pDialog.data("progressTimeout", pDialog.data("sub_container").children(".-progress_timeout"));
	},
	
	pvInitializeLayout: function(pDialog){
		pDialog.data("container").css("opacity", "");
		//Configura cores
		var xColorClose = tinycolor(pDialog.data("content").css("background-color"));
		xColorClose.setAlpha(.5);
		pDialog.data("iconclose").css("background-color", xColorClose.toRgbString());
//		var xColorNav = tinycolor(pDialog.data("content").css("background-color"));
//		xColorNav.setAlpha(.96);
		pDialog.data("content").css("box-shadow", tinycolor("rgba(0, 0, 0, 0.5") + " 0 0 .4em .2em");
//							 .css("background-color", xColorNav.toRgbString());
	},
	

	resized: function(pDialog){
		if (!pDialog.hasClass("-closed")){
//			clearTimeout(pDialog.data("resizetimeout"));
//			pDialog.data("resizetimeout", setTimeout(function(){
				dbsfaces.dialog.pvAjustLayout(pDialog);
//				},50)
//			)
		}
	},

	show: function(pDialog){
		//Está fechado e vai abrir
		if (pDialog.hasClass("-closed")){
			dbsfaces.dialog.pvOpen(pDialog);
			dbsfaces.dialog.pvCloseTimeout(pDialog);
			pDialog.data("sub_content").removeClass("-closed");
		}else{
			dbsfaces.dialog.pvClose(pDialog);
			pDialog.data("sub_content").addClass("-closed");
		}
		pDialog.toggleClass("-closed");
	},

	pvOpen: function(pDialog){
		dbsfaces.dialog.pvAjustLayout(pDialog);
		dbsfaces.ui.disableBackgroundInputs(pDialog);
		$("html").addClass("dbs_dialog-freeze");
		//Coloca o foco no primeiro campo de input dentro do nav
		dbsfaces.ui.focusOnFirstInput(pDialog);
//		pDialog.data("header").css("display", "block");
	},
	
	pvClose: function(pDialog){
		if (pDialog.data("v") || pDialog.data("c")){
			pDialog.data("content").css("width", "0");
		}
		if (!pDialog.data("v") || pDialog.data("c")){
			pDialog.data("content").css("height", "0");
		}
		pDialog.data("progressTimeout").css("animation-name", "none");
		
		dbsfaces.ui.enableForegroundInputs($("body"));
		$("html").removeClass("dbs_dialog-freeze");
		//Retira foco do componente que possuir foco
		$(":focus").blur();
	},
	
	pvForceClose: function(pDialog){
		dbsfaces.dialog.pvClose(pDialog);
		pDialog.removeClass("-opened");
		pDialog.addClass("-closed");
	},
	
	pvAjustLayout: function(pDialog){
		var xLimit = 95;
		var xPadding = 0;
		var xMask = pDialog.data("mask");
		var xHeader = pDialog.data("header");
		var xFooter = pDialog.data("footer");
		var xSubContainer = pDialog.data("sub_container");
		var xMaskWidth = xMask[0].getBoundingClientRect().width * (xLimit / 100);
		var xMaskHeight = xMask[0].getBoundingClientRect().height * (xLimit / 100);
		var xHeaderWidth = pDialog.data("iconclose")[0].getBoundingClientRect().width;
		var xHeaderHeight = pDialog.data("iconclose")[0].getBoundingClientRect().height;
		var xFooterWidth = null;
		var xFooterHeight = null;
		if (xHeader.length > 0){
			xHeaderWidth = xHeader[0].getBoundingClientRect().width;
			xHeaderHeight = xHeader[0].getBoundingClientRect().height;
		}
		if (xFooter.length > 0){
			xFooterWidth = xFooter[0].getBoundingClientRect().width;
			xFooterHeight = xFooter[0].getBoundingClientRect().height;
		}

		//Limita dimensão
//		if (pResize){
//			pDialog.data("content").addClass("-removeTransition"); 
//		}
//		setTimeout(function(){
		if (pDialog.data("v") || pDialog.data("c")){
			//marca novo ponto de inicio da animação, que será o próprio ponto atual(para casos que a animação esteja em andamento)
			pDialog.data("content").css("width", pDialog.data("content")[0].getBoundingClientRect().width); 
			//Salva o tamanho do conteúdo
			var xWidth = pDialog.data("sub_content")[0].getBoundingClientRect().width + (xPadding * 2);
			//Utiliza largura do caption se este for maior que largura do conteúdo do nav
			if (xHeaderWidth > xWidth){
				xWidth = xHeaderWidth;
			}
			//Força largura máxima em 95% caso largura seja superior a largura da tela
			if (xWidth > xMaskWidth){
				xWidth = xLimit + "%";
			}
			//Limita largura do conteúdo do nav
			pDialog.data("content").css("width", xWidth);
			//Configura o espaço do header e do footer
			if (pDialog.data("v")){
				if (pDialog.data("t")){
					if (xHeaderHeight != null){
						xSubContainer.css("padding-top", xHeaderHeight + xPadding);
					}
					if (xFooterHeight != null){
						xSubContainer.css("padding-bottom", xFooterHeight + xPadding);
					}
				}else{
					if (xHeaderHeight != null){
						xSubContainer.css("padding-bottom", xHeaderHeight + xPadding);
					}
					if (xFooterHeight != null){
						xSubContainer.css("padding-top", xFooterHeight + xPadding);
					}
				}
			}
		}
		if (!pDialog.data("v") || pDialog.data("c")){
			pDialog.data("content").css("height", "");
			var xHeight = pDialog.data("sub_content")[0].getBoundingClientRect().height + (xPadding * 2);
			if (pDialog.data("header").size() > 0) {
				xHeight = xHeight + pDialog.data("header")[0].getBoundingClientRect().height;
			}
			if (xHeight > xMaskHeight){
				xHeight = xLimit + "%";
			}
			//Limita altura do conteúdo do nav
			pDialog.data("content").css("height", xHeight);
			if (!pDialog.data("c")){
				if (!pDialog.data("v")){
					//Configura o espaço do footer
					if (pDialog.data("l")){
						xSubContainer.css("padding-left", xHeaderWidth + xPadding);
						if (xFooterWidth != null){
							xSubContainer.css("padding-right", xFooterWidth + xPadding);
						}
					}else{
						xSubContainer.css("padding-right", xHeaderWidth + xPadding);
						if (xFooterWidth != null){
							xSubContainer.css("padding-left", xFooterWidth + xPadding);
						}
					}
				}
			}
		}
//		setTimeout(function(){
//			pDialog.data("content").removeClass("-removeTransition");
//		},30000);
//		},1);
	},

	pvCloseTimeout: function(pDialog){
		var xTimeout = pDialog.attr('timeout');
		if (xTimeout > 0) {
			pDialog.data("progressTimeout").css("animation-name", "progress_timeout_animation");
			pDialog.data("progressTimeout").css("animation-duration", xTimeout+"s");
			setTimeout(function () {
				dbsfaces.dialog.pvForceClose(pDialog);
			}, (xTimeout*1000));
		}
	}
};

