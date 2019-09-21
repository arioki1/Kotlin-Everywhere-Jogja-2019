package com.arioki.belanjaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.arioki.belanjaapp.adapter.ListProductAdapter
import com.arioki.belanjaapp.model.ProductData
import com.arioki.belanjaapp.model.Products
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val list:ArrayList<Products> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showListProduct()

    }

    private fun showListProduct() {
        list.addAll(ProductData.listProduct)
        rvProducts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ListProductAdapter(list)
            setHasFixedSize(true)
        }
    }
}
