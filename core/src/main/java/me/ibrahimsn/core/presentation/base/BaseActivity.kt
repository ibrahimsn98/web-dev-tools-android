package me.ibrahimsn.core.presentation.base

import android.os.Bundle
import android.view.Menu
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutRes(): Int

    @MenuRes
    protected open fun menuRes(): Int? = null

    @StringRes
    protected open fun titleRes(): Int? = null

    protected open fun toolbar(): Toolbar? = null

    protected open fun showBackButton(): Boolean = false

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())

        toolbar()?.let { setSupportActionBar(it) }
        titleRes()?.let { setTitle(it) }

        supportActionBar?.setHomeButtonEnabled(showBackButton())
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton())

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuRes()?.let {
            menuInflater.inflate(it, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
}
