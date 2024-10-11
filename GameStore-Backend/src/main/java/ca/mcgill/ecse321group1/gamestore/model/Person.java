/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;

// line 10 "../../../../../../model.ump"
public abstract class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String username;
  private String email;
  private String passwordHash;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aUsername, String aEmail, String aPasswordHash)
  {
    username = aUsername;
    email = aEmail;
    passwordHash = aPasswordHash;
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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "email" + ":" + getEmail()+ "," +
            "passwordHash" + ":" + getPasswordHash()+ "]";
  }
}