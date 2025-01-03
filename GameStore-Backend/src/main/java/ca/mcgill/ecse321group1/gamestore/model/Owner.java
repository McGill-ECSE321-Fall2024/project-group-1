/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import jakarta.persistence.*;

// line 26 "../../../../../../model.ump"
// line 112 "../../../../../../model.ump"
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

  public Owner(int aId, String aUsername, String aEmail, String aPasswordHash)
  {
    super(aId, aUsername, aEmail, aPasswordHash);
  }
  public Owner(){
    super();
  }


  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  public boolean equals (Object obj) {
    return obj instanceof Owner && super.equals(obj);
  }
}