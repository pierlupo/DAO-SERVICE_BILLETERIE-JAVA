package entity;

import java.util.Date;

public class Event {

private int id;
private String name;
private Date date;
private String hour;
private int PlaceId;
private double price;
private int ticketsSold;

    public Event(String name, Date date, String hour, int idPlace, double price, int ticketsSold) {
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.PlaceId = PlaceId;
        this.price = price;
        this.ticketsSold = ticketsSold;
    }

    public Event(int id, String name, Date date, String hour, int placeId, double price, int ticketsSold) {
        this(name,date, hour, placeId, price, ticketsSold);
        this.id = id;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(int placeId) {
        PlaceId = placeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", hour='" + hour + '\'' +
                ", PlaceId=" + PlaceId +
                ", price=" + price +
                '}';
    }
}
