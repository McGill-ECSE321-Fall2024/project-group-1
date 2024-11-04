/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.sql.Date;
import java.util.*;
import jakarta.persistence.*;
import ca.mcgill.ecse321group1.gamestore.model.Review.Rating;

// line 30 "../../../../../../model.ump"
// line 108 "../../../../../../model.ump"
@Entity
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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  private float price;
  private int quantity;
  private Date date;
  private Status status;

  //VideoGame Associations
  @OneToMany
  private List<Review> reviews;
  @ManyToOne
  private Category category;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public VideoGame(){
    reviews = new ArrayList<Review>();
  }

  public VideoGame(int aId, String aName, String aDescription, float aPrice, int aQuantity, Date aDate, Status aStatus, Category aCategory)
  {
    id = aId;
    name = aName;
    description = aDescription;
    price = aPrice;
    quantity = aQuantity;
    date = aDate;
    status = aStatus;
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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

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

  public boolean setDate(Date aDate)
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

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public float getPrice()
  {
    return price;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public Date getDate()
  {
    return date;
  }

  public Status getStatus()
  {
    return status;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(int aId, String aContent, Date aDate, Rating aRating, Customer aReviewer)
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
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "price" + ":" + getPrice()+ "," +
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "category = "+(getCategory()!=null?Integer.toHexString(System.identityHashCode(getCategory())):"null");
  }
}