package deployr;
import com.jcraft.jsch.*;

import java.awt.*;
import java.io.InputStream;

import javax.swing.*;
 
public class Shell{
  public static void main(String[] arg) throws Throwable{
    
    JSch jsch=new JSch();
 
      //jsch.setKnownHosts("/home/foo/.ssh/known_hosts");
 
      String server = "pglf-prod";
//      if(arg.length>0){
//        host=arg[0];
//      }
//      else{
//        host=JOptionPane.showInputDialog("Enter username@hostname",
//                                         System.getProperty("user.name")+
//                                         "@localhost"); 
//   
//      }
//      String user=host.substring(0, host.indexOf('@'));
//      host=host.substring(host.indexOf('@')+1);
// 
      
      
//      String user = jsch.getConfigRepository().getConfig(server).getUser();
//      String host = jsch.getConfigRepository().getConfig(server).getHostname();
//      int port = jsch.getConfigRepository().getConfig(server).getPort();
      
      
      jsch.setConfigRepository(com.jcraft.jsch.OpenSSHConfig.parseFile("~/.ssh/config"));
      jsch.setKnownHosts("~/.ssh/known_hosts");
      
//      System.out.println(jsch.getConfigRepository().getConfig(server).getUser());
//      System.out.println(jsch.getConfigRepository().getConfig(server).getHostname());
//      System.out.println(jsch.getConfigRepository().getConfig(server).getPort());
//      System.out.println(jsch.getConfigRepository().getConfig(server).getValue("IdentityFile"));
//      jsch.addIdentity(jsch.getConfigRepository().getConfig(server).getValue("IdentityFile"));
      Session session=jsch.getSession(server);
 
   //   String passwd = JOptionPane.showInputDialog("Enter password");
     // session.setPassword(passwd);
 
//      UserInfo ui = new MyUserInfo(){
//        public void showMessage(String message){
//          JOptionPane.showMessageDialog(null, message);
//        }
//        public boolean promptYesNo(String message){
//          Object[] options={ "yes", "no" };
//          int foo=JOptionPane.showOptionDialog(null, 
//                                               message,
//                                               "Warning", 
//                                               JOptionPane.DEFAULT_OPTION, 
//                                               JOptionPane.WARNING_MESSAGE,
//                                               null, options, options[0]);
//          return foo==0;
//        }
// 
//        // If password is not given before the invocation of Session#connect(),
//        // implement also following methods,
//        //   * UserInfo#getPassword(),
//        //   * UserInfo#promptPassword(String message) and
//        //   * UIKeyboardInteractive#promptKeyboardInteractive()
// 
//      };
// 
      session.setUserInfo(null);
 
      // It must not be recommended, but if you want to skip host-key check,
      // invoke following,
      // session.setConfig("StrictHostKeyChecking", "no");
 
      //session.connect();
    		  session.connect(600000);   // making a connection with timeout.
 
      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand("ps aux");
 
      // Enable agent-forwarding.
      //((ChannelShell)channel).setAgentForwarding(true);
 
      channel.setInputStream(null);
      /*
      // a hack for MS-DOS prompt on Windows.
      channel.setInputStream(new FilterInputStream(System.in){
          public int read(byte[] b, int off, int len)throws IOException{
            return in.read(b, off, (len>1024?1024:len));
          }
        });
       */
 
      channel.setOutputStream(System.out);
 
      /*
      // Choose the pty-type "vt102".
      ((ChannelShell)channel).setPtyType("vt102");
      */
 
      /*
      // Set environment variable "LANG" as "ja_JP.eucJP".
      ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
      */
 
      InputStream in=channel.getInputStream();
      channel.connect();
      
      byte[] tmp=new byte[1024];
      while(true){
        while(in.available()>0){
          int i=in.read(tmp, 0, 1024);
          if(i<0)break;
          System.out.print(new String(tmp, 0, i));
        }
        if(channel.isClosed()){
          if(in.available()>0) continue; 
          System.out.println("exit-status: "+channel.getExitStatus());
          break;
        }
        try{Thread.sleep(1000);}catch(Exception ee){}
      }
      //channel.connect(3*1000);
//    }
//    catch(Exception e){
//      System.out.println(e);
//    }
  }
 
  public static abstract class MyUserInfo
                          implements UserInfo, UIKeyboardInteractive{
    public String getPassword(){ return null; }
    public boolean promptYesNo(String str){ return false; }
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return false; }
    public boolean promptPassword(String message){ return false; }
    public void showMessage(String message){ }
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      return null;
    }
  }
}