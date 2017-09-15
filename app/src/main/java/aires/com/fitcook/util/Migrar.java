package aires.com.fitcook.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.webservice.WebService;

/**
 * Created by Saulo Aires on 20/02/2017.
 */

public class Migrar {

    public void doMigrar(final Context ctx) {


        //migrar url

        new Thread(new Runnable() {
            @Override
            public void run() {

               // try {

                    WebService.retrieveRecipes(0, new WebService.CallBack() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray recipes = response.getJSONArray("array");

                                System.out.print(recipes);
                            }catch (Exception e){

                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
/*
                    final Recipe2DAO recipe2DAO = new Recipe2DAO();
                    RecipeDAO recipeDAO = new RecipeDAO(ctx);

                    List<Recipe> lista = recipeDAO.getAll();
                    for (final Recipe r : lista) {

                        URL url = new URL(r.getUrl());

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        InputStream is = null;

                        is = url.openStream();
                        byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                        int n;

                        while ((n = is.read(byteChunk)) > 0) {
                            baos.write(byteChunk, 0, n);
                        }

                        if (is != null) {
                            is.close();
                        }

                        File file = new File(r.getUrl());


                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://fitcook-44eb4.appspot.com");

                        StorageReference imagesRef = storageRef.child("images");

                        StorageReference spaceRef = imagesRef.child(file.getName());


                        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg")
                                .build();

                        UploadTask uploadTask = spaceRef.putBytes(baos.toByteArray(), metadata);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                System.out.print(exception);
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                r.setUrl(downloadUrl.toString());
                                recipe2DAO.persist(r);


                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
            }
        }).start();



    }
}