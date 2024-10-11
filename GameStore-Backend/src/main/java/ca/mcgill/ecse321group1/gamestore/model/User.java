package main.java.ca.mcgill.ecse321group1.gamestore.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/



// line 8 "model.ump"
// line 104 "model.ump"
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  private String email;
  private String passwordHash;

  //User Associations
  private GameStore gameStore;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aEmail, String aPasswordHash, GameStore aGameStore)
  {
    username = aUsername;
    email = aEmail;
    passwordHash = aPasswordHash;
    boolean didAddGameStore = setGameStore(aGameStore);
    if (!didAddGameStore)
    {
      throw new RuntimeException("Unable to create user due to gameStore. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      existingGameStore.removeUser(this);
    }
    gameStore.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GameStore placeholderGameStore = gameStore;
    this.gameStore = null;
    if(placeholderGameStore != null)
    {
      placeholderGameStore.removeUser(this);
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