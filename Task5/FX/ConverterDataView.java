package sample.FX;

import sample.DataBase.DataDB;

import java.util.ArrayList;

public final class ConverterDataView {

    private ConverterDataView() {
    }

    public static DataBaseToView convert(DataDB data) {
        return new DataBaseToView(String.valueOf(data.prodid), String.valueOf(data.title), String.valueOf(data.cost));
    }

    public static DataDB convert(DataBaseToView data) {
        DataDB temp = new DataDB();
        temp.prodid = Integer.parseInt(data.getProdId());
        temp.cost = Integer.parseInt(data.getCost());
        temp.title = data.getTitle();
        return temp;
    }

    public static ArrayList<DataBaseToView> convert(ArrayList<DataDB> data) {
        ArrayList<DataBaseToView> temp = new ArrayList<>();
        for (DataDB item : data) {
            temp.add(convert(item));
        }
        return temp;
    }
}
