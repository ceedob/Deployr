package deployr;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Remote {
	private String name;
	private String ssh_server;
	
	public Remote(Node remoteconfig){
		if (remoteconfig.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) remoteconfig;
			if(e.hasAttribute("name")){
				name = e.getAttribute("name");
			}
			else if(e.hasAttribute("ssh_shortname")){
				name = e.getElementsByTagName("ssh_shortname").item(0).getTextContent();
			}
			else{
				name = e.getElementsByTagName("ssh_server").item(0).getTextContent();
			}
			if(e.hasAttribute("ssh_shortname")){
				this.ssh_server = e.getElementsByTagName("ssh_shortname").item(0).getTextContent();
			}
		}
	}
	public String getName(){
		return name;	
	}
	public boolean uploadFile(String filename){
		System.out.print("Uploading... ");
		if(startFileUpload(filename) == 0){
			System.out.println("[OK]");
			return true;
		}
		else
		{	
			System.out.println("[FAIL]");
			return false;
		}
	}
	
	private int startFileUpload(String filename){
		String cmd = "scp -c blowfish -q " + filename + " " + ssh_server + ":~";
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec(cmd);
			return pr.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public void runCommand(String[] s){
		
	}
	public void installComposer(String path)
	{
		
	}
	
}
