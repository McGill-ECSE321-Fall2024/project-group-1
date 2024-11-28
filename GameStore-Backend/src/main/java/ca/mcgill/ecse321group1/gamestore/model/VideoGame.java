/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.sql.Date;
import jakarta.persistence.*;

// line 30 "../../../../../../model.ump"
// line 117 "../../../../../../model.ump"
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
  @ManyToOne
  private Category category;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public VideoGame(int aId, String aName, String aDescription, float aPrice, int aQuantity, Date aDate, Status aStatus, Category aCategory)
  {
    id = aId;
    name = aName;
    description = aDescription;
    price = aPrice;
    quantity = aQuantity;
    date = aDate;
    status = aStatus;
    if (!setCategory(aCategory))
    {
      throw new RuntimeException("Unable to create VideoGame due to aCategory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public VideoGame(){}


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
  /* Code from template association_GetOne */
  public Category getCategory()
  {
    return category;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCategory(Category aNewCategory)
  {
    boolean wasSet = false;
    if (aNewCategory != null)
    {
      category = aNewCategory;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    category = null;
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

  public boolean equals (Object obj) {
    if (obj instanceof VideoGame game) return
            Math.pow(this.price - game.price, 2) < 1E-3 &&
                    this.name.equals(game.name) && this.description.equals(game.description) &&
                    this.quantity == game.quantity && this.date.toString().equals(game.date.toString()) &&
                    this.status.equals(game.status) && this.category.equals(game.category);
    return false;
  }
}