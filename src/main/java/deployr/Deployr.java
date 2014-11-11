package deployr;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Deployr {
	private static Deployr instance;
	
	private String args;
	private String folder;
	private static Options options = new Options();
	
	CommandLineParser parser = new PosixParser();
	private Deployr(String[] args){
//		options.addOption("h", "help", false, "Display this help text");
//		options.addOption("p", "push", false, "Display this help text");
//		options.addOption("s", "status", false, "Display this help text");
//		options.addOption("f", "folder", true, "Display this help text");
//		
//		try {
//			CommandLine cmd = parser.parse(options, args);
//			if (cmd.hasOption("help")){
//				help();
//			}
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			help();
//		}
//		
		
		if(args.length > 2){
			folder = args[2];
		}
		else{
			folder = "";
		}
		if(args.length > 1){
			switch(args[1]){
			case "push": push(); break;
			case "init": init(); break;
			case "status": break;
			default: help();
			}
		}
		
		
	}
	public void push(){
		File f = new File(folder, "deployr.xml");
		if(f.exists() && !f.isDirectory())
		{
			Project p = new Project(folder, "deployr.xml");
		}
		else{
			System.out.println("fatal: no deployr.xml file found. Run `deployr init` to create one, or run `deployr help` to see usage");
		}
		
	}
	public void init(){
		Project.init(folder);
	}
	public void status(){
		
	}
	public void help(){
//		HelpFormatter formatter = new HelpFormatter();
//		formatter.printHelp( "deployr", options , true);
		String usage;
		usage = "usage: deployr <command> [folder]\n"+
				"\nThe deployr commands are:\n"+
				"    push        Deploy a new version of the project\n"+
				"    init        Initialize a folder as a deployr project\n"+
				"    status      List the status of deployr deployments\n"+
				"    help        Display this help text\n"+
				"    [folder]    A folder to use as a deployr project, the default is the current directory.\n";

		System.out.println(usage);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = new String[]{"deployr", "push", "/Users/chris/Google Drive/Progamming/chrisdobson.ca/" };
		Deployr d = new Deployr(args);
		
		
	}

}
