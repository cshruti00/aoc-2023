package aoc.day5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoilData {
    String mapName;
    List<HashMap<String,Long>> details;

    public SoilData(String mapName) {
        this.mapName = mapName;
        this.details = new ArrayList<>();
    }


    public void addDetails(HashMap<String,Long> map) {
        this.details.add(map);
    }

    @Override
    public String toString() {
        return "SoilData{" +
                "mapName='" + mapName + '\'' +
                ", detail=" + details +
                '}';
    }
}
