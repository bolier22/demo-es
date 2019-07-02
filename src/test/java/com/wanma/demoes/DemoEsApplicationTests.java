package com.wanma.demoes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoEsApplicationTests {

    @Test
    public void contextLoads() {
        for(int i=10;i>0;i--){
            int  num = (int)(Math.random()*5+1);
            System.out.println("-->"+num);
        }
    }

}
