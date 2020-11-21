package com.example.myapplication.Adapters

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.myapplication.Fragments.ProductDetails
import com.example.myapplication.R
import com.example.myapplication.model.Product
import com.squareup.picasso.Picasso
import org.jetbrains.anko.image

class ProductsAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.product_name.text = product.product_name.toString()
        holder.product_price.text = product.product_price.toString()
        holder.club_name.text = product.club_name.toString()
        Picasso.get().load(product.imageurl.toString()).into(holder.imageurl)
        val imageURL = product.imageurl.toString()

    }
   /*  fun loadImageUsingGlide() {
        GlideApp.with(this).asBitmap()
                .load(Uri.parse(imageUrl))
                .into(object: BitmapImageViewTarget(imageView){
                    override fun onResourceReady(resource: Bitmap?, transition: Transition?) {
                        super.onResourceReady(resource, transition)
                        chronometer.stop()
                    }
                })
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_row, parent,false)
        val holder = ViewHolder(view)
        val image = view.findViewById<ImageView>(R.id.productImage)

        view.setOnClickListener{
            val intent = Intent(parent.context, ProductDetails::class.java)
            intent.putExtra("product_name", products[holder.adapterPosition].product_name)
            intent.putExtra("club_name", products[holder.adapterPosition].club_name)
            intent.putExtra("description", products[holder.adapterPosition].description)
           intent.putExtra("imageurl", products[holder.adapterPosition].imageurl)
            intent.putExtra("product_price", products[holder.adapterPosition].product_price)
            parent.context.startActivity(intent)

        }
        return holder
    }
    override fun getItemCount()= products.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageurl: ImageView = itemView.findViewById(R.id.productImage)
        val product_name: TextView = itemView.findViewById(R.id.product_name)
        val club_name: TextView = itemView.findViewById(R.id.club)
        val product_price: TextView = itemView.findViewById(R.id.price)
    }






}
