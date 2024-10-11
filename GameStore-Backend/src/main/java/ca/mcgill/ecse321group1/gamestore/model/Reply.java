/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;

// line 68 "../../../../../../model.ump"
// line 158 "../../../../../../model.ump"
public class Reply
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reply Attributes
  private String content;
  private String date;

  //Reply Associations
  private Review review;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reply(String aContent, String aDate, Review aReview)
  {
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
  /* Code from template association_SetOneToMany */
  public boolean setReview(Review aReview)
  {
    boolean wasSet = false;
    if (aReview == null)
    {
      return wasSet;
    }

    Review existingReview = review;
    review = aReview;
    if (existingReview != null && !existingReview.equals(aReview))
    {
      existingReview.removeReply(this);
    }
    review.addReply(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Review placeholderReview = review;
    this.review = null;
    if(placeholderReview != null)
    {
      placeholderReview.removeReply(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "content" + ":" + getContent()+ "," +
            "date" + ":" + getDate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "review = "+(getReview()!=null?Integer.toHexString(System.identityHashCode(getReview())):"null");
  }
}