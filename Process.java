
public class Process {
    // fixed
    private int pid;
    private int arrivalTime;
    private int burstTime;

    // dynamic
    private int remainingBurst;
    private int firstVisitTime;
    private int startTime;
    private int terminationTime;

    private boolean isOld;

    // calculated
    private int turnaroundTime;
    private int responseTime;
    private int waitingTime;

    static double totalTurnaroundTime;
    static double totalResponseTime;
    static double totalWaitingTime;

    Process(int pid, int arrivalTime,int burstTime){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        remainingBurst=burstTime;
        terminationTime=arrivalTime;


    }
    public void computeStats() {
        turnaroundTime= terminationTime - arrivalTime;
        waitingTime= terminationTime - arrivalTime - burstTime;
        responseTime = firstVisitTime - arrivalTime;

        totalTurnaroundTime+=turnaroundTime;
        totalWaitingTime+=waitingTime;
        totalResponseTime+=responseTime;
    }


    //getters and setters

    public boolean getOld() {
        return isOld;
    }

    public void setOld(boolean old) {
        isOld = old;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(int firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }


    public int getRemainingBurst() {
        return remainingBurst;
    }

    public void setRemainingBurst(int remainingBurst) {
        this.remainingBurst = remainingBurst;
    }

    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPid() {
        return pid;
    }

    public int getTerminationTime() {
        return terminationTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
