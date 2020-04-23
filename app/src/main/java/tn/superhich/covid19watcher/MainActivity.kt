package tn.superhich.covid19watcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import tn.superhich.covid19watcher.ui.dashboard.DashboardFragment
import tn.superhich.covid19watcher.ui.home.HomeFragment
import tn.superhich.covid19watcher.ui.notifications.NotificationsFragment


class MainActivity : AppCompatActivity() {

    private var homeFragment: HomeFragment = HomeFragment()
    private var dashboardFragment: DashboardFragment = DashboardFragment()
    private var notificationsFragment: NotificationsFragment = NotificationsFragment()
    private var active : Fragment = homeFragment


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().hide(active).show(homeFragment).commit()
                active = homeFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction().hide(active).show(notificationsFragment).commit()
                active = notificationsFragment
                return@OnNavigationItemSelectedListener true

            }
            R.id.navigation_notifications -> {
                supportFragmentManager.beginTransaction().hide(active).show(dashboardFragment).commit()
                active = dashboardFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        with(supportFragmentManager.beginTransaction()) {
            add(R.id.nav_host_fragment, notificationsFragment, getString(R.string.title_notifications)).hide(notificationsFragment)
            add(R.id.nav_host_fragment, dashboardFragment, getString(R.string.title_dashboard)).hide(dashboardFragment)
            add(R.id.nav_host_fragment, homeFragment, getString(R.string.title_home)).commit()
        }

    }
}
