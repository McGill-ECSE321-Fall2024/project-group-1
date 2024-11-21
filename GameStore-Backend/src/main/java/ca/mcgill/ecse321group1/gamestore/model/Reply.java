//PLEASE DO NOT EDIT THIS CODE/
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!/
package ca.mcgill.ecse321group1.gamestore.model;
import java.sql.Date;
import jakarta.persistence.*;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

// line 71 "../../../../../../model.ump"
// line 146 "../../../../../../model.ump"
@Entity
public class Reply
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reply Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String content;
  private Date date;

  //Reply Associations
  @ManyToOne
  private Review review;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reply(int aId, String aContent, Date aDate, Review aReview)
  {
    id = aId;
    content = aContent;
    date = aDate;
    if (!setReview(aReview))
    {
      throw new RuntimeException("Unable to create Reply due to aReview. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Reply(){

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

  public boolean setContent(String aContent)
  {
    boolean wasSet = false;
    content = aContent;
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

  public int getId()
  {
    return id;
  }

  public String getContent()
  {
    return content;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Review getReview()
  {
    return review;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setReview(Review aNewReview)
  {
    boolean wasSet = false;
    if (aNewReview != null)
    {
      review = aNewReview;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    review = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "content" + ":" + getContent()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "review = "+(getReview()!=null?Integer.toHexString(System.identityHashCode(getReview())):"null");
  }

  public boolean equals (Object obj) {
    if (obj instanceof Reply reply) return
            this.content.equals(reply.content) &&
            this.date.equals(reply.date) &&
            this.review.equals(reply.review);
    return false;
  }
}