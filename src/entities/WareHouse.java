package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public class WareHouse implements Serializable {

    private int code;
    private TradeType tradeType;
    private Date timeStamp;
    private List<Product> listProduct;

    public WareHouse(int code, TradeType tradeType, Date time, List<Product> listProduct) {
        this.code = code;
        this.timeStamp = time;
        this.tradeType = tradeType;
        this.listProduct = listProduct;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Date getTime() {
        return timeStamp;
    }

    public void setTime(Date time) {
        this.timeStamp = time;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    @Override
    public String toString() {
        String result = code + ", " + timeStamp + ", ";

        for (Product p : listProduct) {
            String pCode = p.getCode();
            result += (", " + pCode);
        }
        return result;
    }
}
