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

class ProductsAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onAddToCartClick: () -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var productList: List<Product> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.productImage.loadFromUrl(product.image)
        holder.productName.text = product.name
        holder.productPrice.text = context.getString(R.string.product_item_price, getPriceWithDiscount(product.price, product.discountPercentage), product.currency)

        // productOldPrice is only visible if the discount is not 0
        holder.productOldPrice.isVisible = product.discountPercentage != 0
        holder.productOldPrice.text = context.getString(R.string.product_item_old_price, formatNumberToTwoDigits(product.price.toDouble()), product.currency)
        holder.productOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        // productDiscount is only visible if the discount is not 0
        holder.productDiscount.isVisible = product.discountPercentage != 0
        holder.productDiscount.text = context.getString(R.string.product_item_discount, product.discountPercentage)

        // Set favorite button click listener
        holder.productFavorite.setOnClickListener { holder.productFavorite.isSelected = !holder.productFavorite.isSelected }

        // Set Add to cart button, this button is only visible if we have stock.
        holder.productAddToCart.isVisible = product.stock > 0
        holder.productAddToCart.setOnClickListener { onAddToCartClick.invoke() }

        // Set item listener
        holder.itemView.setOnClickListener { onProductClick(product) }

    }

    override fun getItemCount(): Int = productList.size

    fun setProducts(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.product_item_image)
        val productFavorite: ImageView = view.findViewById(R.id.product_item_favorite)
        val productAddToCart: ImageView = view.findViewById(R.id.product_item_add_to_cart)
        val productName: TextView = view.findViewById(R.id.product_item_name)
        val productOldPrice: TextView = view.findViewById(R.id.product_item_old_price)
        val productPrice: TextView = view.findViewById(R.id.product_item_price)
        val productDiscount: TextView = view.findViewById(R.id.product_item_discount)
    }
}