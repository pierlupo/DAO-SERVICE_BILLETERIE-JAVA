package entity;

public class TicketsSales {
    private int id;
    private int customerId;
    private int eventId;
    private int numOfTickets;

    public TicketsSales(int customerId, int eventId, int numOfTickets) {
        this.customerId = customerId;
        this.eventId = eventId;
        this.numOfTickets = numOfTickets;
    }

    public TicketsSales(int id, int customerId, int eventId, int numOfTickets) {
        this(customerId,eventId,numOfTickets);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        this.numOfTickets = numOfTickets;
    }

    @Override
    public String toString() {
        return "TicketsSales{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", eventId=" + eventId +
                ", numOfTickets=" + numOfTickets +
                '}';
    }
}
