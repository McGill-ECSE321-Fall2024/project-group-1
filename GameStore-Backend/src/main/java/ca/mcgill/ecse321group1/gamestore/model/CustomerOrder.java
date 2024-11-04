/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.sql.Date;
import java.util.*;
import jakarta.persistence.*;

// line 47 "../../../../../../model.ump"
// line 117 "../../../../../../model.ump"
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
  private Date date;
  private float price;
  private int quantity;
  private String offersApplied;
  private String address;

  //CustomerOrder Associations
  @OneToMany
  private List<VideoGame> purchased;
  @ManyToOne
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CustomerOrder(){
    purchased = new ArrayList<VideoGame>();
  }

  public CustomerOrder(int aId, Date aDate, float aPrice, int aQuantity, String aOffersApplied, String aAddress, Customer aCustomer)
  {
    id = aId;
    date = aDate;
    price = aPrice;
    quantity = aQuantity;
    offersApplied = aOffersApplied;
    address = aAddress;
    purchased = new ArrayList<VideoGame>();
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create customerOrder due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setOffersApplied(String aOffersApplied)
  {
    boolean wasSet = false;
    offersApplied = aOffersApplied;
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

  public String getOffersApplied()
  {
    return offersApplied;
  }

  public String getAddress()
  {
    return address;
  }
  /* Code from template association_GetMany */
  public VideoGame getPurchased(int index)
  {
    VideoGame aPurchased = purchased.get(index);
    return aPurchased;
  }

  public List<VideoGame> getPurchased()
  {
    List<VideoGame> newPurchased = Collections.unmodifiableList(purchased);
    return newPurchased;
  }

  public int numberOfPurchased()
  {
    int number = purchased.size();
    return number;
  }

  public boolean hasPurchased()
  {
    boolean has = purchased.size() > 0;
    return has;
  }

  public int indexOfPurchased(VideoGame aPurchased)
  {
    int index = purchased.indexOf(aPurchased);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPurchased()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPurchased(VideoGame aPurchased)
  {
    boolean wasAdded = false;
    if (purchased.contains(aPurchased)) { return false; }
    purchased.add(aPurchased);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePurchased(VideoGame aPurchased)
  {
    boolean wasRemoved = false;
    if (purchased.contains(aPurchased))
    {
      purchased.remove(aPurchased);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPurchasedAt(VideoGame aPurchased, int index)
  {  
    boolean wasAdded = false;
    if(addPurchased(aPurchased))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPurchased()) { index = numberOfPurchased() - 1; }
      purchased.remove(aPurchased);
      purchased.add(index, aPurchased);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePurchasedAt(VideoGame aPurchased, int index)
  {
    boolean wasAdded = false;
    if(purchased.contains(aPurchased))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPurchased()) { index = numberOfPurchased() - 1; }
      purchased.remove(aPurchased);
      purchased.add(index, aPurchased);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPurchasedAt(aPurchased, index);
    }
    return wasAdded;
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
    purchased.clear();
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
            "price" + ":" + getPrice()+ "," +
            "quantity" + ":" + getQuantity()+ "," +
            "offersApplied" + ":" + getOffersApplied()+ "," +
            "address" + ":" + getAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }

  public boolean equals(Object obj) {
    if (obj instanceof CustomerOrder order) return
            order.id == this.id &&
                    Math.pow(this.price - order.price, 2) < 1E-3 &&
            order.quantity == this.quantity &&
            order.offersApplied.equals(this.offersApplied) &&
            order.address.equals(this.address) &&
            order.date.equals(this.date) &&
            order.customer.equals(this.customer);
    else return false;
  }
}