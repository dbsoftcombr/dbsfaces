package br.com.dbsoft.ui.component.inputnumber;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import br.com.dbsoft.ui.component.DBSRenderer;
import br.com.dbsoft.ui.core.DBSFaces;
import br.com.dbsoft.ui.core.DBSFaces.CSS;
import br.com.dbsoft.util.DBSFormat;
import br.com.dbsoft.util.DBSFormat.NUMBER_SIGN;
import br.com.dbsoft.util.DBSNumber;
import br.com.dbsoft.util.DBSString;

@FacesRenderer(componentFamily = DBSFaces.FAMILY, rendererType = DBSInputNumber.RENDERER_TYPE)
public class DBSInputNumberRenderer extends DBSRenderer {

	@Override
	public void decode(FacesContext pContext, UIComponent pComponent) {
		DBSInputNumber xInputNumber = (DBSInputNumber) pComponent;
        if(xInputNumber.getReadOnly()) {return;}
    	
		decodeBehaviors(pContext, xInputNumber);

		String xClientIdAction = getInputDataClientId(xInputNumber);
		if (pContext.getExternalContext().getRequestParameterMap().containsKey(xClientIdAction)) {
			Object xSubmittedValue = pContext.getExternalContext().getRequestParameterMap().get(xClientIdAction);
			try {
				//Primeiramente converte para double para forçar um valor não nulo
				xSubmittedValue = DBSNumber.toDouble(xSubmittedValue);
				//Este submittedValue irá converter o valor para o tipo de dado do campo que o receberá
				xInputNumber.setSubmittedValue(xSubmittedValue);
			} catch (Exception xE) {
				wLogger.error("Erro na conversão do inputnumber", xE);
			}
		}
	}

	@Override
	public void encodeBegin(FacesContext pContext, UIComponent pComponent) throws IOException {
		if (!pComponent.isRendered()) {
			return;
		}
		DBSInputNumber xInputNumber = (DBSInputNumber) pComponent;
		ResponseWriter xWriter = pContext.getResponseWriter();
		String xClientId = xInputNumber.getClientId(pContext);
		String xClass = CSS.INPUTNUMBER.MAIN + CSS.THEME.INPUT;
		if (xInputNumber.getStyleClass() != null) {
			xClass += xInputNumber.getStyleClass();
		}

		xWriter.startElement("div", xInputNumber);
			DBSFaces.encodeAttribute(xWriter, "id", xClientId);
			DBSFaces.encodeAttribute(xWriter, "name", xClientId);
			DBSFaces.encodeAttribute(xWriter, "class", xClass);
			DBSFaces.encodeAttribute(xWriter, "style", xInputNumber.getStyle());
			if (xInputNumber.getIncrement()){
				DBSFaces.encodeAttribute(xWriter, "increment", "increment");
			}
			//Container
			xWriter.startElement("div", xInputNumber);
				DBSFaces.encodeAttribute(xWriter, "class", CSS.MODIFIER.CONTAINER);
					DBSFaces.encodeLabel(pContext, xInputNumber, xWriter);
					pvEncodeInput(pContext, xInputNumber, xWriter);
					DBSFaces.encodeRightLabel(pContext, xInputNumber, xWriter);
			xWriter.endElement("div");
			DBSFaces.encodeTooltip(pContext, xInputNumber, xInputNumber.getTooltip());
		xWriter.endElement("div");

		//Não gera o JS quando for somente leitura
		if (!xInputNumber.getReadOnly()){
			DBSFaces.encodeJavaScriptTagStart(pComponent, xWriter);
			//Comentado o JS com $(document).ready por não inicializar corretamente o campo no IE
			
			String xJS = "$(document).ready(function() { \n"
						+ " var xInputNumberId = dbsfaces.util.jsid('" + pComponent.getClientId() + "'); \n "
						+ " var xInputNumberDataId = dbsfaces.util.jsid('" + getInputDataClientId(xInputNumber) + "'); \n "
						+ " dbs_inputNumber(xInputNumberId, xInputNumberDataId," + pvGetMaskParm(xInputNumber) + "); \n" + "}); \n";
			xWriter.write(xJS);
			DBSFaces.encodeJavaScriptTagEnd(xWriter);
		}
	}

