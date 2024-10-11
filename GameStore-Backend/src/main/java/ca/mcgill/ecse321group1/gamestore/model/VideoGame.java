/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.util.*;

// line 35 "../../../../../../model.ump"
// line 122 "../../../../../../model.ump"
public class VideoGame
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Pending, Active, Inactive }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //VideoGame Attributes
  private String name;
  private String description;
  private String price;
  private String quantity;
  private String date;
  private Status status;

  //VideoGame Associations
  private GameStore gameStore;
  private List<Review> reviews;
  private Category category;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public VideoGame(String aName, String aDescription, String aPrice, String aQuantity, String aDate, Status aStatus, GameStore aGameStore, Category aCategory)
  {
    name = aName;
    description = aDescription;
    price = aPrice;
    quantity = aQuantity;
    date = aDate;
    status = aStatus;
    boolean didAddGameStore = setGameStore(aGameStore);
    if (!didAddGameStore)
    {
      throw new RuntimeException("Unable to create videoGame due to gameStore. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    reviews = new ArrayList<Review>();
    boolean didAddCategory = setCategory(aCategory);
    if (!didAddCategory)
    {
      throw new RuntimeException("Unable to create videoGame due to category. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
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

  public boolean setQuantity(String aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(String aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public String getPrice()
  {
    return price;
  }

  public String getQuantity()
  {
    return quantity;
  }

  public String getDate()
  {
    return date;
  }

  public Status getStatus()
  {
    return status;
  }
  /* Code from template association_GetOne */
  public GameStore getGameStore()
  {
    return gameStore;
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
  /* Code from template association_GetOne */
  public Category getCategory()
  {
    return category;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGameStore(GameStore aGameStore)
  {
    boolean wasSet = false;
    if (aGameStore == null)
    {
      return wasSet;
    }

    GameStore existingGameStore = gameStore;
    gameStore = aGameStore;
    if (existingGameStore != null && !existingGameStore.equals(aGameStore))
    {
      existingGameStore.removeVideoGame(this);
    }
    gameStore.addVideoGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(String aId, String aContent, String aDate, String aRating, Customer aReviewer)
  {
    return new Review(aId, aContent, aDate, aRating, this, aReviewer);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    VideoGame existingReviewed = aReview.getReviewed();
    boolean isNewReviewed = existingReviewed != null && !this.equals(existingReviewed);
    if (isNewReviewed)
    {
      aReview.setReviewed(this);
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
    //Unable to remove aReview, as it must always have a reviewed
    if (!this.equals(aReview.getReviewed()))
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
  /* Code from template association_SetOneToMany */
  public boolean setCategory(Category aCategory)
  {
    boolean wasSet = false;
    if (aCategory == null)
    {
      return wasSet;
    }

    Category existingCategory = category;
    category = aCategory;
    if (existingCategory != null && !existingCategory.equals(aCategory))
    {
      existingCategory.removeVideoGame(this);
    }
    category.addVideoGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GameStore placeholderGameStore = gameStore;
    this.gameStore = null;
    if(placeholderGameStore != null)
    {
      placeholderGameStore.removeVideoGame(this);
    }
    for(int i=reviews.size(); i > 0; i--)
    {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    Category placeholderCategory = category;
    this.category = null;
    if(placeholderCategory != null)
    {
      placeholderCategory.removeVideoGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "price" + ":" + getPrice()+ "," +
            "quantity" + ":" + getQuantity()+ "," +
            "date" + ":" + getDate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameStore = "+(getGameStore()!=null?Integer.toHexString(System.identityHashCode(getGameStore())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "category = "+(getCategory()!=null?Integer.toHexString(System.identityHashCode(getCategory())):"null");
  }
}