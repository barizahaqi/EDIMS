package com.bangkit.edims.database

import com.bangkit.edims.core.utils.DateConverter
import com.bangkit.edims.core.utils.Filter
import com.bangkit.edims.core.utils.ProductFilterType
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.User
import com.bangkit.edims.data.retrofit.ApiConfig
import com.bangkit.edims.data.retrofit.ApiService
import com.bangkit.edims.data.retrofit.ErrorResponse
import com.bangkit.edims.data.retrofit.ItemsResponse
import com.bangkit.edims.data.retrofit.LoginRequest
import com.bangkit.edims.data.retrofit.LoginResponse
import com.bangkit.edims.data.retrofit.SignupRequest
import com.bangkit.edims.data.retrofit.SignupResponse
import com.bangkit.edims.data.retrofit.UploadResponse
import com.bangkit.edims.data.wrapEspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao,
    private val preference: SettingPreference,
    private val userPreference: UserPreference,
) : ProductRepository {

    companion object {
        private var isDataFetched = false
    }

    override fun isDataFetched(): Boolean = isDataFetched

    override fun login(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = apiService.login(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        emit(Result.Success(loginResponse))
                    }
                } else {
                    val jsonString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                    val errorMessage = errorBody.message
                    emit(Result.Error(errorMessage))
                }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage))
            } catch (e: IOException) {
                emit(Result.Error("Network error. Please check your internet connection."))
            }
        }
    }

    override fun signup(
        email: String,
        username: String,
        password: String
    ): Flow<Result<SignupResponse>> = flow {
        emit(Result.Loading)
        try {
            val signupRequest = SignupRequest(email, username, password)
            val response = apiService.signup(signupRequest)
            if (response.isSuccessful) {
                val signupResponse = response.body()
                if (signupResponse != null) {
                    val message = "Sign up successful"
                    emit(Result.Success(SignupResponse(message)))
                }
            } else {
                val jsonString = response.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage))
        } catch (e: IOException) {
            emit(Result.Error("Network error. Please check your internet connection."))
        }
    }

    override fun getAllData(): Flow<Result<ItemsResponse>> = flow {
        emit(Result.Loading)
        try {
            val apiService = ApiConfig.getApiService(userPreference)
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    emit(Result.Success(result))
                } else {
                    val jsonString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                    val errorMessage = errorBody.message
                    emit(Result.Error(errorMessage))
                }
                isDataFetched = true
            } else {
                val jsonString = response.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage))
        } catch (e: IOException) {
            emit(Result.Error("Network error. Please check your internet connection."))
        }
    }

    override fun uploadProductApi(
        name: RequestBody,
        imageFile: MultipartBody.Part,
        category: RequestBody,
        date: RequestBody,
    ): Flow<Result<UploadResponse>> = flow {
        emit(Result.Loading)
        try {
            val apiService = ApiConfig.getApiService(userPreference)
            val response = apiService.uploadProduct(name, imageFile, category, date)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    emit((Result.Success(result)))
                }
            } else {
                val errorMessage = "error"
                emit(Result.Error(errorMessage))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage))
        } catch (e: IOException) {
            emit(Result.Error("Network error. Please check your internet connection."))
        }
    }

    override fun deleteProductApi(id: Int): Flow<Result<ErrorResponse>> = flow {
        emit(Result.Loading)
        try {
            val apiService = ApiConfig.getApiService(userPreference)
            val response = apiService.deleteProducts(id)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    emit(Result.Success(result))
                }
            } else {
                val jsonString = response.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage))
            }
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage))
        } catch (e: IOException) {
            emit(Result.Error("Network error. Please check your internet connection."))
        }
    }

    override suspend fun logout() {
        productDao.deleteAll()
        userPreference.clearLoginData()
        isDataFetched = false
    }

    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    override suspend fun insertAll(listProduct: List<Product>) {
        for (data in listProduct) {
            productDao.insert(data)
        }
    }

    override suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    override fun getItems(filter: ProductFilterType): Flow<List<Product>> {
        val filterQuery = Filter.getFilterQuery(filter)
        return productDao.getItems(filterQuery)
    }

    override fun getItemsById(id: Int): Flow<Product> {
        return productDao.getItemsById(id)
    }

    override fun getNearestItems(): List<Product> {
        val currentTime = System.currentTimeMillis()
        val dueDateMillis: Long = currentTime + DateConverter.dayToMillis(3)
        return productDao.getNearestItems(dueDateMillis, currentTime)
    }

    override suspend fun saveNotificationSettings(status: Boolean) {
        preference.saveNotificationSettings(status)
    }

    override fun getNotificationSettings(): Flow<Boolean> {
        return preference.getNotificationSettings
    }

    override suspend fun saveLoginData(user: User) {
        userPreference.saveLoginData(user)
    }

    override suspend fun clearLoginData() {
        userPreference.clearLoginData()
    }

    override fun getUserData(): Flow<User> {
        return userPreference.getUserData
    }

    override fun getToken(): Flow<String> {
        return userPreference.token
    }
}