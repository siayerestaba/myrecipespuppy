package com.iliaberlana.myrecipepuppy.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iliaberlana.myrecipepuppy.R

class RecipeDetailActivity : AppCompatActivity() {

    companion object {
        private const val RECIPE_LINK_KEY = "recipe_link_key"
        private const val RECIPE_NAME_KEY = "recipe_name_key"

        fun open(activity: Activity, recipeName: String, recipeLink: String) {
            val intent = Intent(activity, RecipeDetailActivity::class.java)
            intent.putExtra(RECIPE_NAME_KEY, recipeName)
            intent.putExtra(RECIPE_LINK_KEY, recipeLink)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent?.extras?.getString(RECIPE_NAME_KEY)
        supportActionBar?.title = name

        val link = intent?.extras?.getString(RECIPE_LINK_KEY)
        if (savedInstanceState == null) {
            val fragment = RecipeDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(RecipeDetailFragment.RECIPE_LINK, link)
                    putString(RecipeDetailFragment.RECIPE_NAME, link)
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.detail_frame, fragment)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
