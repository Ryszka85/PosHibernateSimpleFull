import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Address> addresses = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Enter a new Entry in Database
        System.out.println("Save Persons and Addresses\n" +
                "----------------------------------");
        System.out.println("Enter First Name: ");
        String firstName = scanner.next();
        System.out.println("Enter Last Name: ");
        String lastName = scanner.next();
        Person p = new Person(firstName, lastName);
        scanner.nextLine();
        System.out.println("Enter Zip Code: ");
        String zip_code = scanner.nextLine();
        System.out.println(zip_code);
        System.out.println("Enter Locality: ");
        String locality = scanner.nextLine();
        System.out.println(locality);
        System.out.println("Enter Street: ");
        String street = scanner.nextLine();
        System.out.println(street);
        System.out.println("Enter Country: ");
        String countryName = scanner.nextLine();


        try (Session session = DbSession.getSessionFactory()) {
            Query query = session.createQuery("from Country c where c.countryName = :countryName")
                    .setParameter("countryName", countryName);

            List<Country> result = (List<Country>) query.list();
            if (result.size() > 0) {
                session.save(new Address(zip_code, locality, street, p, result.get(0)));
            } else {
                session.save(p);
                Country c = new Country(countryName);
                session.save(c);
                Address address = new Address(zip_code, locality, street);
                address.setPerson(p);
                address.setCountry(c);
                session.save(address);
            }
        } catch (Exception e ) {
            System.out.println(e.getMessage());

        }

        // Load Data from Database

        System.out.println("-------------------------------------------------------------------------------------------------------\n" +
                           "Loading Data from Database");
        try (Session sessionFactory = DbSession.getSessionFactory()) {

            // Get Access to Data via Person Class
            List<Person> personList = (List<Person>) sessionFactory.createQuery("from Person").list();
            for (Person person : personList) {
                for (Address address : person.getAddressList()) {
                    System.out.println(person + "  " + address + "  " + address.getCountry());
                }
            }

            //Updating Database Entry
            Person updatePersonObject = (Person) sessionFactory.createQuery("from Person p where p.id =: id")
                    .setParameter("id", 1).getSingleResult();
            updatePersonObject.setFirstName("Alfred");
            updatePersonObject.setLastName("Gusenbauer");
            sessionFactory.persist(updatePersonObject);

            System.out.println("-------------------------------------------------------------------------------------------------------");
            // Get Access to Data via Address Class
            List<Address> addressList = (List<Address>) sessionFactory.createQuery("from  Address").list();
            for (Address address : addressList) {
                System.out.println(address + "  " + address.getPerson() + "  " + address.getCountry());
            }

            System.out.println("-------------------------------------------------------------------------------------------------------");
            // Get Access to Data via Country Class
            List<Country> countryList = (List<Country>) sessionFactory.createQuery("from  Country").list();
            for (Country country : countryList) {
                for (Address address : country.getAddress()) {
                    System.out.println(country + "  " + address + "  " + address.getPerson());
                }
            }


        }
    }

}
