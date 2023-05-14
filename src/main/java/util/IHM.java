package util;

import dao.CustomerDAO;
import dao.EventDAO;
import dao.PlaceDAO;
import service.CustomerService;
import service.EventService;
import service.PlaceService;
import service.TicketsSalesService;

import java.sql.Connection;
//import java.util.Date;
import java.sql.Date;
import java.util.Scanner;

public class IHM {

    Scanner scanner;
    String choix;
    String customerChoice;
    private CustomerDAO customerDAO;
    private CustomerService customerService;
    private EventDAO eventDAO;
    private PlaceDAO placeDAO;

    private Connection connection;
    public IHM() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        do {
            menu();
            choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    customerMenu();
                    break;
                case "2":
                    eventMenu();
                    break;
                case "3":
                    placeMenu();
                    break;
                case "4":
                    buyTicketAction();
                    break;
                case "5":
                    cancelTicketAction();
                    break;
                case "6":
                    showAllEventAction();
                    break;
                case "7":
                    showCustomerTicketAction();
                    break;
            }
        }while (!choix.equals("0"));
    }
    private void menu() {
        System.out.println("-------------------------------");
        System.out.println("TP Billeterie DAO-SERVICE JDBC");
        System.out.println("-------------------------------");
        System.out.println("***************************************");
        System.out.println("Choose an option :");
        System.out.println("***************************************");
        System.out.println("1 - Customer menu ");
        System.out.println("2 - Event menu ");
        System.out.println("3 - Place menu ");
        System.out.println("4 - Buy ticket(s) for an event");
        System.out.println("5 - Cancel ticket(s)");
        System.out.println("6 - List all events");
        System.out.println("7 - List ticket(s) bought by customer");
        System.out.println("0 - Quit ");
    }

    // 1 - CRUD Customer
    private void customerMenu() {
        do {
            menuCustomer();
            customerChoice = scanner.nextLine();
            switch (customerChoice) {
                case "1":
                    saveCustomerAction();
                    break;
                case "2":
                    getAllCustomerAction();
                    break;
                case "3":
                    updateCustomerAction();
                    break;
                case "4":
                    deleteCustomerAction();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (!customerChoice.equals("5"));
    }

    //  Sous Menu Client
    private void menuCustomer() {
        System.out.println("-------------------------------");
        System.out.println(" TP Billeterie DAO-SERVICE JDBC");
        System.out.println("-------------------------------");
        System.out.println("***************************************");
        System.out.println("Choose an option please :");
        System.out.println("***************************************");
        System.out.println("1 - Add a customer");
        System.out.println("2 - list all customers");
        System.out.println("3 - Update a customer");
        System.out.println("4 - Delete a customer");
        System.out.println("5 - go back");
        System.out.println("***************************************");
    }

    // 1 - Create customer
    private void saveCustomerAction() {
        System.out.println("Enter your firstname");
        String name = scanner.nextLine();
        System.out.println("Enter your name");
        String firstname = scanner.nextLine();
        System.out.println("Enter your email");
        String email = scanner.nextLine();
        CustomerService customerService = new CustomerService();
        if (customerService.addCustomer(name, firstname, email)) {
            System.out.println("Customer added");
        } else {
            System.out.println("Error while adding customer");
        }
    }

    // 2 - list all customers
    private void getAllCustomerAction() {
        System.out.println("List of all customers :");
        CustomerService customerService = new CustomerService();
        if (customerService.getAllCustomers() != null) {
            customerService.getAllCustomers().forEach(
                    customer -> System.out.println(
                            "Id : " + customer.getId() +
                                    " Firstname : " + customer.getFirstName() +
                                    " Lastname : " + customer.getLastName() +
                                    " Email : " + customer.getEmail()
                    )
            );
        } else {
            System.out.println("No customers");
        }
    }

    // 3 - Update a customer
    private void updateCustomerAction() {
        System.out.println("Enter id of customer to update");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter firstname");
        String firstname = scanner.nextLine();
        System.out.println("Enter lastname");
        String lastname = scanner.nextLine();
        System.out.println("Enter email");
        String email = scanner.nextLine();
        customerService = new CustomerService();
        System.out.println("Enter number of tickets sold");
        int ticketsSold = scanner.nextInt();
        if (customerService.updateCustomer(id, firstname, lastname, email, ticketsSold)) {
            System.out.println("Customer info successfully updated");
        } else {
            System.out.println("Error while updating customer info");
        }
    }

    // 4 - Delete a customer
    private void deleteCustomerAction() {
        System.out.println("Enter the id of the customer to delete");
        int id = scanner.nextInt();
        scanner.nextLine();
        customerService = new CustomerService();
        if (customerService.deleteCustomer(id)) {
            System.out.println("Le client a été supprimé avec succès");
        } else {
            System.out.println("Le client n'a pas été supprimé");
        }
    }
    // Crud Event
    private void eventMenu() {
        do {
            menuEvent();
            choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    saveEventAction();
                    break;
                case "2":
                    getAllEventAction();
                    break;
                case "3":
                    updateEventAction();
                    break;
                case "4":
                    deleteEventAction();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (!choix.equals("5"));
    }

    //  Sous Menu Event
    private void menuEvent() {
        System.out.println("-------------------------------");
        System.out.println(" TP Billeterie DAO JDBC");
        System.out.println("-------------------------------");
        System.out.println("***************************************");
        System.out.println("Choose an option :");
        System.out.println("***************************************");
        System.out.println("1 - Add an event");
        System.out.println("2 - List of all events");
        System.out.println("3 - Update an event");
        System.out.println("4 - Delete an event");
        System.out.println("5 - Go Back");
        System.out.println("***************************************");
    }

    // 1 - Create event
    private void saveEventAction() {
        System.out.println("Enter the name of the event");
        String name = scanner.nextLine();
        System.out.println("Enter the date of the event");
        //String date = scanner.nextLine();
        Date date = Date.valueOf(scanner.nextLine());
        System.out.println("Enter the date of the event");
        String time = scanner.nextLine();
        System.out.println("Enter the id of the place");
        int placeId = scanner.nextInt();
        System.out.println("Enter the price of the event");
        int price = scanner.nextInt();
        System.out.println("Enter the number of tickets sold");
        int ticketsSold = scanner.nextInt();
        scanner.nextLine();
        EventService eventService = new EventService();
        if (eventService.addEvent(name, date, time, placeId, price, ticketsSold)) {
            System.out.println("L'evenement a été enregistré avec succès");
        } else {
            System.out.println("L'evenement n'a pas été enregistré");
        }

    }

    // 2 - List all events
    private void getAllEventAction() {
        System.out.println("Liste des evenements :");
        EventService eventService = new EventService();
        if (eventService.getAllEvent() != null) {
            eventService.getAllEvent().forEach(
                    event -> System.out.println(
                            "Id : " + event.getId() +
                                    " Name : " + event.getName() +
                                    " Date : " + event.getDate() +
                                    " Hour : " + event.getHour() +
                                    " Place : " + event.getPlaceId() +
                                    " Price : " + event.getPrice() +
                                    " Tickets sold : " + event.getTicketsSold()
                    )
            );
        } else {
            System.out.println("Il n'y a pas d'evenement enregistré");
        }
    }

    // 3 - Update event
    private void updateEventAction() {
        System.out.println("Enter the id of the event to update");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the name of the event");
        String name = scanner.nextLine();
        System.out.println("Enter the date of the event");
        Date date = Date.valueOf(scanner.nextLine());
        System.out.println("Enter the hour of the event");
        String hour = scanner.nextLine();
        System.out.println("Enter the place");
        int placeId = scanner.nextInt();
        System.out.println("Enter the price of the event");
        int price = scanner.nextInt();
        System.out.println("Enter the number of tickets sold");
        int ticketsSold = scanner.nextInt();
        scanner.nextLine();
        EventService eventService = new EventService();
        if (eventService.updateEvent(id, name, date, hour, placeId, price, ticketsSold)) {
            System.out.println("Event successfully updated");
        } else {
            System.out.println("L'evenement n'a pas été modifié");
        }
    }

    // 4 - Delete an event
    private void deleteEventAction() {
        System.out.println("List of events :");
        getAllEventAction();
        System.out.println("Enter the id of the event to delete");
        int id = scanner.nextInt();
        scanner.nextLine();
        EventService eventService = new EventService();
        if (eventService.deleteEvent(id)) {
            System.out.println("Event deleted");
        } else {
            System.out.println("Error while deleting event");
        }
    }
    // 1 - CRUD Lieu
    private void placeMenu() {
        do {
            menuPlace();
            choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    addPlaceAction();
                    break;
                case "2":
                    getAllPlacesAction();
                    break;
                case "3":
                    updatePlaceAction();
                    break;
                case "4":
                    deletePlaceAction();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Choix non valide");
            }
        } while (!choix.equals("5"));
    }

    // Sub Menu Place
    private void menuPlace() {
        System.out.println("-------------------------------");
        System.out.println(" TP Billeterie DAO JDBC");
        System.out.println("-------------------------------");
        System.out.println("***************************************");
        System.out.println("Choose an option please :");
        System.out.println("***************************************");
        System.out.println("1 - Add a place");
        System.out.println("2 - list of all places");
        System.out.println("3 - Update a place");
        System.out.println("4 - Delete a place");
        System.out.println("5 - Go Back");
        System.out.println("***************************************");
    }

    // 1 - Create/ add a place
    private void addPlaceAction() {
        System.out.println("Enter the name of the place : ");
        String name = scanner.nextLine();
        System.out.println("Enter the address : ");
        String address = scanner.nextLine();
        System.out.println("Enter the capacity of the place : ");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        PlaceService placeService = new PlaceService();
        if (placeService.addPlace(name, address, capacity)) {
            System.out.println("Place successfully added");
        } else {
            System.out.println("Error whil adding the place");
        }
    }

    // 2 - List of all places
    private void getAllPlacesAction() {
        System.out.println("List all places :");
        PlaceService placeService = new PlaceService();
        placeService.getAllPlaces().forEach(
                place -> System.out.println(
                        "Id : " +  place.getId() +
                                " Nom : " + place.getName() +
                                " Adresse : " + place.getAddress() +
                                " Capacité : " + place.getCapacity()
                )
        );
    }

    // 3 - Update a place
    private void updatePlaceAction() {
        System.out.println("Enter the id of the place to update");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the name : ");
        String name = scanner.nextLine();
        System.out.println("Enter the address : ");
        String address = scanner.nextLine();
        System.out.println("Enter the capacity : ");
        int capacity = scanner.nextInt();
        PlaceService placeService = new PlaceService();
        if (placeService.updatePlace(id, name, address, capacity)) {
            System.out.println("Place successfully updated");
        } else {
            System.out.println("Error while updating the place");
        }
    }

    // 4 - Delete a place
    private void deletePlaceAction() {
        System.out.println("Enter the id of the place to delete : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        PlaceService placeService = new PlaceService();
        if (placeService.deletePlace(id)) {
            System.out.println("place successfully deleted");
        } else {
            System.out.println("Error while deleting the place");
        }
    }
    //  3 - Buy ticket for an event
    private void buyTicketAction() {
        getAllCustomerAction();
        System.out.println("Enter customer id");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        getAllEventAction();
        System.out.println("Enter event id");
        int eventId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter number of tickets you wnat to buy");
        int numOfTickets = scanner.nextInt();
        scanner.nextLine();
        TicketsSalesService ticketssalesService = new TicketsSalesService();
        if (ticketssalesService.addTicketsSale(customerId, eventId, numOfTickets)) {
            System.out.println("ticket successfully bought");
        } else {
            System.out.println("Error while buying the ticket");
        }
    }

    // 5 - Cancel ticket(s)
    private void cancelTicketAction() {
        getAllTickets();
        System.out.println("Enter the id of the ticketSale you want to cancel");
        int id = scanner.nextInt();
        scanner.nextLine();
        TicketsSalesService ticketssalesService = new TicketsSalesService();
        if (ticketssalesService.deleteTicketsSale(id)) {
            System.out.println("Le ticket a été supprimé avec succès");
        } else {
            System.out.println("Le ticket n'a pas été supprimé");
        }
    }

    private void getAllTickets() {
        System.out.println("List of all tickets :");
        TicketsSalesService ticketssalesService = new TicketsSalesService();
        if (ticketssalesService.getAllTicketsSales() != null) {
            ticketssalesService.getAllTicketsSales().forEach(
                    ticket -> System.out.println(
                            "Id : " + ticket.getId() + " " +
                                    "customerId :" + ticket.getCustomerId() + " " +
                                    "eventId :" + ticket.getEventId() + " " +
                                    "NbTickets :" + ticket.getNumOfTickets()
                    )
            );
        } else {
            System.out.println("No tickets");
        }
    }

    // 6 - List of all events
    private void showAllEventAction() {
        getAllEventAction();
        System.out.println("List of all events :");
        EventService eventService = new EventService();
        if (eventService.getAllEvent() != null) {
            eventService.getAllEvent().forEach(
                    event -> System.out.println(
                            "Id : " + event.getId() +
                                    " Name : " + event.getName() +
                                    " Date : " + event.getDate() +
                                    " Place : " + event.getPlaceId() +
                                    " Number of Tickets : " + event.getTicketsSold() +
                                    " Price : " + event.getPrice()
                    )
            );
        } else {
            System.out.println("No event");
        }
    }

    // 7 - Tickets bought by customer
    private void showCustomerTicketAction() {
        getAllCustomerAction();
        System.out.println("Enter the id of the customer");
        int id = scanner.nextInt();
        scanner.nextLine();
        TicketsSalesService ticketSalesService = new TicketsSalesService();
        if (ticketSalesService.getAllUserEvents(id) != null) {
            ticketSalesService.getAllUserEvents(id).forEach(
                    ticket -> System.out.println(
                            "Id : " + ticket.getName() +
                                    " Customer ID  : " + id +
                                    " Event : " + ticket.getName()
                            //  " Nb Tickets : " + ticket.getNbTickets()
                    )
            );
        } else {
            System.out.println("Il n'y a pas de ticket enregistré");
        }
    }
//    private Customer addCustomerAction() {
//        Customer customer = null;
//        System.out.print("Please Enter your firstname : ");
//        String lastName = scanner.nextLine();
//        System.out.print("Please Enter your lastname : ");
//        String firstName = scanner.nextLine();
//        System.out.print("Please Enter your email : ");
//        String email = scanner.nextLine();
//        customer = new Customer(firstName, lastName, email);
//        try {
//            connection = new DataBaseManager().getConnection();
//            connection.setAutoCommit(false);
//            customerDAO = new CustomerDAO(connection);
//            if(customerDAO.save(customer)) {
//                System.out.println("Customer added ");
//            }else {
//                customer = null;
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            customer = null;
//        } catch (ExecutionControl.NotImplementedException e) {
//            throw new RuntimeException(e);
//        }
//
//        return customer;
//    }
}
