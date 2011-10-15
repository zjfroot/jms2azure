package jifeng.jms2azure;

import jifeng.jms2azure.azure.AzureConnector;
import jifeng.jms2azure.jms.JmsConnector;
import jifeng.jms2azure.jms.SimpleJmsConnector;
import jifeng.jms2azure.main.JmsToAzureTransport;
import jifeng.jms2azure.main.SimpleJmsToAzureTransport;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 1:10 PM
 *
 * TODO dispose connection
 *
 * @author Jifeng Zhang
 */
public class Demo {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static String azureAccountName = "tradedoublerjifzh";
    private static String azureAccountKey = "cXlGB5yDEixZ+zAM/9JqzqVEiBxem/pXX4Efl6Z6BXjZJsCXend4xpk2fg3nrN5E4WecainNjgYIcxk5K/nYkw==";

    private static String jmsQueueName = "jifengtest";
    private static String jmsDeadLetterQueueName = "jifengtest.DLQ";
    private static int azureRetryTimes = 20;
    private static String azureQueueName = "jifengtest";

    public static void main(String[] args) throws JMSException {
        JmsToAzureTransport jmsToAzureTransport
                = new SimpleJmsToAzureTransport(url,jmsQueueName,jmsDeadLetterQueueName,azureAccountName,azureAccountKey,azureQueueName,azureRetryTimes);
        jmsToAzureTransport.start();
    }
}
