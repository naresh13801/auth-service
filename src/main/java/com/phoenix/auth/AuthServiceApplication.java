package com.phoenix.auth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
@SpringBootApplication
public class AuthServiceApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println( "Hello World!" );
    }
}
