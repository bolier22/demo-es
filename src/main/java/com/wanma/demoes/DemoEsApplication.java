package com.wanma.demoes;

import com.wanma.demoes.ds.DynamicDataSourceRegister;
import com.wanma.demoes.mapper.TblUserAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@SpringBootApplication
@Import(DynamicDataSourceRegister.class)
public class DemoEsApplication{

    public static void main(String[] args) {
        SpringApplication.run(DemoEsApplication.class, args);
    }

}
