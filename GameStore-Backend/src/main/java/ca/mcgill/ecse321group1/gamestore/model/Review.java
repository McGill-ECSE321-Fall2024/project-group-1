/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import jakarta.persistence.*;

// line 61 "../../../../../../model.ump"
// line 135 "../../../../../../model.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(int aId, String aContent, Date aDate, Rating aRating, VideoGame aReviewed, Customer aReviewer)
  {
    id = aId;
    content = aContent;
    date = aDate;
    rating = aRating;
    if (!setReviewed(aReviewed))
    {
      throw new RuntimeException("Unable to create Review due to aReviewed. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setReviewer(aReviewer))
    {
      throw new RuntimeException("Unable to create Review due to aReviewer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Review(){}

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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setReviewed(VideoGame aNewReviewed)
  {
    boolean wasSet = false;
    if (aNewReviewed != null)
    {
      reviewed = aNewReviewed;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setReviewer(Customer aNewReviewer)
  {
    boolean wasSet = false;
    if (aNewReviewer != null)
    {
      reviewer = aNewReviewer;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    reviewed = null;
    reviewer = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "content" + ":" + getContent()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewed = "+(getReviewed()!=null?Integer.toHexString(System.identityHashCode(getReviewed())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewer = "+(getReviewer()!=null?Integer.toHexString(System.identityHashCode(getReviewer())):"null");
  }
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------

  // line 68 "../../../../../../model.ump"
  public enum Rating {oneStar, twoStar, threeStar, fourStar, fiveStar}

  public boolean equals(Object obj)
  {
    if (obj instanceof Review rev) return
            rev.content.equals(this.content) &&
                    rev.date.toString().equals(this.date.toString()) &&
                    rev.rating.equals(this.rating) &&
                    ((rev.reviewed == null && this.reviewed == null) || rev.reviewed != null && rev.reviewed.equals(this.reviewed)) &&
                    ((rev.reviewer == null && this.reviewer == null) || rev.reviewer != null && rev.reviewer.equals(this.reviewer));
    else return false;
  }
}