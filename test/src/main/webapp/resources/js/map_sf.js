//常量对象存在的图标
var iconList=["25015","25065","25118","25136","25020","25075","25129","25140","25025","25081","25133","25142","25029","25110","25134","25031","25115","25135"];
//地图偏移量
var mapOffset={"99907700001":2000,"H85200001":2000,"31010000001":200,"002100002":200,"31010000002":200,"31010000004":200
		,"31010000006":2000,"31010000009":1000,"31010000010":200,"99907700002":200};
//把地图倒过来参数
var fg = 1;
var fg_y = -1;
///////////////////////地图算法////////////////////////////////////////
//********图幅控制变量，不用赋初值
//屏幕大小
var screenX=$(window).width();
var screenY=$(window).height();

//屏幕中心点
var screen_src_x=screenX/2;
var screen_src_y=screenY/2;

var screen_x=screen_src_x;
var screen_y=screen_src_y;
//画布的宽
var canvas_w = screenX;
//画布的高
var canvas_h = screenY;
//地图最小值
var min_x=0;
var min_y=0;
//地图最大值
var max_x=0;
var max_y=0;
//初始化比例
var def_scale=1;
var tmpScale=1; //缩放比例（地图与屏幕的比例饿）
var tmpCorner_x=0; //地图在Div左上角坐标X（屏幕中心点和地图在屏幕中心点之间的差）
var tmpCorner_y=0; //地图在Div左上角坐标Y
//var tmpCert_x;   //转动中心X
//var tmpCert_y;   //转动中心Y
var tmpDivScale_x=1; //Div与屏幕x比例
var tmpDivScale_y=1; //Div与屏幕y比例

//********地图坐标到屏幕坐标
//输入：地图坐标
//返回：屏幕坐标
function getMapPoint(map_x,map_y)
{	
	var put_x;
	var put_y;

    var Div=maptoDiv(map_x,map_y); //先转Div坐标
    var Screen=divtoScreen(Div.div_x,Div.div_y); //在转屏幕坐标        
	
        put_x=Screen.screenX;
        put_y=Screen.screenY;
	return {put_x:put_x,put_y:put_y}
}

//**********地图缩放，
//x,y 鼠标点，scale 缩放系数，<1缩小，>1放大

function ScaleMap(x,y,scale)
{
       
       var cert=divtoMap(x,y);      
       tmpScale *=scale;
       y=canvas_h-y;
       tmpCorner_x=x-(cert.map_x-min_x)*tmpScale;
       tmpCorner_y=y-(cert.map_y-min_y)*tmpScale;    
}


//********地图居中
function mapCenter()
{
	
	tmpCorner_x=0;
	tmpCorner_y=0;
    var dx=max_x-min_x;
    var dy=max_y-min_y;

   //** 计算初始缩放比例
    tmpScale=screenX/dx; 
    if (screenY/dy<tmpScale) tmpScale=screenY/dy; //高度比小时以高度为缩放基准
    def_scale=tmpScale;
    //****计算初始转动中心
    //tempCert_x=(max_x+min_x)/2.0;
    //tempCert_y=(max_y+min_y)/2.0;

    //****计算Div与屏幕x比例
     tmpDivScale_x=canvas_w/screenX;
     tmpDivScale_y=canvas_h/screenY;

     //*****居中,主要计算地图在Div左下角坐标，放在屏幕中央
           
      var upCorner=maptoDiv(min_x,min_y);              //最小点Div位置
      var downCorner=maptoDiv(max_x,max_y);             //最大点Div位置
      tmpCorner_x=canvas_w*0.5-(upCorner.div_x+downCorner.div_x)*0.5;   //DivX居中
      tmpCorner_y=canvas_h*0.5-(upCorner.div_y+downCorner.div_y)*0.5;   //DivY居中
//      tmpCorner_y=0.0 - tmpCorner_y;                     //上下反向
}

//*******屏幕坐标到地图坐标
//输入：屏幕坐标
//返回：地图坐标
function screentoMap(screen_x,screen_y)
{
var div=screentoDiv(screen_x,screen_y);
var map=divtoMap(div.div_x,canvas_h-div.div_y);
//var map=divtoMap(div.div_x,div.div_y);
var map_x=map.map_x;
var map_y=map.map_y;
return {map_x:map_x,map_y:map_y};
}

function screentoDiv(screen_x,screen_y)
{
   var div_x;
   var div_y;
   div_x=canvas_w/tmpDivScale_x*(tmpDivScale_x-1.0)*0.5+screen_x;
   div_y=canvas_h/tmpDivScale_y*(tmpDivScale_y-1.0)*0.5+screen_y;
   return {div_x:div_x,div_y:div_y};
}

//*******重算地图坐标，使屏幕放置在DIV中央
//输入：离上次屏幕坐标重算的偏移量 off_x,off_y,
//    离上次屏幕坐标缩放坐标点 scale_x,scale_y,
//     缩放比例 scale
//      若没有缩放  传入 scale=1，  scale_x=0,scale_y=0,
//      若没有平移  传入 off_x=0,   off_y=0


