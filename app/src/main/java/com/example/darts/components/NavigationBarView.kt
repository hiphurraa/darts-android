package com.example.darts.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.darts.R

class NavigationBarView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.navigation_bar_view, this, true)
    }
}