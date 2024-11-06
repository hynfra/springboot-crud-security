package com.matias.curso.springboot.app.springboot_crud;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messages.properties") // archivo que contiene los mensajes de error personalizados
public class AppConfig {

}
