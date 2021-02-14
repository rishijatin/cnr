package com.example.cnrapp.api;

import com.example.cnrapp.models.Category;
import com.example.cnrapp.models.ContactInfo;
import com.example.cnrapp.models.DoctorDetail;
import com.example.cnrapp.models.DoctorList;
import com.example.cnrapp.models.EmploymentCategory;
import com.example.cnrapp.models.EmploymentDetails;
import com.example.cnrapp.models.EmploymentList;
import com.example.cnrapp.models.EventCategory;
import com.example.cnrapp.models.EventDetail;
import com.example.cnrapp.models.FunctionList;
import com.example.cnrapp.models.PersonModel;
import com.example.cnrapp.models.PostList;
import com.example.cnrapp.models.PostListDetail;
import com.example.cnrapp.models.PostListModified;
import com.example.cnrapp.models.eCommerceCategory;
import com.example.cnrapp.models.eCommerceItem;
import com.example.cnrapp.models.eCommerceItemDetail;
import com.example.cnrapp.models.eHealthCategory;
import com.example.cnrapp.models.eVehicleCategory;
import com.example.cnrapp.models.eVehicleDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CNRApi {


    @GET("categories/get/")
    Call<List<Category>> getCategories();

    @GET("categories/get/category={id}/")
    Call<PostListModified> getPosts(@Path("id") int id);

    @GET("categories/get/{id}")
    Call<PostListDetail> getPost(@Path("id") int id);

    @GET("eCommerce/")
    Call<List<eCommerceCategory>> getEcommerceCategories();

    @GET("eCommerce/category={id}/")
    Call<List<eCommerceItem>> getEcommerceItem(@Path("id") int id);

    @GET("eCommerce/{id}")
    Call <eCommerceItemDetail> getEcommerceItemDetail(@Path("id") int id);

    @GET("eHealth/")
    Call <List<eHealthCategory>> getHealthCategories();

    @GET("eHealth/category={id}/")
    Call <List<DoctorList>> getDoctors(@Path("id") int id);

    @GET("eHealth/{id}/")
    Call<DoctorDetail> getDoctor(@Path("id") int id);

    @GET("about/{id}/")
    Call<PersonModel> getPerson(@Path("id") int id);

    @GET("contact/")
    Call<ContactInfo> getContact();

    @GET("events/")
    Call<List<EventCategory>> getEvents();

    @GET("events/category={id}/")
    Call<List<FunctionList>> getFunctions(@Path("id") int id);

    @GET("events/{id}/")
    Call<EventDetail> getEvent(@Path("id") int id);

    @GET("eVehicle/")
    Call<List<eVehicleCategory>> getVehicleCategories();

    @GET("eVehicle/category={id}/")
    Call<eVehicleDetail> getVehicleDetail(@Path("id") int id);

    @GET("employment/")
    Call<List<EmploymentCategory>> getEmploymentCategories();

    @GET("employment/category={id}/")
    Call<List<EmploymentList>> getEmploymentList(@Path("id") int id);

    @GET("employment/{id}/")
    Call<EmploymentDetails> getEmploymentDetails(@Path("id") int id);


}
