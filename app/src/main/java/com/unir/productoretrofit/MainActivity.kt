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

        this.getProductoById("63740f5fe2c75d8744f80a2e")

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
            .baseUrl("https://peticiones.online/api/products/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getProductoById(query:String){

        CoroutineScope(Dispatchers.IO).launch {
            val url = query
            val call: Response<ProductoResponse> = getRetrofit()
                .create(ApiService::class.java)
                .getById(url)

            val puppies : ProductoResponse? = call.body()

            runOnUiThread(){
                if(call.isSuccessful){
                    if(puppies != null ){
                        Log.v("QUERY API " , puppies.image.toString())
                        productoList.clear()
                        productoList.add(puppies)
                        adapter.notifyDataSetChanged()
                    }else{
                        Log.e("QUERY API", "Error en la peticion")
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            this.getProductoById(query.lowercase())}

        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
      return true
    }
}