/* MULTITHREADING <BookingClient.java>
 * EE422C Project 6 submission by
 * Suhas Raja
 * scr2469
 * 16345
 * Slip days used: <0>
 * Fall 2018
 */

package assignment6;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.lang.Thread;

public class BookingClient {
    private Map<String, Integer> offices;
    private Theater theater;
    /*
    * @param office maps box office id to number of customers in line
    * @param theater the theater where the show is playing
    */
    public BookingClient(Map<String, Integer> office, Theater theater) {
      this.offices=office;
      this.theater=theater;
    }
    public static void main(String[] args){
        Theater newTheat = new Theater(3,5,"Ouija");
        Map<String, Integer> officesT= new HashMap<String,Integer>();
        officesT.put("BX1", 3);
        officesT.put("BX2",3);
        officesT.put("BX3", 4);
        officesT.put("BX4",3);
        officesT.put("BX5", 3);
        BookingClient BC = new BookingClient(officesT, newTheat);
        BC.simulate();
        //System.out.println(newTheat.getTransactionLog());
    }
    /*
     * Starts the box office simulation by creating (and starting) threads
     * for each box office to sell tickets for the given theater
     *
     * @return list of threads used in the simulation,
     *         should have as many threads as there are box offices
     */
    public List<Thread> simulate() {
        List<Thread> threads = new ArrayList<>();
        int base = 0;
        for(String boxID:offices.keySet()){
            BoxOffice nextBox = new BoxOffice(boxID,offices.get(boxID), base);
            base += offices.get(boxID);
            Thread newThread = new Thread(nextBox);
            threads.add(newThread);
        }
        for(Thread box: threads){box.start();}
        for(Thread box: threads) {
            try {
                box.join(10000);
            }catch(Exception e){}
        }
        return threads;
    }

    private class BoxOffice implements Runnable{
        private String boxOfficeName;
        private int numberOfCllients;
        private int clientIdBase;
        public BoxOffice(String name, int custs, int clientIdBase){
            this.boxOfficeName=name;
            this.numberOfCllients =custs;
            this.clientIdBase = clientIdBase;
        }
        @Override
        public void run() {
            for(int i=0; i<numberOfCllients; i++) {
                synchronized(theater) {
                    theater.printTicket(boxOfficeName, theater.bestAvailableSeat(), i+clientIdBase);
                }
            }
        }
    }
}
