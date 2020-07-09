package sample.FX;

import javafx.beans.property.SimpleStringProperty;

public class DataBaseToView {
    private SimpleStringProperty prodId = new SimpleStringProperty("");
    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleStringProperty cost = new SimpleStringProperty("");

    public DataBaseToView() {
        this("", "", "");
    }


    public DataBaseToView(String prodID, String title, String cost) {
        setProdId(prodID);
        setTitle(title);
        setCost(cost);
    }

    public void setProdId(String prodId) {
        this.prodId.set(prodId);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public String getProdId() {
        return prodId.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getCost() {
        return cost.get();
    }
}
