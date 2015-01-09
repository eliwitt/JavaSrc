var menuBackColor;  // = "#c0ccff";
var menuForeColor;  // = "#FFFF00";
var menuHighColor;
var menuX;          // = 50;
var menuY;          // = 100;
var menuWidth;      // = 100;
var menuHeight;     // = 20;
var menuIndent;     // = 20;
var menuBottom;
//used internally
var menu = new Object();        //global array
var openImage, closeImage, folderImage;
var thisLayer = null;
var thisName;
var menuCount=0;

   //-------------------------
    function getMenuItem(id) {
       return menu[id];
    }
    //-------------------------
    function showMenu(id) {
       var layer = getObject(id);
       layer.style.visibility = "visible";
    }
    //-------------------------
    function hideMenu(id) {
       var layer = getObject(id);

       layer.style.visibility = "hidden";
    }
    //-------------------------
    function moveTo(id, x, y) {

       var layer = getObject(id);
       layer.style.pixelLeft = x;
       layer.style.pixelTop  = y;

    }                         
    //-------------------------
    function getObject(name) {
       var estring = "document.all." + name;
      return eval(estring);
    }
    //------------------------
    function hiLite(lyr) {
       if (thisLayer != null) {
          thisLayer.style.backgroundColor= menuBackColor;
       }
       thisLayer = getObject(lyr);
       thisLayer.style.backgroundColor= menuForeColor;

    }
    //------------------------ 
    function getMenuId(ix) {
       return "menu"+ix;
    }
    //------------------------ 
    function setName(nm) {
       thisName = nm;
       
    }
    //------------------------
    
    function addMenu(name, index, link) {

       var menuItem = new Object();
       menuItem.name= name;
       
       var id=getMenuId(index);
       var str=
       "<div id='"+id+"' style='position:absolute' >\n";
       
       str +="<a href=\"" + link + "\">" + name+ "</a>\n";
       str += "</div>\n";

       document.body.insertAdjacentHTML("beforeEnd", str);
       var mLayer = getObject(id);
       mLayer.visibility="hidden";
       
       menuItem.layer= mLayer;
       menu[id] = menuItem;
       menu[id].onmouseover=hiLight;
       menu[id].onmouseout= unLight;
       menuCount++;        //count them
       
    }
    //--------------------------------
function hiLight() {  
   this.bgColor=menuForeColor;
   this.fgColor="#ff0000";
}
function unLight() {
   this.bgColor=menuBackColor;
}

    //-----------------------------
    function drawMenus() {
       var y = menuY;            
       var x = menuX;
       var id, sid;
       for (var i = 0; i < menuCount; i++) {
          id=getMenuId(i);
    
          moveTo(id, x, y);
          showMenu(id);

          y += menuHeight;
          
       }
    }



