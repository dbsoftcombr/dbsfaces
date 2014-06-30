dbs_inputDate = function(pId) {
	$(pId + " input").focusin(function(e){
		$(this).select();
	});
	
	$(pId + " > .-container > .dbs_input-data").focusin(function(){
		$(this).addClass("dbs_input-data-FOCUS");
	});
	
	$(pId + " > .-container > .dbs_input-data > .-day").focusout(function(){
		var xV = dbsfaces.inputDate.getInputDateDay($(this));
		$(this).val(xV);
	});
	$(pId + " > .-container > .dbs_input-data > .-month").focusout(function(){
		var xV = dbsfaces.inputDate.getInputDateMonth($(this));
		$(this).val(xV);
	});
	$(pId + " > .-container > .dbs_input-data > .-year").focusout(function(){
		var xV = dbsfaces.inputDate.getInputDateYear($(this));
		$(this).val(xV);
	});
	$(pId + " > .-container > .dbs_input-data > .-hour").focusout(function(){
		var xV = dbsfaces.inputDate.getInputDateHour($(this));
		$(this).val(xV);
	});
	$(pId + " > .-container > .dbs_input-data > .-minute").focusout(function(){
		var xV = dbsfaces.inputDate.getInputDateMinute($(this));
		$(this).val(xV);
	});
	$(pId + " > .-container > .dbs_input-data > .-second").focusout(function(){
		var xV = dbsfaces.inputDate.getInputDateSecond($(this));
		$(this).val(xV);
	});
	
	$(pId + " > .-container > .dbs_input-data").focusout(function(){
		$(this).removeClass("dbs_input-data-FOCUS");
		if (dbsfaces.inputDate.getIsValid($(this).parent())==""){
			$(this).addClass("-error");
		}else{
			$(this).removeClass("-error");
		}
	});
	
	
	$(pId + " > .-container > .dbs_input-data > input").keydown(function(e){
		var xC = "";
		//Ignora tecla se não for uma tecla válida para o campo de data
		if (!dbsfaces.inputDate.isValidKey(e)){
			e.preventDefault();
			return false;
		}
	
		//Ignora valor digitado se a data não for válida
		var xS = $(this).get(0).selectionEnd - $(this).get(0).selectionStart; 
		var xV = String.fromCharCode(e.keyCode);
		if (xS == 0 &&
			dbsfaces.number.isNumber(String.fromCharCode(e.keyCode))){
			var xD = dbsfaces.inputDate.getInputDateDay($(this));
			var xM = dbsfaces.inputDate.getInputDateMonth($(this));
			var xY = dbsfaces.inputDate.getInputDateYear($(this));
			if ($(this).hasClass("-day")){
				xD = xD + xV;
			}else if ($(this).hasClass("-month")){
				xM = xM + xV;
			}
			var xDate = dbsfaces.date.isDate(xY, xM, xD, 0, 0, 0);
			if (xDate == "" && xD != ""  && xM != ""  && xY != ""){
				e.preventDefault();
				return;
			}
		}
		
		//Pula para o próximo campo de input
		if (e.keyCode == 9 && 
			!e.shiftKey){
			//Desabilitador até verificar se é bom ter está opção
			//$(this).parent().children("input").last().focusNextInputField();
			return;
		//puda para o próximo campo dento da data com a BARRA
		}else if (e.keyCode == 191){ 
			xC = $(this).nextAll("input:first");
			e.preventDefault();
			return;
		}
		//Se foi configurado um novo campo para receber o foco, pula para ele
		if (xC.length){
			e.preventDefault();
			xC.focus().select();
			return;
		}		
		
		var xAdd = 0;
		//Adiciona data quando pressionado seta para cima
		if (e.keyCode == 38){
			xAdd = 1;
		//Subtrai data quando pressionado seta para baixo
		}else if (e.keyCode == 40){
			xAdd = -1;
		}
		if (xAdd != 0){
			if ($(this).hasClass("-day")){
				dbsfaces.inputDate.addDate($(this), "day", xAdd);
			}else if ($(this).hasClass("-month")){
				dbsfaces.inputDate.addDate($(this), "month", xAdd);
			}else if ($(this).hasClass("-year")){
				dbsfaces.inputDate.addDate($(this), "year", xAdd);
			}
		}
	});
	
	$(pId + " > .-container > .dbs_input-data > input").keyup(function(e){
		var xC = "";
		var xS = $(this).get(0).selectionEnd - $(this).get(0).selectionStart; 
		//Pula para o próximo campo(mes ou ano) da data caso e campo atual esteja completo
		if (xS == 0 &&
			dbsfaces.number.isNumber(String.fromCharCode(e.keyCode))){
			var xL = $(this).val().length;
			//Se estiver completo com os dois digitos, pula
			if (xL == 2){
				var xC = $(this).nextAll("input:first");
			}
		}
		//Volta o campo quando digitado a seta para esquerda
		if (e.keyCode == 37 &&
			!e.shiftKey){
			xC = $(this).prevAll("input:first");
		//Avança para o próximo campo quando digitado a seta para direita
		}else if ((e.keyCode == 39  &&
				  !e.shiftKey) ||
				  e.which == 47){
			xC = $(this).nextAll("input:first");
		}
		//Se foi configurado um novo campo para receber o foco, pula para ele
		if (xC.length){
			e.preventDefault();
			xC.focus().select();
		}
	});	
}

