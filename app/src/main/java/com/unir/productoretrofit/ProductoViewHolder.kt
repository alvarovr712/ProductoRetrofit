package com.unir.productoretrofit

import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unir.productoretrofit.databinding.ItemProductoBinding

class ProductoViewHolder (view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemProductoBinding.bind(view)

    fun bind(productoResponse: ProductoResponse){
        Picasso.get().load(productoResponse.image).into(binding.ivProducto)
        binding.tvname.text = productoResponse.name
    }
}