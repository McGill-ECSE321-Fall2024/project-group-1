



/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package ca.mcgill.ecse321group1.gamestore.model;
import java.sql.Date;
import java.util.*;
import jakarta.persistence.*;

// line 47 "../../../../../../model.ump"
// line 130 "../../../../../../model.ump"
  @Entity
public class CustomerOrder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CustomerOrder Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int sharedId;
  private float price;
  private int quantity;
  private Date date;
  @ManyToOne
  private Offer offerApplied;
  private String address;

  //CustomerOrder Associations
  @OneToOne
  private VideoGame purchased;
  @ManyToOne
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CustomerOrder(int aId, int aSharedId, Date aDate, float aPrice, int aQuantity, Offer aOfferApplied, String aAddress, VideoGame aPurchased, Customer aCustomer)
  {
    id = aId;
    sharedId = aSharedId;
    date = aDate;
    price = aPrice;
    quantity = aQuantity;
    offerApplied = aOfferApplied;
    address = aAddress;
    if (!setPurchased(aPurchased))
    {
      throw new RuntimeException("Unable to create CustomerOrder due to aPurchased. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create customerOrder due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public CustomerOrder() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setSharedId(int aSharedId)
  {
    boolean wasSet = false;
    sharedId = aSharedId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(float aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setOfferApplied(Offer aOfferApplied)
  {
    boolean wasSet = false;
    offerApplied = aOfferApplied;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getSharedId()
  {
    return sharedId;
  }

  public Date getDate()
  {
    return date;
  }

  public float getPrice()
  {
    return price;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public Offer getOfferApplied()
  {
    return offerApplied;
  }

  public String getAddress()
  {
    return address;
  }
  /* Code from template association_GetOne */
  public VideoGame getPurchased()
  {
    return purchased;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPurchased(VideoGame aNewPurchased)
  {
    boolean wasSet = false;
    if (aNewPurchased != null)
    {
      purchased = aNewPurchased;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeCustomerOrder(this);
    }
    customer.addCustomerOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    purchased = null;
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeCustomerOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "sharedId" + ":" + getSharedId()+ "," +
            "price" + ":" + getPrice()+ "," +
            "quantity" + ":" + getQuantity()+ "," +
            "address" + ":" + getAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "offerApplied" + "=" + (getOfferApplied() != null ? !getOfferApplied().equals(this)  ? getOfferApplied().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "purchased = "+(getPurchased()!=null?Integer.toHexString(System.identityHashCode(getPurchased())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }

  public boolean equals(Object obj) {
    if (obj instanceof CustomerOrder order) return
            order.id == this.id &&
                    Math.pow(this.price - order.price, 2) < 1E-3 &&
                    order.quantity == this.quantity &&
                    order.offerApplied.equals(this.offerApplied) &&
                    order.address.equals(this.address) &&
                    order.date.equals(this.date) &&
                    order.customer.equals(this.customer);
    else return false;
  }
}