dbsfaces.inputDate = {
	isValidKey: function(e){
		if (e.altKey ||
			e.ctrlKey){
			return false;
		} 	
		if (e.keyCode == 9 ||
			e.keyCode == 8 ||
			e.keyCode == 13 ||
			e.keyCode == 37 ||
			e.keyCode == 38 ||
			e.keyCode == 39 ||
			e.keyCode == 40 ||
			e.keyCode == 47 ||
			e.keyCode == 191 ||
			e.keyCode == 110 ||
			(e.keyCode >= 96 && e.keyCode <= 105) ||
			(!e.shiftKey && dbsfaces.number.isNumber(String.fromCharCode(e.keyCode))) ){
			return true;
		}
		return false;
	},

	addDate: function(pId, pField, pAdd){
		var xId = pId.parent();
		var xD = parseInt(dbsfaces.inputDate.getInputDateDay(pId),10);
		var xM = parseInt(dbsfaces.inputDate.getInputDateMonth(pId),10);
		var xY = parseInt(dbsfaces.inputDate.getInputDateYear(pId),10);
		if (pField == "day"){
			xD = xD + pAdd;
		}else if (pField == "month"){
			xM = xM + pAdd;
		}else if (pField == "year"){
			xY = xY + pAdd;
		}	
		var xDate = dbsfaces.date.isDate(xY, xM, xD, 0, 0, 0);
		if (xDate != ""){
			if (pField == "day"){
				$(xId).children(".-day").val(xD);
			}else if (pField == "month"){
				$(xId).children(".-month").val(xM);
			}else if (pField == "year"){
				$(xId).children(".-year").val(xY);
			}	
		}
	},

	getInputDateValue: function(pId){
		var xId = $(pId).find(".dbs_input-data > .-day");
		var xD = dbsfaces.inputDate.getInputDateDay(xId);
		var xM = dbsfaces.inputDate.getInputDateMonth(xId);
		var xY = dbsfaces.inputDate.getInputDateYear(xId);
		var xDate = dbsfaces.date.isDate(xY, xM, xD, 0, 0, 0);
		return xDate;
	},

	getIsValid: function(pId){
		var xId = $(pId).find(".dbs_input-data > .-day");
		var xD = dbsfaces.inputDate.getInputDateDay(xId);
		var xM = dbsfaces.inputDate.getInputDateMonth(xId);
		var xY = dbsfaces.inputDate.getInputDateYear(xId);
		if (xD == "" && 
			xM == "" &&
			xY == ""){
			return true;
		}else{
			var xDate = dbsfaces.inputDate.getInputDateValue(pId);
			if (xDate == ""){
				return false;
			}else{
				return true;
			}
		}
	},

	getInputDateDay: function(pId){
		var xId = pId.parent();
		var xV = $(xId).children(".-day").val();
//		if (xV!=""){
//			if (dbsfaces.number.isNumber(xV)){
//				xV = parseInt(xV,10);
//				if (xV < 10){
//					xV = "0" + xV;
//				}
//			}else{
//				xV = "";
//			}
//		}
//		return xV;
		return dbsfaces.inputDate.leadingZero(xV);
	},

	getInputDateMonth: function(pId){
		var xId = pId.parent();
		var xV = $(xId).children(".-month").val();
//		if (xV!=""){
//			if (dbsfaces.number.isNumber(xV)){
//				xV = parseInt(xV,10);
//				if (xV < 10){
//					xV = "0" + xV; 
//				}
//			}else{
//				xV = "";
//			}
//		}
//		return xV;
		return dbsfaces.inputDate.leadingZero(xV);
	},

	getInputDateYear: function(pId){
		var xId = pId.parent();
		var xV = $(xId).children(".-year").val();
		if (xV!=""){
			if (dbsfaces.number.isNumber(xV)){
				xV = parseInt(xV,10);
				if (xV < 70){
					xV = xV + 2000; 
				}else if (xV < 100){
					xV = xV + 1900; 
				}else if (xV < 1000){
					xV = xV + 2000; 
				}
			}else{
				xV = "";
			}
		}
		return xV;
	},
	
	getInputDateHour: function(pId){
		var xId = pId.parent();
		var xV = $(xId).children(".-hour").val();
//		if (xV!=""){
//			if (dbsfaces.number.isNumber(xV)){
//				xV = parseInt(xV,10);
//				if (xV < 10){
//					xV = "0" + xV; 
//				}
//			}else{
//				xV = "";
//			}
//		}
//		return xV;
		return dbsfaces.inputDate.leadingZero(xV);
	},
	
	getInputDateMinute: function(pId){
		var xId = pId.parent();
		var xV = $(xId).children(".-minute").val();
//		if (xV!=""){
//			if (dbsfaces.number.isNumber(xV)){
//				xV = parseInt(xV,10);
//				if (xV < 10){
//					xV = "0" + xV; 
//				}
//			}else{
//				xV = "";
//			}
//		}
		return dbsfaces.inputDate.leadingZero(xV);
	},
	
	getInputDateSecond: function(pId){
		var xId = pId.parent();
		var xV = $(xId).children(".-second").val();
//		if (xV!=""){
//			if (dbsfaces.number.isNumber(xV)){
//				xV = parseInt(xV,10);
//				if (xV < 10){
//					xV = "0" + xV; 
//				}
//			}else{
//				xV = "";
//			}
//		}
		return dbsfaces.inputDate.leadingZero(xV);
	},
	
	leadingZero: function(pValue){
		if (pValue != ""){
			if (dbsfaces.number.isNumber(pValue)){
				pValue = parseInt(pValue,10);
				if (pValue < 10){
					pValue = "0" + pValue; 
				}
			}else{
				pValue = "";
			}
		}
		return pValue;
	}
}


