package jifeng.jms2azure.apps;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 3:44 PM
 *
 * @author Jifeng Zhang
 */
public class Cmd {

    public static void main(String[] args) throws JMSException {
        String propertiseFilePath;

        if(args.length < 1 || args.length >2){
            System.out.println("invalid args");
            System.exit(0);
        }else{
            propertiseFilePath = args[0];
        }

        
    }
}