	private String pvGetMaskParm(DBSInputNumber pInputNumber) {
		String xType;
		String xMask;
		String xMaskEmptyChr;
		String xDecDigits;
		String xGroupSymbol = "";
		String pDecSymbol = "";
		String pGroupDigits = "3";
		if (pInputNumber.getLeadingZero()) {
			xType = "fixed";
		} else {
			xType = "number";
		}
		if (pInputNumber.getLeadingZero()) {
			xMask = DBSString.repeat("9", pInputNumber.getSize());
			xMaskEmptyChr = "0";
		} else {
			xMask = "0";
			xMaskEmptyChr = " ";
		}
		xDecDigits = pInputNumber.getDecimalPlaces().toString();
		xGroupSymbol = DBSFormat.getGroupSeparator();
		if (!pInputNumber.getSeparateThousand()) {
			pGroupDigits = "0";
		}
		if (pInputNumber.getDecimalPlaces() > 0){
			pDecSymbol = DBSFormat.getDecimalSeparator();
		}
		return "'" + xType + "'," + 
			   "'" + xMask + "'," + 
			   "'" + xMaskEmptyChr + "'," + 
			   xDecDigits + "," + 
			   "'" + xGroupSymbol + "'," + 
			   "'" + pDecSymbol + "'," +
			   "'" + pGroupDigits + "'";
	}

