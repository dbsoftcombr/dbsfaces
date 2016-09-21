package br.com.dbsoft.ui.component;

import static com.sun.faces.renderkit.Attribute.attr;
import static com.sun.faces.util.CollectionsUtils.ar;

import java.util.Map;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.util.CollectionsUtils;


public class DBSPassThruAttributes {

    private static Map<String,Attribute[]> ATTRIBUTE_LOOKUP=CollectionsUtils.<String,Attribute[]>map()
        .add("Accordion",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("onclick","click")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Button",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Calendar",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))        
        .add("Charts",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))        
        .add("Chart",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))        
        .add("ChartValue",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))        
        .add("Checkbox",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Combobox",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("onclick","click")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("ComponentTree",ar(
	        attr("onblur","blur")
	        ,attr("onchange","change")
            ,attr("onclick","click")
	        ,attr("ondblclick","dblclick")
	        ,attr("onfocus","focus")
	        ,attr("onkeydown","keydown")
	        ,attr("onkeypress","keypress")
	        ,attr("onkeyup","keyup")
	        ,attr("onmousedown","mousedown")
	        ,attr("onmousemove","mousemove")
	        ,attr("onmouseout","mouseout")
	        ,attr("onmouseover","mouseover")
	        ,attr("onmouseup","mouseup")
	        ,attr("onselect","select")
	        ,attr("tabindex")
        ))
        .add("CrudView",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("CrudDialog",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("CrudModal",ar(
                attr("onblur","blur")
                ,attr("onchange","change")
                ,attr("ondblclick","dblclick")
                ,attr("onfocus","focus")
                ,attr("onkeydown","keydown")
                ,attr("onkeypress","keypress")
                ,attr("onkeyup","keyup")
                ,attr("onmousedown","mousedown")
                ,attr("onmousemove","mousemove")
                ,attr("onmouseout","mouseout")
                ,attr("onmouseover","mouseover")
                ,attr("onmouseup","mouseup")
                ,attr("onselect","select")
                ,attr("tabindex")
            ))        
        .add("CrudTable",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("DataTable",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
//            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Dialog",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Modal",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Div",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Nav",ar(
                attr("onblur","blur")
                ,attr("onchange","change")
                ,attr("ondblclick","dblclick")
                ,attr("onfocus","focus")
                ,attr("onkeydown","keydown")
                ,attr("onkeypress","keypress")
                ,attr("onkeyup","keyup")
                ,attr("onmousedown","mousedown")
                ,attr("onmousemove","mousemove")
                ,attr("onmouseout","mouseout")
                ,attr("onmouseover","mouseover")
                ,attr("onmouseup","mouseup")
                ,attr("onselect","select")
                ,attr("tabindex")
        ))
        .add("FileUpload",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Group",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Img",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("InputDate",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("InputPhone",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("InputMask",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("InputNumber",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("InputText",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("InputTextArea",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Label",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Li",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Link",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Listbox",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Menu",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Menuitem",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("MenuitemSeparator",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("MessageList",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Progress",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Push",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Radio",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Report",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Tab",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("TabPage",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .add("Ul",ar(
            attr("onblur","blur")
            ,attr("onchange","change")
            ,attr("ondblclick","dblclick")
            ,attr("onfocus","focus")
            ,attr("onkeydown","keydown")
            ,attr("onkeypress","keypress")
            ,attr("onkeyup","keyup")
            ,attr("onmousedown","mousedown")
            ,attr("onmousemove","mousemove")
            ,attr("onmouseout","mouseout")
            ,attr("onmouseover","mouseover")
            ,attr("onmouseup","mouseup")
            ,attr("onselect","select")
            ,attr("tabindex")
        ))
        .fix();
    public enum Key {
        ACCORDION("Accordion"),
        BUTTON("Button"),
        CALENDAR("Calendar"),
        CHECKBOX("Checkbox"),
        CHARTS("Charts"),
        CHART("Chart"),
        CHARTVALUE("ChartValue"),
        COMBOBOX("Combobox"),
        COMPONENTTREE("ComponentTree"),
        CRUDVIEW("CrudView"),
        CRUDDIALOG("CrudDialog"),
        CRUDTABLE("CrudTable"),
        DATATABLE("DataTable"),
        DIALOG("Dialog"),
        MODAL("Modal"),
        DIV("Div"),
        NAV("Nav"),
        FILEUPLOAD("FileUpload"),
        GROUP("Group"),
        IMG("Img"),
        INPUTDATE("InputDate"),
        INPUTMASK("InputMask"),
        INPUTPHONE("InputPhone"),
        INPUTNUMBER("InputNumber"),
        INPUTTEXT("InputText"),
        INPUTTEXTAREA("InputTextArea"),
        LABEL("Label"),
        LI("Li"),
        LINK("Link"),
        LISTBOX("Listbox"),
        MENU("Menu"),
        MENUITEM("Menuitem"),
        MENUITEMSEPARATOR("MenuitemSeparator"),
        MESSAGELIST("MessageList"),
        PROGRESS("Progress"),
        RUSH("Push"),
        RADIO("Radio"),
        REPORT("Report"),
        REPORTFORM("ReportForm"),
        TAB("Tab"),
        TABPAGE("TabPage"),
        UL("Ul");
        private String key;
        Key(String key) {
            this.key = key;
        }
        public String value() {
            return this.key;
        }
    }


    public static Attribute[] getAttributes(Key key) {
        return ATTRIBUTE_LOOKUP.get(key.value());
    }	
}
	
	
	
