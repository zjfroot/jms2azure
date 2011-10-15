package jifeng.jms2azure.main;

import com.sun.org.apache.xml.internal.security.Init;
import jifeng.jms2azure.azure.AzureConnector;
import jifeng.jms2azure.jms.JmsConnector;
import jifeng.jms2azure.jms.SimpleJmsConnector;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:20 PM
 *
 * @author Jifeng Zhang
 */
public class SimpleJmsToAzureTransport implements JmsToAzureTransport{
    private String jmsBrokerUrl;
    private String jmsQueueName;
    private String jmsDeadLetterQueueName;

    private String azureAccountName;
    private String azureAccountKey;
    private String azureQueueName;
    private int maxAzureRetries;

    private AzureConnector azureConnector;
    private JmsConnector jmsConnector;


    /**
     * TODO convert to builder pattern
     */
    public SimpleJmsToAzureTransport(String jmsBrokerUrl, String jmsQueueName, String jmsDeadLetterQueueName, String azureAccountName, String azureAccountKey, String azureQueueName, int maxAzureRetries) {
        this.jmsBrokerUrl = jmsBrokerUrl;
        this.jmsQueueName = jmsQueueName;
        this.jmsDeadLetterQueueName = jmsDeadLetterQueueName;
        this.azureAccountName = azureAccountName;
        this.azureAccountKey = azureAccountKey;
        this.azureQueueName = azureQueueName;
        this.maxAzureRetries = maxAzureRetries;

        init();
    }

    private void init(){
        jmsConnector = new SimpleJmsConnector(jmsBrokerUrl);
        azureConnector =  new AzureConnector(azureAccountName,azureAccountKey);
    }

    @Override
    public void start() throws JMSException {
        while(true){
            TextMessage jmsMsg = jmsConnector.blockTextMessageReceive(jmsQueueName);

            System.out.println("Retrieved msg from jms queue "+jmsQueueName+": "+jmsMsg.getText());

            int hasRetriedTimes = 0;
            sendToAzure(azureConnector, jmsMsg.getText(),hasRetriedTimes);
        }
    }

    private void sendToAzure(AzureConnector azureConnector, String msg, int hasRetriedTimes) throws JMSException {
        try{
            System.out.println("Trying to transfer message on azure queue "+azureQueueName);
            azureConnector.putMessage(azureQueueName, msg);
            System.out.println("Transfer to azure finished");
        }catch (Throwable e){
            if(hasRetriedTimes < maxAzureRetries){
                hasRetriedTimes++;
                System.out.println("Retrying "+hasRetriedTimes);
                sendToAzure(azureConnector, msg,hasRetriedTimes);
            }else{
                e.printStackTrace();
                System.out.println("Not able to deliver message after "+hasRetriedTimes+
                        " retries, putting the message to dead letter queue "+jmsDeadLetterQueueName);
                jmsConnector.sendMessage(jmsDeadLetterQueueName,msg);

            }
        }
    }
}
