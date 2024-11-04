/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321group1.gamestore.model;
import jakarta.persistence.*;
// line 21 "../../../../../../model.ump"
// line 97 "../../../../../../model.ump"
@Entity
@DiscriminatorValue("Staff")
public class Staff extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Staff(){
    super();
  }

  public Staff(int aId, String aUsername, String aEmail, String aPasswordHash)
  {
    super(aId, aUsername, aEmail, aPasswordHash);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}