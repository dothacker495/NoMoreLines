package com.example.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class JsonParsing {

	GeoPoint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd,adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv324234325sdfsgd;URI adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423;List<GeoPoint> slkdfjasdsdjfhdsljkfhjkvchxjhvoihkdsjfhjaklsdgjklasvhdsjgfasduchjvgahgdskgcxgvfaewhkgrakljgdsvjajklsdglewrhjavhdsjkfhsajvbcjkvghjwkefhejkrh324jfkdghjkaeth34j56jerhdfwe6j54654,firstPoint;MapView adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242;
	
	
		public synchronized List<GeoPoint> parsing(GeoPoint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd1, GeoPoint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd, MapView adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242) throws ClientProtocolException, IOException, JSONException, URISyntaxException{
			HttpClient adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312 = new DefaultHttpClient();
			StringBuilder adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31 = new StringBuilder();
			adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31.append("https://maps.googleapis.com/maps/api/directions/json?origin=")
			.append(Double.toString(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd1.getLatitudeE6()/1E6)).append(",").append(Double.toString(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sdfsgd1.getLongitudeE6()/1E6)).append("&destination=")
			.append(Double.toString(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd.getLatitudeE6()/1E6)).append(",").append(Double.toString(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124234325sd.getLongitudeE6()/1E6))
			.append("&sensor=false&alternatives=true");
			//urlstring.append("http://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&sensor=true");
			adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423 = new URI(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31.toString());
			
			HttpPost adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3 = new HttpPost(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423);
			
			HttpResponse adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312.execute(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3);
			HttpEntity adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghc = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv.getEntity();
			InputStream adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvgh = null;
			adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvgh = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghc.getContent();
			BufferedReader adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvg = new BufferedReader(new InputStreamReader(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvgh, "iso-8859-1"), 8);
			StringBuilder adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghv = new StringBuilder();
			adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghv.append(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvg.readLine() + "\n");
			String adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfgh = "0";
			while ((adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfgh = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvg.readLine()) != null) {
			    adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghv.append(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfgh + "\n");
			}
			adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvgh.close();
			adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvg.close();
			String adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfg = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghv.toString();
			JSONObject adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsf = new JSONObject(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfg);
			JSONArray adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhds = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsf.getJSONArray("routes");
			JSONObject adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhd = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhds.getJSONObject(0);
			JSONObject adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewh = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhd.getJSONObject("overview_polyline");
			String adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghew = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewh.getString("points");
		    slkdfjasdsdjfhdsljkfhjkvchxjhvoihkdsjfhjaklsdgjklasvhdsjgfasduchjvgahgdskgcxgvfaewhkgrakljgdsvjajklsdglewrhjavhdsjkfhsajvbcjkvghjwkefhejkrh324jfkdghjkaeth34j56jerhdfwe6j54654 = decodePoly(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghew);

			adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242.getOverlays().add(new RoutePathOverlay(slkdfjasdsdjfhdsljkfhjkvchxjhvoihkdsjfhjaklsdgjklasvhdsjgfasduchjvgahgdskgcxgvfaewhkgrakljgdsvjajklsdglewrhjavhdsjkfhsajvbcjkvghjwkefhejkrh324jfkdghjkaeth34j56jerhdfwe6j54654));
			return slkdfjasdsdjfhdsljkfhjkvchxjhvoihkdsjfhjaklsdgjklasvhdsjgfasduchjvgahgdskgcxgvfaewhkgrakljgdsvjajklsdglewrhjavhdsjkfhsajvbcjkvghjwkefhejkrh324jfkdghjkaeth34j56jerhdfwe6j54654;
		}
		
		
		
		//conver points into GeoPoints
		private synchronized List<GeoPoint> decodePoly(String adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghe) {

		    List<GeoPoint> adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrgh = new ArrayList<GeoPoint>();int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrg = 0, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewr = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghe.length();int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgew = 0, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewz = 0;

		    while (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrg < adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewr) {
		        int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewx, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewc = 0, adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv = 0;
		        do {
		            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewx = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghe.charAt(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrg++) - 63;
		            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv |= (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewx & 0x1f) << adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewc;
		            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewc += 5;
		        } while (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewx >= 0x20);
		        int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshge = ((adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv & 1) != 0 ? ~(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv >> 1) : (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv >> 1));
		        adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgew += adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshge;

		        adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewc = 0;
		        adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv = 0;
		        do {
		            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewx = adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghe.charAt(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrg++) - 63;
		            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv |= (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewx & 0x1f) << adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewc;
		            adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewc += 5;
		        } while (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewx >= 0x20);
		        int adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshg = ((adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv & 1) != 0 ? ~(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv >> 1) : (adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewv >> 1));
		        adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewz += adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshg;
		        GeoPoint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghXZS = new GeoPoint((int) ((adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgew / 1E5) * 1E6), (int) ((adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewz / 1E5) * 1E6));
		        adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrgh.add(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghXZS);
		    }
		    return adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrgh;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public synchronized static ArrayList getDirections(GeoPoint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdsh, GeoPoint adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgds) {
			
			        String adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423 = "http://maps.googleapis.com/maps/api/directions/xml?origin="+adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdsh.getLatitudeE6()/1E6 + "," + adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdsh.getLongitudeE6()/1E6  + "&destination=" + adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgds.getLatitudeE6()/1E6+ "," + adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgds.getLongitudeE6()/1E6 + "&sensor=false&units=metric";String  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242[] = { "lat", "lng" };ArrayList  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124 = new ArrayList();HttpResponse  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312 = null;
			        try {
			            HttpClient  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv = new DefaultHttpClient();
			            HttpContext localContext = new BasicHttpContext();
			            HttpPost httpPost = new HttpPost(adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312423);
			             adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312 =  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv.execute(httpPost, localContext);
			            InputStream  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31 =  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv312.getEntity().getContent();
			            DocumentBuilder  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			            Document doc =  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3.parse( adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31);
			            if (doc != null) {
			                NodeList nl1, nl2;
			                nl1 = doc.getElementsByTagName( adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242[0]);
			                nl2 = doc.getElementsByTagName( adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv31242[1]);
			                if (nl1.getLength() > 0) {
			                     adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124 = new ArrayList();
			                    for (int i = 0; i < nl1.getLength(); i++) {
			                        Node node1 = nl1.item(i);
			                        Node node2 = nl2.item(i);
			                        double lat = Double.parseDouble(node1.getTextContent());
			                        double lng = Double.parseDouble(node2.getTextContent());
			                         adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124.add(new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)));
			                    }
			                } else {
			                    // No points found
			                }
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			        return  adsfhgasdfgadyewyewggdasghdghcvghchcccxhcxhcxvghcxvghdshgdshgewrghewhdsfghvghcv3124;
			    }
	
}
