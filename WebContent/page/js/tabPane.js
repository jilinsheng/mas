var TabPaneConfig = {
    idPrefix: "tab-panel-object-",
    idCounter: 0,
    getId: function(){ return this.idPrefix+this.idCounter++;},
    tabHeadId: "tabHead",
    tabHeadClass: "tab-head tab-border",
    tabBodyId: "tabBody",
    tabBodyClass: "tab-border"
}

function TabPane(id){
    this.id = id;
    this.height = "100%";
    this.width = "100%";
    
    this.tabPages = 0;
    this.head = null;
    this.body = null;    
}

TabPane.prototype.init = function(){    
    var r = document.getElementById(this.id);
    if(!r.style.overflow)    r.style.overflow = "auto";
    r.className = "tab";
    
    //create head
    var div = document.createElement("div");
    div.id = TabPaneConfig.tabHeadId;
    div.className = TabPaneConfig.tabHeadClass;
    
    r.appendChild(div);
    this.head = div;
    
    var ul = document.createElement("ul");
    div.appendChild(ul);
    
    //create body
    div = document.createElement("div");
    div.id = TabPaneConfig.tabBodyId;
    div.className = TabPaneConfig.tabBodyClass;
    
    r.appendChild(div);    
    this.body = div;
}


TabPane.prototype.addTabPage = function(obj){
    if(!document.getElementById(obj.panel)) return;
    if(!this.tabPages)  this.init();    
        
    this.head.firstChild.appendChild(this.createTabTitle(obj));
    this.body.appendChild(document.getElementById(obj.panel));    
    this.tabPages++;
}

TabPane.prototype.createTabTitle = function(obj){
    var li = document.createElement("li");
    li.id = TabPaneConfig.getId();    
    li.data = obj.panel;
    li.onclick=tabOnClick;
    li.style.width = obj.width;
    
    if(this.tabPages){
        li.className="";
        document.getElementById(obj.panel).style.display="none";    
    }else{
        li.className="hover";
        document.getElementById(obj.panel).style.display="block";    
    }
        
    var textNode = document.createTextNode(obj.title);
    li.appendChild(textNode);
    
    return li;
}

function tabOnClick(){    
  var event = window.event || arguments[0];
  var element = event.srcElement || event.target;  
    var liArr = element.parentNode.getElementsByTagName("li");
    for(var i=0; i<liArr.length; i++){
        liArr[i].className="";
        document.getElementById(liArr[i].data).style.display="none";
    }
    element.className="hover";    
    document.getElementById(element.data).style.display="block";
    window.parent.report.location = "about:blank";
}
