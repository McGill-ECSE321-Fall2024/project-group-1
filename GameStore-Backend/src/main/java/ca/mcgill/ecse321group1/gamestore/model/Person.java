/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import jakarta.persistence.*;
// line 4 "../../../../../../model.ump"
// line 83 "../../../../../../model.ump"
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PersonType")
public abstract class Person
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String username;
  private String email;
  private String passwordHash;

  //Autounique Attributes
  @Id
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(){
    
  }
  public Person(String aUsername, String aEmail, String aPasswordHash)
  {
    username = aUsername;
    email = aEmail;
    passwordHash = aPasswordHash;
    id = nextId++;
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

  public int getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "username" + ":" + getUsername()+ "," +
            "email" + ":" + getEmail()+ "," +
            "passwordHash" + ":" + getPasswordHash()+ "]";
  }
}