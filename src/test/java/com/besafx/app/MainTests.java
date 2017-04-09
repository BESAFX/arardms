package com.besafx.app;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

    private static final String ACCESS_TOKEN = "lwXbn73MQTAAAAAAAAAACtvJCtgSD7Rp5hwd7V8jM2V4O9I8c9javetzqM49b1-Y";

    @Test
    public void contextLoads() throws Exception {

        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        try {
            client.files().createFolder("/" + "");
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

}
