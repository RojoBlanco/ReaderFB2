package ru.read.reader.database;

public class Database {
    private final String DB_URL;
    private final String DB_Driver;

    public Database(String DB_URL, String DB_Driver){
        this.DB_Driver = DB_Driver;
        this.DB_URL = DB_URL;
    }

}