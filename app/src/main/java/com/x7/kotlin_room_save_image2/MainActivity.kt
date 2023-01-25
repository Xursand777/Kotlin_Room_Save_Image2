package com.x7.kotlin_room_save_image2

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.x7.kotlin_room_save_image2.databinding.ActivityMainBinding
import com.x7.kotlin_room_save_image2.room.AppDatabase
import com.x7.kotlin_room_save_image2.room.Constants.TABLE_NAME
import com.x7.kotlin_room_save_image2.room.User
import com.x7.kotlin_room_save_image2.room.UserDao
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var userDao:UserDao

    var arrayList=ArrayList<User>()
    var imageuri:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "$TABLE_NAME"
        ).allowMainThreadQueries().build()
        userDao=db.userDao()

        arrayList= userDao.getAllUsers() as ArrayList<User>

        binding.imageview1.setOnClickListener {
          openFileChooser()
        }

        binding.button1.setOnClickListener {
          var user=User(0,binding.edittext1.text.toString(),bitmapconverttoBytArray(uriconverttoBitmap(imageuri!!)))
            userDao.insertUser(user)
        }

      binding.recyclerview1.layoutManager=LinearLayoutManager(this@MainActivity)
      val roomAdapter=RoomAdapter(this@MainActivity,arrayList)
      binding.recyclerview1.adapter=roomAdapter

    }


    fun openFileChooser() {
        getContent.launch("image/*")
    }

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
        binding.imageview1.setImageURI(uri)
        imageuri=uri

    }

    fun uriconverttoBitmap(uri: Uri): Bitmap {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(this@MainActivity.contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(this@MainActivity.contentResolver, uri)
        }
        return bitmap
    }

    fun bitmapconverttoBytArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}