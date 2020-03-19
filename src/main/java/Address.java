import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id_address")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "locality")
    private String locality;
    @Column(name = "street")
    private String street;
    @ManyToOne
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "id_person")
    private Person person;
    @ManyToOne
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "id_country")
    private Country country;

    public Address() {
    }

    public Address(String zipCode, String locality, String street) {
        this.zipCode = zipCode;
        this.locality = locality;
        this.street = street;
    }

    public Address(String zipCode,
                   String locality,
                   String street,
                   Person person,
                   Country country) {
        this.zipCode = zipCode;
        this.locality = locality;
        this.street = street;
        this.person = person;
        this.country = country;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", locality='" + locality + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
