package com.example.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.base.R
import com.example.base.databinding.ViewEmptyBinding

class ViewEmpty(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val viewBinding = ViewEmptyBinding.inflate(LayoutInflater.from(context), this, false)
    private var emptyImage: Int
    private val emptyContent: Int

    init {
        removeAllViews()
        addView(viewBinding.root)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewEmpty)
        emptyImage = typedArray.getResourceId(R.styleable.ViewEmpty_empty_image, 0)
        emptyContent = typedArray.getResourceId(R.styleable.ViewEmpty_empty_content, 0)
        typedArray.recycle()
        if (emptyImage != 0) {
            viewBinding.emptyImage.setImageResource(emptyImage)
        }
        if (emptyContent != 0) {
            viewBinding.emptyContent.setText(emptyContent)
        }
    }

}