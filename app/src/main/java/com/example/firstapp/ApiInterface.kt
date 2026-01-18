package com.example.firstapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    // 1️⃣ Stream → Branches
    @GET("{stream}")
    fun getDataByStream(
        @Path("stream") stream: String
    ): Call<MyData>

    // 2️⃣ Stream + Branch → Semesters
    @GET("{stream}/{branch}")
    fun getDataByBranch(
        @Path("stream") stream: String,
        @Path("branch") branch: String
    ): Call<MyData>

    // 3️⃣ Stream + Branch + Semester → Subjects
    @GET("{stream}/{branch}/{sem}")
    fun getDataBySem(
        @Path("stream") stream: String,
        @Path("branch") branch: String,
        @Path("sem") sem: String
    ): Call<MyData>

    // 4️⃣ Stream + Branch + Semester + Subject → PDF URL
    @GET("{stream}/{branch}/{sem}/{sub}")
    fun getDataBySub(
        @Path("stream") stream: String,
        @Path("branch") branch: String,
        @Path("sem") sem: String,
        @Path("sub") sub: String
    ): Call<String>
}
