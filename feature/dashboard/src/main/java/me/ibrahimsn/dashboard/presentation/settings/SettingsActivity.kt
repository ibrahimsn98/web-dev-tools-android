package me.ibrahimsn.dashboard.presentation.settings

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_home.*
import me.ibrahimsn.core.presentation.base.BaseActivity
import me.ibrahimsn.dashboard.R

class SettingsActivity : BaseActivity() {

    override fun layoutRes(): Int = R.layout.activity_settings

    override fun titleRes(): Int? = R.string.title_settings

    override fun toolbar(): Toolbar? = toolbar

    override fun showBackButton(): Boolean = true

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, SettingsFragment.newInstance())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
