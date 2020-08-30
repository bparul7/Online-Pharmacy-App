package com.HWI.pharmacy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("")
    Call<List<User>> getUsers();
}
