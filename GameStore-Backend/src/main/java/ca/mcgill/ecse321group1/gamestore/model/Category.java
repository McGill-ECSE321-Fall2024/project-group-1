/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 56 "model.ump"
// line 144 "model.ump"
public class Category
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Pending, Active, Inactive }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  private String name;
  private String description;

  //Category Associations
  private GameStore gameStore;
  private List<VideoGame> videoGames;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(String aName, String aDescription, GameStore aGameStore)
  {
    name = aName;
    description = aDescription;
    boolean didAddGameStore = setGameStore(aGameStore);
    if (!didAddGameStore)
    {
      throw new RuntimeException("Unable to create category due to gameStore. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    videoGames = new ArrayList<VideoGame>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public GameStore getGameStore()
  {
    return gameStore;
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
  /* Code from template association_SetOneToMany */
  public boolean setGameStore(GameStore aGameStore)
  {
    boolean wasSet = false;
    if (aGameStore == null)
    {
      return wasSet;
    }

    GameStore existingGameStore = gameStore;
    gameStore = aGameStore;
    if (existingGameStore != null && !existingGameStore.equals(aGameStore))
    {
      existingGameStore.removeCategory(this);
    }
    gameStore.addCategory(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVideoGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public VideoGame addVideoGame(String aName, String aDescription, String aPrice, String aQuantity, String aDate, Status aStatus, GameStore aGameStore)
  {
    return new VideoGame(aName, aDescription, aPrice, aQuantity, aDate, aStatus, aGameStore, this);
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
    GameStore placeholderGameStore = gameStore;
    this.gameStore = null;
    if(placeholderGameStore != null)
    {
      placeholderGameStore.removeCategory(this);
    }
    for(int i=videoGames.size(); i > 0; i--)
    {
      VideoGame aVideoGame = videoGames.get(i - 1);
      aVideoGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "gameStore = "+(getGameStore()!=null?Integer.toHexString(System.identityHashCode(getGameStore())):"null");
  }
}