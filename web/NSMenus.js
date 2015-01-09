
//must be set externally
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
var menu = new Array();        //global array
var openImage, closeImage, folderImage;
var thisLayer = null;
var thisName;
var parentLayer;

//should be created and called by onLoad in parent file
function init() {
   //dysfunctional method for creating base layer
parentLayer=new Layer(200);
parentLayer.visibility = "show";
parentLayer.moveTo(10, 10);
parentLayer.clipBottom = 200;
parentLayer.bgColor= "#c0ccff";
parentLayer.clipRight = 100;
parentLayer.zIndex =2;

}
//------------------------
function hiLite(lyr) {
   
   if (thisLayer != null) {
      thisLayer.bgColor= menuBackColor;
   }
   thisLayer = lyr;
   lyr.bgColor= menuHighColor;
}
function unHiLite(lyr) {
   lyr.bgColor= menuBackColor;
}
//------------------------
function setName(nm) {
   thisName = nm;
}
//------------------------
function unClick(ix) {
var layer = menu[ix].layer;
   
   unHiLite(layer);
}
//------------------------
function addMenu(name, index, link) {
   
   var menuItem = new Object();
   menuItem.name= name;
   menuItem.expanded = false;

   var mLayer = new Layer(menuWidth);
   mLayer.hidden = true;
   mLayer.visibility = false;
   mLayer.onmouseover = hiLight;
   mLayer.onmouseout = unLight;
   
   mLayer.zIndex = 1;
   mLayer.document.writeln("<a href=\""+link+"\">"+ name+"</a>");
   mLayer.document.close();
   menuItem.layer= mLayer;
   menu[index] = menuItem;
   
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
   for (var i = 0; i < menu.length; i++) {

      menu[i].layer.moveTo(x, y);
      menu[i].layer.hidden = false;
      menu[i].layer.clip.right = menuWidth;
      menu[i].layer.clip.bottom = menuHeight;
      menu[i].layer.visibility = "show";
      y += menuHeight;
      }
   
}
//----------------------------------
