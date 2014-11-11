package deployr;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Project {
	private List<String> remotes;
	private String folder;
	private String configFile;
	private String projectName;
	/**
	 * Constructs a Project object that represents a deployr-enabled project on disk.
	 * Note that folder must already contain a configured deployr.xml, to create a new 
	 * deployr.xml use Project.init(folder).
	 * 
	 * @param 	folder		the folder on disk at which resides this project	
	 * @return 				the newly created Project object
	 */
	public Project(String folder, String configFile){
		this.folder = folder; 
		this.configFile = configFile;
		File f = new File(folder);
		this.projectName = f.getName();
		this.loadXMLConfig();
		
		
	}
	/**
	 * Creates a new Project, complete with deployr.xml in the folder specified by folder
	 * 
	 * @param 	folder		the folder on disk at which resides this project	
	 * @return 				the newly created Project object
	 */
	public static Project init(String folder){
		try {
			
			File f = new File(folder);
			String projectName = f.getName();
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("deployr");
			doc.appendChild(rootElement);
	 
			// staff elements
			Element site = doc.createElement("site");
			rootElement.appendChild(site);
	 
//			// set attribute to staff element
//			Attr attr = doc.createAttribute("id");
//			attr.setValue("1");
//			staff.setAttributeNode(attr);
	 
			// shorten way
			// staff.setAttribute("id", "1");
	 
			// path elements
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(projectName));
			site.appendChild(name);
			Element path = doc.createElement("path");
			path.appendChild(doc.createTextNode("web"));
			site.appendChild(path);
	 
			// php elements
			Element language = doc.createElement("php");
			language.setAttribute("usecomposer","true");
			site.appendChild(language);
			
			Element remote = doc.createElement("remote");
			
			Element server = doc.createElement("ssh_server");
			server.appendChild(doc.createTextNode("pglf-prod"));
			remote.appendChild(server);
			Element document_root = doc.createElement("document_root");
			document_root.appendChild(doc.createTextNode("/var/www/"+projectName));
			remote.appendChild(document_root);
			rootElement.appendChild(remote);
	 
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(new File(folder, "deployr.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);
	 
			
			System.out.println("File saved!");
	 
			return new Project(folder, "deployr.xml");
					
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		return null;
	}
	private void loadXMLConfig(){
		try {
			 
			File fXmlFile = new File(folder,configFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
//			NodeList nList = doc.getElementsByTagName("staff");
//		 
//			System.out.println("----------------------------");
//		 
//			for (int temp = 0; temp < nList.getLength(); temp++) {
//		 
//				Node nNode = nList.item(temp);
//		 
//				System.out.println("\nCurrent Element :" + nNode.getNodeName());
//		 
//				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//		 
//					Element eElement = (Element) nNode;
//		 
//					System.out.println("Staff id : " + eElement.getAttribute("id"));
//					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
//					System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
//					System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
//					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
//		 
//				}
//			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
	}
	
}
