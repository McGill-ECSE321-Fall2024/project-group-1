/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.util.*;
import java.sql.Date;
import jakarta.persistence.*;

// line 41 "../../../../../../model.ump"
// line 112 "../../../../../../model.ump"
@Entity
public class Category
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;

  //Category Associations
  @OneToMany
  private List<VideoGame> videoGames;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Empty constructor
  public Category(){
    videoGames = new ArrayList<VideoGame>();
  }

  public Category(int aId, String aName, String aDescription)
  {
    id = aId;
    name = aName;
    description = aDescription;
    videoGames = new ArrayList<VideoGame>();
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
  /* Code from template association_GetMany */
  public VideoGame getVideoGame(int index)
  {
    VideoGame aVideoGame = videoGames.get(index);
    return aVideoGame;
  }

  public List<VideoGame> getVideoGames()
  {
    List<VideoGame> newVideoGames = Collections.unmodifiableList(videoGames);
    return newVideoGames;
  }

  public int numberOfVideoGames()
  {
    int number = videoGames.size();
    return number;
  }

  public boolean hasVideoGames()
  {
    boolean has = videoGames.size() > 0;
    return has;
  }

  public int indexOfVideoGame(VideoGame aVideoGame)
  {
    int index = videoGames.indexOf(aVideoGame);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVideoGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public VideoGame addVideoGame(int aId, String aName, String aDescription, float aPrice, int aQuantity, Date aDate, VideoGame.Status aStatus)
  {
    return new VideoGame(aId, aName, aDescription, aPrice, aQuantity, aDate, aStatus, this);
  }

  public boolean addVideoGame(VideoGame aVideoGame)
  {
    boolean wasAdded = false;
    if (videoGames.contains(aVideoGame)) { return false; }
    Category existingCategory = aVideoGame.getCategory();
    boolean isNewCategory = existingCategory != null && !this.equals(existingCategory);
    if (isNewCategory)
    {
      aVideoGame.setCategory(this);
    }
    else
    {
      videoGames.add(aVideoGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVideoGame(VideoGame aVideoGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aVideoGame, as it must always have a category
    if (!this.equals(aVideoGame.getCategory()))
    {
      videoGames.remove(aVideoGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVideoGameAt(VideoGame aVideoGame, int index)
  {  
    boolean wasAdded = false;
    if(addVideoGame(aVideoGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVideoGames()) { index = numberOfVideoGames() - 1; }
      videoGames.remove(aVideoGame);
      videoGames.add(index, aVideoGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVideoGameAt(VideoGame aVideoGame, int index)
  {
    boolean wasAdded = false;
    if(videoGames.contains(aVideoGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVideoGames()) { index = numberOfVideoGames() - 1; }
      videoGames.remove(aVideoGame);
      videoGames.add(index, aVideoGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVideoGameAt(aVideoGame, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=videoGames.size(); i > 0; i--)
    {
      VideoGame aVideoGame = videoGames.get(i - 1);
      aVideoGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "]";
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof Category tbc)) return false;
    return this.name.equals(tbc.name) && this.description.equals(tbc.description);
  }
}