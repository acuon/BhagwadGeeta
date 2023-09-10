package com.example.bhagwadgeeta.utils

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.bhagwadgeeta.R
import com.google.android.material.snackbar.BaseTransientBottomBar

class GeetaSnackBar(
    parent: ViewGroup,
    content: CustomSnackBar
) : BaseTransientBottomBar<GeetaSnackBar>(parent, content, content) {


    fun showSnack(): GeetaSnackBar {
        show()
        return this
    }


    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {
        val snackModel = SnackBarDTO()
    }

    enum class SnackType {
        SUCCESS,
        FAILURE,
        VALIDATION
    }

    class Builder {

        private var iSnackListener: ISnackListener? = null

        interface ISnackListener {
            fun onClosed(view: View)
        }

        fun type(type: SnackType): Builder {
            snackModel.type = type
            return this
        }

        fun message(msg: String?): Builder {
            snackModel.message = msg
            return this
        }

        fun setCallBack(listener: ISnackListener): Builder {
            iSnackListener = listener
            return this
        }

        fun make(view: View): GeetaSnackBar {

            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.widget_geeta_snackbar,
                parent,
                false
            ) as CustomSnackBar


            customView.snackPic.setOnClickListener {
                iSnackListener?.onClosed(it)
            }

            val snackBarLay = customView.findViewById<ConstraintLayout>(R.id.appSnackbarLay)

            var pic: Int? = null
            var bgRes: Drawable? = null

            when (snackModel.type) {
                SnackType.SUCCESS -> {
                    pic = R.drawable.ic_snack_done
                    bgRes = ContextCompat.getDrawable(customView.context, R.drawable.snack_success)
                }

                SnackType.FAILURE -> {
                    pic = R.drawable.ic_snack_close
                    bgRes = ContextCompat.getDrawable(customView.context, R.drawable.snack_error)
                }

                SnackType.VALIDATION -> {
                    pic = R.drawable.ic_snack_info
                    bgRes =
                        ContextCompat.getDrawable(customView.context, R.drawable.snack_validation)
                }

                else -> {}
            }

            snackBarLay?.let { sLay ->
                pic?.let { sLay.findViewById<ImageView>(R.id.ivSnackPic)?.setImageResource(it) }
                bgRes?.let { sLay.background = it }
                snackModel.message?.let {
                    sLay.findViewById<AppCompatTextView>(R.id.tvSnackText).text = it
                }
            }

            return GeetaSnackBar(
                parent,
                customView
            )
        }

        fun showSnack(): Any {
            return this
        }

        fun dismiss() {

        }
    }

}

data class SnackBarDTO(
    var type: GeetaSnackBar.SnackType? = null,
    var message: String? = null
)