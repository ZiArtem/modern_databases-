package com.example.modern_databases
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.Image
import com.example.modern_databases.data.UserViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Activity2 : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel
    lateinit var  test: Button
    lateinit var  test2: Button
    lateinit var  addImage_: Button
    lateinit var  changeImage: Button
    lateinit var  image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        test=  findViewById(R.id.test_button)
        test2=  findViewById(R.id.test_button2)
        image = findViewById(R.id.imageView)
        addImage_ = findViewById(R.id.button_image2)
        changeImage = findViewById(R.id.button_image)

        test.setOnClickListener {  addAdTestFunction () }
        test2.setOnClickListener { getAdTest () }
        addImage_.setOnClickListener { addImageTest ()  }
        changeImage.setOnClickListener {  changeImage()  }


        image.load("https://gamerspack.co.il/wp-content/uploads/2020/09/RTX-3090-FACE-2048x1152.jpg") {
            transformations(RoundedCornersTransformation(40f))
        }

    }

    private fun addAdTestFunction () {
        var ad: Ad = Ad(0, "Rtx 3090"," MSI GeForce RTX 3090 VENTUS 3X OC", 1,320000,Date(),1)
        mUserViewModel.addAd(ad)
        Toast.makeText(applicationContext,"ad added successfully!!!!", Toast.LENGTH_SHORT).show()
    }

    private fun getAdTest () {
        Thread(Runnable {

//            val ad = mUserViewModel.getByKeyword("3090")
            val ad = mUserViewModel.getAdById(1)
            if (!ad.isEmpty()) {
                runOnUiThread {
                    val sdf = SimpleDateFormat("dd MM,yyy -HH:mm")

                    Toast.makeText(
                        applicationContext,
                        ("test 1 ad \n" +
                                " title = " + ad[0].title + "\n description = " + ad[0].description + " \n" +
                                " price = " + ad[0].price + " \n" + "date of placement " +sdf.format(ad[0].date)),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(applicationContext, "There are no ads in the database", Toast.LENGTH_SHORT).show()
                }
            }
        }).start()
    }

    private suspend fun getBitmap(path:String): Bitmap {
        val loading:ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this).data (path).build()

        val result =(loading.execute(request)as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private  fun addImageTest () {
        lifecycleScope.launch {
            val im = Image(
                0,
                getBitmap("https://vplate.ru/images/article/orig/2019/04/siba-inu-opisanie-porody-harakter-i-soderzhanie.jpg"),1,1
            )
            mUserViewModel.addImage(im)
        }
        Toast.makeText(applicationContext, "image added successfully", Toast.LENGTH_SHORT).show()
    }

    private  fun changeImage () {
        Thread(Runnable {
            var im1 =  mUserViewModel.getImageById(1)
            image.load(im1[0].image) {
                crossfade(600)
                transformations(RoundedCornersTransformation(40f))
            }
        }).start()
    }
}