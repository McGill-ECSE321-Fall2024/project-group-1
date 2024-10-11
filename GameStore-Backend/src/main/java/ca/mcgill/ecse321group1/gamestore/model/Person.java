/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;

// line 10 "../../../../../../model.ump"
// line 97 "../../../../../../model.ump"
@jakarta.persistence.Entity
public abstract class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String username;
  private String email;
  private String passwordHash;

  //Person Associations
  private GameStore gameStore;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aUsername, String aEmail, String aPasswordHash, GameStore aGameStore)
  {
    username = aUsername;
    email = aEmail;
    passwordHash = aPasswordHash;
    boolean didAddGameStore = setGameStore(aGameStore);
    if (!didAddGameStore)
    {
      throw new RuntimeException("Unable to create person due to gameStore. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPasswordHash(String aPasswordHash)
  {
    boolean wasSet = false;
    passwordHash = aPasswordHash;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPasswordHash()
  {
    return passwordHash;
  }
  /* Code from template association_GetOne */
  public GameStore getGameStore()
  {
    return gameStore;
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
      existingGameStore.removePerson(this);
    }
    gameStore.addPerson(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GameStore placeholderGameStore = gameStore;
    this.gameStore = null;
    if(placeholderGameStore != null)
    {
      placeholderGameStore.removePerson(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "email" + ":" + getEmail()+ "," +
            "passwordHash" + ":" + getPasswordHash()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "gameStore = "+(getGameStore()!=null?Integer.toHexString(System.identityHashCode(getGameStore())):"null");
  }
}