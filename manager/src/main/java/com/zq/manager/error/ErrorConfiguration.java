//package com.zq.manager.error;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.web.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class ErrorConfiguration {
//    @Bean
//    public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,ServerProperties serverProperties,
//                                                     ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
//        return new MyErrorController(errorAttributes, serverProperties.getError(),
//                errorViewResolversProvider.getIfAvailable());
//    }
//}
