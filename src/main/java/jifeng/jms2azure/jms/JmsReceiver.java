package jifeng.jms2azure.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:24 PM
 *
 * @author Jifeng Zhang
 */
public class JmsReceiver extends JmsBase{

    private Map<String, MessageConsumer> consumers = new HashMap<String, MessageConsumer>();

    public JmsReceiver(String brokerUrl) {
        super(brokerUrl);
    }

    public TextMessage blockTextMessageReceive(String jmsQueueName) throws JMSException {

        MessageConsumer consumer;

        if(consumers.containsKey(jmsQueueName)){
            consumer = consumers.get(jmsQueueName);
        }else{
            consumer = session.createConsumer(session.createQueue(jmsQueueName));
            consumers.put(jmsQueueName,consumer);
        }

        Message message = consumer.receive();

        if (message instanceof TextMessage) {

        }else{
            //TODO throw not text message exception
        }

        return (TextMessage) message;
    }
}
