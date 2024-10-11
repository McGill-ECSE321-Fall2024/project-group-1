/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import java.util.*;

// line 4 "../../../../../../model.ump"
// line 82 "../../../../../../model.ump"
public class GameStore
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameStore Associations
  private List<Person> persons;
  private List<VideoGame> videoGames;
  private List<Category> categories;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameStore()
  {
    persons = new ArrayList<Person>();
    videoGames = new ArrayList<VideoGame>();
    categories = new ArrayList<Category>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Person getPerson(int index)
  {
    Person aPerson = persons.get(index);
    return aPerson;
  }

  public List<Person> getPersons()
  {
    List<Person> newPersons = Collections.unmodifiableList(persons);
    return newPersons;
  }

  public int numberOfPersons()
  {
    int number = persons.size();
    return number;
  }

  public boolean hasPersons()
  {
    boolean has = persons.size() > 0;
    return has;
  }

  public int indexOfPerson(Person aPerson)
  {
    int index = persons.indexOf(aPerson);
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
  public static int minimumNumberOfPersons()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addPerson(Person aPerson)
  {
    boolean wasAdded = false;
    if (persons.contains(aPerson)) { return false; }
    GameStore existingGameStore = aPerson.getGameStore();
    boolean isNewGameStore = existingGameStore != null && !this.equals(existingGameStore);
    if (isNewGameStore)
    {
      aPerson.setGameStore(this);
    }
    else
    {
      persons.add(aPerson);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePerson(Person aPerson)
  {
    boolean wasRemoved = false;
    //Unable to remove aPerson, as it must always have a gameStore
    if (!this.equals(aPerson.getGameStore()))
    {
      persons.remove(aPerson);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPersonAt(Person aPerson, int index)
  {  
    boolean wasAdded = false;
    if(addPerson(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePersonAt(Person aPerson, int index)
  {
    boolean wasAdded = false;
    if(persons.contains(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPersonAt(aPerson, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVideoGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public VideoGame addVideoGame(String aName, String aDescription, String aPrice, String aQuantity, String aDate, VideoGame.Status aStatus, Category aCategory)
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
    while (persons.size() > 0)
    {
      Person aPerson = persons.get(persons.size() - 1);
      aPerson.delete();
      persons.remove(aPerson);
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