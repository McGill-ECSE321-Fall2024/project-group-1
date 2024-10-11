/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// line 25 "../../../../../../model.ump"
// line 98 "../../../../../../model.ump"
@Entity
@DiscriminatorValue("Owner")
public class Owner extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aEmail, String aPasswordHash)
  {
    super(aUsername, aEmail, aPasswordHash);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}