<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<title>画图</title>
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/map_sf.js"></script>
</head>

<body>
  <canvas id = "myCanvas" width = '2000' height = '1000'>Canvas画线技巧</canvas>
<script>
	var storage = window.localStorage;
    var myCanvas = document.getElementById("myCanvas");
    var context =  myCanvas.getContext("2d");
	var plateMap={};
	var maxminPoint={};
	
	   function initMaxminPoint(){
			
			min_x = maxminPoint.min_x;
			min_y = maxminPoint.min_y;
			max_x = maxminPoint.max_x;
			max_y = maxminPoint.max_y;
		}
      
        function drawPath(point,list,plate)
        {
        		//context.clearRect(0, 0, 2000, 1000);//清理画布
                initMaxminPoint();//初始化最大值最小值
                
				mapCenter();//地图居中
                context.fillStyle ='rgba(255,0,0,.3)';//填充红色，半透明
                context.strokeStyle ='green';//填充绿色
                context.lineWidth = 1;//设置线宽
                context.beginPath();
			
                for(i = 0;i < list.length; i ++)
                {
                    var p = list[i];
					var screenPoint = getMapPoint(p.x,p.y);
				   if ( i== 0) {
					 // drawCircle(screenPoint.put_x,screenPoint.put_y);
					  context.moveTo(screenPoint.put_x, screenPoint.put_y);
					} else {
						context.lineTo(screenPoint.put_x, screenPoint.put_y);
				   }
					
                }
                context.closePath();
                context.stroke();
                context.fill();
                context.restore();//返回原始状态
				var screenPoint2 = getMapPoint(point.x,point.y);
				if(context.isPointInPath(screenPoint2.put_x,screenPoint2.put_y)){
					if(!plateMap[plate]){
						plateMap[plate]=[];
						plateMap[plate].push(point.id);
					}else{
						plateMap[plate].push(point.id);
					}
					//alert(point.id);
					return true;
				}
				//drawCircle(screenPoint1.put_x,screenPoint1.put_y);
        }
        
		function drawCircle(x,y){
			//开始一个新的绘制路径
			context.beginPath();
			//设置弧线的颜色为蓝色
			context.strokeStyle = "blue";
			var circle = {
				x : x,    //圆心的x轴坐标值
				y : y,    //圆心的y轴坐标值
				r : 5      //圆的半径
			};
			//沿着坐标点(100,100)为圆心、半径为50px的圆的顺时针方向绘制弧线
			context.arc(circle.x, circle.y, circle.r, 0, Math.PI*2, false);    
			//按照指定的路径绘制弧线
			context.stroke();
		}
		
		

		//var list = [{"y":121.496685,"x":31.298387},{"y":121.495678,"x":31.297785},{"y":121.494681,"x":31.296898},{"y":121.493334,"x":31.295756},{"y":121.492112,"x":31.294676},{"y":121.491501,"x":31.293997},{"y":121.490998,"x":31.293064},{"y":121.490684,"x":31.292262},{"y":121.490316,"x":31.291151},{"y":121.489965,"x":31.290472},{"y":121.489714,"x":31.2896},{"y":121.489579,"x":31.288875},{"y":121.489525,"x":31.287586},{"y":121.489444,"x":31.28683},{"y":121.489354,"x":31.28592},{"y":121.489211,"x":31.28538},{"y":121.48886,"x":31.284894},{"y":121.488438,"x":31.284423},{"y":121.487899,"x":31.284115},{"y":121.487046,"x":31.283729},{"y":121.486336,"x":31.283343},{"y":121.485725,"x":31.282965},{"y":121.48542,"x":31.282633},{"y":121.485141,"x":31.282209},{"y":121.484926,"x":31.281677},{"y":121.485096,"x":31.281607},{"y":121.485797,"x":31.28136},{"y":121.486902,"x":31.28099},{"y":121.487764,"x":31.280836},{"y":121.488591,"x":31.280759},{"y":121.489238,"x":31.280774},{"y":121.489597,"x":31.280836},{"y":121.490217,"x":31.280928},{"y":121.490738,"x":31.281121},{"y":121.491654,"x":31.281584},{"y":121.492176,"x":31.281836},{"y":121.492508,"x":31.281999},{"y":121.492975,"x":31.28213},{"y":121.493456,"x":31.282242},{"y":121.493833,"x":31.282334},{"y":121.494372,"x":31.282342},{"y":121.494942,"x":31.282342},{"y":121.495598,"x":31.282288},{"y":121.496038,"x":31.282249},{"y":121.496595,"x":31.282211},{"y":121.497062,"x":31.282176},{"y":121.497071,"x":31.280837},{"y":121.497103,"x":31.280394},{"y":121.497377,"x":31.279892},{"y":121.498109,"x":31.278631},{"y":121.498845,"x":31.277385},{"y":121.499299,"x":31.276582},{"y":121.500018,"x":31.275402},{"y":121.500391,"x":31.274842},{"y":121.500638,"x":31.274456},{"y":121.500718,"x":31.274341},{"y":121.500754,"x":31.274341},{"y":121.500709,"x":31.274321},{"y":121.500754,"x":31.274356},{"y":121.500777,"x":31.274237},{"y":121.500745,"x":31.274229},{"y":121.5007,"x":31.274337},{"y":121.500723,"x":31.27426},{"y":121.500745,"x":31.274275},{"y":121.500714,"x":31.274298},{"y":121.500705,"x":31.274194},{"y":121.500777,"x":31.274206},{"y":121.500826,"x":31.274202},{"y":121.500826,"x":31.274154},{"y":121.502254,"x":31.275396},{"y":121.503413,"x":31.276438},{"y":121.504913,"x":31.277842},{"y":121.505641,"x":31.278521},{"y":121.50697,"x":31.27964},{"y":121.507383,"x":31.279956},{"y":121.507819,"x":31.280415},{"y":121.50724,"x":31.280889},{"y":121.506831,"x":31.281217},{"y":121.506431,"x":31.281484},{"y":121.505902,"x":31.281669},{"y":121.505412,"x":31.281796},{"y":121.504797,"x":31.281873},{"y":121.504469,"x":31.281908},{"y":121.503719,"x":31.281908},{"y":121.503054,"x":31.281908},{"y":121.503045,"x":31.281958},{"y":121.503045,"x":31.281997},{"y":121.503036,"x":31.282367},{"y":121.503036,"x":31.28283},{"y":121.502987,"x":31.283794},{"y":121.50287,"x":31.285137},{"y":121.502825,"x":31.285588},{"y":121.50269,"x":31.287015},{"y":121.502564,"x":31.288462},{"y":121.502618,"x":31.289237},{"y":121.502762,"x":31.289565},{"y":121.502955,"x":31.290028},{"y":121.503067,"x":31.29029},{"y":121.50349,"x":31.290834},{"y":121.503665,"x":31.291089},{"y":121.503993,"x":31.291544},{"y":121.504541,"x":31.291902},{"y":121.50521,"x":31.292385},{"y":121.506086,"x":31.292975},{"y":121.506535,"x":31.293276},{"y":121.507137,"x":31.293692},{"y":121.507837,"x":31.29429},{"y":121.508848,"x":31.295177},{"y":121.508372,"x":31.295806},{"y":121.507842,"x":31.296516},{"y":121.507316,"x":31.297252},{"y":121.50706,"x":31.297626},{"y":121.507069,"x":31.298683},{"y":121.506921,"x":31.299135},{"y":121.506813,"x":31.299435},{"y":121.506598,"x":31.299416},{"y":121.50649,"x":31.29964},{"y":121.506396,"x":31.29991},{"y":121.506158,"x":31.300481},{"y":121.506072,"x":31.300762},{"y":121.505677,"x":31.300677},{"y":121.505452,"x":31.300623},{"y":121.505075,"x":31.300554},{"y":121.50476,"x":31.300477},{"y":121.504824,"x":31.300461},{"y":121.503287,"x":31.30013},{"y":121.502254,"x":31.299867},{"y":121.500072,"x":31.299304},{"y":121.496667,"x":31.298379}]
		
		
		/* for(j=0;j<10; j++){
			if(!storage[j])storage[j]="";
			debugger;
			drawPath(31.298387,121.496685, list);
			console.log("====================="+j);
			debugger;
			if(storage[j]){
				storage[j]=storage[j]+","+j
			}else{
				storage[j]=storage[j]+j
			}
		} */
		
	
		$.ajax({ 
		     type: "get", 
				url: 'mapData?loc_type='+3, 
				dataType: "json", 
				success: function (data) {
					if(data.success){
						debugger;
						maxminPoint = data.context.maxPoint;
						var points = data.context.points;
						var mapData = data.context.mapData;
						for(j=0;j<points.length; j++){
							var point = points[j];
							context.clearRect(0, 0, 2000, 1000);//清理画布
							$.each(mapData, function(key, value) { 
								var plate = key;
								var list = value;
								var flag = drawPath(point,list,plate);
								if(flag){
									return false;
								}
							}); 
						}
						alert("判断完成");
						$.each(plateMap, function(key, value) { 
							console.log(key+":"+value);
						}); 
						alert("输出完成");
					}
				}, 
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
						   
				} 
		});
		
        
</script>
</body>
</html>