package jifeng.jms2azure.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:07 PM
 *
 * TODO dispose connection
 *
 * @author Jifeng Zhang
 */
public class JmsSender extends JmsBase{

    public JmsSender(String brokerUrl) {
        super(brokerUrl);
    }

    /**
     * TODO try to reuse the destination and producer instead of creating one every time
     * @param jmsQueueName
     * @param msg
     * @throws JMSException
     */
    public void send(String jmsQueueName, String msg) throws JMSException {
        Destination destination = session.createQueue(jmsQueueName);
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(msg);
        producer.send(message);
    }
}
