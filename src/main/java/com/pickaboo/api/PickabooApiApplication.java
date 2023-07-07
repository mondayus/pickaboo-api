package com.pickaboo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// 디비 세팅 자동으로 설정해서 서버가 뜨면서 확인하는 옵션 배제 exclude = DataSourceAutoConfiguration.class
@SpringBootApplication
public class PickabooApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickabooApiApplication.class, args);
    }

}
