package com.school.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

//专门用于 配置类bean
@Configuration
//专门用于 配置读取的bean
@EnableConfigurationProperties(MyBeanPro.class)
//通用的定义
@Component
// 控制层  service dao 层的 bean @Controller @Mapper @Service @RestController
//通过快速导入的方式实现把实例加入spring的IOC容器中
@Import(MyBeanImport.class)
@Data
public class MyBean {

    private String name;

    @Bean
    public MyBeanMethod myBeanMethod() {
        MyBeanMethod myBeanMethod = new MyBeanMethod();
        return myBeanMethod;
    }
}
