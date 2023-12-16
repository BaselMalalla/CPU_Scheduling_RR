import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        // *declare variables*
        Scanner kbd = new Scanner(System.in);
        ArrayList<Process> processesList = new ArrayList<>();
        int     quantum, pid, arrivalTime, burstTime,
                pCounter=0;

        // *prompt input*
        System.out.println("\nCPU Scheduling Algorithm Simulator (Round-Robin) \n");

        System.out.print("Enter the quantum value (ms): ");
        do{
            quantum = kbd.nextInt();
            if (quantum>0) break;
            System.out.print("Invalid input. Please enter a positive integer: ");
        }while (true);

        System.out.println("\nEnter the details of the processes:");
        System.out.println("[Process ID   Arrival Time (ms)   Burst Time (ms)] \tor\t [0  0  0] to stop");
        do{ // populate processesList
            pid = kbd.nextInt();
            arrivalTime = kbd.nextInt();
            burstTime = kbd.nextInt();
            if(pid == 0 && arrivalTime == 0 && burstTime==0) { // to stop adding
                if (pCounter == 0){
                    System.out.println("No process has been added");
                    System.exit(0);
                }
                break;
            }
            if (pid < 0 || arrivalTime < 0 || burstTime <= 0) { // validate input
                System.out.println("Invalid input. Please enter non-negative integers for " +
                        "PID and AT, and a positive integer for BT.");
                continue;
            }
            processesList.add(new Process(pid, arrivalTime, burstTime));
            pCounter++;
            System.out.println("Process added");
        }while (true);

        kbd.close();


        // *execution simulation*
        ArrayList<Process> arrivedProcesses = new ArrayList<Process>();

        // sort by processesList by arrival time
        processesList.sort(Comparator.comparingInt(Process::getArrivalTime));

        arrivedProcesses.add(processesList.get(0));
        int     time = arrivedProcesses.get(0).getArrivalTime();
        int     execBegin =0,
                execEnd;
        System.out.println("\nGantt Chart (each [_] = 0.5ms):\n");

        // execution loop
        while(!arrivedProcesses.isEmpty()){
            // for new processes, termination time is set as arrival time for comparison
            arrivedProcesses.sort(Comparator.comparingInt(Process::getTerminationTime));
            Process currentProcess = arrivedProcesses.remove(0);

            if(currentProcess.getRemainingBurst()<=quantum){ // remaining burst <= quantum
                if(!currentProcess.getOld()) // if first visit
                    currentProcess.setFirstVisitTime(time);
                currentProcess.setStartTime(time);
                time+=currentProcess.getRemainingBurst();
                currentProcess.setTerminationTime(time);
                currentProcess.setOld(true);
                execBegin = currentProcess.getStartTime();
                execEnd = currentProcess.getTerminationTime();
                checkForArrivedProcesses(processesList,arrivedProcesses,execBegin,execEnd);
            }

            else{ // remaining burst > quantum
                if(!currentProcess.getOld()) // first visit?
                    currentProcess.setFirstVisitTime(time);
                currentProcess.setStartTime(time);
                time+= quantum;
                currentProcess.setRemainingBurst(currentProcess.getRemainingBurst()-quantum);
                currentProcess.setTerminationTime(time);
                currentProcess.setOld(true);
                checkForArrivedProcesses(processesList, arrivedProcesses,currentProcess.getStartTime(),currentProcess.getTerminationTime());
                arrivedProcesses.add(currentProcess);
            }
            displayGanttChart(currentProcess, time, arrivedProcesses.isEmpty());
        }// end of execution loop
        displayCalculationsTable(processesList, pCounter);
    }
    public static void checkForArrivedProcesses(ArrayList<Process> processesList,
                                                ArrayList<Process> arrivedProcesses,
                                                int execBegin, int execEnd){
        int arrivalTime;
        for (Process process : processesList) {
            arrivalTime = process.getArrivalTime();
            if (arrivalTime > execBegin && arrivalTime <= execEnd && !process.getOld()){
                arrivedProcesses.add(process);
            }
        }

    }
    private static void displayGanttChart(Process cur, int time, boolean arrivedProcessesIsEmpty) {

        int length = time-cur.getStartTime();

        System.out.print(cur.getStartTime());
        for(int i=0;i<length;i++)
            System.out.print("_");
        System.out.print("P"+cur.getPid());
        for(int i=0;i<length;i++)
            System.out.print("_");
        if(arrivedProcessesIsEmpty) // if it is the last process display its termination time
            System.out.println(time);
    }
    private static void displayCalculationsTable(ArrayList<Process> processesList, int pCounter) {
        System.out.println("\nCalculations Table:\n");
        System.out.printf("  %-5s | %-15s | %-15s | %-15s |%n","",
                "Waiting Time", "Turnaround Time",
                "Response Time");
        // sort by processesList by pid
        processesList.sort(Comparator.comparingInt(Process::getPid));
        for (int i = 0; i < pCounter; i++) {
            Process cur = processesList.get(i);
            cur.computeStats(); // stats are calculated in Process class
            System.out.printf("  P%-4d | %-13dms | %-13dms | %-13dms |%n",
                    cur.getPid(), cur.getWaitingTime(),
                    cur.getTurnaroundTime(), cur.getResponseTime());
        }
        System.out.printf("  %-5s | %-15s | %-15s | %-15s |%n","",
                "", "",
                "");
        System.out.printf("  %-5s | %-13.3fms | %-13.3fms | %-13.3fms |%n","AVG",
                (double) Process.totalWaitingTime / pCounter,
                (double) Process.totalTurnaroundTime / pCounter,
                (double) Process.totalResponseTime / pCounter);
    }
}
