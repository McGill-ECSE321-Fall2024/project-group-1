/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;

import jakarta.persistence.*;

// line 55 "../../../../../model.ump"
// line 121 "../../../../../model.ump"
@Entity
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  private String content;
  private String date;
  private String rating;

  //Review Associations
  @ManyToOne
  private VideoGame reviewed;
  @ManyToOne
  private Customer reviewer;
  @OneToOne
  private Reply reply;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetReviewed;
  private boolean canSetReviewer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(String aId, String aContent, String aDate, String aRating, VideoGame aReviewed, Customer aReviewer)
  {
    cachedHashCode = -1;
    canSetReviewed = true;
    canSetReviewer = true;
    id = aId;
    content = aContent;
    date = aDate;
    rating = aRating;
    boolean didAddReviewed = setReviewed(aReviewed);
    if (!didAddReviewed)
    {
      throw new RuntimeException("Unable to create review due to reviewed. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddReviewer = setReviewer(aReviewer);
    if (!didAddReviewer)
    {
      throw new RuntimeException("Unable to create review due to reviewer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setRating(String aRating)
  {
    boolean wasSet = false;
    rating = aRating;
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

  public String getRating()
  {
    return rating;
  }
  /* Code from template association_GetOne */
  public VideoGame getReviewed()
  {
    return reviewed;
  }
  /* Code from template association_GetOne */
  public Customer getReviewer()
  {
    return reviewer;
  }
  /* Code from template association_GetOne */
  public Reply getReply()
  {
    return reply;
  }

  public boolean hasReply()
  {
    boolean has = reply != null;
    return has;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setReviewed(VideoGame aReviewed)
  {
    boolean wasSet = false;
    if (!canSetReviewed) { return false; }
    if (aReviewed == null)
    {
      return wasSet;
    }

    VideoGame existingReviewed = reviewed;
    reviewed = aReviewed;
    if (existingReviewed != null && !existingReviewed.equals(aReviewed))
    {
      existingReviewed.removeReview(this);
    }
    if (!reviewed.addReview(this))
    {
      reviewed = existingReviewed;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setReviewer(Customer aReviewer)
  {
    boolean wasSet = false;
    if (!canSetReviewer) { return false; }
    if (aReviewer == null)
    {
      return wasSet;
    }

    Customer existingReviewer = reviewer;
    reviewer = aReviewer;
    if (existingReviewer != null && !existingReviewer.equals(aReviewer))
    {
      existingReviewer.removeReview(this);
    }
    if (!reviewer.addReview(this))
    {
      reviewer = existingReviewer;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setReply(Reply aNewReply)
  {
    boolean wasSet = false;
    if (reply != null && !reply.equals(aNewReply) && equals(reply.getReview()))
    {
      //Unable to setReply, as existing reply would become an orphan
      return wasSet;
    }

    reply = aNewReply;
    Review anOldReview = aNewReply != null ? aNewReply.getReview() : null;

    if (!this.equals(anOldReview))
    {
      if (anOldReview != null)
      {
        anOldReview.reply = null;
      }
      if (reply != null)
      {
        reply.setReview(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Review compareTo = (Review)obj;
  
    if (getReviewed() == null && compareTo.getReviewed() != null)
    {
      return false;
    }
    else if (getReviewed() != null && !getReviewed().equals(compareTo.getReviewed()))
    {
      return false;
    }

    if (getReviewer() == null && compareTo.getReviewer() != null)
    {
      return false;
    }
    else if (getReviewer() != null && !getReviewer().equals(compareTo.getReviewer()))
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
    if (getReviewed() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getReviewed().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getReviewer() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getReviewer().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetReviewed = false;
    canSetReviewer = false;
    return cachedHashCode;
  }

  public void delete()
  {
    VideoGame placeholderReviewed = reviewed;
    this.reviewed = null;
    if(placeholderReviewed != null)
    {
      placeholderReviewed.removeReview(this);
    }
    Customer placeholderReviewer = reviewer;
    this.reviewer = null;
    if(placeholderReviewer != null)
    {
      placeholderReviewer.removeReview(this);
    }
    Reply existingReply = reply;
    reply = null;
    if (existingReply != null)
    {
      existingReply.delete();
      existingReply.setReview(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "content" + ":" + getContent()+ "," +
            "date" + ":" + getDate()+ "," +
            "rating" + ":" + getRating()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "reviewed = "+(getReviewed()!=null?Integer.toHexString(System.identityHashCode(getReviewed())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewer = "+(getReviewer()!=null?Integer.toHexString(System.identityHashCode(getReviewer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reply = "+(getReply()!=null?Integer.toHexString(System.identityHashCode(getReply())):"null");
  }
}