	private void pvEncodeInput(FacesContext pContext, DBSInputNumber pInputNumber, ResponseWriter pWriter) throws IOException {
		Integer xSize = pvGetSize(pInputNumber); //Ajusta tamanho considerando os pontos e virgulas.
		String xClientId = getInputDataClientId(pInputNumber);
		String xStyle = DBSFaces.getCSSStyleWidthFromInputSize(xSize);
		String xStyleClass = "";
		String xValue = "";
		if (pInputNumber.getValueDouble() != null){
			if (DBSNumber.toDouble(pInputNumber.getMinValue())<0){
				//Exibe valor com sinal
				xValue = DBSFormat.getFormattedNumber(pInputNumber.getValueDouble(), NUMBER_SIGN.MINUS_PREFIX, pvGetNumberMask(pInputNumber));
			}else{
				//Exibe valor sem sinal
				xValue = DBSFormat.getFormattedNumber(pInputNumber.getValueDouble(), NUMBER_SIGN.NONE, pvGetNumberMask(pInputNumber));
			}
		}
		if (!pInputNumber.getLeadingZero()){
			xStyle += " text-align:right;";
		}
		if (pInputNumber.getValueDouble() > DBSNumber.toDouble(pInputNumber.getMaxValue()) ||
			pInputNumber.getValueDouble() < DBSNumber.toDouble(pInputNumber.getMinValue())){
			xStyleClass = CSS.MODIFIER.ERROR;
		}

		pWriter.startElement("div", pInputNumber);
			DBSFaces.encodeAttribute(pWriter, "class", "-input");
		
			if (pInputNumber.getReadOnly()) {
				// Se for somente leitura, gera código como <Span>
				DBSFaces.encodeInputDataReadOnly(pInputNumber, pWriter, xClientId, false, xValue, xSize, null, xStyle);
			} else {
				pWriter.startElement("input", pInputNumber);
					DBSFaces.encodeAttribute(pWriter, "id", xClientId);
					DBSFaces.encodeAttribute(pWriter, "name", xClientId);
					if (pInputNumber.getSecret()) {
						DBSFaces.encodeAttribute(pWriter, "type", "password");
					} else {
						DBSFaces.encodeAttribute(pWriter, "type", "text");
					}
					DBSFaces.encodeAttribute(pWriter, "pattern", "[0-9]*"); //Força a exibição do teclado númerico no mobile
					DBSFaces.encodeAttribute(pWriter, "inputmode", "numeric");
					DBSFaces.encodeAttribute(pWriter, "class", DBSFaces.getInputDataClass(pInputNumber) + xStyleClass);
					DBSFaces.encodeAttribute(pWriter, "style", xStyle);
					DBSFaces.encodeAttribute(pWriter, "placeHolder", pInputNumber.getPlaceHolder());
					DBSFaces.setSizeAttributes(pWriter, xSize, null);
					DBSFaces.encodeAttribute(pWriter, "minValue", pInputNumber.getMinValue()); 
					DBSFaces.encodeAttribute(pWriter, "maxValue", pInputNumber.getMaxValue());
					//Verifica se o sinal é negativo
					if (DBSNumber.toDouble(pInputNumber.getMinValue())<0){
						if (pInputNumber.getValueDouble()<0){
							DBSFaces.encodeAttribute(pWriter, "n", "-");
						}
					}
					if (!pInputNumber.getAutocomplete().toLowerCase().equals("on") &&
						!pInputNumber.getAutocomplete().toLowerCase().equals("true")){
						DBSFaces.encodeAttribute(pWriter, "autocomplete", "off");
					}
	
	
					DBSFaces.encodeAttribute(pWriter, "size", xSize);
					DBSFaces.encodeAttribute(pWriter, "maxlength", xSize);
					DBSFaces.encodeAttribute(pWriter, "value", xValue, "0");
					encodeClientBehaviors(pContext, pInputNumber);
				pWriter.endElement("input");
				if (pInputNumber.getIncrement()){
//					pWriter.startElement("svg", pInputNumber);
//						DBSFaces.encodeSVGNamespaces(pWriter);
//						DBSFaces.encodeAttribute(pWriter, "class", "-button");
//						DBSFaces.encodeAttribute(pWriter, "viewBox", "0 0 2 1");
////						DBSFaces.encodeAttribute(pWriter, "viewBox", "0 0 3.3 1");
//						DBSFaces.encodeSVGPath(pInputNumber, pWriter, "M 1,0 L2,1 L0,1 Z", "-path -up", null, null);
////						DBSFaces.encodeSVGPath(pInputNumber, pWriter, "M 1,1 L2,0 L0,0 Z", "-path -down", null, null);
////						DBSFaces.encodeSVGPath(pInputNumber, pWriter, "M 2.3,1 L3.3,0 L1.3,0 Z", "-path -down", null, null);
//						
//					pWriter.endElement("svg");
//					pWriter.startElement("svg", pInputNumber);
//						DBSFaces.encodeSVGNamespaces(pWriter);
//						DBSFaces.encodeAttribute(pWriter, "class", "-button");
//						DBSFaces.encodeAttribute(pWriter, "viewBox", "0 0 2 1");
////						DBSFaces.encodeSVGPath(pInputNumber, pWriter, "M 1,0 L2,1 L0,1 Z", "-path -up", null, null);
//						DBSFaces.encodeSVGPath(pInputNumber, pWriter, "M 1,1 L2,0 L0,0 Z", "-path -down", null, null);
////						DBSFaces.encodeSVGPath(pInputNumber, pWriter, "M 2.3,1 L3.3,0 L1.3,0 Z", "-path -down", null, null);
//					pWriter.endElement("svg");

					//					DBSFaces.encodeAttribute(pWriter, "width", pCharts.getWidth());
//					DBSFaces.encodeAttribute(pWriter, "height", pCharts.getHeight() - pCharts.getCaptionHeight() - pCharts.getFooterHeight());
						
//					String xUp =  " "
//							pWriter.startElement("div", pInputNumber);
//							<svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
//							 width="800px" height="800px" viewBox="0 0 800 800" enable-background="new 0 0 2 1" xml:space="preserve">
//						<polygon fill="none" stroke="#000000" stroke-miterlimit="10" points=“2,1 0,1 1,0 "/>
//						</svg>
					//Encode do botão
					pWriter.startElement("div", pInputNumber);
						DBSFaces.encodeAttribute(pWriter, "class", "-buttons");
						pWriter.startElement("div", pInputNumber);
							DBSFaces.encodeAttribute(pWriter, "class", " -container -th_flex -not_selectable");
							pWriter.startElement("div", pInputNumber);
								DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -delete -i_delete -th_col"); //-i_navigate_down 
							pWriter.endElement("div");
							pWriter.startElement("div", pInputNumber);
								DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -i_media_play -up -th_col");
	//							DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -i_media_play -down -th_col"); //-i_navigate_down 
							pWriter.endElement("div");
							Integer xUnits = (pInputNumber.getSize() - pInputNumber.getDecimalPlaces());
							if (xUnits >= 7){
								pWriter.startElement("div", pInputNumber);
									DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -op_mm -th_col");
								pWriter.endElement("div");
								xUnits = 7;
							}
							if ((xUnits - 6) < 3){
								pWriter.startElement("div", pInputNumber);
									DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -op_cm -th_col");
								pWriter.endElement("div");
							}
							if ((xUnits - 5) < 3){
								pWriter.startElement("div", pInputNumber);
									DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -op_xm -th_col");
								pWriter.endElement("div");
							}
							if ((xUnits - 4) < 3){
								pWriter.startElement("div", pInputNumber);
									DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -op_m -th_col");
								pWriter.endElement("div");
							}
							if ((xUnits - 3) < 3){
								pWriter.startElement("div", pInputNumber);
									DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -op_c -th_col");
								pWriter.endElement("div");
							}
							if ((xUnits - 2) < 3){
								pWriter.startElement("div", pInputNumber);
									DBSFaces.encodeAttribute(pWriter, "class",CSS.THEME.ACTION + " -op_x -th_col");
								pWriter.endElement("div");
							}
							pWriter.startElement("div", pInputNumber);
								DBSFaces.encodeAttribute(pWriter, "class", CSS.THEME.ACTION + " -close -i_cancel -th_col"); //-i_navigate_down 
							pWriter.endElement("div");
						pWriter.endElement("div");
					pWriter.endElement("div");
				}
			pWriter.endElement("div");
		}
	}
	
