/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;

// line 106 "../../../../../../model.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Associations
  private GameStore gameStore;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(GameStore aGameStore)
  {
    boolean didAddGameStore = setGameStore(aGameStore);
    if (!didAddGameStore)
    {
      throw new RuntimeException("Unable to create user due to gameStore. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
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

}