/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import jakarta.persistence.*;

import java.sql.Date;


/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

import java.sql.Date;

// line 78 "../../../../../../model.ump"
// line 154 "../../../../../../model.ump"
@Entity
public class Offer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Offer Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  private String effect;
  private Date startDate;
  private Date endDate;

  //Offer Associations
  @ManyToOne
  private VideoGame videoGame;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Offer(int aId, String aName, String aDescription, String aEffect, Date aStartDate, Date aEndDate)
  {
    id = aId;
    name = aName;
    description = aDescription;
    effect = aEffect;
    startDate = aStartDate;
    endDate = aEndDate;
  }
  public Offer(){}


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

  public boolean setEffect(String aEffect)
  {
    boolean wasSet = false;
    effect = aEffect;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
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

  public String getEffect()
  {
    return effect;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }
  /* Code from template association_GetOne */
  public VideoGame getVideoGame()
  {
    return videoGame;
  }

  public boolean hasVideoGame()
  {
    boolean has = videoGame != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setVideoGame(VideoGame aNewVideoGame)
  {
    boolean wasSet = false;
    videoGame = aNewVideoGame;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    videoGame = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "effect" + ":" + getEffect()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "videoGame = "+(getVideoGame()!=null?Integer.toHexString(System.identityHashCode(getVideoGame())):"null");
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Offer offer) return
            offer.name.equals(this.name) &&
                    offer.description.equals(this.description) &&
                    offer.effect.equals(this.effect) &&
                    offer.startDate.toString().equals(this.startDate.toString()) &&
                    offer.endDate.toString().equals(this.endDate.toString()) &&
            (this.videoGame == offer.videoGame) || (this.videoGame != null && this.videoGame.equals(offer.videoGame));
    return false;
  }
}