function recalcDraw(off_x,off_y,scale_x,scale_y,scale)
{
	tmpCorner_x += off_x;
	tmpCorner_y += off_y;
	var div=screentoDiv(scale_x.scale_y);	
//	ScaleMap(div.div_x,div.div_y,scale);	
}
//*********************中间函数，前面函数需调用
//地图坐标到Div坐标
function maptoDiv(map_x,map_y)
{
      var div_x;
      var div_y;
      div_x=(map_x-min_x)*tmpScale+tmpCorner_x;
      div_y=(map_y-min_y)*tmpScale+tmpCorner_y;
      return {div_x:div_x,div_y:div_y};
}

//Div坐标到屏幕坐标
function divtoScreen(div_x,div_y)
{
     var screenX=(div_x-canvas_w/tmpDivScale_x*(tmpDivScale_x-1.0)*0.5);
     var screenY=(div_y-canvas_h/tmpDivScale_y*(tmpDivScale_y-1.0)*0.5);
     return {screenX:screenX,screenY:screenY};

}

//Div到地图坐标系
function divtoMap(div_x,div_y)
{
     var map_x;
	 var map_y; 
     div_y=canvas_h-div_y;
     map_x=(div_x-tmpCorner_x)/tmpScale+min_x;
     map_y=(div_y-tmpCorner_y)/tmpScale+min_y;
     return {map_x:map_x,map_y:map_y};

};

//地图到屏幕坐标系
function maptoScreen(map_x,map_y)
{
	var div = maptoDiv(map_x,map_y);
	var screen = divtoScreen(div.div_x,div.div_y);
	return {x:screen.screenX,y:screen.screenY};

};
//////////////////////////////////////////////////////////////////////////////////////////////////


//判断地图轮廓是否在屏幕内
//入口：list 地图轮廓列表, maxP 屏幕最大值，
//返回：true,在屏幕内，false 相离
function isRemoveMap(list,maxP)
{
	
//只要有轮廓点落在屏幕内，则肯定相交
	for(var i=0;i<list.length;i++){
		for(var j=0;j<list[i].length;j++){
			var x = list[i][j].x;
			var y = list[i][j].y;
			if ((x>0 && x<maxP.x) && (y>0 && y<maxP.y)) return true;
		}
	}
//再只要有屏幕的四个角点有一点落在轮廓内，就是相交或包含
 var corner1={x:0,y:0};
 var corner2={x:0,y:maxP.y};
 var corner3={x:maxP.x,y:maxP.y};
 var corner4={x:maxP.x,y:0};
 if (isPointInRegn(list,corner1)) return true;
 if (isPointInRegn(list,corner2)) return true;
 if (isPointInRegn(list,corner3)) return true;
 if (isPointInRegn(list,corner4)) return true;
 
 //判断屏幕四条线和地图轮廓是否相交
 for(var i=0;i<list.length;i++){
		for(var j=0;j<list[i].length-1;j++){
			if(LSegInterLSeg(list[i][j],list[i][j+1],corner1,corner2)){
				return true;
			}
			if(LSegInterLSeg(list[i][j],list[i][j+1],corner1,corner4)){
				return true;
			}
			if(LSegInterLSeg(list[i][j],list[i][j+1],corner2,corner3)){
				return true;
			}
			if(LSegInterLSeg(list[i][j],list[i][j+1],corner3,corner4)){
				return true;
			}
		}
	}
 
//相离
 return false;
}

//判断点是否有交点
//入口：y0,坐标y值，p1,p2 线段起始点
//出口：outx,交点x值
//返回：true 有交点
function hascross(y0,p1, p2)
{
	var outx=0;
	var minlen=0.00000001;
	if ((p2.y-p1.y<minlen) && (p2.y-p1.y>-minlen)) return {outx:outx,result:false};
	if ((p2.y-y0)*(y0-p1.y) < 0) return {outx:outx,result:false};
	outx=(p2.x*(y0-p1.y)+p1.x*(p2.y-y0))/(p2.y-p1.y);
	
	return {outx:outx,result:true};
}

//单点是否在多边形内
//利用水平线与多边形的交点数判断是否在面内
//入口：list 多边形坐标列表, p 单点坐标，
//返回：true,在区域内
function isPointInRegn(list,p)
{
    
    var updirect,nowdirect; //方向   
    var p1,p2;
    var outx;
    var inregn=false;

  //计算初始竖向方向，防止坐标
    for(var i=0;i<list.length;i++){
        for(var j=0;j<list[i].length;j++)
        {
        p1=list[i][j];
        if(p.x==p1.x&&p.y==p1.y)
		     return false;
        if(j==list[i].length-1)
            p2=list[i][0];  //取第一个点
         else
            p2=list[i][j+1];//取下一个点

         if((p2.y-p1.y)!=0) updirect=p2.y-p1.y; 
         }
        
        //通过y向的交点数奇偶判断是否
        for(var j=0;j<list[i].length;j++)
             {
              p1=list[i][j];
               if(j==list[i].length-1)
                 p2=list[i][0];  //取第一个点
              else
                 p2=list[i][j+1];//取下一个点
               var result = hascross(p.y,p1,p2);
              if(result.result)
     		 {
                   nowdirect=p2.y-p1.y;
                   if(!(p.y==p1.y&&nowdirect*updirect>=0))
                     {
                       if(result.outx<p.x) inregn=!inregn;
                     }
              }
              if(p2.y-p1.y!=0)  updirect=p2.y-p1.y;
             }
    }

    return inregn;
}

