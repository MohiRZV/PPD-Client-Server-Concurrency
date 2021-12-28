package controller;

import model.Spectacol;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Service;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping(value="/reserve", consumes = "application/json")
    public ResponseEntity<?> reserve(@RequestBody String body) {
        JSONObject params = new JSONObject(body);
        JSONArray jsonArray = (JSONArray) params.get("seats");
        int id_spectacol = params.getInt("id_spectacol");

        List<Integer> list = new ArrayList<>();
        for(Object place: jsonArray){
            list.add((Integer) place);
        }

        return new ResponseEntity<>(service.reserve(list, id_spectacol), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> all() {
        return new ResponseEntity<>(service.getAllSpectacole().toString(), HttpStatus.OK);
    }
}

