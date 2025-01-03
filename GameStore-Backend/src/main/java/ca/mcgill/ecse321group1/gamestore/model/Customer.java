/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.util.*;
import java.sql.Date;
import jakarta.persistence.*;
import ca.mcgill.ecse321group1.gamestore.model.Review.Rating;


// line 12 "../../../../../../model.ump"
// line 98 "../../../../../../model.ump"
@Entity
@DiscriminatorValue("Customer")
public class Customer extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String address;
  private String phoneNumber;

  //Customer Associations
  @OneToMany
  private List<VideoGame> wishlist;
  @OneToMany
  private List<VideoGame> cart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(int aId, String aUsername, String aEmail, String aPasswordHash, String aAddress, String aPhoneNumber)
  {
    super(aId, aUsername, aEmail, aPasswordHash);
    address = aAddress;
    phoneNumber = aPhoneNumber;
    wishlist = new ArrayList<VideoGame>();
    cart = new ArrayList<VideoGame>();
  }

  public Customer(){
    super();
    wishlist = new ArrayList<VideoGame>();
    cart = new ArrayList<VideoGame>();
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
  public VideoGame getCart(int index)
  {
    VideoGame aCart = cart.get(index);
    return aCart;
  }

  public List<VideoGame> getCart()
  {
    List<VideoGame> newCart = Collections.unmodifiableList(cart);
    return newCart;
  }

  public int numberOfCart()
  {
    int number = cart.size();
    return number;
  }

  public boolean hasCart()
  {
    boolean has = cart.size() > 0;
    return has;
  }

  public int indexOfCart(VideoGame aCart)
  {
    int index = cart.indexOf(aCart);
    return index;
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
  public static int minimumNumberOfCart()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCart(VideoGame aCart)
  {
    boolean wasAdded = false;
    //if (cart.contains(aCart)) { return false; } //REMOVED
    cart.add(aCart);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCart(VideoGame aCart)
  {
    boolean wasRemoved = false;
    if (cart.contains(aCart))
    {
      cart.remove(aCart);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCartAt(VideoGame aCart, int index)
  {
    boolean wasAdded = false;
    if(addCart(aCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCart()) { index = numberOfCart() - 1; }
      cart.remove(aCart);
      cart.add(index, aCart);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCartAt(VideoGame aCart, int index)
  {
    boolean wasAdded = false;
    if(cart.contains(aCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCart()) { index = numberOfCart() - 1; }
      cart.remove(aCart);
      cart.add(index, aCart);
      wasAdded = true;
    }
    else
    {
      wasAdded = addCartAt(aCart, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    wishlist.clear();
    cart.clear();
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Customer cust) return super.equals(cust) &&
            cust.getAddress().equals(this.getAddress()) &&
            cust.getPhoneNumber().equals(this.getPhoneNumber());
    else return false;
  }
}