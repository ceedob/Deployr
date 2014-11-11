package deployr;

import java.io.File;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Site {
	
	public Site(Node siteconfig, Map<String, Remote> remotes){
		
	}
	public File getArchive(){
		System.out.println("Site.java:13 Not Implemented");
		return null;		
	}
	public String[] deployCommands(){
		System.out.println("Site.java:16 Not Implemented");
		return null;
	}
	public void push(){
		System.out.println("Site.java:19 Not Implemented");
	}
}
