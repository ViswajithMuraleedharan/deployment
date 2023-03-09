package com.geekster.instagram.contoller;

import com.geekster.instagram.dao.UserRepository;
import com.geekster.instagram.model.Post;
import com.geekster.instagram.model.User;
import com.geekster.instagram.service.PostService;
import com.geekster.instagram.service.UserService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
public class PostController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @PostMapping(value = "/post")
    public ResponseEntity<String> savePost(@RequestBody String postRequest){
        Post post=setPost(postRequest);
        int postId= postService.savePost(post);
        return new ResponseEntity<>(String.valueOf(postId),HttpStatus.CREATED);
    }

    private Post setPost(String postRequest) {
        JSONObject jsonObject=new JSONObject(postRequest);
        User user=null;
        int userId=jsonObject.getInt("userId");
        if(userRepository.findById(userId).isPresent()) {
            user = userRepository.findById(userId).get();
        }
        else {
            return null;
        }
        Post post=new Post();
        post.setUser(user);
        post.setPostData(jsonObject.getString("postData"));
        Timestamp currTime=new Timestamp(System.currentTimeMillis());
        post.setCreatedDate(currTime);
        return post;
    }
    @GetMapping(value = "/post")
    public ResponseEntity<String> getPost(@RequestParam String userId,@Nullable @RequestParam String postId){
        Integer uId=Integer.valueOf(userId);
        JSONArray postArray=postService.getPost(uId,postId);
        return new ResponseEntity<>(postArray.toString(),HttpStatus.OK);
    }

    @PutMapping(value = "/post/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable String postId, @RequestBody String postRequest){
        Post post=setPost(postRequest);
        postService.updatePost(postId,post);
        return new ResponseEntity<>("Post updated",HttpStatus.OK);
    }
}
