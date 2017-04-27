dbs_chartX = function(pId, pValues, pRelationalCaptions) {
	var xChart = $(pId);
	dbsfaces.chartX.initialize(xChart, pValues, pRelationalCaptions);
	
	xChart.on("mouseleave", function(e){
		xChart = $(this);
		//Seleciona chartvalue encontrado
		dbsfaces.chartX.selectChartValue(xChart.data("data"), null);
		e.stopImmediatePropagation();
		return false;
	});
};



dbsfaces.chartX = {
	initialize: function(pChart, pValues, pRelationalCaptions){
		var xChartData = dbsfaces.chartX.pvInitializeData(pChart, pValues, pRelationalCaptions);
		dbsfaces.chartX.pvInitializeChartValues(xChartData);
		dbsfaces.chartX.pvInitializeLayout(xChartData);
	},

	pvInitializeData: function(pChart, pValues, pRelationalCaptions){
		var xCharts = pChart.closest(".dbs_chartsX");
		var xData = {
			dom : {
				self : pChart, //O próprio chart
				parent : xCharts, //Pai
				childrenData : [], //Filhos
				caption : null, //Caption do gráfico
				captionText : null, //Texto do gráfico
				chart : pChart.children(".-chart"), //Chart - SVG
				values : pChart.find(".-chart > .-values"), //grupo contendo os chartvalue
				info : pChart.children(".-info"), //Container das infos
				minChartValueData : null, //chartValue que contém o valor máximo
				maxChartValueData : null, //chartValue que contém o valor mínimo
				maxLabelChartValueData : null,//chartValue que contém o maior label
				path: null, //Desenho do caminho
				links: null, //Links entres os chartvalues
				relationalCaptions: null, //Títulos do grupos de relacionalmento
				hoverChartValueData: null, //ChartValue atualmente com hover  
				delta: null, //Container do delta
				deltaHandle1Data: null, // DataHandle 1
				deltaHandle2Data: null, // DataHandle 2
				deltaValue: null, //Texto do valor + label do delta
				deltaValueInt: null, //Texto do valor inteiro do delta
				deltaValueDec: null, //Texto do valor decimal e sinal do percentual do delta
				deltaCircle: null, //Círculo que contem as informações
				movingDeltaHandleData: null, //Handle que está em movimento(selecionado pelo usuário)
				leftDeltaHandleData: null, //Handle mais a esquerda
				rightDeltaHandleData: null //Handle mais a direita
			},	
			type: xCharts.attr("type"), //Tipo de gráfico
			index: 0, //Número do gráfico - Configurado da inicialização do dbschats
			width: null, //largura do gráfico total
			height: null, //largura do gráfico total 
			totalValue: 0, //Somatório para ser utilizado no cálculo do percentual que cada valor representa
			medValue: null, //valor médio
			originalValues: pValues, //Valores recebidos
//			normalizedValuesData: [], //Valores organizados considerando a relação entre eles e ordenados
			relationalCaptions: pRelationalCaptions, //Texto do títulos dos grupos dos labels
			relationalCaptionsCount: 1, //Quantidade de grupos de labels
			relationships: [], //Relacionamentos entres os chartvalues
			color: pChart.attr("color"), //Cor definida pelo usuário
			colorInverted: null, //Cor configurada na pvInitializeLayoutColor do dbscharts
			colorLight: null, //Cor configurada na pvInitializeLayoutColor do dbscharts
			currentColorInverted: tinycolor(xCharts.css("color")).invertLightness().setAlpha(1).toString(),
			findPointTimeout: null,
			showDelta: pChart.hasClass("-showDelta"),
			globalSequence: 0, //Número sequencial do item do chartValue, considerando todos os gráficos 
			diameter: 0, //diametro do máximo (menor valor entre a altura e largura
			center : {x:0, y:0}, //Centro do gráfico
			arcWidth: 0, //largura do arco principal
			arcFator: null, //Arco de cada relationalGroup(Divide diametro entres os relationalGroups)
			arcSpace: 0.0005, //Espaço entre os arcos dos relationalgroups
			pointRadius: 0, //Raio da posição do arco
			pointLinkRadius: 0, //Raio da posição do arco que liga o arco principal do chartvalue ao centro
			pointLinkWidth: 0 //Largura do arco que liga o arco principal do chartvalue ao centro 
		}
		//COnfigura como cor nula quando não tiver sido informada. Posteriormente será calculado uma cor baseada no atributo CSS color(currentColor).
		if (typeof xData.color == "undefined"){
			xData.color = null;
		}
		xData.width = xData.dom.chart[0].getBoundingClientRect().width;
		xData.height = xData.dom.chart[0].getBoundingClientRect().height;
		pChart.data("data", xData);
//		dbsfaces.chartX.addChartValue(pChart, 123);
//		dbsfaces.chartX.clearChartValue(pChart);
		return xData;
	},
	
	pvInitializeChartValues: function(pChartData){
		if (pChartData.originalValues.length > 0){
			pChartData.relationships = [];
			//Loop por todos os valores da originais recebidos
			for (var xI = 0; xI < pChartData.originalValues.length; xI++){
				var xOriginalValue = pChartData.originalValues[xI];
				//Defini valor padrão, caso displayvalue não tenha sido informado
				xOriginalValue.displayValue = ((typeof xOriginalValue.displayValue == "undefined" || xOriginalValue.displayValue == "") ? xOriginalValue.value : xOriginalValue.displayValue);

				//Somatório para ser utilizado posteriormente no cálculo do percentual que cada valor representa
				if (pChartData.type == "pie"){
					pChartData.totalValue += Math.abs(xOriginalValue.value);
				}else{
					pChartData.totalValue += xOriginalValue.value;
				}

				//Quebra grupo de labels
				var xLabels = [];
				if (typeof xOriginalValue.label == "undefined"){
					xLabels.push(xI); //Força que o label seja o index para que sempre exista um label
				}else{
					xLabels = xOriginalValue.label.split(/[;]+/);
				}
				
				//Salva quantidade máxima de grupos de labels existentes
				pChartData.relationalCaptionsCount = Math.max(pChartData.relationalCaptionsCount, xLabels.length);

				//Lista de chartvalue que estão vinculados ao valor original
				var xRelationalChartValueData = [];
				//Cria um chartValuedata para cada relationalCaption e cada label.
				//Valores serão agrupados por relationalCaption e label
				xLabels.forEach(function(pLabel, pRelationalGroupIndex){
					var xChartValueData = null;
					var xLabel = pLabel.trim();
					//Procura se já existe chartValue com este label no mesmo labelindex 
					for (var xN = 0; xN < pChartData.dom.childrenData.length; xN++){
						if (pChartData.dom.childrenData[xN].relationalGroupIndex == pRelationalGroupIndex
						 && pChartData.dom.childrenData[xN].label == xLabel){
							xChartValueData = pChartData.dom.childrenData[xN];
							break;
						}
					}

					//Cria componente chartValueData
					if (xChartValueData == null){
						var xChartValueData = dbsfaces.chartX.pvInitializeChartValuesCreateData(pChartData, pLabel, pRelationalGroupIndex);
						//Adiciona valor normalizado
						pChartData.dom.childrenData.push(xChartValueData);
					}
					//Salva valor original vinculado a este chartValue
					xChartValueData.originalValues.push(xOriginalValue);
					//Calcula somatório dos valores vinculados a este label
					xChartValueData.value += xOriginalValue.value;
					//Salva o valor máximo e valor mínimo
					if (pChartData.dom.minChartValueData == null || xChartValueData.value < pChartData.dom.minChartValueData.value){
						pChartData.dom.minChartValueData = xChartValueData;
					}
					if (pChartData.dom.maxChartValueData == null || xChartValueData.value > pChartData.dom.maxChartValueData.value){
						pChartData.dom.maxChartValueData = xChartValueData;
					}
					//Salva maior label
					if (pChartData.dom.maxLabelChartValueData == null || xChartValueData.label.length > pChartData.dom.maxLabelChartValueData.label.length){
						pChartData.dom.maxLabelChartValueData = xChartValueData;
					}

					//Relação dos chartvaluedata que compoem este valor
					xRelationalChartValueData.push(xChartValueData);
				});
				//Cria lista com os pares de relacionamento
				for (var xA = 0; xA < xRelationalChartValueData.length - 1; xA++){
					var xKeyA = dbsfaces.chartX.pvInitializeChartValuesAddKeyToBinaryKey("", xRelationalChartValueData[xA].key);
					for (var xB = xA + 1; xB < xRelationalChartValueData.length; xB++){
						var xKey = dbsfaces.chartX.pvInitializeChartValuesAddKeyToBinaryKey(xKeyA, xRelationalChartValueData[xB].key);
						var xRelationship = null;
						for (var xN = 0; xN < pChartData.relationships.length; xN++){
							if (pChartData.relationships[xN].key == xKey){
								xRelationship = pChartData.relationships[xN];
								break;
							}
						}
						if (xRelationship == null){
							xRelationship = {
											key: xKey, 
											total:0 
											};
							pChartData.relationships.push(xRelationship);
						}
						xRelationship.total += xOriginalValue.value;
					}
				}
			}
			
			//Calcula valor médio e salva
			pChartData.medValue = (pChartData.totalValue * pChartData.relationalCaptionsCount) / pChartData.dom.childrenData.length;

			//Ordena por RelationalGroup e valor
			dbsfaces.chartX.pvInitializeChartValuesSort(pChartData);
		}
	},


	pvInitializeChartValuesSort: function(pChartData){
		//Ordena por RelationalGroup e valor
		if (pChartData.type == "pie"){
			pChartData.dom.childrenData.sort(function(a, b){
				var x = a.relationalGroupIndex - b.relationalGroupIndex;
				if (x == 0){
					x = b.value - a.value;
				}
				return x;
	//		    var x = a.label.toLowerCase();
	//		    var y = b.label.toLowerCase();
	//		    if (x < y) {return -1;}
	//		    if (x > y) {return 1;}
	//		    return 0;
			});
		}
	},
	

	
	//Cria representação binária do somatórios do index dos labels
	pvInitializeChartValuesAddKeyToBinaryKey: function(pBinaryKey, pIndex){
		var xLengthDif = pIndex - pBinaryKey.length + 1; 
		if (xLengthDif > 0){
			pBinaryKey = "0".repeat(xLengthDif) + pBinaryKey;
		}
		var xStart = pBinaryKey.length - pIndex - 1;
		var xEnd = xStart + 1;
		pBinaryKey = pBinaryKey.substr(0, xStart) + "1" + pBinaryKey.substring(xEnd); 
		return pBinaryKey;
	},

	
	pvInitializeChartValuesCreateData: function(pChartData, pLabel, pRelationalGroupIndex){
		var xChartValueData = {
			dom : {
				self : null, // o próprio chartvalue
				parent : pChartData.dom.self,  //o pai(chart)
				point : null, //elemento point
				pointLink : null, //elemento de liga point ao centro
				info : null, //elemento que contém infos
				infoValues : null, //elemento que contém os elementos dos textos do info(somente usado no chartpie)
				infoLabel : null, //elemento que contém o label
				infoLabelBox : null, //elemento que contém o box do Label
				infoValue : null, //elemento que contém o value
				infoValueBox : null, //elemento que contém o box do Value
				infoPath : null, //elemento que contém o caminho do label e value até o point
				infoPerc : null, //elemento que contém o valor percentual no chartpie
				infoPercInt : null, //elemento que contém o inteiro do valor percentual no chartpie
				infoPercDec : null, //elemento que contém o decimal do valor percentual no chartpie
				infoPercBox : null //elemento que contém o box do perc
			},
			key : pChartData.dom.childrenData.length, //Chave sequencial binária
			index : null, //Index - //Chave sequencial será atribuita após o sort 
			value : 0, //somatótio dos valores que possuem o mesmo label
			label : pLabel.trim(), //Label já desmembrado do group label
			relationalGroupIndex: pRelationalGroupIndex, //Index em relação ao label quando houver mais de uma
			originalValues : [], //valores originals que agrupados neste chartvalue
			x : null, //posição X no gráfico (dentro da escala)
			y : null, //posição Y no gráfico (dentro da escala)
			color: null, //Cor do valor,
			perc: null, //Percentual que valor representa sobre o total
			totalValue: 0, //Total até este chartvalue
			globalSequence: 0, //Número sequencial do item do chartValue, considerando todos os gráficos 
			arcInfo: null //Informacoes do arco quando for gráfico pie
		}
		return xChartValueData;
	},

	
	pvInitializeLayout: function(pChartData){
		//Cria elementos do chartvalueData e configura totalizador e index 
		dbsfaces.chartX.pvInitializeLayoutChartValueCreateElement(pChartData);
		
		//Cria elementos do delta
		dbsfaces.chartX.pvInitializeLayoutDelta(pChartData);

		if (pChartData.type == "line"){
			dbsfaces.chartX.pvInitializeLayoutChartLine(pChartData);
		}else if (pChartData.type == "pie"){
			dbsfaces.chartX.pvInitializeLayoutChartPie(pChartData);
		}

		//Cria elementos do título dos relationalGroup
		dbsfaces.chartX.pvInitializeLayoutCreateRelationalCaptions(pChartData);
	},

	pvInitializeLayoutChartValueCreateElement: function(pChartData){
		var xTotalValue = 0;
		var xRelationalGroupIndex = 0;
		//Cria elementos do chartvalueData e configura totalizador e index 
		pChartData.dom.childrenData.forEach(function(pChartValueData, pI){
			if (xRelationalGroupIndex != pChartValueData.relationalGroupIndex){
				xTotalValue = 0;
			}
			pChartValueData.index = pI;
			dbsfaces.chartX.pvInitializeLayoutChartValuesCreate(pChartData, pChartValueData);
			//Força a exibição do primeiro e último item
			if (pI == 0 || pI == pChartData.dom.childrenData.length - 1){
				pChartValueData.dom.self.addClass("-showLabel");
			}
			//Calcula somatório até este chartvalue
			if (pChartData.type == "pie"){
				xTotalValue += Math.abs(pChartValueData.value);
			}else{
				xTotalValue += pChartValueData.value;
			}
			pChartValueData.totalValue = xTotalValue;
			xRelationalGroupIndex = pChartValueData.relationalGroupIndex;
		});
		//Força a exibição do valor mínimo e máximo
		pChartData.dom.minChartValueData.dom.self.addClass("-min");
		pChartData.dom.maxChartValueData.dom.self.addClass("-max");
	},

	pvInitializeLayoutChartValuesCreate: function(pChartData, pChartValueData){
//		var xChartValueData = dbsfaces.chartX.pvInitializeChartValuesCreateData(pChartData, pLabel, prelationalGroupIndex);
		//Cria ChartValue
		pChartValueData.dom.self = dbsfaces.svg.g(pChartData.dom.values, "dbs_chartValueX -" + pChartData.type, null, {index: pChartValueData.index, relationalGroupIndex: pChartValueData.relationalGroupIndex});
		//Salva data
		pChartValueData.dom.self.data("data", pChartValueData);
		//Cria Elemento que contém infos
		pChartValueData.dom.info = dbsfaces.svg.g(pChartValueData.dom.self, "-info", null, null);
		if (pChartData.type == "line"){
			//Ponto
			pChartValueData.dom.point = dbsfaces.svg.circle(pChartValueData.dom.self, null, null, null, "-point", null, {r:".3em"}); //'r' precisa ser um atributo por problema no FIREFOX
			//Path
			pChartValueData.dom.infoPath = dbsfaces.svg.path(pChartValueData.dom.info, null, "-path", null, null);
			//LabelBox
			pChartValueData.dom.infoLabelBox = dbsfaces.svg.rect(pChartValueData.dom.info, null, null, null, null, ".2em", ".2em", "-labelBox", null, null); //'r' precisa ser um atributo por problema no FIREFOX
			//valueBox
			pChartValueData.dom.infoValueBox = dbsfaces.svg.rect(pChartValueData.dom.info, null, null, null, null, ".2em", ".2em", "-valueBox", null, null); //'r' precisa ser um atributo por problema no FIREFOX
			//Texto do Label
			pChartValueData.dom.infoLabel = dbsfaces.svg.text(pChartValueData.dom.info, null, null, pChartValueData.label, "-label", null, null);
			//Texto do Valor
			pChartValueData.dom.infoValue = dbsfaces.svg.text(pChartValueData.dom.info, null, null, pChartValueData.value, "-value", null, null);
		}else if (pChartData.type == "bar"){
			//Box
			pChartValueData.dom.infoLabelBox = dbsfaces.svg.rect(pChartValueData.dom.info, null, null, null, null, ".2em", ".2em", "-labelBox", null, null); //'r' precisa ser um atributo por problema no FIREFOX
			//Ponto
			pChartValueData.dom.point = dbsfaces.svg.path(pChartValueData.dom.self, null, "-point", null, null);
		}else if (pChartData.type == "pie"){
			//Ponto
//			pChartValueData.dom.point = dbsfaces.svg.path(pChartValueData.dom.self, null, "-point", null, {fill:"none"});
			pChartValueData.dom.point = dbsfaces.svg.path(pChartValueData.dom.self, null, "-point", null, {stroke:"currentColor", fill:"none"});
			pChartValueData.dom.pointLink = dbsfaces.svg.path(pChartValueData.dom.self, null, "-pointLink", null, {stroke:"currentColor", fill:"none"});
			//Path que liga o point ao label
//			pChartValueData.dom.infoPath = dbsfaces.svg.path(pChartValueData.dom.self, null, "-path", null, null);
			//Container do value
			pChartValueData.dom.infoValues = dbsfaces.svg.g(pChartValueData.dom.info, "-values", null, null);
			//Texto do Label
			pChartValueData.dom.infoLabel = dbsfaces.svg.text(pChartValueData.dom.infoValues, "0.2em", null, pChartValueData.label, "-label", null, null);
//			pChartValueData.dom.infoLabel = dbsfaces.svg.text(pChartValueData.dom.infoValues, "3.3em", "-.2em", pChartValueData.label, "-label", null, null);
			//Texto do Valor
			pChartValueData.dom.infoValue = dbsfaces.svg.text(pChartValueData.dom.infoValues, "0.1em", ".8em", pChartValueData.value, "-value", null, null);
//			pChartValueData.dom.infoValue = dbsfaces.svg.text(pChartValueData.dom.infoValues, "3.3em", ".8em", pChartValueData.value, "-value", null, null);
			//BoxPerc
			pChartValueData.dom.infoPercBox = dbsfaces.svg.rect(pChartValueData.dom.infoValues, "7em", "-1em", "1em", "1em", ".2em", ".2em", "-percBox", null, null); //'r' precisa ser um atributo por problema no FIREFOX
//			pChartValueData.dom.infoPercBox = dbsfaces.svg.rect(pChartValueData.dom.infoValues, ".2em", "-1em", "3em", "2em", ".2em", ".2em", "-percBox", null, null); //'r' precisa ser um atributo por problema no FIREFOX
			//Texto do Perc
			pChartValueData.dom.infoPerc = dbsfaces.svg.text(pChartValueData.dom.infoValues, "7.4em", ".2em", null, "-perc", null, null);
//			pChartValueData.dom.infoPerc = dbsfaces.svg.text(pChartValueData.dom.infoValues, ".4em", ".2em", null, "-perc", null, null);
			//Texto dos Inteiros do Perc
			pChartValueData.dom.infoPercInt = dbsfaces.svg.tspan(pChartValueData.dom.infoPerc, null, "-int", null, null);
			//Texto dos Decimais do Perc
			pChartValueData.dom.infoPercDec = dbsfaces.svg.tspan(pChartValueData.dom.infoPerc, null, "-dec", null, null);
		}
		dbsfaces.ui.moveToBack(pChartValueData.dom.point);
		//Captura movimento do mouse para seleciona ponto
		if (pChartData.type == "pie"){
			pChartValueData.dom.self.on("mousemove touchmove touchstart", function(e){
				var xChartValueData;
				//Procura chartvalue a partir da posição do touch
				if (e.type == "touchmove"){
					var xMyLocation = e.originalEvent.changedTouches[0];
					var xRealTarget = document.elementFromPoint(xMyLocation.clientX, xMyLocation.clientY);
					xChartValueData = $(xRealTarget).closest(".dbs_chartValueX").data("data");
				}else{
					xChartValueData = $(this).data("data");
				}
				
				//Seleciona chartvalue encontrado
				dbsfaces.chartX.selectChartValue(pChartData, xChartValueData);
				e.stopImmediatePropagation();
				$(this).blur();
				return false;
			});
		}
	},

	//Cria os elementos para exibir os captions dos relationalGroups
	pvInitializeLayoutCreateRelationalCaptions: function(pChartData){
		if (pChartData.type == "pie"){
			pChartData.relationalCaptions.forEach(function(pRelationalCaption, pI){
				var xPathId = pChartData.dom.self[0].id + ":relationalPath_" + pI;
				//Path que será utilizado para alinhar o texto do caption
				dbsfaces.svg.path(pChartData.dom.relationalCaptions, null, "-path", null, {id:xPathId});
				//Texto do caption
				var xTextElement = dbsfaces.svg.text(pChartData.dom.relationalCaptions, null, null, null, "-caption", null, {relationalGroupIndex:pI, fill:pChartData.currentColor});
				dbsfaces.svg.textPath(xTextElement, xPathId, pRelationalCaption, null, null, {"startOffset": "50%"});
			});
		}
	},

	pvInitializeLayoutChartLineDeltaHandle: function(pChartData, pHandleNumber){
		var xDeltaHandle = dbsfaces.svg.g(pChartData.dom.delta, "-handle", null, {handle:pHandleNumber});
		return dbsfaces.chartX.pvInitializeLayoutChartLineDeltaHandleData(pChartData, xDeltaHandle, pHandleNumber);
	},
	
	pvInitializeLayoutChartLineDeltaHandleData: function(pChartData, pDeltaHandle, pHandleNumber){
		var xData = {
			dom : {
				self: pDeltaHandle, //O próprio handle
				rect: dbsfaces.svg.rect(pDeltaHandle, null, null, null, null, null, null, "-rect", null, null), //Retangulo da área excluida do cálculo do delta
				handle: dbsfaces.chartX.pvInitializeLayoutChartLineCreateHandle(pChartData, pDeltaHandle), //Puxador
				chartValueData: null //Chartvalue que está a posição deste handle
			},
			number: pHandleNumber //Número do handle(1 ou 2)
		}
		pDeltaHandle.data("data", xData);
		return xData;
	},


	pvInitializeLayoutChartLineCreateHandle: function(pChartData, pDeltaHandle){
//		var xDeltaHandleHandle = dbsfaces.svg.svg(pDeltaHandle, null, null, "16", "16", "-handle", "overflow: visible;", {viewBox:"0 0 16 16"});
		var xDeltaHandleHandle = dbsfaces.svg.svg(pDeltaHandle, null, null, "1.5em", "1.5em", "-handle", "overflow: visible;", {viewBox:"0 0 16 16"});
		dbsfaces.svg.circle(xDeltaHandleHandle, "0", "0", null, "-touch", null, {r:"16", fill:"transparent"});
		dbsfaces.svg.rect(xDeltaHandleHandle, "-3", "-8", "6", "16", "2", "2", "-rect", null, {fill:pChartData.currentColorInverted, stroke:"currentColor", "stroke-width":"1px"});
		dbsfaces.svg.line(xDeltaHandleHandle, "-1", "-4", "-1", "4", "-line", null, {stroke:"currentColor", "stroke-width":"1px"});
		dbsfaces.svg.line(xDeltaHandleHandle, "1", "-4", "1", "4", "-line", null, {stroke:"currentColor", "stroke-width":"1px"});
		
		//Captura eventos para mover handle
		pDeltaHandle.on("mousedown touchstart", function(e){
			dbsfaces.chartX.setMovingDeltaHandleData(pChartData, $(this).data("data"));
			e.stopImmediatePropagation();
			return false;
		});
		pDeltaHandle.on("mousemove touchmove", function(e){
			if (e.originalEvent.type == "mousemove" 
			 && e.which == 0){
				dbsfaces.chartX.setMovingDeltaHandleData(pChartData, null);
				return;
			}
			dbsfaces.chartX.findPoint(e, pChartData);
			e.stopImmediatePropagation();
			return false;
		});	
		pChartData.dom.self.on("mouseup touchend", function(e){
			dbsfaces.chartX.setMovingDeltaHandleData(pChartData, null);
			e.stopImmediatePropagation();
			return false;
		});
		pChartData.dom.self.on("mouseleave", function(e){
			dbsfaces.chartX.setMovingDeltaHandleData(pChartData, null);
		});
		return xDeltaHandleHandle;
	},
	
	pvInitializeLayoutDelta: function(pChartData){
		if (!pChartData.showDelta){return;}
		pChartData.dom.delta = dbsfaces.svg.g(pChartData.dom.chart, "-delta", null, null);
		//Cria elemento Value
		pChartData.dom.deltaValue = dbsfaces.svg.text(pChartData.dom.delta, null, null, null, "-value", null, {fill:pChartData.color});
//		pChartData.dom.deltaValue = dbsfaces.svg.text(pChartData.dom.delta, null, null, null, "-value", null, {fill:"white"});
		pChartData.dom.deltaValueInt = dbsfaces.svg.tspan(pChartData.dom.deltaValue, "0", "-int", null, null);
		pChartData.dom.deltaValueDec = dbsfaces.svg.tspan(pChartData.dom.deltaValue, "%", "-dec", null, null);
		//Cria elementos da guia do delta
		if (pChartData.type == "line"){
			//Guia 1
			pChartData.deltaHandle1Data = dbsfaces.chartX.pvInitializeLayoutChartLineDeltaHandle(pChartData, 1);
			pChartData.dom.leftDeltaHandleData = pChartData.deltaHandle1Data;
			//Guia 2
			pChartData.deltaHandle2Data = dbsfaces.chartX.pvInitializeLayoutChartLineDeltaHandle(pChartData, 2);
			pChartData.dom.rightDeltaHandleData = pChartData.deltaHandle2Data;
		}else if (pChartData.type == "pie"){
			//Cria círculo de sevirá de fundo para a exibição do delta
			pChartData.dom.deltaCircle = dbsfaces.svg.circle(pChartData.dom.delta, null, null, null, "-circle", null, {fill:"white", stroke:"currentColor", "stroke-width":".1em"});
			dbsfaces.ui.moveToBack(pChartData.dom.deltaCircle);
			//Encobre 
			dbsfaces.ui.moveToFront(pChartData.dom.values);
		}
	},
	
	pvInitializeLayoutChartLine: function(pChartData){
		//Cria elemento que será a linha que conecta os pontos
		pChartData.dom.path = dbsfaces.svg.path(pChartData.dom.chart, null, "-path", "stroke:" + pChartData.color, null);
		dbsfaces.ui.moveToBack(pChartData.dom.path);

		
		//Captura movimento do mouse para seleciona ponto
		if (pChartData.type == "line"){
			pChartData.dom.self.on("mousemove touchmove touchstart", function(e){
				var xChart = $(this);
				if (xChart.hasClass("-selected")){
					//Timeout para diminuir a quantidade de chamada
					clearTimeout(pChartData.findPointTimeout);
					pChartData.findPointTimeout = setTimeout(function(){
						dbsfaces.chartX.findPoint(e, xChart.data("data"));
					},5);
				}
				e.stopImmediatePropagation();
				return false;
			});
		}
	},

	pvInitializeLayoutChartPie: function(pChartData){
		//Cria elemento que será a linha que conecta os pontos
		pChartData.dom.links = dbsfaces.svg.g(pChartData.dom.chart, "-links", null, null);
		dbsfaces.ui.moveToBack(pChartData.dom.links);
		//Cria elemento que agrupa todos os captions dos relationsGroup
		pChartData.dom.relationalCaptions = dbsfaces.svg.g(pChartData.dom.chart, "-relationalCaptions", null, null);
		dbsfaces.ui.moveToBack(pChartData.dom.relationalCaptions);
	},

	//Procura ponto da caminho(path)
	findPoint: function(e, pChartData){
		var xDecimals = 1;
		var xXY = dbsfaces.ui.pointerEventToXY(e);
		var xPosition = pChartData.dom.self.offset();
		var xCurrentX = dbsfaces.math.round(xXY.x - xPosition.left + $(window).scrollLeft() - parseFloat(pChartData.dom.self.css("padding-left")), xDecimals);
//		var xCurrentX = dbsfaces.math.round(xXY.x - xPosition.left, xDecimals);
		if (xCurrentX < 0){return;}
		var xChartPath = pChartData.dom.path[0];
	    var xBeginning = xCurrentX;
        var xEnd = dbsfaces.math.round(xChartPath.getTotalLength(), xDecimals);
        var xTargetLenght;
        var xTargetPos;
        var xTargetPosX;
        //Procura ponto da caminho(path) que o X é iqual a posição X selecionada
        while (Math.abs(xBeginning - xEnd) > 1) {
        	xTargetLenght = xBeginning +  dbsfaces.math.round((xEnd - xBeginning) / 2, xDecimals); //Meio do caminho
			xTargetPos = xChartPath.getPointAtLength(xTargetLenght); //Ponto do path 
			xTargetPosX = dbsfaces.math.round(xTargetPos.x, xDecimals);
			if (xTargetPosX < xCurrentX){
				xBeginning = xTargetLenght;
			}else if (xTargetPosX > xCurrentX){
				xEnd = xTargetLenght;
			}else{
				break; //Encontrou posição
			}
        }
		if (typeof(xTargetPos) != "undefined"){
			//Procura qual dos chartsValues está mais próximo a posição do cursor
			var xTotalSegs = pChartData.dom.path.svgGetPathTotalSegs();
			var xIndex = xChartPath.getPathSegAtLength(xTargetLenght);
			var xClosestX = xCurrentX;
			var xChartValueData = pChartData.dom.childrenData[xIndex];
			var xX = Number(xChartValueData.x);
			var xY = Number(xChartValueData.y);
			//Se cursos estiver antes do ponto, seleciona o chartvalue anterior
			if (xCurrentX < xX && xIndex > 0){
				xClosestX = pChartData.dom.childrenData[xIndex - 1].x;
			//Se cursos não estiver após do ponto, seleciona o chartvalue posterior
			}else if(xCurrentX > xX && xIndex < (xTotalSegs - 1)){
				xClosestX = pChartData.dom.childrenData[xIndex + 1].x;
			}
			var xXMiddle = (Number(xClosestX) + xX) / 2;
			//Escolhe o item anterior se estiver antes do meio do caminho entre o próximo item
			if (xCurrentX < xXMiddle){
				xChartValueData = pChartData.dom.childrenData[xIndex - 1];
			}
			//Seleciona chartvalue encontrado
			dbsfaces.chartX.selectChartValue(pChartData, xChartValueData);
		}
	},
	
	selectChartValue: function(pChartData, pChartValueData){
		if (pChartData.showDelta){
			dbsfaces.chartX.pvShowDelta(pChartData, pChartValueData);
		}else{
			//Seleção simples do chartvalue 
			pChartData.dom.hoverChartValueData = dbsfaces.chartX.pvHover(pChartData, pChartValueData, pChartData.dom.hoverChartValueData);
		}
	},
	

	setMovingDeltaHandleData: function(pChartData, pDeltaHandleData){
		//Indica se handle está em movimento
		if (pDeltaHandleData == null){
			if (pChartData.dom.movingDeltaHandleData != null){
				pChartData.dom.self.removeClass("-moving");
				pChartData.dom.movingDeltaHandleData.dom.self.removeClass("-selected");
			}
		}else{
			pChartData.dom.self.addClass("-moving");
			pDeltaHandleData.dom.self.addClass("-selected");
		}
		//Salva qual o delta handle esta sendo movimentado
		pChartData.dom.movingDeltaHandleData = pDeltaHandleData;
	},


	addChartValue: function(pChart, pValue, pLabel, pDisplayValue, pTooltip){
		if (typeof pValue == "undefined"){
			return;
		}
		if (typeof pLabel == "undefined"){
			pLabel = pValue.toString();
		}
		if (typeof pDisplayValue == "undefined"){
			pDisplayValue = pLabel;
		}
		if (typeof pTooltip == "undefined"){
			pTooltip = "";
		}
		var xValue = JSON.parse('{ "value":' + pValue + ', "label":"' + pLabel + '", "displayValue":"' + pDisplayValue + '", "tooltip":"' + pTooltip + '"}');
		pChartData.originalValues.push(xValue);
	},
	
	clearChartValue: function(pChart){
		pChartData.originalValues = [];
	},
	
	
	pvHover: function(pChartData, pChartValueData, pOldChartValueData){
		//Remove hover anterios
		if (pOldChartValueData != null){
			if (pChartValueData != null 
			 && pOldChartValueData == pChartValueData){
				return pChartValueData;
			}else{
				pOldChartValueData.dom.self.removeClass("-hover");
				//Esconde links entre os chartvalues
				if (pChartData.type == "pie"){
					pChartData.dom.self.find("> .-chart > .-links > .-hover").removeClass("-hover");
					pChartData.dom.deltaCircle.removeClass("-hover");
				}	
			}
		}
		//Ativa hover atual
		if (pChartValueData != null){
			pChartValueData.dom.self.addClass("-hover");
			if (pChartData.type == "line"){
				//Move chartvalue para a frente de todos os outros
				dbsfaces.ui.moveToFront(pChartValueData.dom.self);
			}else if (pChartData.type == "pie"){
				pChartData.dom.deltaCircle.addClass("-hover");
				//Exibe links entre os chartvalues
				var xLinks = pChartData.dom.self.find("> .-chart > .-links > [key='" + pChartValueData.key + "']");
				xLinks.addClass("-hover");
//				xLinks.svgAttr("fill", pChartValueData.dom.self.css("color"));
				xLinks = pChartData.dom.self.find("> .-chart > .-links > [b='" + pChartValueData.key + "']");
				xLinks.addClass("-hover");
				pChartData.dom.deltaCircle.svgAttr("fill", pChartValueData.dom.self.css("color"));
				pChartData.dom.delta.css("color", pChartValueData.dom.info.svgAttr("fill"));
//				xLinks.svgAttr("fill", pChartValueData.dom.self.css("color"));
			}
		}
		return pChartValueData;
	},
	
	
	pvShowDelta: function(pChartData, pChartValueData){
		if (pChartData.type == "line"){
			dbsfaces.chartX.pvShowDeltaChartLine(pChartData, pChartValueData);
		}else if (pChartData.type == "pie"){
			dbsfaces.chartX.pvShowDeltaChartPie(pChartData, pChartValueData);
		}
	},

	pvShowDeltaChartPie: function(pChartData, pChartValueData){
		dbsfaces.chartX.pvShowDeltaValueChartPie(pChartData, pChartValueData);
		//Seleciona chartvalue encontrado
		pChartData.dom.hoverChartValueData = dbsfaces.chartX.pvHover(pChartData, pChartValueData, pChartData.dom.hoverChartValueData);
	},

	pvShowDeltaValueChartPie: function(pChartData, pChartValueData){
		if (pChartValueData == null){
			dbsfaces.chartX.pvShowDeltaValue(pChartData, null);
		}else{
			dbsfaces.chartX.pvShowDeltaValue(pChartData, pChartValueData.perc);
		}
	},

	pvShowDeltaChartLine: function(pChartData, pChartValueData){
		if (pChartData.dom.movingDeltaHandleData == null){return;}
		var xChartsData = pChartData.dom.parent.data("data"); 
		if (xChartsData == null
		 || !xChartsData.showDelta){return;}
		
		var xX;
		var xWidth;
		var xTrocou = false;
		//Retira hover de ambos para recolocar posteriormente. Isto evita apagar hover quando left e right estão no mesmo ponto
		dbsfaces.chartX.pvHover(pChartData, null, pChartData.dom.rightDeltaHandleData.dom.chartValueData);
		dbsfaces.chartX.pvHover(pChartData, null, pChartData.dom.leftDeltaHandleData.dom.chartValueData);
		
		//Salva qual o chartvaluedata está vinculado ao handle
		pChartData.dom.movingDeltaHandleData.dom.chartValueData = pChartValueData;
		//Se for selecionado o handle a direita, mas a posição selecionada estiver mais a esquerda do que handle a esquerda,
		//Seta o handle a esquerda corrente como sendo a esquerda
		//e o handle a direita passa a ser o handle a esquerda. 
		if (pChartData.dom.movingDeltaHandleData == pChartData.dom.rightDeltaHandleData){
			if (pChartValueData.x < parseFloat(pChartData.dom.leftDeltaHandleData.dom.handle.svgAttr("x"))){
				pChartData.dom.rightDeltaHandleData = pChartData.dom.leftDeltaHandleData;
				pChartData.dom.leftDeltaHandleData = pChartData.dom.movingDeltaHandleData;
			}
		//Vice-versa quando selecionado o handle a esquerda, conforme explicação acima.
		}else{
			if (pChartValueData.x > parseFloat(pChartData.dom.rightDeltaHandleData.dom.handle.svgAttr("x"))){
				pChartData.dom.leftDeltaHandleData = pChartData.dom.rightDeltaHandleData;
				pChartData.dom.rightDeltaHandleData = pChartData.dom.movingDeltaHandleData;
			}
		}
		//Configura posição e tamanho do rect
		if (pChartData.dom.leftDeltaHandleData.dom.chartValueData != null){
			pChartData.dom.leftDeltaHandleData.dom.rect.svgAttr("width", pChartData.dom.leftDeltaHandleData.dom.chartValueData.x - xChartsData.infoWidth);
			pChartData.dom.leftDeltaHandleData.dom.rect.svgAttr("x", xChartsData.infoWidth);
			//Seta hover
			dbsfaces.chartX.pvHover(pChartData, pChartData.dom.leftDeltaHandleData.dom.chartValueData, null);
		}
		if (pChartData.dom.rightDeltaHandleData.dom.chartValueData != null){
			pChartData.dom.rightDeltaHandleData.dom.rect.svgAttr("width", xChartsData.width - pChartData.dom.rightDeltaHandleData.dom.chartValueData.x + xChartsData.infoWidth);
			pChartData.dom.rightDeltaHandleData.dom.rect.svgAttr("x", pChartData.dom.rightDeltaHandleData.dom.chartValueData.x);
			//Seta hover
			dbsfaces.chartX.pvHover(pChartData, pChartData.dom.rightDeltaHandleData.dom.chartValueData, null);
		}
		//Configura posição do handle
		pChartData.dom.movingDeltaHandleData.dom.handle.svgAttr("x", pChartValueData.x);
		//Exibe valor do delta
		dbsfaces.chartX.pvShowDeltaValueChartLine(pChartData);
	},

	pvShowDeltaValueChartLine: function(pChartData){
		var xValue = dbsfaces.chartX.pvCalcDeltaChartLine(pChartData);
		dbsfaces.chartX.pvShowDeltaValue(pChartData, xValue);
	},

	pvShowDeltaValue: function(pChartData, pValue){
		if (pValue == null){
//			pChartData.dom.deltaValueText.text("(na)");
			pChartData.dom.deltaValueInt.text("");
			pChartData.dom.deltaValueDec.text("");
		}else{
			var xSplit = dbsfaces.format.splitNumber(pValue);
			pChartData.dom.deltaValueInt.text(xSplit.int);
			pChartData.dom.deltaValueDec.text(xSplit.dec + "%");
		}
	},

	pvCalcDeltaChartLine: function(pChartData){
		if (pChartData.dom.rightDeltaHandleData.dom.chartValueData == null
		 || pChartData.dom.rightDeltaHandleData.dom.chartValueData == null){
			return null; 
		}
		var xLeftValue = pChartData.dom.leftDeltaHandleData.dom.chartValueData.value;
		var xRightValue = pChartData.dom.rightDeltaHandleData.dom.chartValueData.value;
		var xChartsData = pChartData.dom.parent.data("data");
		if (xLeftValue == 0
		 || xRightValue == 0
		 || Math.sign(xLeftValue) != Math.sign(xRightValue)
		 || xChartsData == null){
			return null;
		}
		var xValue;
		if (xChartsData.isPerc){
			xValue = (xRightValue - xLeftValue);
			xValue *= 100;
		}else{
			if (xLeftValue < 0){
				xValue = (xLeftValue / xRightValue);
			}else{
				xValue = (xRightValue / xLeftValue);;
			}
			xValue = dbsfaces.math.round(xValue, 4);
			xValue = (xValue - 1) * 100;
		}
		return dbsfaces.format.number(xValue, 2);
	}

};

