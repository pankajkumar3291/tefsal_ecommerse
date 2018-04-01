package com.tefsalkw.kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tefsalkw.R
import com.tefsalkw.activity.SigninActivity
import kotlinx.android.synthetic.main.activity_signup_thank_you.*

class SignupThankYouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_thank_you)


        supportActionBar?.setDisplayShowTitleEnabled(false)

        val email =   intent.getStringExtra("email")

        txtStatus.text = "We have sent an activation link to your email address "+email+ ". Please activate your account to login."

        buttonLogin.setOnClickListener {



            val intent = Intent(this, SigninActivity::class.java)
                    //.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                   // .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)


        }


    }
}
