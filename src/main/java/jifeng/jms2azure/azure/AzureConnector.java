package jifeng.jms2azure.azure;

import com.sun.tools.corba.se.idl.toJavaPortable.StringGen;
import org.soyatec.windowsazure.error.StorageException;
import org.soyatec.windowsazure.internal.util.TimeSpan;
import org.soyatec.windowsazure.queue.IMessage;
import org.soyatec.windowsazure.queue.IQueue;
import org.soyatec.windowsazure.queue.QueueStorageClient;
import org.soyatec.windowsazure.queue.internal.Message;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * Date: 10/15/11
 * Time: 1:08 PM
 *
 * @author Jifeng Zhang
 */
public class AzureConnector {

    private static final String QUEUE_NAMESPACE = "https://queue.core.windows.net/";

    private QueueStorageClient storageClient;
    private String storageAccountName;
    private String storageAccountKey;
    private long serviceRequestTimeoutMs = 5000;

    public AzureConnector(String azureAccountName, String azureAccountKey) {
        this.storageAccountName = azureAccountName;
        this.storageAccountKey = azureAccountKey;
        init();
    }

    private void init() {
        storageClient = QueueStorageClient.create(URI.create(QUEUE_NAMESPACE), false, storageAccountName, storageAccountKey);
        storageClient.setTimeout(TimeSpan.fromMilliseconds(serviceRequestTimeoutMs));
    }

    public boolean putMessage(String queueName, String message){
        return putMessage(queueName,new Message(message));
    }

    public boolean putMessage(String queueName, IMessage message) {
        return getQueue(queueName).putMessage(message);
    }

    public boolean deleteMessage(String queueName, IMessage message) {
        return getQueue(queueName).deleteMessage(message);
    }

    public IMessage getMessage(String queueName, int visibilityTimeoutSeconds) {
        return getQueue(queueName).getMessage(visibilityTimeoutSeconds);
    }

    public boolean clearQueue(String queueName) {
        return getQueue(queueName).clear();
    }

    public boolean isQueueEmpty(String queueName) {
        return (getQueue(queueName).peekMessage() == null);
    }

    private IQueue getQueue(String queueName) throws StorageException {
        if(storageClient == null) {
            throw new StorageException("Could not get queue " + queueName + ". Not connected to Azure storage account.");
        }
        IQueue queue = storageClient.getQueue(queueName);
        if ((queue == null) || (!queue.isQueueExist())) {
            throw new StorageException("The specified queue does not exist");
        }

        return queue;
    }
}
