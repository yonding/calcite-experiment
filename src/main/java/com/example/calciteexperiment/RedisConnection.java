package com.example.calciteexperiment;

import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class RedisConnection {
    private String host = "localhost";
    private int port = 6379;
    private JedisPool pool;

    public void connectToRedis(){
        pool = new JedisPool("redis://" + host + ":" + port);
    }

    public void loadDataToRedis() {
        this.loadJsonInHash();
        this.loadCsvInHash();
    }

    private void loadJsonInHash() {
        Map<String, String> employees = new HashMap<String,String>();
        employees.put("row_1",  "{\"EMP_NO\":10, \"FIRST_NAME\":\"John\", \"LAST_NAME\":\"Doe\", \"SALARY\": 5500, \"BONUS\" : 100, \"DEPT_NO\" : 10} "  );
        employees.put("row_2",  "{\"EMP_NO\":20, \"FIRST_NAME\":\"Jane\", \"LAST_NAME\":\"Davis\", \"SALARY\": 5200, \"BONUS\" : 520, \"DEPT_NO\" : 20}"  );
        employees.put("row_3",  "{\"EMP_NO\":30, \"FIRST_NAME\":\"Randi\", \"LAST_NAME\":\"Melton\", \"SALARY\": 5100, \"BONUS\" : 510, \"DEPT_NO\" : 30}"  );
        employees.put("row_4",  "{\"EMP_NO\":40, \"FIRST_NAME\":\"Violet\", \"LAST_NAME\":\"Shields\", \"SALARY\": 4600, \"BONUS\" : 150, \"DEPT_NO\" : 10}"  );
        employees.put("row_5",  "{\"EMP_NO\":50, \"FIRST_NAME\":\"Quinn\", \"LAST_NAME\":\"Galloway\", \"SALARY\": 6600, \"BONUS\" : 350, \"DEPT_NO\" : 20}"  );
        pool.getResource().hset("employees", employees);
    }

    private void loadCsvInHash() {
        Map<String, String> dept = new HashMap<String,String>();
        dept.put("row_1",  "10:Sales"  );
        dept.put("row_2",  "20:R&D"  );
        dept.put("row_3",  "30:Marketirng"  );
        pool.getResource().hset("departments", dept);
    }

}

