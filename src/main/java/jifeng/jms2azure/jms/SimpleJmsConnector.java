package jifeng.jms2azure.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:04 PM
 *
 * @author Jifeng Zhang
 */
public class SimpleJmsConnector implements JmsConnector{

    private JmsSender sender;
    private JmsReceiver receiver;

    public SimpleJmsConnector(String brokerUrl) {
        this.sender = new JmsSender(brokerUrl);
        this.receiver = new JmsReceiver(brokerUrl);
    }

    @Override
    public void sendMessage(String jmsQueueName, String msg) {
        try {
            sender.send(jmsQueueName, msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TextMessage blockTextMessageReceive(String jmsQueueName) throws JMSException {
        return receiver.blockTextMessageReceive(jmsQueueName);
    }
}
