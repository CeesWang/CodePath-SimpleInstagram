package com.codepath.simpleinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.simpleinstagram.Post;
import com.codepath.simpleinstagram.PostsAdapter;
import com.codepath.simpleinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private RecyclerView rvPosts;
    private DividerItemDecoration itemDecorator;
    protected PostsAdapter adapter;
    protected List<Post> list_posts;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvPost);
        list_posts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(),list_posts);
        rvPosts.setAdapter(adapter);
        itemDecorator = new DividerItemDecoration(getContext(), this.getResources().getConfiguration().orientation);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        rvPosts.addItemDecoration(itemDecorator);
//        rvPosts.setLayoutManager(new GridLayoutManager(getContext(), GridLayoutManager.DEFAULT_SPAN_COUNT));
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();

    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e("PostsFragment", "Post failed");
                    return;
                }
                list_posts.addAll(posts);
                adapter.notifyDataSetChanged();
                for (int i = 0; i < posts.size(); ++i) {
                    if (list_posts.get(i).getImage() != null)
                    Log.d("PostsFragment", "Post" + list_posts.get(i).getImage().getUrl());
                }
            }
        });
    }
}
