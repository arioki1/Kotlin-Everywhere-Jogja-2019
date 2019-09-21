package com.arioki.belanjaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arioki.belanjaapp.adapter.ListProductAdapter
import com.arioki.belanjaapp.model.ProductData
import com.arioki.belanjaapp.model.Products
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val list:ArrayList<Products> = arrayListOf()
    private lateinit var listProductAdapter: ListProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showListProduct()
        setupAddProduct()
        setListClickAction()
    }

    private fun setListClickAction() {
        listProductAdapter.setOnItemCallback(
            object : ListProductAdapter.OnItemClickCallback{
                override fun onItemClick(data: Products) {
                  //  Toast.makeText(this@MainActivity, "Anda memilih $data",Toast.LENGTH_SHORT).show()

                    val manageDetailIntent = Intent(this@MainActivity,
                        DetailProductActivity::class.java).apply {
                        putExtra(DetailProductActivity.EXTRA_NAME,data.name)
                        putExtra(DetailProductActivity.EXTRA_PRICE,data.price.toString())
                        putExtra(DetailProductActivity.EXTRA_IMAGE_URL,data.image)
                    }
                    startActivity(manageDetailIntent)
                }
            }
        )
    }

    private fun setupAddProduct() {
        btnAddProduct.setOnClickListener {
            val detailIntent = Intent(this, DetailProductActivity::class.java)
            startActivity(detailIntent)
        }
    }

    private fun showListProduct() {
        list.addAll(ProductData.listProduct)
        listProductAdapter = ListProductAdapter(list)

        rvProducts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listProductAdapter
            setHasFixedSize(true)
        }
    }
}
