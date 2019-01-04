/* MULTITHREADING <Theater.java>
 * EE422C Project 6 submission by
 * Suhas Raja
 * scr2469
 * 16345
 * Slip days used: <0>
 * Fall 2018
 */

package assignment6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Theater {
	private int maxRows;
	private int maxRowSeats;
	private String currShow;
	private static List<Ticket> tickRecords;
	private Integer lastRow = 0;
	private Integer lastCol = 0;
	private Queue printQueue;
	private boolean done=false;

	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;

		public int getSeatNum() {
			return seatNum;
		}
		public int getRowNum() {
			return rowNum;
		}
		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}
		@Override
		public String toString() {
			String result="";
			int tempRowNumber=rowNum+1;
			do {
				tempRowNumber--;
				result= ((char)('A'+tempRowNumber%26)) + result;
				tempRowNumber=tempRowNumber/26;
			} while(tempRowNumber>0);
			result += seatNum;
			return result;
		}
	}

    /*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
	  	private int client;
		public static final int ticketStringRowLength = 31;
		public Seat getSeat() {
			return seat;
		}
		public String getShow() {
			return show;
		}
		public String getBoxOfficeId() {
			return boxOfficeId;
		}
		public int getClient() {
			return client;
		}
		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}
		public String toString() {
			  String result, dashLine, showLine, boxLine, seatLine, clientLine, eol;
			  eol = System.getProperty("line.separator");
			  dashLine = new String(new char[ticketStringRowLength]).replace('\0', '-');
			  showLine = "| Show: "+show;
			  for(int i=showLine.length(); i<ticketStringRowLength-1; ++i)
				  showLine += " ";
			  showLine += "|";
			  boxLine = "| Box Office ID: "+boxOfficeId;
			  for(int i=boxLine.length(); i<ticketStringRowLength-1; ++i)
				  boxLine += " ";
			  boxLine += "|";
			  seatLine = "| Seat: "+seat.toString();
			  for(int i=seatLine.length(); i<ticketStringRowLength-1; ++i)
				  seatLine+=" ";
			  seatLine+="|";
			  clientLine="| Client: "+client;
			  for(int i=clientLine.length(); i<ticketStringRowLength-1; ++i)
				  clientLine+=" ";
			  clientLine+="|";
			  result = dashLine+eol+showLine + eol +boxLine + eol +
					   seatLine + eol + clientLine + eol + dashLine;
			  return result;
		  }
	}

	public Theater(int numRows, int seatsPerRow, String show) {
		this.maxRows=numRows;
		this.maxRowSeats=seatsPerRow;
		this.currShow=show;
		this.tickRecords = new ArrayList<>();
		this.printQueue = new LinkedList<>();
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
     */
	public Seat bestAvailableSeat() {
		synchronized (this.lastRow) {
			synchronized (this.lastCol) {
				this.lastCol+=1;
				if(lastCol<=maxRowSeats){return new Seat(lastRow, lastCol);}
				lastRow+=1;
				if(lastRow>=maxRows) return null;
				this.lastCol=1;
				return new Seat(lastRow,this.lastCol);
			}
		}
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
     * Also prints the ticket to the console
	 *
     * @param seat a particular seat in the theater
     * @return a ticket or null if a box office failed to reserve the seat
     */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		if(seat==null){
			if(!done){
				done=true;
				System.out.println("Sorry, we are sold out!");
			}
			return null;
		}
		Ticket newTick;
		newTick = new Ticket(this.currShow, boxOfficeId, seat, client);
		tickRecords.add(newTick);
		try {Thread.sleep(50);} catch (InterruptedException e) {}
		System.out.println(newTick+"");
		return newTick;
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
     * @return list of tickets sold
     */
	public List<Ticket> getTransactionLog() {return this.tickRecords;}
}
