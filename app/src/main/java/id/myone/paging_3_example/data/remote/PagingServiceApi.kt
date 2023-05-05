/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data.remote

import id.myone.paging_3_example.data.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PagingServiceApi {

    @GET("/v2/top-headlines?country=us")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): NewsResponse
}