package com.besafx.app;

import com.besafx.app.entity.Operation;
import com.besafx.app.service.OperationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

    @Autowired
    private OperationService operationService;

    @Test
    public void contextLoads() throws Exception {
        System.out.println();
    }

}
