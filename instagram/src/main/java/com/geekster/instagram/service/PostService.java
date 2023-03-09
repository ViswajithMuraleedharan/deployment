package com.geekster.instagram.service;

import com.geekster.instagram.dao.PostRepository;
import com.geekster.instagram.model.Post;
import com.geekster.instagram.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public int savePost(Post post){
       Post savedPost =postRepository.save(post);
       return savedPost.getPostId();
    }

    public JSONArray getPost(int userId, String postId) {
        JSONArray postArray=new JSONArray();
        if(postId!=null && postRepository.findById(Integer.valueOf(postId)).isPresent()){
            Post post=postRepository.findById(Integer.valueOf(postId)).get();
            JSONObject postobj=setPostObj(post);
            postArray.put(postobj);
        }
        else {
            List<Post> postList=postRepository.findAll();
            for(Post post:postList){
                if(post.getUser().getUserId()==userId){
                    JSONObject postobj=setPostObj(post);
                    postArray.put(postobj);
                }
                JSONObject postobj=setPostObj(post);
                postArray.put(postobj);
            }
        }
        return postArray;
    }

    private JSONObject setPostObj(Post post) {
        JSONObject postObject=new JSONObject();
        postObject.put("postId",post.getPostId());
        postObject.put("postData",post.getPostData());

        User user=post.getUser();
        JSONObject userObj=new JSONObject();
        userObj.put("userId",user.getUserId());
        userObj.put("firstName",user.getFirstName());
        userObj.put("age",user.getAge());
        postObject.put("user",userObj);
        return postObject;
    }

    public void updatePost(String postId, Post newpost) {
        if(postId!=null && postRepository.findById(Integer.valueOf(postId)).isPresent()){
            Post post=postRepository.findById(Integer.valueOf(postId)).get();
            newpost.setPostId(post.getPostId());
            newpost.setUser(post.getUser());
            Timestamp updatedtime=new Timestamp(System.currentTimeMillis());
            newpost.setUpdatedDate(updatedtime);
            newpost.setCreatedDate(post.getCreatedDate());
            postRepository.save(newpost);
        }

    }
}
