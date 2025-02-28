package com.unir.productoretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val productoResponse: List<ProductoResponse>) : RecyclerView.Adapter<ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.item_producto,parent,false))
    }

    override fun getItemCount(): Int {
       return this.productoResponse.size
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productoResponse[position]
        holder.bind(producto)
    }

}