	private String pvGetNumberMask(DBSInputNumber pInputNumber) {
		Integer xLeadingZeroSize;
		Boolean xShowSeparator;

		if (pInputNumber.getLeadingZero()) {
			xLeadingZeroSize = pInputNumber.getSize() - pInputNumber.getDecimalPlaces();
			xShowSeparator = false;
		} else {
			xLeadingZeroSize = 1;
			xShowSeparator = pInputNumber.getSeparateThousand();
		}
		return DBSFormat.getNumberMask(pInputNumber.getDecimalPlaces(), xShowSeparator, xLeadingZeroSize);
	}
	
	/**
	 * Retorma tamanho máximo de caracteres que sertão exibidos, considerando o valor máximo e respectivos pontos/virgulas
	 * @param pInputNumber
	 * @return
	 */
	private Integer pvGetSize(DBSInputNumber pInputNumber){
		Integer xSize = pInputNumber.getSize(); 
		if (xSize == 0){
			//Utiliza maxlength se tiver sido informado
			if (pInputNumber.getMaxLength()!=0){
				xSize = pInputNumber.getMaxLength();
			}else{
			//Utiliza tamanho do valor mínimo e/ou máximo se não tiver sido informado.
				Integer xMax = pInputNumber.getMaxValue().length();
				Integer xMin = pInputNumber.getMinValue().length();
				xSize = (xMax > xMin ? xMax: xMin);
			}
		}else if (pInputNumber.getMaxLength()!=0
			   && pInputNumber.getMaxLength() < xSize){
			xSize = pInputNumber.getMaxLength();
		}
//		//Ajusta tamanho considerando o valor máximo e respectivos pontos/virgulas		
//		String xFoo;
//		//String somente com a parte inteira, já que a aprte decimal, quando houver, é obrigatória a exibição.
//		xFoo = "-" + DBSString.repeat("1", xSize -  pInputNumber.getDecimalPlaces());
//		if (DBSNumber.toDouble(pInputNumber.getMinValue())<0){
//			xSize = DBSFormat.getFormattedNumber(DBSNumber.toDouble(xFoo), NUMBER_SIGN.MINUS_PREFIX, pvGetNumberMask(pInputNumber)).length();
//		}else{
//			xSize = DBSFormat.getFormattedNumber(DBSNumber.toDouble(xFoo), NUMBER_SIGN.NONE, pvGetNumberMask(pInputNumber)).length();
//		}
		//Define valor de um caracter como tamanho mínimo
		if (xSize < 1){
			xSize = 1;
		}
		return xSize;
	}

//	private Integer pvGetUnits(DBSInputNumber pInputNumber){
//		Integer xUnits = (pInputNumber.getSize() - pInputNumber.getDecimalPlaces());
//		if (xUnits > 7){
//			return 7;
//		}
//		if (xUnits > 7){
//			return xUnits;
//		}
//		10
//		100
//		1000
//		10000
//		100000
//		1000000
//		
//	}
}
