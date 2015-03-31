$(document).ready(function() {
	
	mapInit($("#longitude").val(),$("#latitude").val());
});

function mapInit(lng,lat){
	var marker;
	var initLng = 120.62542;
	var initLat = 31.307888;
	if(lng&&lat){
		initLng = lng;
		initLat = lat;
	}
    var mapObj = new AMap.Map("iCenter",{
    	level:13,
    	center:new AMap.LngLat(initLng,initLat)
    	});  
    mapObj.plugin(["AMap.ToolBar"],function(){        
        toolBar = new AMap.ToolBar();  
        mapObj.addControl(toolBar);       
    });
    
    if(lng&&lat){
    	marker=new AMap.Marker({                    
    	    icon:"http://webapi.amap.com/images/marker_sprite.png",  
    	    position:new AMap.LngLat(lng,lat)  
    	    });  
    	marker.setMap(mapObj);
    }
    var clickEventListener=AMap.event.addListener(mapObj,'click',function(e){
    	if(marker){
    		marker.setMap(null);
    	}
    	marker=new AMap.Marker({                    
    	    icon:"http://webapi.amap.com/images/marker_sprite.png",  
    	    position:new AMap.LngLat(e.lnglat.getLng(),e.lnglat.getLat())  
    	    });  
    	marker.setMap(mapObj);
    	$("#longitude").val(e.lnglat.getLng());
    	$("#latitude").val(e.lnglat.getLat());
    }); 
    
    
}

