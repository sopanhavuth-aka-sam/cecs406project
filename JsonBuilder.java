import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class JsonBuilder
{
	
	public static final String XML_PATH = "/Users/drewstiles/Developer/Web/NameVis/rows.xml";
	
	public static final String JSON_PATH = "/Users/drewstiles/Developer/Web/NameVis/out.json";

	public JsonBuilder()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	public static void main(String[] args) {
		
		try {
			
			// Map each name to the number of unique rows that contain it for the nm element.
			Multimap<String, Element> map = buildNameMap(new File(XML_PATH));
			
			// Build a flat ordering of each unique row in the input XML file.
			Set<Element> sampleSet = buildSampleSetFromMap(map);
			 
			// Create an hierarchicly ordered by ethnicity JSON object.
			JSONObject json = buildJSON(sampleSet);
			
			// Write JSON to file.
			PrintWriter pw = new PrintWriter(JSON_PATH);
			pw.print(json);
			pw.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private static JSONObject buildJSON(Set<Element> set) {
		
		List<Element> asians = new ArrayList<Element>();
		List<Element> blacks = new ArrayList<Element>();
		List<Element> hispanics = new ArrayList<Element>();
		List<Element> whites = new ArrayList<Element>();
		
		Iterator<Element> samples = set.iterator();
		while(samples.hasNext()) {
			Element e = samples.next();
			String ethnicity = e.getElementsByTagName("ethcty").item(0).getTextContent();
			
			switch(ethnicity) {
				case "ASIAN AND PACIFIC ISLANDER":
					asians.add(e);
					break;
				case "BLACK NON HISPANIC":
					blacks.add(e);
					break;
				case "HISPANIC":
					hispanics.add(e);
					break;
				case "WHITE NON HISPANIC":
					whites.add(e);
					break;
				default:
					throw new RuntimeException("Unknown ethnicity encountered: " + ethnicity);
			}
		}
		
		// Building output JSON 
		
		JSONObject asian = new JSONObject();
		asian.put("name", "asians");
		asian.put("children", getJSONArrayForEthnicity(asians));
		
		JSONObject black = new JSONObject();
		black.put("name", "blacks");
		black.put("children", getJSONArrayForEthnicity(blacks));
		
		JSONObject hispanic = new JSONObject();
		hispanic.put("name", "hispanics");
		hispanic.put("children", getJSONArrayForEthnicity(hispanics));
		
		JSONObject white = new JSONObject();
		white.put("name", "whites");
		white.put("children", getJSONArrayForEthnicity(whites));
		
		JSONObject root = new JSONObject();
		JSONArray ethnicities = new JSONArray();
		ethnicities.add(asian);
		ethnicities.add(black);
		ethnicities.add(hispanic);
		ethnicities.add(white);
		
		root.put("name", "root");
		root.put("children", ethnicities);
		
		return root;
	}
	
	
	private static Multimap<String, Element> buildNameMap(File xml) throws Exception {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xml);
		
		// optional, but recommended
		// read here - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		
		
		NodeList nList = doc.getElementsByTagName("row");
		
		Multimap<String, Element> mm = ArrayListMultimap.create();
		
		for (int i = 0; i < nList.getLength(); i++) {
			
			Node nNode = nList.item(i);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element eElement = (Element)nNode;
				
				Node nameNode = eElement.getElementsByTagName("nm").item(0);
				
				String elementName = nameNode.getTextContent();
				
				if (mm.containsKey(elementName)) {
					
					// Set when a hashed Element matches this element.
					boolean matched = false;
					
					// The Elements already hashed to by this element's name.
					Collection<Element> elements = mm.get(elementName);
					
					for (Element otherElement : elements) {
						
						boolean birthYearsAreEqual = elementsAreEqual(eElement, otherElement, "brth_yr");
						boolean gendersAreEqual = elementsAreEqual(eElement, otherElement, "gndr");
						boolean ethnicitiesAreEqual = elementsAreEqual(eElement, otherElement, "ethcty");
						boolean namesAreEqual = elementsAreEqual(eElement, otherElement, "nm");
						boolean countsAreEqual = elementsAreEqual(eElement, otherElement, "cnt");
						boolean ranksAreEqual = elementsAreEqual(eElement, otherElement, "rnk");
						
						if (birthYearsAreEqual
								&& gendersAreEqual
								&& ethnicitiesAreEqual
								&& namesAreEqual
								&& countsAreEqual
								&& ranksAreEqual) {
							
							// Elements are duplicate data.
							matched = true;
							
							// Exit loop since a match Element was found.
							break;
						}
						else {
							
							// There is a discrepency in some child element of the row.
							matched = false;
							
							continue; // checking for some discrepancy
							
						}
					} // End collision comparison for loop
					
					if (!matched) {
						// Collision loop completed without finding a match for this Element.
						mm.put(elementName, eElement);
					}
					else {
						// The element already exists within the multimap.
					}
					
				}
				else {
					// Collection does not contain any values for this name.
					mm.put(elementName, eElement);
				}
			}
		} // End iteration over all row elements.
		
		return mm;
	}
	
	
	private static Set<Element> buildSampleSetFromMap(Multimap<String, Element> map) {
		
		Set<Element> sampleSet = new HashSet<Element>();
		
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			Collection<Element> elements = map.get(keys.next());
			for (Element e : elements) {
				
				
				// only add the top 10 for each category
				if (Integer.parseInt(e.getElementsByTagName("rnk").item(0).getTextContent()) > 25) { 
					continue;
				}
				else { 
					sampleSet.add(e);
				}
			}
		}
		
		return sampleSet;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray getJSONArrayForEthnicity(List<Element> ethnicityList) {
		
		JSONArray ethnicityArray = new JSONArray();
		
		for (int i = 0; i < ethnicityList.size(); i++) {
			
			Element e = ethnicityList.get(i);
			
			String name = e.getElementsByTagName("nm").item(0).getTextContent();
			String gndr = e.getElementsByTagName("gndr").item(0).getTextContent();
			String ethcty = e.getElementsByTagName("ethcty").item(0).getTextContent();
			Integer cnt = new Integer(e.getElementsByTagName("cnt").item(0).getTextContent());
			
			JSONObject sample = new JSONObject();
			
			sample.put("name", name);
			sample.put("gndr", gndr);
			sample.put("ethcty", ethcty);
			sample.put("cnt", cnt);
			
			ethnicityArray.add(sample);
		}
		
		return ethnicityArray;
	}
	
	private static boolean elementsAreEqual(Element e1, Element e2, String eName) {
		String v1 = e1.getElementsByTagName(eName).item(0).getTextContent();
		String v2 = e2.getElementsByTagName(eName).item(0).getTextContent();
		return v1.equals(v2);
	}
}


