package com.arioki.belanjaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.arioki.belanjaapp.ext.toast
import com.arioki.belanjaapp.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlin.random.Random


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
        render(isEdit, product)
    }

    private fun render(edit: Boolean, product: Product) {
        if (edit){
            showDetailProduct(product)
            btnSimpan.text = "Update"
        }
        btnHapus.visibility = if(edit) View.VISIBLE else View.GONE
        etProductImageDetail.isSingleLine = true
        etProductHargaDetail.isSingleLine = true
        etProductNameDetail.isSingleLine = true

        btnSimpan.setOnClickListener {
            if (edit){
                actionUpdateData(product)
            }else{
                actionSaveData()
            }
        }
        btnHapus.setOnClickListener {
            product.id?.let { it1 -> actionBtnDelete(it1) }
        }
    }

    private fun actionBtnDelete(id: Int) {

            App.instances.repository.delete(id, {
                "Data delete successfully".toast(this@DetailProductActivity)
                setResult(RESULT_CODE_RELOAD_DATA)
                finish()
            },{
                it.printStackTrace()
                it.message?.toast(this@DetailProductActivity)
            })
    }

    private fun actionSaveData() {
        val random = Random.nextInt(100,1000)
        val product = Product(
            etProductNameDetail.text.toString(),
            etProductHargaDetail.text.toString().toInt(),
            if (etProductImageDetail.text.toString().isNotEmpty()){
                etProductImageDetail.text.toString()
            }else{
                "https://loremflickr.com/100/100?lock=$random"
            }
        )
        App.instances.repository.save(product,{
            "data saved successfully".toast(this@DetailProductActivity)
            setResult(RESULT_CODE_RELOAD_DATA)
            finish()
        },{
            it.printStackTrace()
            it.message?.toast(this@DetailProductActivity)
        })

    }

    private fun actionUpdateData(product: Product) {
        product.apply {
            if(etProductNameDetail.text.toString().isNotEmpty()) name =
                etProductNameDetail.text.toString()
            if(etProductHargaDetail.text.toString().isNotEmpty()) price =
                etProductHargaDetail.text.toString().toInt()
            if(etProductImageDetail.text.toString().isNotEmpty()){
                image = etProductImageDetail.text.toString()
            }else{
                val random = Random.nextInt(100,1000)
                image = "https://loremflickr.com/100/100?lock=$random"
            }
        }

        product.id?.let {
            App.instances.repository.update(it,product,{
                "data updated successfully".toast(this@DetailProductActivity)
                setResult(RESULT_CODE_RELOAD_DATA)
                finish()
            },{
                it.printStackTrace()
                it.message?.toast(this@DetailProductActivity)
            })
        }
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
