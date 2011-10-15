package jifeng.jms2azure.main;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 2:20 PM
 *
 * @author Jifeng Zhang
 */
public interface JmsToAzureTransport {
    public void start() throws JMSException;
}
