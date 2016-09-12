package domain;

public class Route {

    private int id;

    private Priority priority;

    private City origin;

    private City destination;

    private TransportCompany transportCompany;

    private double duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getCostPerGram() {
        return costPerGram;
    }

    public void setCostPerGram(double costPerGram) {
        this.costPerGram = costPerGram;
    }

    public double getCostPerCC() {
        return costPerCC;
    }

    public void setCostPerCC(double costPerCC) {
        this.costPerCC = costPerCC;
    }

    private double costPerGram;

    private double costPerCC;


	

}
