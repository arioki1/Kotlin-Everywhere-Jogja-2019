package com.arioki.belanjaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arioki.belanjaapp.ext.toast
import com.arioki.belanjaapp.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.item_list_product.*

class DetailProductActivity : AppCompatActivity() {
    companion object{
        fun editIntent(context: Context, product: Product): Intent {
            return Intent(context, DetailProductActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT, product)
                putExtra(EXTRA_EDIT, true)
            }
        }

        fun addIntent(context: Context): Intent {
            return Intent(context, DetailProductActivity::class.java)
        }

        const val REQUEST_CODE_DETAIL_PRODUCT: Int = 123
        const val RESULT_CODE_RELOAD_DATA: Int = 124
        const val EXTRA_PRODUCT = "extra_product"
        const val EXTRA_EDIT = "extra_edit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        val isEdit = intent.getBooleanExtra(EXTRA_EDIT, false)
        val data = intent.getSerializableExtra(EXTRA_PRODUCT)

        val product = if (data == null) Product() else data as Product
        showDetailProduct(product)
    }

    private fun showDetailProduct(product: Product) {
        with(product){
            etProductNameDetail.setText(name)
            etProductHargaDetail.setText(price.toString())
            etProductImageDetail.setText(image)
            Glide.with(this@DetailProductActivity)
                .load(image)
                .transform(CenterCrop(),RoundedCorners(20))
                .into(imgProductDetail)
        }
    }

}
