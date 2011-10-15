package jifeng.jms2azure.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:05 PM
 *
 * @author Jifeng Zhang
 */
public interface JmsConnector {
    public void sendMessage(String jmsQueueName, String msg);

    public TextMessage blockTextMessageReceive(String queueName) throws JMSException;
}
