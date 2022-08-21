package br.com.zup.tana.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.zup.tana.R
import br.com.zup.tana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.setupWithNavController(navController)
        setNavControllerDestination()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Menu -> {
                    navController.navigate(R.id.menuFragment)
                    true
                }
                R.id.Favoritos -> {
                    navController.navigate(R.id.favoriteFragment)
                    true
                }
                R.id.Carrinho -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setNavControllerDestination() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.endFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }
}