/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import jakarta.persistence.*;

// line 64 "../../../../../../model.ump"
// line 132 "../../../../../../model.ump"
@Entity
public class Reply
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reply Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  private String content;
  private String date;

  //Reply Associations
  @OneToOne
  private Review review;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reply(String aId, String aContent, String aDate, Review aReview)
  {
    id = aId;
    content = aContent;
    date = aDate;
    boolean didAddReview = setReview(aReview);
    if (!didAddReview)
    {
      throw new RuntimeException("Unable to create reply due to review. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
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

  public boolean setDate(String aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String getContent()
  {
    return content;
  }

  public String getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Review getReview()
  {
    return review;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setReview(Review aNewReview)
  {
    boolean wasSet = false;
    if (aNewReview == null)
    {
      //Unable to setReview to null, as reply must always be associated to a review
      return wasSet;
    }
    
    Reply existingReply = aNewReview.getReply();
    if (existingReply != null && !equals(existingReply))
    {
      //Unable to setReview, the current review already has a reply, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Review anOldReview = review;
    review = aNewReview;
    review.setReply(this);

    if (anOldReview != null)
    {
      anOldReview.setReply(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Review existingReview = review;
    review = null;
    if (existingReview != null)
    {
      existingReview.setReply(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "content" + ":" + getContent()+ "," +
            "date" + ":" + getDate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "review = "+(getReview()!=null?Integer.toHexString(System.identityHashCode(getReview())):"null");
  }
}