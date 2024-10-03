/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

/**
 * 1. namespace
 * 2. classes + attributes
 * 3. associations
 * 4. composition at highest level
 * namespace ca.mcgill.ecse.snorelam.model;
 * namespace ca.mcgill.ecse.wareflow.model;
 */
// line 11 "model.ump"
// line 101 "model.ump"
public class GameStore
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Pending, Active, Inactive }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameStore Associations
  private List<User> users;
  private List<VideoGame> videoGames;
  private List<Category> categories;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameStore()
  {
    users = new ArrayList<User>();
    videoGames = new ArrayList<VideoGame>();
    categories = new ArrayList<Category>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  /**
   * add composition relationships
   * subclasses, other compositions (part) and enumerations don't have compositions with the main class
   */
  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
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
  /* Code from template association_GetMany */
  public Category getCategory(int index)
  {
    Category aCategory = categories.get(index);
    return aCategory;
  }

  public List<Category> getCategories()
  {
    List<Category> newCategories = Collections.unmodifiableList(categories);
    return newCategories;
  }

  public int numberOfCategories()
  {
    int number = categories.size();
    return number;
  }

  public boolean hasCategories()
  {
    boolean has = categories.size() > 0;
    return has;
  }

  public int indexOfCategory(Category aCategory)
  {
    int index = categories.indexOf(aCategory);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    GameStore existingGameStore = aUser.getGameStore();
    boolean isNewGameStore = existingGameStore != null && !this.equals(existingGameStore);
    if (isNewGameStore)
    {
      aUser.setGameStore(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a gameStore
    if (!this.equals(aUser.getGameStore()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVideoGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public VideoGame addVideoGame(String aName, String aDescription, String aPrice, String aQuantity, String aDate, Status aStatus, Category aCategory)
  {
    return new VideoGame(aName, aDescription, aPrice, aQuantity, aDate, aStatus, this, aCategory);
  }

  public boolean addVideoGame(VideoGame aVideoGame)
  {
    boolean wasAdded = false;
    if (videoGames.contains(aVideoGame)) { return false; }
    GameStore existingGameStore = aVideoGame.getGameStore();
    boolean isNewGameStore = existingGameStore != null && !this.equals(existingGameStore);
    if (isNewGameStore)
    {
      aVideoGame.setGameStore(this);
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
    //Unable to remove aVideoGame, as it must always have a gameStore
    if (!this.equals(aVideoGame.getGameStore()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCategories()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Category addCategory(String aName, String aDescription)
  {
    return new Category(aName, aDescription, this);
  }

  public boolean addCategory(Category aCategory)
  {
    boolean wasAdded = false;
    if (categories.contains(aCategory)) { return false; }
    GameStore existingGameStore = aCategory.getGameStore();
    boolean isNewGameStore = existingGameStore != null && !this.equals(existingGameStore);
    if (isNewGameStore)
    {
      aCategory.setGameStore(this);
    }
    else
    {
      categories.add(aCategory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCategory(Category aCategory)
  {
    boolean wasRemoved = false;
    //Unable to remove aCategory, as it must always have a gameStore
    if (!this.equals(aCategory.getGameStore()))
    {
      categories.remove(aCategory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCategoryAt(Category aCategory, int index)
  {  
    boolean wasAdded = false;
    if(addCategory(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCategoryAt(Category aCategory, int index)
  {
    boolean wasAdded = false;
    if(categories.contains(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCategoryAt(aCategory, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (videoGames.size() > 0)
    {
      VideoGame aVideoGame = videoGames.get(videoGames.size() - 1);
      aVideoGame.delete();
      videoGames.remove(aVideoGame);
    }
    
    while (categories.size() > 0)
    {
      Category aCategory = categories.get(categories.size() - 1);
      aCategory.delete();
      categories.remove(aCategory);
    }
    
  }

}