//判断两条线是否相交
function LInterL(pt1,pt2,pt3,pt4)
{
	 var dx21,dy21,dx43,dy43,dd,u,v;
	 dx21=pt2.x-pt1.x;	dy21=pt2.y-pt1.y;
	 dx43=pt4.x-pt3.x;	dy43=pt4.y-pt3.y;

	 dd=(pt3.x-pt4.x)*dy21-(pt3.y-pt4.y)*dx21;

	 //平行或重合时无交点
	 if(dd==0) return false;

	 //直线段参数方程中的参数求取
	 u=(pt3.x-pt1.x)*dy43-(pt3.y-pt1.y)*dx43;	u=u/dd;
	 v=(pt3.x-pt1.x)*dy21-(pt3.y-pt1.y)*dx21;	v=v/dd;

	 //判断是否为有效交点
	 if(u>=0 && u<=1)
	 {
		 return true;
	 }
	 return false;
}

function LSegInterLSeg(pt1,pt2,pt3,
		pt4)
	{
		//利用两条线段的MBR判断是否存在交点
		var min1x,min1y,max1x,max1y;
		var min2x,min2y,max2x,max2y;
		min1x=min(pt1.x,pt2.x);min1y=min(pt1.y,pt2.y);
		max1x=max(pt1.x,pt2.x);max1y=max(pt1.y,pt2.y);
		min2x=min(pt3.x,pt4.x);min2y=min(pt3.y,pt4.y);
		max2x=max(pt3.x,pt4.x);max2y=max(pt3.y,pt4.y);
		if(min1x>max2x) return false;
		if(max1x<min2x) return false;
		if(min1y>max2y) return false;
		if(max1y<min2y) return false;

		var dx21,dy21,dx43,dy43,dd,u,v;

		dx21=pt2.x-pt1.x;	dy21=pt2.y-pt1.y;
		dx43=pt4.x-pt3.x;	dy43=pt4.y-pt3.y;

		dd=(pt3.x-pt4.x)*dy21-(pt3.y-pt4.y)*dx21;

		//平行或重合时无交点
		if(dd==0) return false;

		//直线段参数方程中的参数求取
		u=(pt3.x-pt1.x)*dy43-(pt3.y-pt1.y)*dx43;	u=u/dd;
		v=(pt3.x-pt1.x)*dy21-(pt3.y-pt1.y)*dx21;	v=v/dd;

		//判断是否为有效交点
		if(u>=0 && u<=1 && v>=0 && v<=1)
		{
		
			return true;
		}
		else
		{
		
			return false;
		}
	}
function max(x1,x2){
	return x1>x2?x1:x2;
}

function min(x1,x2){
	return x1<x2?x1:x2;
}

//把地图floor层全部转换成屏幕上的点  
var floorToScreen = function(json_floor){
	var rings = json_floor.features[0].geometry.rings;
	var arr_floor = [];
	$.each(rings,function(i, ring){
		var floor = [];
		$.each(ring,function(i, o){
			var x = o[0]*fg;
			var y = o[1]*fg_y;
			var screen = maptoScreen(x,y);
			floor.push(screen);
		})
		arr_floor.push(floor);
	})
	return arr_floor;
}

//判断图标周围50像素是否有图标
var isNotExist = function(facList,fac,fg_y){
	if(!facList||facList.length==0){
		return true;
	}
	var fac1 = getMapPoint(fac.x,fac.y*fg_y);
	var flag = true;
	$.each(facList,function(i, o){
		var fac2 = getMapPoint(o.x,o.y*fg_y);
		var xdiff_2 = Number(fac2.put_x) - Number(fac1.put_x);
	var ydiff_2 = Number(fac2.put_y) - Number(fac1.put_y);
	if(Math.pow((xdiff_2 * xdiff_2 + ydiff_2 * ydiff_2), 0.5)<80){
		flag = false;
		return true;
	}
});
return flag;
}

//判断一个对象在列表中是否存在
var isExist = function(list,obj){
	var flag =false;
	$.each(list,function(i, o){
		if(o==obj){
			flag = true;
			return true;
		}
	})
	return flag;
}



/** 
 * 时间对象的格式化; 
 */  
Date.prototype.format = function(format) {  
    /* 
     * eg:format="yyyy-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" : this.getMonth() + 1, // month  
        "d+" : this.getDate(), // day  
        "h+" : this.getHours(), // hour  
        "m+" : this.getMinutes(), // minute  
        "s+" : this.getSeconds(), // second  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" : this.getMilliseconds()  
        // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
  
    for (var k in o) {  
        if(new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  


