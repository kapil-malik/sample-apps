package com.kmalik.sample.text;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class TextRestController {

  private Map<String, String> map;
  
  @PostConstruct
  public void init() {
    this.map = new HashMap<String, String>();
  }
  
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/hello", method = GET)
  public String hello(
    @RequestParam(value = "name", defaultValue = "sir", required = false) String name) {
    System.out.println("Hello, name = " + name);
    return "Hello " + name;
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/map", method = POST)
  public String writeMap(
    @RequestParam(value = "key", defaultValue = "key", required = false) String key,
    @RequestParam(value = "value", defaultValue = "value", required = false) String value) {
    System.out.println("Write map : " + key + "=" + value);
    
    final String prev = map.containsKey(key) ? map.get(key) : "";
    map.put(key, value);
    return prev;
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/map", method = GET)
  public String readMap(@RequestParam(value = "key", defaultValue = "", required = false) String key) {
    System.out.println("Read map : " + key);
    
    if (key == null || "".equals(key.trim())) {
      return map.toString();
    }
    return map.containsKey(key) ? map.get(key) : "NONE";
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/map", method = DELETE)
  public String deleteMap(@RequestParam(value = "key", defaultValue = "", required = false) String key) {
    System.out.println("Delete map : " + key);
    
    String value;
    if (key == null || "".equals(key.trim())) {
      map.clear();
      value = map.toString();
    } else {
      value = map.remove(key);  
    }    
    return value == null ? "" : value;
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/exit", method = POST)
  public void exit() {
    System.out.println("Exit");
    System.exit(1);
  }
  
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/status", method = GET)
  public String getStatus() {
    System.out.println("Status");
    return "running";
  }

}
