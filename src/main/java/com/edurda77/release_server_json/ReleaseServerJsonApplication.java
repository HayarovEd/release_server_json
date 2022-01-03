package com.edurda77.release_server_json;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.DbHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ReleaseServerJsonApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String key = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyb3YiLCJleHAiOjE2NzI3NDQ1NDF9.omFvfJQjTjjr1DK63dwgij51NLvin0-OxbQg-seF_ZI";

        DbHandler dbHandler = new DbHandler();
        //dbHandler.generateJwt("petrov", "222");
        //dbHandler.addSendMessage(key,"petrov","Hallo");
        //SpringApplication.run(ReleaseServerJsonApplication.class, args);
        List<String> takesMessages = dbHandler.getLastMessages(key, "petrov", 3);
        for (String str: takesMessages) {
            System.out.println(str);
        }
    }

}
