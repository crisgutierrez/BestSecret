package com.example.bestsecret.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.bestsecret.R
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.ext.loadFromUrl
import com.example.bestsecret.utils.formatNumberToTwoDigits
import com.example.bestsecret.utils.getPriceWithDiscount

class ProductsAdapter() : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var productList: List<Product> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productList[position]
        holder.productImage.loadFromUrl(item.image)
        holder.productName.text = item.name
        holder.productPrice.text = context.getString(R.string.product_item_price, item.currency, getPriceWithDiscount(item.price, item.discountPercentage))

        // productOldPrice is only visible if the discount is not 0
        holder.productOldPrice.isVisible = item.discountPercentage != 0
        holder.productOldPrice.text = context.getString(R.string.product_item_old_price, item.currency, formatNumberToTwoDigits(item.price.toDouble()))
        holder.productOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        // productDiscount is only visible if the discount is not 0
        holder.productDiscount.isVisible = item.discountPercentage != 0
        holder.productDiscount.text = context.getString(R.string.product_item_discount, item.discountPercentage)

        // Set favorite button click listener
        holder.productFavorite.setOnClickListener { holder.productFavorite.isSelected = !holder.productFavorite.isSelected }

    }

    override fun getItemCount(): Int = productList.size

    fun setProducts(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.product_item_image)
        val productFavorite: ImageView = view.findViewById(R.id.product_item_favorite)
        val productName: TextView = view.findViewById(R.id.product_item_name)
        val productOldPrice: TextView = view.findViewById(R.id.product_item_old_price)
        val productPrice: TextView = view.findViewById(R.id.product_item_price)
        val productDiscount: TextView = view.findViewById(R.id.product_item_discount)
    }
}