package Model;

public class Client {

    private int id;
    private int timeArrival;
    private int timeService;
    private int timeWait;

    public Client() {
        this(0, 0, 0);
    }

    public Client(int id, int timeArrival, int timeService) {
        this.id = id;
        this.timeArrival = timeArrival;
        this.timeService = timeService;
        this.timeWait = 0;
    }

    public int getId() {
        return id;
    }
    public int getTimeArrival() {
        return timeArrival;
    }
    public int getTimeService() {
        return timeService;
    }
    public int getTimeWait() {
        return timeWait;
    }
    public int decrementAndGetService() {
        return --this.timeService;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTimeArrival(int timeArrival) {
        this.timeArrival = timeArrival;
    }

    public void setTimeWait(int timeWait) {
        this.timeWait = timeWait;
    }

    public void setTimeService(int timeService) {
        this.timeService = timeService;
    }

    public String toString(){
        return " (" + id + ", " + timeArrival + ", " + timeService + ")";
    }
}
