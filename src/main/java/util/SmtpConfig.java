package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SmtpConfig {

    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String PROTOCOL = "protocol";
    public static final String CONNECTIONTIMEOUT = "connectiontimeout";
    public static final String TIMEOUT = "timeout";
    public static final String WRITETIMEOUT = "writetimeout";
    public static final String FROM = "from";
    public static final String SUBJECT = "subject";
    public static final String TO = "to";
    public static final String MAXATTACHMENTSIZE = "max_attachment_size_in_MB";
    public static final String REPOURL = "automation.repo.url";

    public Map<String,String> getPropValues() throws IOException{
        Map<String, String> properties = null;
        String propFileName = "config.properties";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            Properties prop = new Properties();
            if (inputStream != null) {
                prop.load(inputStream);
                String host = prop.getProperty(HOST);
                String port = prop.getProperty(PORT);
                String protocol = prop.getProperty(PROTOCOL);
                String connectionTimeout = prop.getProperty(CONNECTIONTIMEOUT);
                String timeout = prop.getProperty(TIMEOUT);
                String writeTimeout = prop.getProperty(WRITETIMEOUT);
                String from = prop.getProperty(FROM);
                String subject = prop.getProperty(SUBJECT);
                String to = prop.getProperty(TO);
                String maxAttachmentSizeInMB = prop.getProperty(MAXATTACHMENTSIZE);
                String automationRepoRul = prop.getProperty(REPOURL);

                properties = new HashMap<>();
                properties.put(HOST,host);
                properties.put(PORT,port);
                properties.put(PROTOCOL,protocol);
                properties.put(CONNECTIONTIMEOUT,connectionTimeout);
                properties.put(TIMEOUT,timeout);
                properties.put(WRITETIMEOUT,writeTimeout);
                properties.put(FROM,from);
                properties.put(TO,to);
                properties.put(MAXATTACHMENTSIZE,maxAttachmentSizeInMB);
                properties.put(REPOURL,automationRepoRul);
            }
        }
 return properties;
    }

}
