package com.unir.productoretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unir.productoretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductoAdapter

    private val productoList = mutableListOf<ProductoResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.initRecyclerView()
        this.displayData("products/")
        this.fetchData("products/")



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initRecyclerView(){
        adapter = ProductoAdapter(productoList)
        binding.rvProducto.layoutManager = LinearLayoutManager(this)
        binding.rvProducto.adapter = adapter
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://peticiones.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun fetchData(query:String){

        CoroutineScope(Dispatchers.IO).launch {

            val call: Response<ProductoCabecera> = getRetrofit()
                .create(ApiService::class.java)
                .getAll(query)

            val puppies : ProductoCabecera? = call.body()

            runOnUiThread(){
                if(call.isSuccessful){
                    val productos = puppies?.results?: emptyList()
                    Log.v("API RESPUESTA",productos.toString())
                }
            }
        }
    }

    private fun displayData(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ProductoCabecera> = getRetrofit()
                .create(ApiService::class.java)
                .getAll(query)

            val response: ProductoCabecera? = call.body()

            runOnUiThread {
                if(call.isSuccessful){
                    val productos = response?.results?: emptyList()
                    productoList.clear()
                    productoList.addAll(productos)
                    adapter.notifyDataSetChanged()
                }
            }
        }



    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            }

        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
      return true
    }
}