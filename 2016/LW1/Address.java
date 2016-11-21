/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

 public class Address {
  
  private final String street;
  private final String city;
  private final String stateOrProvince;
  private final String country;
  private final String postalCode;

  public Address(String street, String city, String stateOrProvince, String country, String postalCode) {
    this.street = street;
    this.city = city;
    this.stateOrProvince = stateOrProvince;
    this.country = country;
    this.postalCode = postalCode;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getStateOrProvince() {
    return stateOrProvince;
  }

  public String getCountry() {
    return country;
  }

  public String getPostalCode() {
    return postalCode;
  }

}