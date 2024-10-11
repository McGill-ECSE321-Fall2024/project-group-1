package main.java.ca.mcgill.ecse321group1.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.util.*;

// line 58 "model.ump"
// line 145 "model.ump"
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  private String content;
  private String date;
  private String rating;

  //Review Associations
  private VideoGame reviewed;
  private Customer reviewer;
  private List<Reply> replies;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetReviewed;
  private boolean canSetReviewer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(String aContent, String aDate, String aRating, VideoGame aReviewed, Customer aReviewer)
  {
    cachedHashCode = -1;
    canSetReviewed = true;
    canSetReviewer = true;
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
    replies = new ArrayList<Reply>();
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

  public boolean setRating(String aRating)
  {
    boolean wasSet = false;
    rating = aRating;
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
  /* Code from template association_GetMany */
  public Reply getReply(int index)
  {
    Reply aReply = replies.get(index);
    return aReply;
  }

  public List<Reply> getReplies()
  {
    List<Reply> newReplies = Collections.unmodifiableList(replies);
    return newReplies;
  }

  public int numberOfReplies()
  {
    int number = replies.size();
    return number;
  }

  public boolean hasReplies()
  {
    boolean has = replies.size() > 0;
    return has;
  }

  public int indexOfReply(Reply aReply)
  {
    int index = replies.indexOf(aReply);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReplies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Reply addReply(String aContent, String aDate)
  {
    return new Reply(aContent, aDate, this);
  }

  public boolean addReply(Reply aReply)
  {
    boolean wasAdded = false;
    if (replies.contains(aReply)) { return false; }
    Review existingReview = aReply.getReview();
    boolean isNewReview = existingReview != null && !this.equals(existingReview);
    if (isNewReview)
    {
      aReply.setReview(this);
    }
    else
    {
      replies.add(aReply);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReply(Reply aReply)
  {
    boolean wasRemoved = false;
    //Unable to remove aReply, as it must always have a review
    if (!this.equals(aReply.getReview()))
    {
      replies.remove(aReply);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReplyAt(Reply aReply, int index)
  {  
    boolean wasAdded = false;
    if(addReply(aReply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReplies()) { index = numberOfReplies() - 1; }
      replies.remove(aReply);
      replies.add(index, aReply);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReplyAt(Reply aReply, int index)
  {
    boolean wasAdded = false;
    if(replies.contains(aReply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReplies()) { index = numberOfReplies() - 1; }
      replies.remove(aReply);
      replies.add(index, aReply);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReplyAt(aReply, index);
    }
    return wasAdded;
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
    while (replies.size() > 0)
    {
      Reply aReply = replies.get(replies.size() - 1);
      aReply.delete();
      replies.remove(aReply);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "content" + ":" + getContent()+ "," +
            "date" + ":" + getDate()+ "," +
            "rating" + ":" + getRating()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "reviewed = "+(getReviewed()!=null?Integer.toHexString(System.identityHashCode(getReviewed())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewer = "+(getReviewer()!=null?Integer.toHexString(System.identityHashCode(getReviewer())):"null");
  }
}