package com.lifex.loginapp.utils

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.lifex.loginapp.R
/**
Some Utils Used in project
*/
fun isUserNameValid(username: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(username).matches()
}

 fun isPasswordValid(password: String): Boolean {
    return password.length > 5
}

fun TextInputLayout.clearError() {
     this.error =null
}
fun showLoginSuccessDialog(activity: Activity, msg: String?) {
    val dialog = BottomSheetDialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.success_dialog)
    val name = dialog.findViewById<TextView>(R.id.name)
    name?.text = msg

    dialog.show()
}
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}