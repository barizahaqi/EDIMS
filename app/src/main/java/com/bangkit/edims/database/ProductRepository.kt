package com.bangkit.edims.database

import com.bangkit.edims.core.utils.ProductFilterType
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.User
import com.bangkit.edims.data.retrofit.ErrorResponse
import com.bangkit.edims.data.retrofit.ItemsResponse
import com.bangkit.edims.data.retrofit.LoginResponse
import com.bangkit.edims.data.retrofit.SignupResponse
import com.bangkit.edims.data.retrofit.UploadResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProductRepository{

    fun isDataFetched() : Boolean

    fun login(email: String, password: String) : Flow<Result<LoginResponse>>

    fun signup(email: String, username : String, password: String) : Flow<Result<SignupResponse>>

    fun getAllData() : Flow<Result<ItemsResponse>>

    fun uploadProductApi(name: RequestBody, imageFile: MultipartBody.Part, category: RequestBody, date: RequestBody) : Flow<Result<UploadResponse>>

    fun deleteProductApi(id : Int) : Flow<Result<ErrorResponse>>

    suspend fun logout()

    suspend fun insert(product: Product)

    suspend fun insertAll(listProduct: List<Product>)

    suspend fun delete(product: Product)

    fun getItems(filter: ProductFilterType) : Flow<List<Product>>

    fun getItemsById(id: Int) : Flow<Product>

    fun getNearestItems() : List<Product>

    suspend fun saveNotificationSettings(status: Boolean)

    fun getNotificationSettings() : Flow<Boolean>

    suspend fun saveLoginData(user : User)

    suspend fun clearLoginData()

    fun getUserData() : Flow<User>

    fun getToken() : Flow<String>
}