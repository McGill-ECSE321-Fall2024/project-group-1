/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import jakarta.persistence.*;

// line 58 "../../../../../model.ump"
// line 126 "../../../../../model.ump"
@Entity
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String content;
  private Date date;
  private Rating rating;

  //Review Associations
  @ManyToOne
  private VideoGame reviewed;
  @ManyToOne
  private Customer reviewer;
  @OneToOne(cascade = CascadeType.ALL)
  private Reply reply;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetReviewed = true;
  private boolean canSetReviewer = true;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(){
    canSetReviewed = true;
    canSetReviewer = true;
  }

  public Review(int aId, String aContent, Date aDate, Rating aRating, VideoGame aReviewed, Customer aReviewer)
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

  public boolean setRating(Rating aRating)
  {
    boolean wasSet = false;
    rating = aRating;
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

  public Rating getRating()
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
    if (obj instanceof Review rev) return
            rev.content.equals(this.content) &&
            rev.date.toString().equals(this.date.toString()) &&
            rev.rating.equals(this.rating) &&
                    ((rev.reviewed == null && this.reviewed == null) || rev.reviewed != null && rev.reviewed.equals(this.reviewed)) &&
                    ((rev.reviewer == null && this.reviewer == null) || rev.reviewer != null && rev.reviewer.equals(this.reviewer));
    else return false;
    //pre-existing filth
    /*
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

    return true;*/
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
            "content" + ":" + getContent()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewed = "+(getReviewed()!=null?Integer.toHexString(System.identityHashCode(getReviewed())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewer = "+(getReviewer()!=null?Integer.toHexString(System.identityHashCode(getReviewer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reply = "+(getReply()!=null?Integer.toHexString(System.identityHashCode(getReply())):"null");
  }

  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 65 ../../../../../model.ump
  public static enum Rating {oneStar, twoStar, threeStar, fourStar, fiveStar}

}