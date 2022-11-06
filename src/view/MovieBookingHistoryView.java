package view;

import java.util.*;

import controller.MovieReviewManager;
import model.*;
import handler.*;
import database.*;

public class MovieBookingHistoryView extends MainView {
    private ArrayList<BookingHistory> bookingHistories;
    private ArrayList<String> movieTitle;
    private ArrayList<DateTime> showingTime;
    private ArrayList<Cinema> cinemaList;
    private ArrayList<Cineplex> cineplexList;
    private ArrayList<ArrayList<String>> seatIDList;
    private MovieGoer movieGoer;
    private String errorMessage;

    public MovieBookingHistoryView(MovieGoer movieGoer) {
        ArrayList<Cineplex> cineplexes = Database.getValueList(Database.CINEPLEX.values());
        this.bookingHistories = movieGoer.getBookingHistory();
        this.movieGoer = movieGoer;

        for (int i = 0; i < this.bookingHistories.size(); i++) {
            BookingHistory history = bookingHistories.get(i);
            MovieTicket ticket = history.getMovieTicket().get(0);
            this.movieTitle.add(ticket.getMovieToWatch().getMovieTitle());
            this.showingTime.add(ticket.getShowTime());
            this.cinemaList.add(ticket.getShowingVenue());
            ArrayList<String> IDList = new ArrayList<String>();
            for (int j = 0; j < history.getMovieTicket().size(); j++) {
                IDList.add(history.getMovieTicket().get(j).getSeatID());
            }
            this.seatIDList.add(IDList);
            String cinemaUUID = ticket.getShowingVenue().getUUID();
            for (int j = 0; j < cineplexes.size(); j++) {
                Cineplex cineplex = cineplexes.get(j);
                for (int k = 0; k < cineplex.getCinemas().size(); k++) {
                    if (cinemaUUID.equals(cineplex.getCinemas().get(k).getUUID())) {
                        cineplexList.add(cineplex);
                        break;
                    }
                }

            }
        }

        this.errorMessage = "";
    }

    public void printBookingHistories() {
        String content = "\n";
        int count = 0;
        for (int i = 0; i < this.bookingHistories.size(); i++) {
            String index = String.format("%02d. ", (i + 1));
            String payload1 = String.format(index + "Booking ID: %s\n", this.bookingHistories.get(i).getUUID());
            String payload2 = String.format("   Movie Title: %s\n", this.movieTitle.get(i));
            String payload3 = String.format("   Cineplex Location: %s\n",
                    this.cineplexList.get(i).getCineplexLocation());
            String payload4 = String.format("   Cinema: %s %s\n", this.cinemaList.get(i).getUUID(),
                    this.cinemaList.get(i).getCinemaClass());
            String payload5 = String.format("   Showing Time: %s\n", showingTime.get(i).getTimeNow());
            content = content + payload1 + payload2 + payload3 + payload4 + payload5;
            count = i + 1;
        }
        String index = String.format("%02d. ", (count + 1));
        String payload = String.format(index + "Return.\n");
        content = content + payload;
        MainView.printMenuContent(content);
    }

    public void printMenu() {
        MainView.printBoilerPlate("Booking Histories");
        this.printBookingHistories();
    }

    public void appContent() {
        int choice = -1;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();

            if (choice == -1 || choice < 0 || choice > this.bookingHistories.size() + 1) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == (this.bookingHistories.size() + 1)) {
                this.errorMessage = "";
                return;
            } else {
                MovieTicketView ticketView = new MovieTicketView(this.seatIDList.get(choice - 1),
                        this.bookingHistories.get(choice - 1).getMovieTicket().get(0).getMovieToWatch(),
                        this.showingTime.get(choice - 1), this.cinemaList.get(choice - 1),
                        this.cinemaList.get(choice - 1).getSeats(),
                        this.bookingHistories.get(choice - 1).getPayment().getMovieTicketPrice());
                ticketView.printMovieTickets();
                this.errorMessage = "";
            }
            if (MovieMenuView.exit) {
                this.errorMessage = "";
                return;
            }
            System.out.println("Press any key to return: ");
            String dummy = InputHandler.stringHandler();
        } while (true);
    }

}