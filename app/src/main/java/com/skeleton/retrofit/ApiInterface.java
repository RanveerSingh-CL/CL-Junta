package com.skeleton.retrofit;


import com.skeleton.model.category.CategoryResponse;
import com.skeleton.model.getuser.GetUserResponse;
import com.skeleton.model.login.LoginResponse;
import com.skeleton.model.otp.Otp;
import com.skeleton.model.profileConstants.ProfileConstantsResponse;
import com.skeleton.model.signup.SignUpResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

import static com.skeleton.constant.ApiKeyConstant.AUTHORIZATION;
import static com.skeleton.constant.ApiKeyConstant.KEY_REQUEST_TYPE;

/**
 * Developer: Saurabh Verma
 * Dated: 27-09-2016.
 */
public interface ApiInterface {
    String UPDATE_LOCATION = "/api/user/register ";
    String USER_LOGIN = "/api/user/login ";
    String CATEGORY_LIST = "/api/category/list ";
    String VERIFY_OTP = "/api/user/verifyOTP ";
    String PROFLE_CONSTANTS = "/api/profile/constants ";
    String GET_PROFILE = "/api/user/getProfile";
    String USER_RESEND_OTP = "api/user/resendOTP";
    String USER_UPDATE_PROFILE = "api/user/updateProfile";
    String SELECT_CATEGORY = "api/user/selectCategory";



    /**
     * @param map hashmap
     * @return response
     */
    @Multipart
    @POST(UPDATE_LOCATION)
    Call<SignUpResponse> register(@PartMap HashMap<String, RequestBody> map);

    /**
     * @param map hashmap
     * @return authorizarion
     */
    @POST(USER_LOGIN)
    Call<LoginResponse> userLogin(@Body HashMap<String, String> map);

    /**
     * @param authorization authorization key
     * @param requestType   requesttype key
     * @return catagories
     */
    @GET(CATEGORY_LIST)
    Call<CategoryResponse> getCategory(@Header(AUTHORIZATION) String authorization,
                                       @Query(KEY_REQUEST_TYPE) String requestType);

    /**
     * @param authorization auth token
     * @param map           hash map
     * @return otp verification
     */
    @PUT(VERIFY_OTP)
    Call<Otp> verifyOtp(@Header(AUTHORIZATION) String authorization,
                        @Body HashMap<String, String> map);

    /**
     * @return profile constants for former profile completeness
     */
    @GET(PROFLE_CONSTANTS)
    Call<ProfileConstantsResponse> getConstants();

    /**
     * @param authorization authorization with access token
     * @return user profile data
     */
    @GET(GET_PROFILE)
    Call<GetUserResponse> getUserProfile(@Header(AUTHORIZATION) String authorization);

    /**
     * @param authorization access token
     * @return otp new
     */
    @PUT(USER_RESEND_OTP)
    Call<CommonResponse> resendOTP(@Header(AUTHORIZATION) String authorization);

    /**
     * @param authorization access key
     * @param params        profile credentials
     * @return : update profile
     */
    @Multipart
    @PUT(USER_UPDATE_PROFILE)
    Call<CommonResponse> updateProfile(@Header(AUTHORIZATION) String authorization,
                                       @PartMap HashMap<String, RequestBody> params);

    /**
     *
     * @param authorization : access key
     * @param map hashmap
     * @return profile update result
     */
    @FormUrlEncoded
    @PUT(SELECT_CATEGORY)
    Call<CategoryResponse> selectCategories(@Header(AUTHORIZATION) String authorization, @FieldMap Map<String, String> map);

//    /**
//     * @param map
//     * @return
//     */
//    @Multipart
//    @POST("api/v1/user/createUser")
//    Call<LoginResponse> register(@PartMap HashMap<String, RequestBody> map);
//
//
//    /**
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/socialLogin")
//    Call<LoginResponse> socialLogin(@FieldMap HashMap<String, String> map);
//
//    /**
//     * @param authorization
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/loginToken")
//    Call<LoginResponse> accessTokenLogin(@Header(AUTHORIZATION) String authorization,
//                                         @FieldMap HashMap<String, String> map);
//
//
//    /**
//     * @param email
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/v1/user/forgotpassword")
//    Call<CommonResponse> forgotPassword(@Field("email") String email);
//
//    /**
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/loginCredential")
//    Call<LoginResponse> login(@FieldMap HashMap<String, String> map);


    /**
     * Update location call.
     *
     * @param authorization the authorization
     * @param map           the map
     * @return the call
     */
    @FormUrlEncoded
    @POST(UPDATE_LOCATION)
    Call<CommonParams> updateLocation(@Header(AUTHORIZATION) String authorization,
                                      @FieldMap HashMap<String, String> map);

}

