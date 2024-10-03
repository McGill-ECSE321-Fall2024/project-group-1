/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 27 "model.ump"
// line 121 "model.ump"
public class Customer extends User
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Pending, Active, Inactive }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String address;
  private String phoneNumber;

  //Customer Associations
  private List<VideoGame> purchased;
  private List<VideoGame> wishlist;
  private List<Order> orders;
  private List<Review> reviews;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aUsername, String aEmail, String aPasswordHash, GameStore aGameStore, String aAddress, String aPhoneNumber)
  {
    super(aUsername, aEmail, aPasswordHash, aGameStore);
    address = aAddress;
    phoneNumber = aPhoneNumber;
    purchased = new ArrayList<VideoGame>();
    wishlist = new ArrayList<VideoGame>();
    orders = new ArrayList<Order>();
    reviews = new ArrayList<Review>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
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
  /* Code from template association_GetMany */
  public VideoGame getWishlist(int index)
  {
    VideoGame aWishlist = wishlist.get(index);
    return aWishlist;
  }

  public List<VideoGame> getWishlist()
  {
    List<VideoGame> newWishlist = Collections.unmodifiableList(wishlist);
    return newWishlist;
  }

  public int numberOfWishlist()
  {
    int number = wishlist.size();
    return number;
  }

  public boolean hasWishlist()
  {
    boolean has = wishlist.size() > 0;
    return has;
  }

  public int indexOfWishlist(VideoGame aWishlist)
  {
    int index = wishlist.indexOf(aWishlist);
    return index;
  }
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }
  /* Code from template association_GetMany */
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWishlist()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addWishlist(VideoGame aWishlist)
  {
    boolean wasAdded = false;
    if (wishlist.contains(aWishlist)) { return false; }
    wishlist.add(aWishlist);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWishlist(VideoGame aWishlist)
  {
    boolean wasRemoved = false;
    if (wishlist.contains(aWishlist))
    {
      wishlist.remove(aWishlist);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWishlistAt(VideoGame aWishlist, int index)
  {  
    boolean wasAdded = false;
    if(addWishlist(aWishlist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlist()) { index = numberOfWishlist() - 1; }
      wishlist.remove(aWishlist);
      wishlist.add(index, aWishlist);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWishlistAt(VideoGame aWishlist, int index)
  {
    boolean wasAdded = false;
    if(wishlist.contains(aWishlist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlist()) { index = numberOfWishlist() - 1; }
      wishlist.remove(aWishlist);
      wishlist.add(index, aWishlist);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWishlistAt(aWishlist, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(String aDate, String aPrice, String aOffersApplied, String aAddress, VideoGame aVideoGame)
  {
    return new Order(aDate, aPrice, aOffersApplied, aAddress, aVideoGame, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    Customer existingCustomer = aOrder.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aOrder.setCustomer(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a customer
    if (!this.equals(aOrder.getCustomer()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(String aContent, String aDate, String aRating, VideoGame aReviewed)
  {
    return new Review(aContent, aDate, aRating, aReviewed, this);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Customer existingReviewer = aReview.getReviewer();
    boolean isNewReviewer = existingReviewer != null && !this.equals(existingReviewer);
    if (isNewReviewer)
    {
      aReview.setReviewer(this);
    }
    else
    {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aReview, as it must always have a reviewer
    if (!this.equals(aReview.getReviewer()))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    purchased.clear();
    wishlist.clear();
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
    for(int i=reviews.size(); i > 0; i--)
    {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]";
  }
}