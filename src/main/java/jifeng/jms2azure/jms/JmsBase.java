package jifeng.jms2azure.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:25 PM
 *
 * @author Jifeng Zhang
 */
public class JmsBase {
    Session session;
    Connection connection;

    public JmsBase(String brokerUrl) {
        try {
            init(brokerUrl);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void init(String brokerUrl) throws JMSException {
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory(brokerUrl);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false,
            Session.AUTO_ACKNOWLEDGE);
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }
}
