package com.anjoyo.xyl.run.util;
import java.util.HashMap;
import java.util.Map;

import com.amap.api.maps.model.LatLng;

public class Converter {
	public static Double PI = 3.14159265358979324;
	public static Double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	
	public static Map<String,Double> delta(Double lat, Double lon){
		Double a = 6378245.0; //  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
        Double ee = 0.00669342162296594323; //  ee: 椭球的偏心率。
        Double dLat = Converter.transformLat(lon - 105.0, lat - 35.0);
        Double dLon = Converter.transformLon(lon - 105.0, lat - 35.0);
        Double radLat = lat / 180.0 * Converter.PI;
        Double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        Double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Converter.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Converter.PI);
        Map<String,Double> map = new HashMap<String,Double>();
        map.put("lat", dLat);
        map.put("lon", dLon);
        return map;//{'lat': dLat, 'lon': dLon};
	}
	
	public static LatLng gcj_encrypt(double wgsLat, double wgsLon){
		//WGS-84 to GCJ-02
		if (Converter.outOfChina(wgsLat, wgsLon))
            return new LatLng(wgsLat, wgsLon);
		
        Map<String,Double> d = Converter.delta(wgsLat, wgsLon);
        return new LatLng(d.get("lat")+wgsLat, d.get("lon")+wgsLon);
	}
	
	public static Map<String,Double> gcj_decrypt(Double gcjLat, Double gcjLon){
		//GCJ-02 to WGS-84
		Map<String,Double> map = new HashMap<String,Double>();
        map.put("lat", gcjLat);
        map.put("lon", gcjLon);
		if (Converter.outOfChina(gcjLat, gcjLon))
            return map;
         
		 Map<String,Double> d = Converter.delta(gcjLat, gcjLon);
	        map.put("lat", gcjLat-d.get("lat"));
	        map.put("lon", gcjLon-d.get("lon"));
        return map;
	}
//	
//	public static Map<String,Double> gcj_decrypt_exact(Double gcjLat, Double gcjLon){
//		 //GCJ-02 to WGS-84 exactly
//		Double initDelta = 0.01;
//		Double threshold = 0.000000001;
//		Double dLat = initDelta, dLon = initDelta;
//		Double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
//		Double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
//		Double wgsLat, wgsLon, i = 0.0;
//        while (true) {
//            wgsLat = (mLat + pLat) / 2;
//            wgsLon = (mLon + pLon) / 2;
//            Map<String,Double> tmp = Converter.gcj_encrypt(wgsLat, wgsLon);
//            dLat = tmp.get("lat") - gcjLat;
//            dLon = tmp.get("lon") - gcjLon;
//            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
//                break;
// 
//            if (dLat > 0) pLat = wgsLat; else mLat = wgsLat;
//            if (dLon > 0) pLon = wgsLon; else mLon = wgsLon;
// 
//            if (++i > 10000) break;
//        }
//        Map<String,Double> map = new HashMap<String,Double>();
//        map.put("lat", wgsLat);
//        map.put("lon", wgsLon);
//        //console.log(i);
//        return map;
//	}
	
	public static Map<String,Double> bd_encrypt(Double gcjLat, Double gcjLon){
		//GCJ-02 to BD-09
		Map<String,Double> map = new HashMap<String,Double>();
		Double x = gcjLon, y = gcjLat;  
		Double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Converter.x_pi);  
		Double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Converter.x_pi);  
		Double bdLon = z * Math.cos(theta) + 0.0065;  
		Double bdLat = z * Math.sin(theta) + 0.006; 
		map.put("lat", bdLat);
		map.put("lon", bdLon);
	    return map;
	}
	
	public static Map<String,Double> bd_decrypt(Double bdLat, Double bdLon){
		////BD-09 to GCJ-02
		Map<String,Double> map = new HashMap<String,Double>();
		Double x = bdLon - 0.0065, y = bdLat - 0.006;  
        Double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Converter.x_pi);  
        Double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Converter.x_pi);  
        Double gcjLon = z * Math.cos(theta);  
        Double gcjLat = z * Math.sin(theta);
		map.put("lat", gcjLat);
		map.put("lon", gcjLon);
        return map;
	}
	
	public static Map<String,Double> mercator_encrypt(Double wgsLat, Double wgsLon){
		//WGS-84 to Web mercator
	    //mercatorLat -> y mercatorLon -> x
		Map<String,Double> map = new HashMap<String,Double>();
		Double x = wgsLon * 20037508.34 / 180.;
        Double y = Math.log(Math.tan((90. + wgsLat) * Converter.PI / 360.)) / (Converter.PI / 180.);
        y = y * 20037508.34 / 180.;
		map.put("lat", y);
		map.put("lon", x);
        return map;
	}
	
	public static Map<String,Double> mercator_decrypt(Double mercatorLat, Double mercatorLon){
		//WGS-84 to Web mercator
	    //mercatorLat -> y mercatorLon -> x
		Map<String,Double> map = new HashMap<String,Double>();
		Double x = mercatorLon / 20037508.34 * 180.;
	    Double y = mercatorLat / 20037508.34 * 180.;
	    y = 180 / Converter.PI * (2 * Math.atan(Math.exp(y * Converter.PI / 180.)) - Converter.PI / 2);
		map.put("lat", y);
		map.put("lon", x);
        return map;
	}
	
	//两点距离
	public static Double distance(Double latA, Double lonA, Double latB, Double lonB){
		Double earthR = 6371000.0;
        Double x = Math.cos(latA * Converter.PI / 180.) * Math.cos(latB * Converter.PI / 180.) * Math.cos((lonA - lonB) * Converter.PI / 180);
        Double y = Math.sin(latA * Converter.PI / 180.) * Math.sin(latB * Converter.PI / 180.);
        Double s = x + y;
        if (s > 1) s = 1.0;
        if (s < -1) s = -1.0;
        Double alpha = Math.acos(s);
        Double distance = alpha * earthR;
        return distance;
	}
	
	public static Boolean outOfChina(Double lat, Double lon){
		if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
	}
	
	public static Double transformLat(Double x,Double y){
		Double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Converter.PI) + 20.0 * Math.sin(2.0 * x * Converter.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Converter.PI) + 40.0 * Math.sin(y / 3.0 * Converter.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Converter.PI) + 320 * Math.sin(y * Converter.PI / 30.0)) * 2.0 / 3.0;
        return ret;
	}
	
	public static Double transformLon(Double x,Double y){
		Double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Converter.PI) + 20.0 * Math.sin(2.0 * x * Converter.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Converter.PI) + 40.0 * Math.sin(x / 3.0 * Converter.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Converter.PI) + 300.0 * Math.sin(x / 30.0 * Converter.PI)) * 2.0 / 3.0;
        return ret;
	}
	
//	public static void main(String[] args){
//		System.out.println("GPS: 40.095713333333336,116.28087");
//		LatLng arr2 = Converter.gcj_encrypt(40.095713333333336, 116.28087);
//		System.out.println("中国:" + arr2.getLat()+","+arr2.getLon());
//		Map<String,Double> arr3 = Converter.gcj_decrypt(arr2.getLat(), arr2.getLon());
//		System.out.println("逆算:" + arr3.get("lat")+","+arr3.get("lon")+"需要和第一行相似（目前是小数点后9位相等)");
//	}
	
}

