package controller;

import model.Spectacol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Service;

@CrossOrigin
@RestController
@RequestMapping("/ppd")
public class Controller {
    private Service service = new Service();

    @GetMapping("/spectacol")
    public ResponseEntity<?> getSpectacol(@RequestParam int id) {
        Spectacol spectacol = service.getSpectacol(id);

        if(spectacol!=null) {
            return new ResponseEntity<>(spectacol, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid id", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> all() {
        return new ResponseEntity<>(service.getAllSpectacole().toString(), HttpStatus.OK);
    }
}

