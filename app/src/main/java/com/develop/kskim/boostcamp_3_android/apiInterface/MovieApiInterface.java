package com.develop.kskim.boostcamp_3_android.apiInterface;

import com.develop.kskim.boostcamp_3_android.repository.MovieInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MovieApiInterface {

    @Headers({"X-Naver-Client-Id: 6GIY4ht4BHiQxHcorQSl", "X-Naver-Client-Secret: dAvPqb22ds"})
    @GET("/v1/search/movie.json")
    Call<MovieInfo> getMovieList(@Query("query") String title, @Query("display") int displaySize,
                                 @Query("start") int startPosition);

}
