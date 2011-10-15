package jifeng.jms2azure.util;

import jifeng.jms2azure.jms.JmsConnector;
import jifeng.jms2azure.jms.JmsSender;
import jifeng.jms2azure.jms.SimpleJmsConnector;
import org.apache.activemq.ActiveMQConnection;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:52 PM
 *
 * @author Jifeng Zhang
 */
public class BatchJmsSender {
    public static void main(String[] args) throws JMSException {
        JmsSender sender = new JmsSender(ActiveMQConnection.DEFAULT_BROKER_URL);
        String jmsQueueName = "jifengtest";

        for (int i = 1; i <= 15; i ++){
            sender.send(jmsQueueName,"test msg "+i);
        }

        sender.closeConnection();
    }
}
