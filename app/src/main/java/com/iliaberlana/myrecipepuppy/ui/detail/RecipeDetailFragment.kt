package com.iliaberlana.myrecipepuppy.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.commons.logDebug
import kotlinx.android.synthetic.main.recipe_detail.view.*

class RecipeDetailFragment : Fragment() {
    private lateinit var linkRecipe: String

    companion object {
        const val RECIPE_LINK = "recipe_link"
        const val RECIPE_NAME = "recipe_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(RECIPE_LINK)) {
                linkRecipe = it.getString(RECIPE_LINK) ?: "NO_LINK"
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.recipe_detail, container, false)
        rootView.recipe_detail.text = linkRecipe
        return rootView
    }
}
