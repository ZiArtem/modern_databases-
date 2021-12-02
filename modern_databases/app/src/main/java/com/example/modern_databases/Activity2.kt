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
    lateinit var  testPicture: Button
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
        testPicture = findViewById(R.id.button_image)

        image.load("https://gamerspack.co.il/wp-content/uploads/2020/09/RTX-3090-FACE-2048x1152.jpg") {
//            placeholder(R.drawable.ic_placeholder)
            transformations(RoundedCornersTransformation(40f))
        }
        test.setOnClickListener { testFunk () }
        test2.setOnClickListener { testFunk2 () }
        testPicture.setOnClickListener { changePic () }
    }

    private fun testFunk () {
        var ad: Ad = Ad(0, "title test","description test", 80,Date(),1)
        mUserViewModel.addAd(ad)
        Toast.makeText(applicationContext,"Successful!!!!", Toast.LENGTH_SHORT).show()
    }

    private fun testFunk2 () {
        Thread(Runnable {
            val ad = mUserViewModel.getAdById(1)
            if (!ad.isEmpty()) {
                runOnUiThread {
                    val sdf = SimpleDateFormat("dd MM,yyy -HH:mm")

                    Toast.makeText(
                        applicationContext,sdf.format(ad[0].date), Toast.LENGTH_LONG
                    ).show()
//                    Toast.makeText(
//                        applicationContext,
//                        ("ad id 1 \n" +
//                                " title = " + ad[0].title + "\n description = " + ad[0].description + " \n" +
//                                " price = " + ad[0].price),
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(applicationContext, "not ad", Toast.LENGTH_SHORT).show()
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

    private  fun changePic () {
        lifecycleScope.launch {
            val im = Image(
                0,
                getBitmap("https://vplate.ru/images/article/orig/2019/04/siba-inu-opisanie-porody-harakter-i-soderzhanie.jpg")
            )
            Thread(Runnable {
                mUserViewModel.addImage(im)
                var im1 =  mUserViewModel.getImageById(1)
                image.load(im1[0].image) {
                    transformations(RoundedCornersTransformation(40f))

                }
            }).start()
        }
    }
}