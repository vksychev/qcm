package ru.vksychev.qchoice.utils;

public class DataBaseDriver {
    private String name;
    private String driverPath;

    public DataBaseDriver(String name, String driverPath){
        this.name = name;
        this.driverPath = driverPath;
    }

    public String getName(){
        return name;
    }

    public String getDriverPath(){
        return driverPath;
    }

    @Override
    public String toString() {
        return name;
    }
}
