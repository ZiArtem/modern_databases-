package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.R
import com.example.modern_databases.data.entities.Ad
import com.example.modern_databases.data.entities.Image
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SettigActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: PrViewModel
    private lateinit var lastName: TextView
    private lateinit var secondName: TextView
    private lateinit var signOut: TextView
    private lateinit var userImage: ImageView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var location: TextView
    private lateinit var registrationDate: TextView
    private lateinit var changePassword: Button
    private lateinit var test: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settig)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        setInformation()
        bottomNavigation()

        changePassword = findViewById(R.id.changePassword)
        test = findViewById(R.id.test_button)
        signOut = findViewById(R.id.exit)

        signOut.setOnClickListener { signOut() }
        changePassword.setOnClickListener { ChangePassword() }
        test.setOnClickListener { addAdTestFunction() }
    }

    private fun ChangePassword() {
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id = sharedPref.getInt("id_user", -1)

        val intent = Intent(this, ChangePasswordActivity::class.java)
        intent.putExtra("id_ad", save_id)
        startActivity(intent)
        overridePendingTransition(0, 0);
    }

    private fun bottomNavigation() {
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.settings
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
//                R.id.settings-> {
//                    val intent = Intent(this, SettigActivity::class.java)
//                    startActivity(intent)
//                }
                R.id.order -> {
//                    val intent = Intent(this, TestActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(0, 0);
                }
            }
            true
        }
    }

    private fun setInformation() {

        lastName = findViewById(R.id.userName)
        secondName = findViewById(R.id.userLastName)
        userImage = findViewById(R.id.userImage)
        email = findViewById(R.id.email)
        phoneNumber = findViewById(R.id.phoneNumber)
        location = findViewById(R.id.location)
        registrationDate = findViewById(R.id.date_registry)

        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id = sharedPref.getInt("id_user", -1)

        Thread(Runnable {
            var user = mUserViewModel.getUserInfo(save_id)
            var userInfo = mUserViewModel.getUserInformation(save_id)
            runOnUiThread {
                lastName.text = user[0].firstName
                secondName.text = user[0].lastName
                email.text = userInfo[0].email
                location.text = userInfo[0].location
                val sdf = SimpleDateFormat("dd.MM HH:mm")
                registrationDate.text = sdf.format(userInfo[0].date_registry).toString()
                phoneNumber.text = userInfo[0].phone_number
                userImage.load(userInfo[0].image_user) {
                    transformations(RoundedCornersTransformation(40f))
                }
            }
        }).start()
    }

    private fun signOut() {
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putInt("id_user", -1)
            putBoolean("is_checked", false)
        }.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0);
        finish();
    }

    private fun addAdTestFunction() {
        var images_list: ArrayList<String> = ArrayList()

        images_list.add("https://pbs.twimg.com/media/E00ZZrKXoAEiyla.jpg")
        images_list.add("https://fun-cats.ru/wp-content/uploads/3/0/7/3071381c7efe0c6c94cec8f4eb4442e6.jpeg")
        images_list.add("https://pbs.twimg.com/media/EkIUGBXWoAE9b0i.jpg")
        images_list.add("https://mirsobaki.ru/wp-content/uploads/2019/01/Siba-inu-88.jpg")
        images_list.add("https://doggav.ru/wp-content/uploads/siba-inu_24.jpg")
        images_list.add("https://kot-pes.com/wp-content/uploads/2019/06/post_5cf6ff9b6710e-765x754.jpg")
        images_list.add("https://porodisobak.ru/wp-content/uploads/2021/07/siba-inu-12.jpg")
        images_list.add("https://lapkins.ru/upload/iblock/945/945b1c8bba53680aca6d63dcd04fce9b.jpg")
        images_list.add("https://hypecrib.com/wp-content/uploads/2019/12/8-1.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-13.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-17.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-16.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-15.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-14.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-12.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-11.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-10.jpg")

        //size =17

        lifecycleScope.launch {
            var ad: Ad
            var im: Image

            ad = Ad(
                0,
                "Apple Pencil (2nd Generation)" ,
                        "Color                        White\n" +
                        "Brand                        Apple\n" +
                        "Number of Batteries          1 Lithium Polymer batteries required. (included)\n" +
                        "Item Weight                  0.73 Ounces\n" +
                        "Item Dimensions LxWxH\t      6.53 x 0.35 x 0.35 inches\n" +
                        "Are Batteries Included\t     Yes\n" +
                        "Batteries Required?\t        Yes\n" +
                        "Item Diameter\t              0.35 Inches\n",
                "   Compatible with iPad mini (6th generation), iPad Air (4th generation), iPad Pro 12.9-inch (3rd, 4th, and 5th generations), iPad Pro 11-inch (1st, 2nd, and 3rd generations)\n" +
                        "   Apple Pencil (2nd generation) brings your work to life. With imperceptible lag, pixel-perfect precision, and tilt and pressure sensitivity, it transforms into your favorite creative instrument, your paint brush, your charcoal, or your pencil.\n" +
                        "   It makes painting, sketching, doodling, and even note-taking better than ever.\n" +
                        "   It magnetically attaches to iPad mini (6th generation), iPad Pro and iPad Air, charges wirelessly, and lets you change tools with a simple double tap.  ",
                1,
                99.00,
                Date(),
                1
            )
            mUserViewModel.addAd(ad)

            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://www.ventasrosario.com.ar/wp-content/uploads/2019/09/pencil1.jpg"),
                1,
                1
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://www.iphones.ru/wp-content/uploads/2018/11/app_pencil9-768x432.jpg"),
                1,
                2
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://ugra.ru/pics-akket.com/wp-content/uploads/2016/05/Apple-Pencil-1.jpg"),
                1,
                2
            ))



            ad = Ad(
                0,
                "Xbox Series S",
                "Platform : Xbox, Xbox Series S",
                "   Access your favorite entertainment through apps like YouTube, Netflix, and more\n" +
                        "   Enjoy over 100 games right out of the box with a 1 month Xbox Game Pass trial\n" +
                        "   Watch 4K Blu-ray movies and stream 4K video on Netflix, Amazon, Hulu, Microsoft Movies & TV, and more\n" +
                        "   Play with friends and family near and far—sitting together on the sofa or around the world on Xbox Live, the fastest, most reliable gaming network\n" +
                        "   Xbox 1 games and accessories work together",
                2,
                299.99,
                Date(),
                1
            )
            mUserViewModel.addAd(ad)
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://3dnews.ru/assets/external/illustrations/2020/10/13/1022849/sm.xbox.5.750.jpg"),
                2,
                1
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://cryptichack3r.com/wp-content/uploads/2020/11/3735881-xboxseriess.jpg"),
                2,
                2
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://forum.xboxera.com/uploads/default/original/2X/0/06ec74ac491b0f8568b804ad1910a1a43ebbbeca.jpeg"),
                2,
                3
            ))


            ad = Ad(
                0,
                "DJI Mavic 3 - Camera Drone with 4/3 CMOS Hasselblad Camera, 5.1K Video, Omnidirectional Obstacle Sensing, 46-Min Flight, RC Quadcopter with Advanced Auto Return, Max 15km Video Transmission",
                "Brand                    DJI\n" +
                        "Color                     Gray\n" +
                        "Control Type\t            Remote Control\n" +
                        "Video Capture Resolution\t4K HD, FHD 1080p\n" +
                        "Are Batteries Included\t  Yes\n" +
                        "Wireless Communication Technology\tCellular\n" +
                        "Item Weight\t              895 Grams\n" +
                        "Video Output Resolution\t 1920x1080 Pixels\n" +
                        "Remote Control Included?\tYes\n" +
                        "Battery Cell Composition\tLithium Ion",
                "   Imaging Above Everything - With a 4/3 CMOS Hasselblad Camera, the sensor provides a 12.8-stop dynamic range that retains more details in highlights and shadows, upgrading your work to a professional level.\n" +
                        "   46 Minutes of Flight Time - Stay in the air longer and capture more with a breathtaking max flight time of 46 minutes. This lets you get all the shots you want on just a single battery.\n" +
                        "   Fly Safer - DJI Mavic 3 drone with camera is equipped with advanced Omnidirectional Obstacle Sensing. This lets you fly confidently and safely wherever you are.\n" +
                        "   Explore and Capture more - the DJI Mavic 3 drone offers a 15-kilometer max transmission range, which means you can fly farther to explore and capture more. And with the O3 Plus Transmission system, live feeds are smooth and stable.\n" +
                        "   Smart Return to Home - With a new Advanced RTH system, Mavic 3 camera drone can return to its home point on a fast, safe, and optimized route. Whether activated by low battery or the pilot, getting the quadcopter drone back to you during flight is easier than ever.",
                3,
                2199.00,
                Date(),
                1
            )
            mUserViewModel.addAd(ad)
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-13.userapi.com/impg/B7zylRfe5LsvH6CVpBKZygNKYCh6_3jtGvBVvw/KzIfxbL5rCk.jpg?size=1486x726&quality=96&sign=5ca8f9624f8e4d3311cfd2093da2273a&type=album"),
                3,
                1
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-26.userapi.com/impg/YLkJLTGq6ceXfQAzvORjIYc0UbFPugZJbbJHUw/dRLV-n348EQ.jpg?size=1417x706&quality=96&sign=8f921d32a8722c5f3120117a4250a72f&type=album"),
                3,
                2
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-2.userapi.com/impg/w2TlQKhKdt8tBfQEn52m9vORLKm0HBd5mZAkQA/g7XQPsTrnfc.jpg?size=536x693&quality=96&sign=2ec25cf78e05c0865c9573c39d3fde9e&type=album"),
                3,
                3
            ))




            for (item: Int in 4..20) {
                ad = Ad(
                    0,
                    "Test title  " + (0..100).random(),
                    "test",
                    "about this item test  " + (0..100).random().toString(),
                    (0..15).random(),
                    (0..10000).random().toDouble()/100,
                    Date(),
                    1
                )
                mUserViewModel.addAd(ad)


                for (i in 1..(2..4).random()) {
                    im = Image(
                        0,
                        getBitmap(images_list[(0..16).random()]),
                        item,
                        i
                    )
                    mUserViewModel.addImage(im)
                }
            }
        }
    }

    private suspend fun getBitmap(path: String): Bitmap {
        val loading: ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this).data(path).build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}