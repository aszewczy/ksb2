package pl.szewczyk.ksb2;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoApi {

     private List<Video> videoList = new ArrayList<>();

     public VideoApi(){
         this.videoList = new ArrayList<>();
         videoList.add(new Video(1L,"Testowanie automatyczne z wykorzystaniem mocków", "https://www.youtube.com/watch?v=n2SWC-yMg6o"));
         videoList.add(new Video(2L,"Spring Security od podstaw", "https://www.youtube.com/watch?v=1S1XydtDgo8"));
     }

     @GetMapping
     public ResponseEntity<List<Video>> getVideo(){
        return new ResponseEntity<>(videoList, HttpStatus.OK);
     }

    @GetMapping("/{id}")
    public ResponseEntity<List<Video>> getVideoById(@PathVariable long id){
         Optional<Video> first = videoList.stream().filter(video -> video.getId() == id).findFirst();
         if(first.isPresent()){
             return new ResponseEntity(first, HttpStatus.OK);
         }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addVideo(@RequestBody Video video){
         boolean add = videoList.add(video);
            if(add){
                return new ResponseEntity(HttpStatus.CREATED);
            }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modVideo(@RequestBody Video newVideo){
        Optional<Video> first = videoList.stream().filter(video -> video.getId() == newVideo.getId()).findFirst();
        if(first.isPresent()){
           videoList.remove(first.get());
           videoList.add(newVideo);
                   return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity( HttpStatus.NOT_FOUND);
    }


    @PutMapping
    public ResponseEntity removeVideo(@PathVariable long id){
        Optional<Video> first = videoList.stream().filter(video -> video.getId() == id).findFirst();
        if(first.isPresent()){
            videoList.remove(first.get());
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity( HttpStatus.NOT_FOUND);
    }


}
