package ProductCatalogServiceProxy.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//This class is specially for handling exceptions. Rest Controller Advice is to advice something
//to the user. ExceptionHandler will automatically picks up the exception wherever it was thrown
//in Controller module. It comes here and execute the message.
//We can give different message for different exceptions based on exception type provided
//in ExceptionHandler Argument i.e. it handles now only IllegalArgument exceptions
//If we want to get the same message for all exceptions we write as
//ExceptionHandler({Exception.class}
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleException(IllegalArgumentException ex)
    {
        return new ResponseEntity<String>("Something went wrong i.e.: "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
