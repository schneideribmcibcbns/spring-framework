package com.logicbig.example;

public class OrderScreenProperties {
    private String title;
    private String itemLabel;
    private String qtyLabel;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getQtyLabel() {
        return qtyLabel;
    }

    public void setQtyLabel(String qtyLabel) {
        this.qtyLabel = qtyLabel;
    }

    @Override
    public String toString() {
        return "OrderScreenProperties{" +
                "title='" + title + '\'' +
                ", itemLabel='" + itemLabel + '\'' +
                ", qtyLabel='" + qtyLabel + '\'' +
                '}';
    }
}