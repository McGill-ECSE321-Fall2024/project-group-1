package ca.mcgill.ecse321group1.gamestore.dto;

// import java.util.List;

// Based off Louis Hildebrand tutorial example code
public class ErrorDto {
    // private List<String> errors;
    private String error;

    // Needs no-arg constructor
    @SuppressWarnings("unused")
    private ErrorDto() {
    }

    /* 
    public ErrorDto(List<String> errors) {
        this.errors = errors;
    }
    */

    // Currently only having only one error message. May need to change this later.
    public ErrorDto(String error) {
       // this.errors = List.of(error);
       this.error = error;
    }

    public String getError() {
        return error;
    }

    /* 
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    */
}
