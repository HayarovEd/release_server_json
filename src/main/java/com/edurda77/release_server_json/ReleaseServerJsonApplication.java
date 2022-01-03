package com.edurda77.release_server_json;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.DbHandler;

import java.sql.SQLException;

@SpringBootApplication
public class ReleaseServerJsonApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DbHandler dbHandler = new DbHandler();
        dbHandler.generateJwt("ivanov", "111");

        //SpringApplication.run(ReleaseServerJsonApplication.class, args);
    }

}
