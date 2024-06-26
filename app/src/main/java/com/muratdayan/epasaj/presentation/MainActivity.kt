package com.muratdayan.epasaj.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.databinding.ActivityMainBinding
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import com.muratdayan.epasaj.presentation.notification.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
    private val notificationViewModel : NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintLayoutMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.navView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, 0)
            insets
        }
        // Remote Config Firebaseden veri değişince toolbar title veya background colorunu değiştirir
        FirebaseApp.initializeApp(this)
        mainViewModel.appColor.observe(this) { color ->
            //binding.materialToolbar.setBackgroundColor(color)
        }
        mainViewModel.appTitle.observe(this) { title ->
            // binding.materialToolbar.title = title
        }

        // bildirimleri dinler ve toast olarak yazdırır
        notificationViewModel.notification.observe(this) { notification ->
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show()
        }

        // navhost ve toggle drawer layout işlevlerini yapar
        setSupportActionBar(binding.materialToolbar)
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragmentMain.id) as NavHostFragment
        navController = navHostFragment.findNavController()
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.materialToolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        // gidilen fragmentları dinler ve istenilen fragmentlarda toolbarın visibilitysini gone yapar
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.loginFragment, R.id.detailFragment, R.id.profileFragment,R.id.profileEditFragment,R.id.categoryProductsFragment -> {
                    binding.materialToolbar.visibility = View.GONE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                else -> {
                    binding.materialToolbar.visibility = View.VISIBLE
                    binding.navView.visibility = View.VISIBLE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
            binding.materialToolbar.title = getFragmentTitle(destination.id)
        }

        // drawer açıldıgında userin image ve name değerlerini drawerin header layoutuna yazdırır
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // Not needed
            }

            override fun onDrawerOpened(drawerView: View) {
                val sharedPrefManagerUserData = SharedPrefManager(this@MainActivity, "user_data")
                val userName = sharedPrefManagerUserData.getValue("user_name", "")
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                val userPhotoUrl = sharedPrefManagerUserData.getValue("user_photo_url", "")
                if (userName.isNotEmpty() && userPhotoUrl.isNotEmpty()) {
                    updateNavigationViewHeader(userName, userPhotoUrl)
                }
            }

            override fun onDrawerClosed(drawerView: View) {
                // Not needed
            }

            override fun onDrawerStateChanged(newState: Int) {
                // Not needed
            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // drawer layouttaki itemlere tıklanınca neler yapılacağını yönetir
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_menu_products -> {
                navController.navigate(R.id.productFragment)
            }

            R.id.drawer_menu_search -> {
                navController.navigate(R.id.searchFragment)
            }

            R.id.drawer_menu_categories -> {
                navController.navigate(R.id.categoriesFragment)
            }

            R.id.drawer_menu_likes -> {
                navController.navigate(R.id.likesFragment)
            }

            R.id.drawer_menu_orders -> {
                navController.navigate(R.id.ordersFragment)
            }

            R.id.drawer_menu_profile -> {
                navController.navigate(R.id.profileFragment)
            }

            R.id.drawer_menu_logout -> {
                showAlertDialog()
            }

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    // toolbarın options menü itemlerine tıklanınca neler yapılcağını yönetir
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_likes -> {
                navController.navigate(R.id.likesFragment)
                true
            }

            R.id.action_profile -> {
                navController.navigate(R.id.profileFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    // sharedden gelen verilere göre userin image ve name değerlerini header layouta yazdıran kod
    private fun updateNavigationViewHeader(userName: String?, userPhotoUrl: String?) {
        val headerView = binding.navView.getHeaderView(0)
        val headerImgView = headerView.findViewById<ImageView>(R.id.img_view_header_user_photo)
        val headerTxtView = headerView.findViewById<TextView>(R.id.txt_view_header_username)

        headerTxtView.text = userName ?: ""
        if (!userPhotoUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(userPhotoUrl)
                .into(headerImgView)
        } else {
            headerImgView.setImageResource(R.drawable.ic_guest)
        }
    }

    // logout işlemi için kullanıcıya alert dialog gösterir
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)

        // AlertDialog başlığı ve mesajı
        builder.setTitle("E-Pasaj")
        builder.setMessage("Do you want to log out?")

        // Pozitif buton (OK)
        builder.setPositiveButton("OK") { dialog, which ->
            val sharedPrefManagerUserData = SharedPrefManager(this, "user_data")
            val sharedPrefManagerRememberMe = SharedPrefManager(this, "remember_me")
            sharedPrefManagerRememberMe.deleteValue("remember_me")
            sharedPrefManagerUserData.deleteValue("user_id")
            sharedPrefManagerUserData.deleteValue("user_name")
            sharedPrefManagerUserData.deleteValue("user_photo_url")
            sharedPrefManagerUserData.deleteValue("user_token")
            navController.popBackStack(R.id.nav_graph,true)
            navController.navigate(R.id.loginFragment)
        }

        // Negatif buton (Cancel)
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        // AlertDialog nesnesini oluştur ve göster
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun getFragmentTitle(destinationId: Int): String {
        return when (destinationId) {
            R.id.productFragment -> "Products"
            R.id.searchFragment -> "Search"
            R.id.categoriesFragment -> "Categories"
            R.id.likesFragment -> "Likes"
            R.id.ordersFragment -> "Orders"
            else -> getString(R.string.app_name)
        }
    }



}