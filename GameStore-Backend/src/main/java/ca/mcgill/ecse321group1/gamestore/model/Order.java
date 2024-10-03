/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 61 "model.ump"
// line 149 "model.ump"
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private String date;
  private String price;
  private String offersApplied;
  private String address;

  //Order Associations
  private VideoGame videoGame;
  private Customer customer;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetVideoGame;
  private boolean canSetCustomer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(String aDate, String aPrice, String aOffersApplied, String aAddress, VideoGame aVideoGame, Customer aCustomer)
  {
    cachedHashCode = -1;
    canSetVideoGame = true;
    canSetCustomer = true;
    date = aDate;
    price = aPrice;
    offersApplied = aOffersApplied;
    address = aAddress;
    boolean didAddVideoGame = setVideoGame(aVideoGame);
    if (!didAddVideoGame)
    {
      throw new RuntimeException("Unable to create order due to videoGame. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create order due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(String aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(String aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
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

  public String getDate()
  {
    return date;
  }

  public String getPrice()
  {
    return price;
  }

  public String getOffersApplied()
  {
    return offersApplied;
  }

  public String getAddress()
  {
    return address;
  }
  /* Code from template association_GetOne */
  public VideoGame getVideoGame()
  {
    return videoGame;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setVideoGame(VideoGame aVideoGame)
  {
    boolean wasSet = false;
    if (!canSetVideoGame) { return false; }
    if (aVideoGame == null)
    {
      return wasSet;
    }

    VideoGame existingVideoGame = videoGame;
    videoGame = aVideoGame;
    if (existingVideoGame != null && !existingVideoGame.equals(aVideoGame))
    {
      existingVideoGame.removeOrder(this);
    }
    if (!videoGame.addOrder(this))
    {
      videoGame = existingVideoGame;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (!canSetCustomer) { return false; }
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeOrder(this);
    }
    if (!customer.addOrder(this))
    {
      customer = existingCustomer;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Order compareTo = (Order)obj;
  
    if (getVideoGame() == null && compareTo.getVideoGame() != null)
    {
      return false;
    }
    else if (getVideoGame() != null && !getVideoGame().equals(compareTo.getVideoGame()))
    {
      return false;
    }

    if (getCustomer() == null && compareTo.getCustomer() != null)
    {
      return false;
    }
    else if (getCustomer() != null && !getCustomer().equals(compareTo.getCustomer()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getVideoGame() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getVideoGame().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getCustomer() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getCustomer().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetVideoGame = false;
    canSetCustomer = false;
    return cachedHashCode;
  }

  public void delete()
  {
    VideoGame placeholderVideoGame = videoGame;
    this.videoGame = null;
    if(placeholderVideoGame != null)
    {
      placeholderVideoGame.removeOrder(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "date" + ":" + getDate()+ "," +
            "price" + ":" + getPrice()+ "," +
            "offersApplied" + ":" + getOffersApplied()+ "," +
            "address" + ":" + getAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "videoGame = "+(getVideoGame()!=null?Integer.toHexString(System.identityHashCode(getVideoGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}